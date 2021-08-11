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

import org.techtown.catsby.R;
import org.techtown.catsby.cattown.addCat.AddCatActivity;
import org.techtown.catsby.community.AddActivity;
import org.techtown.catsby.home.adapter.BowlAdapter;
import org.techtown.catsby.home.adapter.FeedAdapter;
import org.techtown.catsby.home.model.Bowl;
import org.techtown.catsby.home.model.Feed;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentHome extends Fragment implements BowlAdapter.BowlAdapterClickListener {

    ArrayList<Bowl> bowlList = new ArrayList<>();
    ArrayList<Feed> feedList = new ArrayList<>();

    int[] bowlImg = {R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_star_border_24, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    String[] bowlName = {"밥그릇1", "밥그릇2", "밥그릇3", "밥그릇4", "밥그릇5"};

    String[] userName = {"익명1", "익명2"};
    int[] feedImg = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    String[] feedContent = {"글내용1", "글내용2"};

    final BowlAdapter bowlAdapter = new BowlAdapter(bowlList);
    final FeedAdapter feedAdapter = new FeedAdapter(feedList);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

        for (int i = 0; i< bowlImg.length; i++) {
            Bowl bowl = new Bowl(bowlImg[i], bowlName[i]);
            bowlList.add(bowl);
        }

        RecyclerView bowlRecyclerView = (RecyclerView)view.findViewById(R.id.horizontal_recyclerview);

        RecyclerView.LayoutManager bowlLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bowlRecyclerView.setLayoutManager(bowlLayoutManager);

        bowlRecyclerView.setAdapter(bowlAdapter);
        bowlAdapter.setOnClickListener(this);

        Feed feed1 = new Feed(bowlImg[0], userName[0], feedImg[0], feedContent[0]);
        Feed feed2 = new Feed(bowlImg[1], userName[1], feedImg[1], feedContent[1]);

        feedList.add(feed1);
        feedList.add(feed2);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));

        RecyclerView.LayoutManager feedLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(feedLayoutManager);
        recyclerView.setAdapter(feedAdapter);

        return view;
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
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item) ;
        }
    }

}
