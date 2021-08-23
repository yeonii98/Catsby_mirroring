package org.techtown.catsby.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.techtown.catsby.Bowladd;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    ArrayList<Feed> feedList = new ArrayList<>();

    int[] bowlImg = {R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_star_border_24, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    int[] feedImg = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    final BowlAdapter bowlAdapter = new BowlAdapter(bowlList);
    final FeedAdapter feedAdapter = new FeedAdapter(feedList);
    ArrayList<byte[]> bowlImageArray = new ArrayList<>();

    public static ArrayList<String> bowlCommunityContext = new ArrayList<>();
    public static ArrayList<Integer> bowlCommunityId = new ArrayList<>();
    public static ArrayList<String> bowlCommunityUser= new ArrayList<>();
    public static ArrayList<Integer> bowlCommunityUserId = new ArrayList<>();
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
            loadCommunity(user.getUid());
            loadBowls(user.getUid());
        }

        bowlAdapter.setOnClickListener(this);
        return view;

    }

    private void loadCommunity(String uid) {
        bowlCommunityService.getCommunities(uid).enqueue(new Callback<List<BowlCommunity>>() {
            @Override
            public void onResponse(Call<List<BowlCommunity>> call, Response<List<BowlCommunity>> response) {
                if(response.isSuccessful()) {
                    List<BowlCommunity> BowlCommunityResult = response.body();

                    for(int i = 0; i < BowlCommunityResult.size(); i++){
                        tempCommunityId.add((long) BowlCommunityResult.get(i).getId());
                        loadComments((long) BowlCommunityResult.get(i).getId());
                    }

                    for(int i =0; i < BowlCommunityResult.size(); i++){
                        bowlCommunityContext.add(BowlCommunityResult.get(i).getContent());
                        bowlCommunityId.add(BowlCommunityResult.get(i).getId());
                        bowlCommunityUser.add(BowlCommunityResult.get(i).getUser().getNickname());
                        bowlCommunityUserId.add(BowlCommunityResult.get(i).getUser().getId());
                    }

                    if (BowlCommunityResult.size() == 0) {
                        bowlCommunityContext.add("첫 번쩨 글을 업로드 해주세요 :)");
                        bowlCommunityId.add(0);
                        bowlCommunityUser.add("관리자");
                        bowlCommunityUserId.add(0);
                    }
                }
                RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
                recyclerView.addItemDecoration(new DividerItemDecoration(mContext, 1));
                RecyclerView.LayoutManager feedLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(feedLayoutManager);
                recyclerView.setAdapter(feedAdapter);
            }

            @Override
            public void onFailure(Call<List<BowlCommunity>> call, Throwable t) {
                System.out.println("t.getMessage() loadCommunity = " + t.getMessage());
            }
        });
    }



    private void loadComments(long communityId) {
        bowlCommunityService.getComments(communityId).enqueue(new Callback<List<BowlComment>>() {
            @Override
            public void onResponse(Call<List<BowlComment>> call, Response<List<BowlComment>> response) {
                if(response.isSuccessful()){
                    List<BowlComment> bowlComments = response.body();
                    bowlCommunityComment.add(bowlComments);
                }

                for (int i = 0; i< bowlCommunityId.size(); i++) {
                    Feed feed = new Feed(bowlCommunityId.get(i) ,bowlImg[0], bowlCommunityUserId.get(i), bowlCommunityUser.get(i), feedImg[0], bowlCommunityContext.get(i), bowlCommunityComment);
                    feedList.add(feed);
                }

                RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
                RecyclerView.LayoutManager feedLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(feedLayoutManager);
                recyclerView.setAdapter(feedAdapter);
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

                    for(int i =0; i < result.size(); i++){
                        Bowl bowl = new Bowl(result.getBowls().get(i).getBowl_id(), bowlImg[i] , result.getBowls().get(i).getName(), result.getBowls().get(i).getInfo(), result.getBowls().get(i).getAddress(), result.getBowls().get(i).getUpdated_time());
                        bowlList.add(bowl);
                    }

                    if (result.size() == 0){
                        Bowl bowl = new Bowl(0, bowlImg[0] , "관리자" , "관리자", "첫 번째 밥그릇을 업로드 해보세요 :)", now());
                        bowlList.add(bowl);
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
        inflater.inflate(R.menu.actionbar_addbowl, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_write:
                Intent intent = new Intent(getActivity(), Writemain.class);
                startActivity(intent);
                break;

            case R.id.addbowl:
                Intent intent2 = new Intent(getActivity(), Bowladd.class);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
