package org.techtown.catsby.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.catsby.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentlistActivity extends AppCompatActivity {

    EditText commentlist_add;
    ImageView image_profile;
    Button commentlist_postbtn;
    Button commentlist_delete;
    Button commentlist_update;
    TextView commentlist_text;
    TextView commentlist_username;
    ImageView commentlist_image;
    TextView commentlist_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentlist);

        //뒤로가기
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    @NonNull
    @Override
    public FragmentCommunity.RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_list_item,
                viewGroup, false);
        return new FragmentCommunity.RecyclerAdapter.ItemViewHolder(view);
    } */

}