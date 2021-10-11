package org.techtown.catsby.home;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.soundcloud.android.crop.Crop;

import android.Manifest;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlCheckListAdapter;
import org.techtown.catsby.home.model.Bowl;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlList;
import org.techtown.catsby.retrofit.service.BowlCommunityService;
import org.techtown.catsby.retrofit.service.BowlService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.techtown.catsby.home.ImageResizeUtils.exifOrientationToDegrees;
import static org.techtown.catsby.home.ImageResizeUtils.rotate;

public class BowlWrite extends AppCompatActivity{
    ListView listview ;

    private static final String TAG = "blackjin";
    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    BowlService bowlService = RetrofitClient.getBowlService();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    BowlCommunityService bowlCommunityService = RetrofitClient.getBowlCommunityService();
    ArrayList<String> bowlNameArray = new ArrayList<>();
    static ArrayList<Bowl> bowlList = new ArrayList<>();
    String allContext;
    BowlCheckListAdapter adapter;
    static int cPosition;

    String mCurrentPhotoPath;

    private Boolean isCamera = false;
    Uri photoUri;
    File tempFile;
    File image;
    ImageView imageView;
    EditText postContext;
    int[] bowlImg = {R.drawable.ic_baseline_favorite_red, R.drawable.ic_baseline_star_border_24, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writemain);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("홈 화면 글쓰기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (user != null) {
            loadBowls(user.getUid());
        }

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tedPermission();

        findViewById(R.id.btnGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission)
                goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btnCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPermission)
                takePhoto();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

        Button postButton = findViewById(R.id.btn_signupfinish);
        postButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                postContext = findViewById(R.id.context);
                if (image != null) {
                    allContext = postContext.getText().toString();
                    savePost(image, bowlList.get(cPosition).getId(), user.getUid(), allContext);
                    imageView.setImageResource(0);
                    postContext.setText("");
                    image = null;

                }else{
                    Toast.makeText(getApplicationContext(),"이미지를 첨부해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void savePost(File file, int id, String uid, String context) {

        RequestBody content = RequestBody.create(MediaType.parse("text/plain"), context);
        RequestBody filePath = RequestBody.create(MediaType.parse("text/plain"), image.toString());

        HashMap<String, RequestBody> map = new HashMap<String, RequestBody>();
        map.put("content", content);
        map.put("path", filePath);
        InputStream inputStream = null;
        try {
            inputStream = this.getContentResolver().openInputStream(photoUri);
        }catch(IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray());
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName() ,requestBody);
        bowlCommunityService.saveCommunity(body, id, uid, map).enqueue(new Callback<List<BowlCommunity>>() {
            @Override
            public void onResponse(Call<List<BowlCommunity>> call, Response<List<BowlCommunity>> response) {
                System.out.println(" success" );
            }

            @Override
            public void onFailure(Call<List<BowlCommunity>> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
            }
        });
    }

    private void loadBowls(String uid) {
        bowlService.getBowls(uid).enqueue(new Callback<BowlList>() {
            @Override
            public void onResponse(Call<BowlList> call, Response<BowlList> response) {
                if(response.isSuccessful()) {
                    BowlList result = response.body();

                    for(int i =0; i < result.size(); i++){
                        bowlNameArray.add(result.getBowls().get(i).getName());
                        Bowl bowl = new Bowl(result.getBowls().get(i).getId(), R.drawable.bowl, result.getBowls().get(i).getName(), result.getBowls().get(i).getInfo(), result.getBowls().get(i).getAddress(), result.getBowls().get(i).getUpdated_time());
                        bowlList.add(bowl);
                    }

                    adapter = new BowlCheckListAdapter(bowlList, allContext);
                    // 첫 번째 아이템 추가.
                    for (int i =0; i < bowlNameArray.size(); i++){
                        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_indicator_active), bowlNameArray.get(i), i) ;
                    }

                    listview = findViewById(R.id.listview1);
                    listview.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<BowlList> call, Throwable t) {
                System.out.println("t.getMessage() loadBowls= " + t.getMessage());
            }
        });

    }

    public static void clickSave(int clickPosition){
        cPosition = clickPosition;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if(tempFile.exists()) {

                    if(tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    } else {
                        Log.e(TAG, "tempFile 삭제 실패");
                    }

                } else {
                    Log.e(TAG, "tempFile 존재하지 않음");
                }
            } else {
                Log.e(TAG, "tempFile is null");
            }

            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: {

                Uri photoUri = data.getData();
                Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

                cropImage(photoUri);

                break;
            }
            case PICK_FROM_CAMERA: {

                // 앨범에 있지만 카메라 에서는 data.getData()가 없음
                Uri photoUri = Uri.fromFile(tempFile);
                Log.d(TAG, "takePhoto photoUri : " + photoUri);

                cropImage(photoUri);

                break;
            }
            case Crop.REQUEST_CROP: {
                //File cropFile = new File(Crop.getOutput(data).getPath());
                setImage();
            }
        }
    }

    /**
     *  앨범에서 이미지 가져오기
     */
    private void goToAlbum() {
        isCamera = false;

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    /**
     *  카메라에서 이미지 가져오기
     */
    private void takePhoto() {
        isCamera = true;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            /**
             *  안드로이드 OS 누가 버전 이후부터는 file:// URI 의 노출을 금지로 FileUriExposedException 발생
             *  Uri 를 FileProvider 도 감싸 주어야 합니다.
             *
             *  참고 자료 http://programmar.tistory.com/4 , http://programmar.tistory.com/5
             */
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                Uri photoUri = FileProvider.getUriForFile(this,
                        "com.appgrider.test_android.fileprovider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            } else {

                Uri photoUri = Uri.fromFile(tempFile);
                Log.d(TAG, "takePhoto photoUri : " + photoUri);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            }
        }
    }

    /**
     *  Crop 기능
     */
    private void cropImage(Uri photoUri) {

        Log.d(TAG, "tempFile : " + tempFile);

        /**
         *  갤러리에서 선택한 경우에는 tempFile이 없으므로 새로 생성해줍니다.
         */
        if(tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }

        //크롭에 후 저장할 Uri
        Uri savingUri = Uri.fromFile(tempFile);
        Crop.of(photoUri, savingUri).asSquare().start(this);
    }

    /**
     *  폴더 및 파일 만들기
     */
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }



    /**
     *  tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
     */
    private void setImage() {
        imageView = findViewById(R.id.imageView);


        ImageResizeUtils.resizeFile(tempFile, tempFile, 1280, isCamera);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());

        imageView.setImageBitmap(originalBm);
        //Glide.with(this).load(photoUri).into(imageView);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }


    /**
     *  권한 설정
     */
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


}

