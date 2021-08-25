package org.techtown.catsby.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.techtown.catsby.R;
import org.techtown.catsby.Writemain;
import org.techtown.catsby.home.adapter.BowlAdapter;
import org.techtown.catsby.home.adapter.FeedAdapter;
import org.techtown.catsby.home.model.Bowl;
import org.techtown.catsby.home.model.Feed;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlList;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.BowlService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.time.LocalDateTime.now;

public class FragmentHome extends Fragment implements BowlAdapter.BowlAdapterClickListener {
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    ArrayList<Bowl> bowlList= new ArrayList<>();
    final BowlAdapter bowlAdapter = new BowlAdapter(bowlList);

    ArrayList<byte[]> bowlImageArray = new ArrayList<>();
    int[] bowlImg = {R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_star_border_24, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    public static ArrayList<String> bowlCommunityContext = new ArrayList<>();
    public static ArrayList<Integer> bowlCommunityId = new ArrayList<>();
    public static ArrayList<String> bowlCommunityUser= new ArrayList<>();
    public static ArrayList<Integer> bowlCommunityUserId = new ArrayList<>();
    public static ArrayList<byte[]> bowlCommunityImage = new ArrayList<>();
    public static ArrayList<List<BowlComment>> bowlCommunityComment = new ArrayList<List<BowlComment>>();
    ArrayList<Long> tempCommunityId = new ArrayList<>();
    BowlService bowlService = RetrofitClient.getBowlService();
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (user != null) {
            loadBowls(user.getUid());
        }

        bowlAdapter.setOnClickListener(this);
        return view;

    }

    private void loadCommunity(int bowlId) {

        bowlCommunityService.getCommunitiesByBowl(bowlId).enqueue(new Callback<List<BowlCommunity>>() {
            @Override
            public void onResponse(Call<List<BowlCommunity>> call, Response<List<BowlCommunity>> response) {
                if(response.isSuccessful()) {
                    List<BowlCommunity> BowlCommunityResult = response.body();

                    for(int i = 0; i < BowlCommunityResult.size(); i++){
                        tempCommunityId.add((long) BowlCommunityResult.get(i).getId());
                    }

                    for(int i =0; i < BowlCommunityResult.size(); i++){
                        bowlCommunityContext.add(BowlCommunityResult.get(i).getContent());
                        bowlCommunityId.add(BowlCommunityResult.get(i).getId());
                        bowlCommunityUser.add(BowlCommunityResult.get(i).getUser().getNickname());
                        bowlCommunityUserId.add(BowlCommunityResult.get(i).getUser().getId());

                        byte[] blob = Base64.decode(BowlCommunityResult.get(i).getImage(), Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(blob,0, blob.length);
                        bowlCommunityImage.add(BowlCommunityResult.get(i).getImage().getBytes());
                    }

                    for(int i = 0; i < BowlCommunityResult.size(); i++){
                        loadComments((long) BowlCommunityResult.get(i).getId());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<BowlCommunity>> call, Throwable t) {
                System.out.println("t.getMessage() loadCommunity = " + t.getMessage());
            }
        });
    }



    private void loadComments(long communityId) {
        ArrayList<Feed> feedList = new ArrayList<>();

        bowlCommunityService.getComments(communityId).enqueue(new Callback<List<BowlComment>>() {
            @Override
            public void onResponse(Call<List<BowlComment>> call, Response<List<BowlComment>> response) {
                if(response.isSuccessful()){
                    List<BowlComment> bowlComments = response.body();
                    if (bowlComments.size() > 0){
                        bowlCommunityComment.add(bowlComments);
                    }
                    else{
                        bowlCommunityComment.add(new ArrayList<>());
                    }
                }

                for (int i = 0; i< bowlCommunityId.size(); i++) {

                    try{
                        Feed feed = new Feed(bowlCommunityId.get(i), bowlImg[i], bowlCommunityUserId.get(i), bowlCommunityUser.get(i), bowlCommunityImage.get(i), bowlCommunityContext.get(i), bowlCommunityComment.get(i));
                        feedList.add(feed);

                        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
                        RecyclerView.LayoutManager feedLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(feedLayoutManager);
                        FeedAdapter feedAdapter = new FeedAdapter(feedList);
                        recyclerView.setAdapter(feedAdapter);
                    }
                    catch(Exception e){
                        System.out.println("e.getMessage()  = " + e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<BowlComment>> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
            }
        });

    }


    private void loadBowls(String uid) {
        bowlService.getBowls(uid).enqueue(new Callback<BowlList>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<BowlList> call, Response<BowlList> response) {
                if(response.isSuccessful()) {
                    BowlList result = response.body();
                    HashSet<Integer> bowlUniId = new HashSet<Integer>();

                    for(int i =0; i < result.size(); i++){
                        Bowl bowl = new Bowl(result.getBowls().get(i).getBowl_id(), bowlImg[i] , result.getBowls().get(i).getName(), result.getBowls().get(i).getInfo(), result.getBowls().get(i).getAddress(), result.getBowls().get(i).getUpdated_time());

                        loadCommunity(result.getBowls().get(i).getBowl_id());

                        bowlList.add(bowl);
                        bowlUniId.add(result.getBowls().get(i).getBowl_id());
                    }

                    RecyclerView bowlRecyclerView = (RecyclerView)view.findViewById(R.id.horizontal_recyclerview);
                    RecyclerView.LayoutManager bowlLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    bowlRecyclerView.setLayoutManager(bowlLayoutManager);
                    bowlRecyclerView.setAdapter(bowlAdapter);
                }

            }

            @Override
            public void onFailure(Call<BowlList> call, Throwable t) {
                System.out.println("t.getMessage() loadBowls= " + t.getMessage());
            }
        });


    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getActivity(), "Item : "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_write, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_write:
                Intent intent = new Intent(getActivity(), Writemain.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}