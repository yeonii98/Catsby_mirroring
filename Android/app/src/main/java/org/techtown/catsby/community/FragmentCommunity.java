package org.techtown.catsby.community;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.util.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCommunity extends Fragment {
    private View view;
    private Button btnAdd;
    private RecyclerView recyclerView;
    public RecyclerAdapter recyclerAdapter;
    private TownCommunityService townCommunityService = RetrofitClient.getTownCommunityService();
    private TownLikeService townLikeService = RetrofitClient.getTownLikeService();
    private Bitmap bm = null;
    private Bitmap userBm = null;
    private String nickName;
    String uid = FirebaseAuth.getInstance().getUid();
    List<Integer> idList = new ArrayList<>();
    List<Memo> memoList;
    int addressExist;

    int push;

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

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setLayoutManager(layoutManager);

        townCommunityService.getTownList(uid).enqueue(new Callback<List<TownCommunity>>() {
            @Override
            public void onResponse(Call<List<TownCommunity>> call, Response<List<TownCommunity>> response) {
                if (response.isSuccessful()) {
                    addressExist = 1;
                    List<TownCommunity> result = response.body();
                    System.out.println("result = " + result);
                    System.out.println("result.size = " + result.size());
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getImage() != null)
                            bm = ImageUtils.makeBitMap(result.get(i).getImage());
                        else
                            bm = null;

                        if (result.get(i).isAnonymous())
                            nickName = "익명";
                        else
                            nickName = result.get(i).getUser().getNickname();

                        if (result.get(i).getUser().getImage() != null)
                            userBm = ImageUtils.makeBitMap(result.get(i).getUser().getImage());
                        else userBm = null;

                        push = push(result.get(i).getTownLike().size(), result.get(i).getTownLike());

                        Memo memo = new Memo(result.get(i).getId(), result.get(i).getUser().getUid(),
                                result.get(i).getTitle(), result.get(i).getContent(), result.get(i).getTownLike().size(),
                                nickName, result.get(i).getDate(), bm, push, userBm);
                        recyclerAdapter.addItem(memo);
                    }
                    recyclerAdapter.notifyDataSetChanged();

                } else {
                    Memo memo = new Memo("설정에서 동네를 등록해주세요", "동네를 등록 한 후 글쓰기가 가능합니다.");
                    addressExist = 0;
                    recyclerAdapter.addItem(memo);
                    btnAdd.setEnabled(false);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TownCommunity>> call, Throwable t) {
                System.out.println("통신 실패!");
            }
        });

//                    else{
//                        Memo memo = new Memo("설정에서 동네를 등록해주세요", "동네를 등록 한 후 글쓰기가 가능합니다.");
//                        addressExist = 0;
//                        recyclerAdapter.addItem(memo);
//                        btnAdd.setEnabled(false);
//                        recyclerAdapter.notifyDataSetChanged();
//                    }



    //새로운 메모 작성
    btnAdd =view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        Intent intent = new Intent(getActivity(), AddActivity.class);
        startActivityForResult(intent, 2);
    }
    });

    //검색
