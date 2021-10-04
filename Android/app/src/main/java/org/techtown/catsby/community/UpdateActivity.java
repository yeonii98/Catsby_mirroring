package org.techtown.catsby.community;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
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

import com.bumptech.glide.Glide;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private static final String TAG = "catsby";
    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
    private Bitmap bm;
    private TownCommunityService townCommunityService = RetrofitClient.getTownCommunityService();
    private HashMap<String, RequestBody> map = new HashMap<String, RequestBody>();
    private MultipartBody.Part body;
    private int position;
    private int push;
    private Drawable preImg;
    EditText edtTitle,edtContent;
    CheckBox checkBox;
    ImageView townImg;
    String uid = FirebaseAuth.getInstance().getUid();


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
        int id = intent.getIntExtra("id",0);
        push = intent.getIntExtra("push",0);
        String mImg = intent.getStringExtra("img");
        String nickName = intent.getStringExtra("nickName");
        position = intent.getIntExtra("position",0);

        if(mImg != null){
            try {
                URL url = new URL(mImg);
                InputStream inputStream = url.openConnection().getInputStream();
                bm = BitmapFactory.decodeStream(inputStream);
                townImg.setImageBitmap(bm);
                preImg = townImg.getDrawable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
                    RequestBody reqTitle = RequestBody.create(MediaType.parse("text/plain"), title);
                    RequestBody reqContent = RequestBody.create(MediaType.parse("text/plain"), content);
                    RequestBody anonymous = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(checkBox.isChecked()));
                    RequestBody filePath = RequestBody.create(MediaType.parse("text/plain"), townImg.toString());
                    RequestBody mUid = RequestBody.create(MediaType.parse("text/plain"), uid);

                    map.put("title", reqTitle);
                    map.put("content", reqContent);
                    map.put("anonymous", anonymous);
                    map.put("uid", mUid);
                    map.put("path", filePath);

                    if (townImg.getDrawable() != null && townImg.getDrawable() != preImg) {
                        Bitmap img = ((BitmapDrawable) townImg.getDrawable()).getBitmap();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.JPEG, 20, stream);

                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), stream.toByteArray());
                        body = MultipartBody.Part.createFormData("file", "", requestBody);
                    } else if(townImg.getDrawable() != null && townImg.getDrawable() == preImg){
                        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "same image");
                        body = MultipartBody.Part.createFormData("file", "same", requestBody);
                    } else if(townImg.getDrawable() == null){
                        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "no image");
                        body = MultipartBody.Part.createFormData("file", "empty", requestBody);
                    }

                    putTown(body,map,id);
                }

            }
        });
    }

    public void putTown(MultipartBody.Part body, HashMap<String, RequestBody> map, int id){
        townCommunityService.putTown(body, map, id).enqueue(new Callback<TownCommunity>() {
            @Override
            public void onResponse(Call<TownCommunity> call, Response<TownCommunity> response) {
                if(response.isSuccessful()){
                    TownCommunity result = response.body();
                    Intent intent = new Intent(UpdateActivity.this, FragmentCommunity.class);
                    intent.putExtra("id", result.getId());
                    intent.putExtra("title", result.getTitle());
                    intent.putExtra("content", result.getContent());
                    intent.putExtra("date", result.getDate());
                    intent.putExtra("position",position);
                    intent.putExtra("mImg", result.getImage());
                    intent.putExtra("likeCnt", result.getTownLike().size());
                    intent.putExtra("push", push);
                    intent.putExtra("userImg",result.getUser().getImage());

                    if(!checkBox.isChecked())
                        intent.putExtra("nickName", result.getUser().getNickname());
                    else
                        intent.putExtra("nickName", "익명");

                    setResult(3, intent);

                    finish();
                } else {
                    System.out.println("실패");
                }
            }

            @Override
            public void onFailure(Call<TownCommunity> call, Throwable t) {
                System.out.println("통신 실패 : " + t.getMessage());
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

        //이미지 회전 방지
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        Glide.with(this).load(ImageUtils.rotateBitmap(originalBm, orientation)).into(imageView);

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