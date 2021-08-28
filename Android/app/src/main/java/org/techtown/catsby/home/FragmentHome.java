package org.techtown.catsby.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import org.techtown.catsby.qrcode.LoadingActivity;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlList;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.BowlService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;

import static java.time.LocalDateTime.now;

public class FragmentHome extends Fragment implements BowlAdapter.BowlAdapterClickListener {
    private Context mContext;
    private FragmentManager fragmentManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    BowlAdapter bowlAdapter;
    ArrayList<byte[]> bowlImageArray = new ArrayList<>();

    int[] bowlImg = {R.drawable.fish, R.drawable.cutecat, R.drawable.flowercat, R.drawable.fish, R.drawable.cutecat, R.drawable.cutecat, R.drawable.cutecat, R.drawable.cutecat, R.drawable.cutecat, R.drawable.cutecat, R.drawable.cutecat};

    BowlService bowlService = RetrofitClient.getBowlService();
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    View view;
    Intent intent;
    ArrayList<Feed> feedDeepList;
    ArrayList<Bowl> bowlList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_home, container, false);
            setHasOptionsMenu(true);
            super.onCreate(savedInstanceState);

            if (user != null) {
                bowlAdapter = new BowlAdapter(bowlList);
                Call<BowlList> call = bowlService.getBowls(user.getUid());

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BowlList result = call.execute().body();
                            for(int i =0; i < result.size(); i++){
                                Bowl bowl = new Bowl(result.getBowls().get(i).getBowl_id(), bowlImg[i] , result.getBowls().get(i).getName(), result.getBowls().get(i).getInfo(), result.getBowls().get(i).getAddress(), result.getBowls().get(i).getUpdated_time());
                                bowlList.add(bowl);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        RecyclerView bowlRecyclerView = (RecyclerView)view.findViewById(R.id.horizontal_recyclerview);
                                        RecyclerView.LayoutManager bowlLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                        bowlRecyclerView.setLayoutManager(bowlLayoutManager);
                                        bowlRecyclerView.setAdapter(bowlAdapter);
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
            ArrayList<Feed> feedList= new ArrayList<>();
            for (int i =0; i < bowlList.size(); i++) {
                Call<List<BowlCommunity>> call1 = bowlCommunityService.getCommunitiesByBowl(bowlList.get(i).getId());
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<BowlCommunity> BowlCommunityResult = call1.execute().body();

                            for (int i = 0; i < BowlCommunityResult.size(); i++) {
                                try{
                                    Feed feed = new Feed(BowlCommunityResult.get(i).getId(), bowlImg[i], BowlCommunityResult.get(i).getUser().getId(), BowlCommunityResult.get(i).getUser().getNickname(), BowlCommunityResult.get(i).getImage().getBytes(), BowlCommunityResult.get(i).getContent());
                                    feedList.add(feed);
                                }
                                catch (Exception e){
                                    System.out.println("e.getMessage() = " + e.getMessage());
                                }
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
            RecyclerView.LayoutManager feedLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(feedLayoutManager);
            FeedAdapter feedAdapter = new FeedAdapter(feedList);
            recyclerView.setAdapter(feedAdapter);
            bowlAdapter.setOnClickListener(this);
        }
        else{
            view.refreshDrawableState();
        }
        return view;
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getActivity(), "Item : "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_write, menu);
        inflater.inflate(R.menu.actionbar_qrscan, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_write:
                Intent intent = new Intent(getActivity(), Writemain.class);
                startActivity(intent);
                break;

            case R.id.action_qrscan:
                Intent intent2 = new Intent(getActivity(), LoadingActivity.class);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }
}
