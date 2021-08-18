package org.techtown.catsby.home;

import android.content.Intent;
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
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlList;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.BowlService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.like.LikeButton;
public class FragmentHome extends Fragment implements BowlAdapter.BowlAdapterClickListener {

    ArrayList<Bowl> bowlList= new ArrayList<>();
    ArrayList<Feed> feedList = new ArrayList<>();

    int[] bowlImg = {R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_star_border_24, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    int[] feedImg = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    //final BowlAdapter bowlAdapter;
    final BowlAdapter bowlAdapter = new BowlAdapter(bowlList);
    final FeedAdapter feedAdapter = new FeedAdapter(feedList);
    ArrayList<String> bowlNameArray = new ArrayList<>();
    ArrayList<byte[]> bowlImageArray = new ArrayList<>();

    ArrayList<String> bowlCommunityContext = new ArrayList<>();
    ArrayList<Integer> bowlCommunityId = new ArrayList<>();
    ArrayList<String> bowlCommunityUser= new ArrayList<>();

    BowlService bowlService = RetrofitClient.getBowlService();
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //BowlCommunityService bowlCommunityService;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

        if (user != null) {

            loadBowls(user.getUid());
            loadCommunity(user.getUid());
        }

        bowlAdapter.setOnClickListener(this);
        return view;
        //LikeButton likeButton = view.findViewById(R.id.likeButton);
    }

    private void loadCommunity(String uid) {
        bowlCommunityService.getCommunities(uid).enqueue(new Callback<List<BowlCommunity>>() {
            @Override
            public void onResponse(Call<List<BowlCommunity>> call, Response<List<BowlCommunity>> response) {
                if(response.isSuccessful()) {
                    List<BowlCommunity> BowlCommunityResult = response.body();
                    System.out.println("BowlCommunityResult = " + BowlCommunityResult.getClass());

                    for (int i = 0; i < BowlCommunityResult.size(); i++){
                        System.out.println(BowlCommunityResult.get(i).getUser());
                    }

                    System.out.println("BowlCommunityResult = " + BowlCommunityResult.get(0).getUser());

                    for(int i = 0; i < BowlCommunityResult.size(); i++){

                        /*
                        * 좋아요 누른 사람 수 get
                        * Long likeCnt = loadLike((long) BowlCommunityResult.get(i).getId());
                        * */

                        bowlCommunityContext.add(BowlCommunityResult.get(i).getContent());
                        bowlCommunityId.add(BowlCommunityResult.get(i).getId());
                        bowlCommunityUser.add(BowlCommunityResult.get(i).getUser().getNickname());

                    }

                    for (int i = 0; i< BowlCommunityResult.size(); i++) {
                        Feed feed = new Feed(bowlCommunityId.get(i) ,bowlImg[0], bowlCommunityUser.get(i), feedImg[0], bowlCommunityContext.get(i));
                        feedList.add(feed);
                    }
                }

                RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
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

    private Long loadLike(Long communityId) {
        final Long[] likeCnt = new Long[1];
        bowlCommunityService.getLikes(communityId).enqueue(new Callback<Long>(){
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if(response.isSuccessful()) {
                    likeCnt[0] = response.body();
                }
            }
            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                System.out.println("t.getMessage() loadlike = " + t.getMessage());
            }
        });
        return likeCnt[0];
    }


    private void loadBowls(String uid) {
        bowlService.getBowls(uid).enqueue(new Callback<BowlList>() {
            @Override
            public void onResponse(Call<BowlList> call, Response<BowlList> response) {
                if(response.isSuccessful()) {
                    BowlList result = response.body();

                    for(int i =0; i < result.size(); i++){
                        bowlNameArray.add(result.getBowls().get(i).getName());
                        //Bowl Bowl = new Bowl(result.getBowls().get(i).getImage(), result.getBowls().get(i).getName());
                        Bowl bowl = new Bowl(bowlImg[i] , result.getBowls().get(i).getName(), result.getBowls().get(i).getInfo(), result.getBowls().get(i).getAddress(), result.getBowls().get(i).getUpdated_time());
                        bowlList.add(bowl);
                        System.out.println("result = " + result.getBowls().get(i).getName());
                    }

                    /*
                    for (int i = 0; i< bowlNameArray.size(); i++) {
                        Bowl bowl = new Bowl(bowlImageArray.get(i), bowlNameArray.get(i));
                        bowlList.add(bowl);
                    }*/

                    for (int i = 0; i< result.size(); i++) {
                        //Bowl bowl = new Bowl(bowlImg[i], bowlNameArray.get(i));
                        //bowlList.add(bowl);
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
