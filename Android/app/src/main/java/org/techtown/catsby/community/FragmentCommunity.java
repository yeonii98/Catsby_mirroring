package org.techtown.catsby.community;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.dto.TownCommunity;
//import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.service.MyWritingService;
import org.techtown.catsby.retrofit.service.TownCommunityService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCommunity extends Fragment implements View.OnClickListener {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    private FragmentActivity myContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, container, false);
        super.onCreate(savedInstanceState);

        SearchView searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        noticeListView = (ListView) view.findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();


        final Button btn2 = (Button) view.findViewById(R.id.btn2);
        final Button btn3 = (Button) view.findViewById(R.id.btn3);
        final Button btn4 = (Button) view.findViewById(R.id.btn4);
        final LinearLayout notice = (LinearLayout) view.findViewById(R.id.notice);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TownCommunityService service1 = retrofit.create(TownCommunityService.class);

        Call<List<TownCommunity>> call = service1.getTownList();

        call.enqueue(new Callback<List<TownCommunity>>() {
            @Override
            public void onResponse(Call<List<TownCommunity>> call, Response<List<TownCommunity>> response) {
                if(response.isSuccessful()){
                    //정상적으로 통신이 성공된 경우
                    List<TownCommunity> result = response.body();
                    for(int i =0; i < result.size(); i++){
                        noticeList.add(new Notice(result.get(i).getTitle(),result.get(i).getUser().getNickname(), result.get(i).getDate()));
                        adapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList);
                        noticeListView.setAdapter(adapter);
                    }
                } else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<List<TownCommunity>> call, Throwable t) {
                System.out.println("통신 실패!");
            }
        });
//        noticeList.add(new Notice("고양이가 아픈데 병원 추천 부탁드려요ㅠㅠ", "익명", "2021-04-05"));
//        noticeList.add(new Notice("냥이 츄르 같이 사실 분 계신가요~?", "연지니", "2021-04-03"));
//        noticeList.add(new Notice("(사진)", "냥집사", "2021-04-02"));

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

    }
