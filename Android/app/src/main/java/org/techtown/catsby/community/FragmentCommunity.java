package org.techtown.catsby.community;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.techtown.catsby.R;
import org.techtown.catsby.community.data.model.TownLike;
import org.techtown.catsby.community.data.service.TownLikeService;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.community.data.model.TownComment;
import org.techtown.catsby.community.data.model.TownCommunity;
import org.techtown.catsby.community.data.service.TownCommentService;
import org.techtown.catsby.community.data.service.TownCommunityService;
import org.techtown.catsby.setting.MaincommentActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCommunity extends Fragment {
    private View view;
    private Button btnAdd;
    private String result;
    private RecyclerView recyclerView;
    public RecyclerAdapter recyclerAdapter;
    private TownCommunityService townCommunityService;
    private TownCommentService townCommentService;
    private TownLikeService townLikeService;
    private int index = 0;
    private Bitmap bm = null;
    private String nickName;
    String uid = FirebaseAuth.getInstance().getUid();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    List<Memo> memoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_community, container, false);

        memoList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView = view.findViewById(R.id.recyclerview);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setLayoutManager(layoutManager);

        //레트로핏
        townCommunityService = RetrofitClient.getTownCommunityService();

        townCommunityService.getTownList().enqueue(new Callback<List<TownCommunity>>() {
            @Override
            public void onResponse(Call<List<TownCommunity>> call, Response<List<TownCommunity>> response) {
                if(response.isSuccessful()){
                    //정상적으로 통신이 성공된 경우
                    List<TownCommunity> result = response.body();
//                    index = result.get(result.size() - 1).getId();

                    for(int i = 0; i < result.size(); i++){
                        if(result.get(i).getImage() != null)
                            bm = makeBitMap(result.get(i).getImage());
                        else
                            bm = null;

                        if(result.get(i).isAnonymous())
                            nickName = "익명";
                        else
                            nickName = result.get(i).getUser().getNickname();

                        Memo memo = new Memo(result.get(i).getId(),result.get(i).getUser().getUid(),
                                result.get(i).getTitle(),result.get(i).getContent(),
                                nickName,result.get(i).getDate(),bm);
                        recyclerAdapter.addItem(memo);
                    }
                    recyclerAdapter.notifyDataSetChanged();

                } else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<List<TownCommunity>> call, Throwable t) {
                System.out.println("통신 실패!");
            }
        });


        //새로운 메모 작성
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //검색
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String date = data.getStringExtra("date");
            String nickName = data.getStringExtra("nickName");
            String uid = data.getStringExtra("uid");
            byte[] byteArray = data.getByteArrayExtra("byteArray");
            if(byteArray != null)
                bm = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

            Memo memo = new Memo(uid,title, content,nickName,date, bm);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();

            recyclerView.smoothScrollToPosition(recyclerAdapter.getItemCount());
        }
        else if(requestCode == 1){
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            int position = data.getIntExtra("position",0);
            String nickName = data.getStringExtra("nickName");
            byte[] byteArray = data.getByteArrayExtra("byteArray");
            if(byteArray != null)
                bm = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

            recyclerAdapter.updateItem(position,title,content,nickName,bm);
            recyclerAdapter.notifyDataSetChanged();
        }
    }

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

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

        private List<Memo> listdata;

        public RecyclerAdapter(List<Memo> listdata) {
            this.listdata = listdata;
        }


        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,
                    viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }


        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
            Memo memo = listdata.get(position);
            boolean push = true;

            itemViewHolder.title.setText(memo.getMaintext());
            itemViewHolder.content.setText(memo.getSubtext());
            itemViewHolder.nickname.setText(memo.getNickname());

            if(memo.getImg() == null)
                itemViewHolder.img.setVisibility(View.GONE);
            else
                itemViewHolder.img.setImageBitmap(memo.getImg());

            itemViewHolder.date.setText(memo.getDate());
