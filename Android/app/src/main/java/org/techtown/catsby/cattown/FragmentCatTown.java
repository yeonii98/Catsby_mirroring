package org.techtown.catsby.cattown;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.catsby.R;
import org.techtown.catsby.cattown.adapter.FragmentCatTownAdapter;
import org.techtown.catsby.cattown.addCat.AddCatActivity;
import org.techtown.catsby.cattown.model.Cat;
import org.techtown.catsby.retrofit.dto.CatProfile;
import org.techtown.catsby.retrofit.service.CatService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCatTown extends Fragment {
    RecyclerView recyclerView;
    FragmentCatTownAdapter adapter;
    ArrayList<Cat> catList;
    private int catpicture;
    private String name;
    private int helppeople;

    List<Cat> catlist;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cattown, container, false);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

        catlist = new ArrayList<>();

        //붙여넣기 시작

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatService catService1 = retrofit.create(CatService.class);

        Call<List<CatProfile>> call = catService1.getCatProfileList();

        call.enqueue(new Callback<List<CatProfile>>() {

            @Override
            public void onResponse(Call<List<CatProfile>> call, Response<List<CatProfile>> response) {
                if(response.isSuccessful()){
                    List<CatProfile> result = response.body();
                    for(int i=0; i<result.size(); i++) {
                        //result.get(i).getImage()
                        Cat cat = new Cat(result.get(i).getCatName(), null,0);
                        adapter.addItem(cat);
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<List<CatProfile>> call, Throwable t) {
                System.out.println("통신 실패");
            }
            });

/*
        private void addItem(int picture, String catName, int helper) {
            Cat cat = new Cat();
            cat.setCatPicture(picture);
            cat.setName(catName);
            cat.setHelpPeople(helper);
            catList.add(cat);
        }

 */


        recyclerView = (RecyclerView)view.findViewById(R.id.recyceler_view);
        catList = new ArrayList<>();

        adapter = new FragmentCatTownAdapter(catList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        catpicture = R.drawable.pic_001;
        name = "Happy";
        helppeople = 2;

        /*
        addItem(catpicture, name + "  1", helppeople);
        addItem(catpicture, name+ "  2", helppeople);
        addItem(catpicture, name + "  3", helppeople);
        addItem(catpicture, name + "  4", helppeople);
        addItem(catpicture, name + "  5", helppeople);
        addItem(catpicture, name + "  6", helppeople);
        addItem(catpicture, name + "  7", helppeople);
        addItem(catpicture, name + "  8", helppeople);
        addItem(catpicture, name + "  9", helppeople);
        addItem(catpicture, name + "  6", helppeople);
        addItem(catpicture, name + "  7", helppeople);
        addItem(catpicture, name + "  8", helppeople);
        addItem(catpicture, name + "  9", helppeople);
        addItem(catpicture, name + "  6", helppeople);
        addItem(catpicture, name + "  7", helppeople);
        addItem(catpicture, name + "  8", helppeople);
        addItem(catpicture, name + "  9", helppeople);

         */
        adapter.notifyDataSetChanged();
        return view;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_our_cat_town, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_cat:
                Intent intent = new Intent(getActivity(), AddCatActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item) ;
        }
    }


}
