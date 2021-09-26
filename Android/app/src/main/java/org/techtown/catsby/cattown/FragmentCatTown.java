package org.techtown.catsby.cattown;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.cattown.adapter.FragmentCatTownAdapter;
import org.techtown.catsby.cattown.addCat.AddCatActivity;
import org.techtown.catsby.cattown.model.Cat;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.CatProfile;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.service.CatService;
import org.techtown.catsby.retrofit.service.UserService;

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
    private String linkid = "";
    private long lastTimeBackPressed;
    private TextView tvcatgen;
    private TextView tvcatloc;
    private String text1;
    private String text2;
    private UserService userService = RetrofitClient.getUser();
    private CatService catService = RetrofitClient.catService();
    String uid = FirebaseAuth.getInstance().getUid();
    int addressExist = 1;
    List<Cat> catlist;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_cattown, container, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyceler_view);
        catList = new ArrayList<>();

        adapter = new FragmentCatTownAdapter(catList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));

        tvcatgen = (TextView)view.findViewById(R.id.towncatgen);
        tvcatloc = (TextView)view.findViewById(R.id.towncatloc);

        catlist = new ArrayList<>();

        userService.getUser(uid).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User result = response.body();
                    String userAddress = result.getAddress();

                     if (userAddress != null) {
                        addressExist = 1;
                        Call<List<CatProfile>> call2 = catService.getCatProfileList();
                        call2.enqueue(new Callback<List<CatProfile>>() {
                            @Override
                            public void onResponse(Call<List<CatProfile>> call2, Response<List<CatProfile>> response) {
                                if(response.isSuccessful()){
                                    List<CatProfile> result = response.body();

                                    for(int i=0; i<result.size(); i++) {

                                        String address_for = result.get(i).getUser_add();
                                        System.out.println(address_for);
                                        if(!userAddress.equals(address_for)) continue;
//                                        long a = result.get(i).getUserid();
//                                        System.out.println("캣유저테스트"+a);

                                        //System.out.println("캣유저테스트"+a);
                                        //리스트에 표시될 이미지
                                        if(result.get(i).getImage() != null){
                                            bm = makeBitMap(result.get(i).getImage());}
                                        else{
                                            bm = null;}

                                        //리스트에 표시될 성별
                                        String text1;
                                        if(result.get(i).getGender()==1){
                                            text1 = "암컷";
                                        } else if(result.get(i).getGender()==2) {
                                            text1 = "수컷";
                                        } else {
                                            text1 = "성별 모름";
                                        }
                                        //tvcatgen.setText(text1);

                                        //리스트에 표시될 발견 지역
                                        String text2;
                                        text2 = result.get(i).getAddress();
                                        //tvcatloc.setText(text2);

                                        //매핑을 위한 고양이 아이디
                                        linkid = Integer.toString(result.get(i).getCatId());

                                        Cat cat = new Cat(result.get(i).getUserid(), result.get(i).getUser_add(),result.get(i).getCatName(), bm, linkid,text2, text1,0);

                                        System.out.println(linkid);
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
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        adapter.notifyDataSetChanged();
        return view;

    }

            private Bitmap bm=null;

    public Bitmap makeBitMap(String s){
        int idx = s.indexOf("=");
        byte[] b = binaryStringToByteArray(s.substring(idx+1));
        Bitmap bm = BitmapFactory.decodeByteArray(b,0,b.length);
        return bm;
    }

    public byte[] binaryStringToByteArray(String s){
        int count=s.length()/8;
        byte[] b=new byte[count];
        for(int i=1; i<count; ++i){
            String t=s.substring((i-1)*8, i*8);
            b[i-1]=binaryStringToByte(t);
        }
        return b;
    }

    public byte binaryStringToByte(String s){
        byte ret=0, total=0;
        for(int i=0; i<8; ++i){
            ret = (s.charAt(7-i)=='1') ? (byte)(1 << i) : 0;
            total = (byte) (ret|total);
        }
        return total;
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