//        SearchView searchView = view.findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//        });

        return view;
}

    public int push(int size, List<TownLike> townLike) {
        for (int i = 0; i < size; i++) {
            if (townLike.get(i).getUser().getUid().equals(uid)) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String date = data.getStringExtra("date");
            String nickName = data.getStringExtra("nickName");
            String uid = data.getStringExtra("uid");

            String userImg = data.getStringExtra("userImg");
            if (userImg != null)
                userBm = ImageUtils.makeBitMap(userImg);
            else
                userBm = null;

            int id = 0;
            if (memoList.size() != 0) {
                id = memoList.get(memoList.size() - 1).getId() + 1;
                while (true) {
                    if (idList.contains(id)) id++;
                    else break;
                }

                idList.add(id);
            }
            byte[] byteArray = data.getByteArrayExtra("byteArray");

            if (byteArray != null)
                bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            else
                bm = null;

            Memo memo = new Memo(id, uid, title, content, nickName, date, bm, userBm);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();

            recyclerView.smoothScrollToPosition(recyclerAdapter.getItemCount());//특정 포지션으로 이동
        } else if (resultCode == 3) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String date = data.getStringExtra("date");
            int position = data.getIntExtra("position", 0);
            String nickName = data.getStringExtra("nickName");
            int likeCnt = data.getIntExtra("likeCnt", 0);
            int push = data.getIntExtra("push", 0);
            int id = data.getIntExtra("id", 0);

            byte[] byteArray = data.getByteArrayExtra("byteArray");
            if (byteArray != null)
                bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            else
                bm = null;

            byte[] userImgBytes = data.getByteArrayExtra("userImgByte");
            if (userImgBytes != null)
                userBm = BitmapFactory.decodeByteArray(userImgBytes, 0, userImgBytes.length);
            else
                userBm = null;

            Memo memo = new Memo(id, uid, title, content, nickName, date, bm, likeCnt, push, userBm);
            recyclerAdapter.updateItem(memo, position);
            recyclerAdapter.notifyItemChanged(position);
            recyclerView.smoothScrollToPosition(position);
//            recyclerAdapter.updateItem(position, title, content, nickName, bm);
//            recyclerAdapter.notifyDataSetChanged();
        } else return;

    }

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    @Override
    public int getItemViewType(int position) {
        return position;
    }

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

        itemViewHolder.title.setText(memo.getMaintext());
        itemViewHolder.content.setText(memo.getSubtext());
        itemViewHolder.nickname.setText(memo.getNickname());
        itemViewHolder.date.setText(memo.getDate());
        itemViewHolder.likeCnt.setText(Integer.toString(memo.getLikeCnt()));

        if (memo.getImg() == null)
            itemViewHolder.img.setVisibility(View.GONE);
        else
            Glide.with(itemViewHolder.itemView.getContext()).load(memo.getImg()).into(itemViewHolder.img);

        if (!uid.equals(memo.getUid())) {
            itemViewHolder.deleteBtn.setVisibility(View.GONE);
            itemViewHolder.updateBtn.setVisibility(View.GONE);
//                itemViewHolder.town_menu.setVisibility(View.GONE);
        }

        if (memo.getPush() == 1)
            itemViewHolder.likeImg.setImageResource(R.drawable.ic_baseline_favorite_red);

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
                                if (response.isSuccessful()) {
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
                intent.putExtra("title", listdata.get(position).getMaintext());
                intent.putExtra("content", listdata.get(position).getSubtext());
                intent.putExtra("id", listdata.get(position).getId());
                intent.putExtra("date", listdata.get(position).getDate());
                intent.putExtra("likeCnt", listdata.get(position).getLikeCnt());
                intent.putExtra("push", listdata.get(position).getPush());

                byte[] byteArray = new byte[0];

                if (listdata.get(position).getUserImg() != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    listdata.get(position).getUserImg().compress(Bitmap.CompressFormat.JPEG, 20, stream);
                    byteArray = stream.toByteArray();
                    intent.putExtra("userImg", byteArray);
                } else
                    intent.putExtra("userImg", byteArray);

                byteArray = new byte[0];

                if (listdata.get(position).getImg() != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    listdata.get(position).getImg().compress(Bitmap.CompressFormat.JPEG, 20, stream);
                    byteArray = stream.toByteArray();
                    intent.putExtra("img", byteArray);
                } else
                    intent.putExtra("img", byteArray);

                intent.putExtra("nickName", listdata.get(position).getNickname());
                intent.putExtra("position", position);
                startActivityForResult(intent, 3);
            }
        });

        itemViewHolder.chatbubble.setVisibility(View.GONE);
