package org.techtown.catsby.community;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.community.data.model.TownCommunity;
import org.techtown.catsby.community.data.service.TownCommunityService;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.util.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private TownCommunityService townCommunityService;
    private TownCommunity townCommunity;
    private static final String TAG = "catsby";
    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
    byte[] byteArray;
    private UserService userService = RetrofitClient.getUser();
    EditText edtTitle,edtContent;
    CheckBox checkBox;
    ImageView townImg;
    Bitmap img;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        townImg = findViewById(R.id.townImgView);
        checkBox = findViewById(R.id.checkBox1);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("커뮤니티 글 수정하기");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");
        int likeCnt = intent.getIntExtra("likeCnt",0);
        int push = intent.getIntExtra("push",0);
        int id = intent.getIntExtra("id",0);

        byte[] arr = getIntent().getByteArrayExtra("img");
        byte[] userImgByte = getIntent().getByteArrayExtra("userImg");


        String nickName = intent.getStringExtra("nickName");
        int position = intent.getIntExtra("position",0);

        if(arr.length != 0){
            img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            townImg.setImageBitmap(img);
        }//이건 잘 된다.

        edtTitle.setText(title);
        edtContent.setText(content);

        if(nickName.equals("익명")) checkBox.setChecked(true);

        tedPermission();
        findViewById(R.id.Townalbum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermission) goToAlbum();
                else
                    Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                if(title.length() > 0 && content.length() > 0) {
                    System.out.println("townImg = " + townImg.getDrawable());
                    if(townImg.getDrawable() == null){
                        townCommunity = new TownCommunity(title, content, checkBox.isChecked());
                        byteArray = null;
                    }

                    else{
                        Bitmap img = ((BitmapDrawable)townImg.getDrawable()).getBitmap();

                        String image = "";

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                        byteArray = stream.toByteArray();
                        image = "&image=" + ImageUtils.byteArrayToBinaryString(byteArray) ;
                        townCommunity = new TownCommunity(title, content, image, checkBox.isChecked());
                    }

                    townCommunityService = RetrofitClient.getTownCommunityService();
                    townCommunityService.putTown(id, townCommunity).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                //정상적으로 통신이 성공된 경우
                                System.out.println("성공");
                            } else {
                                System.out.println("실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("통신 실패 : " + t.getMessage());
                        }
                    });

//                    Date date = new Date();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//                    String substr = sdf.format(date);

                    Intent intent = new Intent(UpdateActivity.this, FragmentCommunity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("position",position);
                    intent.putExtra("byteArray", byteArray);
                    intent.putExtra("date", date);
                    intent.putExtra("id", id);
                    intent.putExtra("likeCnt", likeCnt);
                    intent.putExtra("push", push);
                    intent.putExtra("userImgByte",userImgByte);

                    int idx = user.getEmail().indexOf("@");
                    if(!checkBox.isChecked())
                        intent.putExtra("nickName", nickName);
                    else
                        intent.putExtra("nickName", "익명");
                    setResult(3, intent);

                    finish();
                }

            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();
            Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(tempFile));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {

            setImage();

        }
    }

    //앨범에서 이미지 가져오기
    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //tempFile을 bitmap으로 변환 후 ImageView에 설정한다.
    private void setImage() {

        ImageView imageView = findViewById(R.id.townImgView);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());

        imageView.setImageBitmap(originalBm);

        tempFile = null;

    }

    //권한 설정
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
}