//            itemViewHolder.likeCnt.setText(Integer.toString(memo.getLikeCnt()));

            itemViewHolder.deleteBtn.setVisibility(View.GONE);
            itemViewHolder.updateBtn.setVisibility(View.GONE);

            if(!uid.equals(memo.getUid())){
                itemViewHolder.town_menu.setVisibility(View.GONE);
            }

            itemViewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                    ad.setTitle("게시글 삭제");
                    ad.setMessage("해당 게시물을 삭제하시겠습니까?");

                    ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeItem(position);
                            notifyItemRemoved(position);

                            townCommunityService.deleteTown(memo.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        System.out.println("삭제 성공");
                                    } else {
                                        System.out.println("실패");
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println("통신 실패!");
                                }
                            });
                        }
                    });

                    ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                }
            });

            itemViewHolder.updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UpdateActivity.class);
                    intent.putExtra("title",listdata.get(position).getMaintext());
                    intent.putExtra("content",listdata.get(position).getSubtext());
                    intent.putExtra("id",listdata.get(position).getId());

                    byte[] byteArray = new byte[0];
                    if(listdata.get(position).getImg() != null){
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        listdata.get(position).getImg().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byteArray = stream.toByteArray();
                        intent.putExtra("img",byteArray);
                    }
                    else
                        intent.putExtra("img",byteArray);

                    System.out.println(byteArray.length);

                    intent.putExtra("nickName",listdata.get(position).getNickname());
                    intent.putExtra("position",position);
                    startActivityForResult(intent,1);
                }
            });

            itemViewHolder.chatbubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TownCommentListActivity.class);
                    intent.putExtra("id",listdata.get(position).getId());
                    System.out.println(listdata.get(position).getId());
                    startActivity(intent);
                }
            });

            /* 홈화면 말풍선에 댓글 리스트 연동 시키기
            itemViewHolder.mainchatbubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MaincommentActivity.class);
                    intent.putExtra("id",listdata.get(position).getId());
                    System.out.println(listdata.get(position).getId());
                    startActivity(intent);
                }
            }); */


            itemViewHolder.commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = itemViewHolder.commentContent.getText().toString();
                    if(content.length() > 0){
                        itemViewHolder.commentContent.setText("");
                        TownComment townComment = new TownComment(content);
                        townCommentService = RetrofitClient.getTownCommentService();
                        townCommentService.postTownComment(memo.getId(),uid,townComment).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    //정상적으로 통신이 성공된 경우
                                    System.out.println("댓글 쓰기 성공");
                                } else {
                                    System.out.println("실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("통신 실패 : " + t.getMessage());
                            }
                        });
                    }
                }
            });


//            townLikeService = RetrofitClient.getTownLikeService();
//
//            TownLike townLike = new TownLike();
//
//            itemViewHolder.likeImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(memo.isPush() == true){
//                        itemViewHolder.likeImg.setImageResource(R.drawable.ic_baseline_favorite_24);
//                        memo.setPush(false);
//                        memo.setLikeCnt(memo.getLikeCnt() + 1);
//                        itemViewHolder.likeCnt.setText(Integer.toString(memo.getLikeCnt()));
//
//                        townLikeService.postTownLike(memo.getId(),townLike).enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                if(response.isSuccessful()){
//                                    //정상적으로 통신이 성공된 경우
//                                    System.out.println("좋아요 성공");
//                                } else {
//                                    System.out.println("실패");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                System.out.println("통신 실패 : " + t.getMessage());
//                            }
//                        });
//                    }else{
//                        itemViewHolder.likeImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
//                        memo.setPush(true);
//                        memo.setLikeCnt(memo.getLikeCnt() - 1);
//                        itemViewHolder.likeCnt.setText(Integer.toString(memo.getLikeCnt()));
//
//                        townLikeService.deleteTownLike(memo.getId()).enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                if(response.isSuccessful()){
//                                    //정상적으로 통신이 성공된 경우
//                                    System.out.println("좋아요 취소 성공");
//                                } else {
//                                    System.out.println("실패");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                System.out.println("통신 실패 : " + t.getMessage());
//                            }
//                        });
//                    }
//                }
//            });

        }

        void addItem(Memo memo) {
            listdata.add(memo);
        }

        void removeItem(int position) {
            listdata.remove(position);
        }

        public void updateItem(int position, String title, String content,String nickName, Bitmap bm){
            listdata.get(position).setMaintext(title);
            listdata.get(position).setSubtext(content);
            listdata.get(position).setNickname(nickName);
            if(bm != null)
                listdata.get(position).setImg(bm);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private TextView nickname;
            private TextView title;
            private TextView content;
            private ImageView img;
            private Button deleteBtn;
            private Button updateBtn;
            private TextView date;

            private Button commentBtn;
            private EditText commentContent;

            private Button town_menu;

//            private TextView likeCnt;
//            private ImageView likeImg;

            private ImageView chatbubble;
//            private ImageView mainchatbubble;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                nickname = itemView.findViewById(R.id.user_nickname);
                title = itemView.findViewById(R.id.town_title);
                content = itemView.findViewById(R.id.town_content);
                img = itemView.findViewById(R.id.town_img);
                deleteBtn = itemView.findViewById(R.id.town_delete);
                updateBtn = itemView.findViewById(R.id.town_update);
                date = itemView.findViewById(R.id.town_date);
                chatbubble = itemView.findViewById(R.id.town_comment);
               // mainchatbubble = itemView.findViewById(R.id.feed_comment);

                commentBtn = itemView.findViewById(R.id.town_commentBtn);
                commentContent = itemView.findViewById(R.id.town_comment_content);

//                likeCnt = itemView.findViewById(R.id.likeCnt);
//                likeImg = itemView.findViewById(R.id.town_likeBtn);

                town_menu = itemView.findViewById(R.id.town_menu);
                town_menu.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem Edit = menu.add(Menu.NONE, R.id.item1, 1, "수정하기");
                MenuItem Delete = menu.add(Menu.NONE, R.id.item2, 2, "삭제하기");
                Edit.setOnMenuItemClickListener(onMenuItemClickListener);
                Delete.setOnMenuItemClickListener(onMenuItemClickListener);
            }

            private final MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.item1:

                            return true;

                        case R.id.item2:
                            AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                            ad.setTitle("게시글 삭제");
                            ad.setMessage("해당 게시물을 삭제하시겠습니까?");

                            ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad.show();
                            return true;
                    }
                    return false;
                }
            };
        }
    }
}