//            itemViewHolder.linearLayout.setVisibility(View.GONE);
        itemViewHolder.userImg.setVisibility(View.GONE);
        itemViewHolder.likeImg.setVisibility(View.GONE);
        itemViewHolder.likeCnt.setVisibility(View.GONE);


        if (addressExist == 1) {

            itemViewHolder.chatbubble.setVisibility(View.VISIBLE);
//                itemViewHolder.linearLayout.setVisibility(View.VISIBLE);

            itemViewHolder.userImg.setVisibility(View.VISIBLE);

            if (memo.getUserImg() == null)
                Glide.with(itemViewHolder.itemView.getContext()).load(R.drawable.catsby_logo).into(itemViewHolder.userImg);
            else
                Glide.with(itemViewHolder.itemView.getContext()).load(memo.getUserImg()).into(itemViewHolder.userImg);

            itemViewHolder.likeImg.setVisibility(View.VISIBLE);
            itemViewHolder.likeCnt.setVisibility(View.VISIBLE);

            itemViewHolder.chatbubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TownCommentListActivity.class);
                    intent.putExtra("id", listdata.get(position).getId());
                    startActivity(intent);
                }
            });

//                itemViewHolder.commentBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String content = itemViewHolder.commentContent.getText().toString();
//                        if (content.length() > 0) {
//                            itemViewHolder.commentContent.setText("");
//                            TownComment townComment = new TownComment(content);
//                            townCommentService.postTownComment(memo.getId(), uid, townComment).enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call, Response<Void> response) {
//                                    if (response.isSuccessful()) {
//                                        //정상적으로 통신이 성공된 경우
//                                        System.out.println("댓글 쓰기 성공");
//                                        Toast.makeText(getContext(), "댓글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        System.out.println("실패");
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call, Throwable t) {
//                                    System.out.println("통신 실패 : " + t.getMessage());
//                                }
//                            });
//                        }
//                    }
//                });

            TownLike townLike = new TownLike();

            itemViewHolder.likeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (memo.getPush() == 0) {
                        itemViewHolder.likeImg.setImageResource(R.drawable.ic_baseline_favorite_red);
                        memo.setPush(1);
                        memo.setLikeCnt(memo.getLikeCnt() + 1);
                        itemViewHolder.likeCnt.setText(Integer.toString(memo.getLikeCnt()));
                        recyclerView.smoothScrollToPosition(position);

                        townLikeService.postTownLike(memo.getId(), uid, townLike).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    //정상적으로 통신이 성공된 경우
                                    System.out.println("좋아요 성공");
                                } else {
                                    System.out.println("실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("통신 실패 : " + t.getMessage());
                            }
                        });
                    } else {
                        itemViewHolder.likeImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        memo.setPush(0);
                        memo.setLikeCnt(memo.getLikeCnt() - 1);
                        itemViewHolder.likeCnt.setText(Integer.toString(memo.getLikeCnt()));
                        recyclerView.smoothScrollToPosition(position);

                        townLikeService.deleteTownLike(memo.getId(), uid).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    //정상적으로 통신이 성공된 경우
                                    System.out.println("좋아요 취소 성공");
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
        }
    }

    void addItem(Memo memo) {
        listdata.add(memo);
    }

    void updateItem(Memo memo, int position) {
        listdata.set(position, memo);
    }

    void removeItem(int position) {
        listdata.remove(position);
    }

//        public void updateItem(int position, String title, String content, String nickName, Bitmap bm) {
//            listdata.get(position).setMaintext(title);
//            listdata.get(position).setSubtext(content);
//            listdata.get(position).setNickname(nickName);
//            if (bm != null)
//                listdata.get(position).setImg(bm);
//        }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nickname;
        private TextView title;
        private TextView content;
        private ImageView img;
        private Button deleteBtn;
        private Button updateBtn;
        private TextView date;

//            private Button commentBtn;
//            private EditText commentContent;

        private Button town_menu;

        private TextView likeCnt;
        private ImageView likeImg;

        private ImageView chatbubble;
//            private ImageView mainchatbubble;

        private ImageView userImg;

//            private LinearLayout linearLayout;

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

//                commentBtn = itemView.findViewById(R.id.town_commentBtn);
//                commentContent = itemView.findViewById(R.id.town_comment_content);

            likeCnt = itemView.findViewById(R.id.likeCnt);
            likeImg = itemView.findViewById(R.id.town_likeBtn);

            userImg = itemView.findViewById(R.id.user_img);

//                linearLayout = itemView.findViewById(R.id.linearLayout);

//                town_menu = itemView.findViewById(R.id.town_menu);
        }
    }
}
}