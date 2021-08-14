package org.techtown.catsby.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlAdapter;
import org.techtown.catsby.home.adapter.FeedAdapter;
import org.techtown.catsby.home.model.Feed;
import org.techtown.catsby.retrofit.dto.Bowl;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.BowlService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentHome extends Fragment implements BowlAdapter.BowlAdapterClickListener {

    ArrayList<Bowl> bowlList = new ArrayList<>();
    ArrayList<Feed> feedList = new ArrayList<>();

    int[] bowlImg = {R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_star_border_24, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    String[] userName = {"익명1", "익명2"};
    int[] feedImg = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    //String[] feedContent = {"글내용1", "글내용2"};

    final BowlAdapter bowlAdapter = new BowlAdapter(bowlList);
    final FeedAdapter feedAdapter = new FeedAdapter(feedList);
    ArrayList<String> bowlArray = new ArrayList<>();
    ArrayList<String> bowlCommunityArray = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://15.164.36.183:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BowlService bowlService = retrofit.create(BowlService.class);
        Call<List<org.techtown.catsby.retrofit.dto.Bowl>> call = bowlService.getBowls();

        call.enqueue(new Callback<List<Bowl>>() {
            @Override
            public void onResponse(Call<List<Bowl>> call, Response<List<Bowl>> response) {
                if(response.isSuccessful()){

                    List<Bowl> result = response.body();

                    for(int i = 0; i < result.size(); i++){
                        bowlArray.add(result.get(i).getName());

                        FragmentBowlInfo fragmentBowlInfo = new FragmentBowlInfo();
                        Bundle bundle = new Bundle();
                        bundle.putString("name",result.get(i).getName());
                        fragmentBowlInfo.setArguments(bundle);

                    }

                    for (int i = 0; i< bowlImg.length; i++) {
                        Bowl bowl = new Bowl(bowlImg[i], bowlArray.get(i));
                        bowlList.add(bowl);
                    }

                    RecyclerView bowlRecyclerView = (RecyclerView)view.findViewById(R.id.horizontal_recyclerview);

                    RecyclerView.LayoutManager bowlLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    bowlRecyclerView.setLayoutManager(bowlLayoutManager);
                    bowlRecyclerView.setAdapter(bowlAdapter);

                } else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<List<Bowl>> call, Throwable t) {

            }


        });

        BowlCommunityService bowlCommunityService = retrofit.create(BowlCommunityService.class);
        Call<List<BowlCommunity>> callBowlCommunity = bowlCommunityService.getCommunities();

        callBowlCommunity.enqueue(new Callback<List<BowlCommunity>>() {
            @Override
            public void onResponse(Call<List<BowlCommunity>> call, Response<List<BowlCommunity>> response) {
                if(response.isSuccessful()){

                    List<BowlCommunity> BowlCommunityResult = response.body();
                    for(int i = 0; i < BowlCommunityResult.size(); i++){
                        bowlCommunityArray.add(BowlCommunityResult.get(i).getContent());
                    }

                    for (int i = 0; i< feedImg.length; i++) {
                        //Bowl bowl = new Bowl(bowlImg[i], bowlArray.get(i));
                        Feed feed = new Feed(bowlImg[0], userName[0], feedImg[0], bowlCommunityArray.get(i));
                        feedList.add(feed);

                    }

                    /*
                    RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);

                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));

                    RecyclerView.LayoutManager feedLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(feedLayoutManager);
                    recyclerView.setAdapter(feedAdapter);*/



                } else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<List<BowlCommunity>> call, Throwable t) {

            }
        });



        bowlAdapter.setOnClickListener(this);
        return view;
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getActivity(), "Item : "+position, Toast.LENGTH_SHORT).show();
    }
}
