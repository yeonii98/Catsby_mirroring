package org.techtown.catsby.setting;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.catsbe.account;
import com.example.catsbe.alert;
import com.example.catsby.writingList;
import com.google.firebase.auth.FirebaseAuth;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.R;
import org.techtown.catsby.home.BowlDetailActivity;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.NicknameResponse;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.dto.UserAddressUpdate;
import org.techtown.catsby.retrofit.dto.UserImageUpdate;
import org.techtown.catsby.retrofit.service.UserService;
import org.techtown.catsby.util.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentSetting_New extends Fragment {

    //변수 선언
    private ImageButton imageButton;
    private EditText editNickName;
    private TextView nickName;
    private TextView local;
    private TextView personalNum;
    private Button editButton;
    private Button backButton;
    private TextView alertManage;
    private TextView accountManage;
    private TextView writingList;

    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    private static final String TAG = "catsby";
    private static final int PICK_FROM_CAMERA = 2;

    private int OPEN_GALLERY=1;
    private String txtadd = null;

    private UserService userService;

    Uri photoUri;

    String uid = FirebaseAuth.getInstance().getUid();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        tedPermission();

        //savedInstanceState = false;
        //inflater.inflate(R.layout.fragment_setting, container, false);

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        //변수 매칭
        imageButton = (ImageButton)view.findViewById(R.id.imageButton);
        editNickName = (EditText)view.findViewById(R.id.editNickName);
        nickName = (TextView)view.findViewById(R.id.nickName);
        local = (TextView)view.findViewById(R.id.local);
        editButton = (Button)view.findViewById(R.id.editButton);
        alertManage = (TextView)view.findViewById(R.id.alertManage);
        accountManage = (TextView)view.findViewById(R.id.accountManage);
        writingList = (TextView)view.findViewById(R.id.writingList);

        userService = RetrofitClient.getUser();


        Bundle bundle = getArguments();
        if (bundle != null) {
            String nickname = bundle.getString("nickname");
            String address = bundle.getString("address");
            byte[] image = bundle.getByteArray("image");
            nickName.setText(nickname);
            if (address == null)
                local.setText("동네를 설정하세요");
            else
                local.setText(address);
            if (image != null) {
                imageButton.setImageBitmap(ImageUtils.makeBitMap(ImageUtils.byteArrayToBinaryString(image)));
            }
            else
                imageButton.setImageResource(R.drawable.catsby_logo);
        }

        //        setFragmentResultListener("myaddkey") { key, bundle ->
//                bundle.getString("myaddkey")?.let {
//            //프로필에 주소 등록
//            //나중에 데이터 베이스에 등록 후 전역으로 setText 해야할듯..?
//            local.setText(it)
//        }
//        }

        //카테고리 클릭 시 Activity to Fragment 화면전환
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        alertManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment alert = new alert();
                transaction.replace(R.id.fragment_setting, alert).commit();
                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });

        accountManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment account = new account();
                transaction.replace(R.id.fragment_setting, account).commit();
                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });

        writingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment writingList = new writingList();
                transaction.replace(R.id.fragment_setting, writingList).commit();
                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });


        //프로필 이미지 수정
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission)
                    DialogClick(view);
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

        //닉네임 수정
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //수정 버튼 클릭 시
                if (editButton.getText().equals("수정")){
                    nickName.setVisibility(View.GONE);
                    editNickName.setVisibility(View.VISIBLE);
                    editButton.setText("완료");
                }

                //(수정)완료 버튼 클릭 시
                else if(editButton.getText().equals("완료")) {
                    nickName.setVisibility(View.VISIBLE);
                    editNickName.setVisibility(View.GONE);
                    editButton.setText("수정");

                    new AlertDialog.Builder(getContext())
                            .setMessage("닉네임을 수정하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String newNickname = editNickName.getText().toString();
                                    if (newNickname.trim().equals("")) {
                                        Toast.makeText(getContext(), "닉네임을 입력헤주세요", Toast.LENGTH_SHORT).show();
                                    } else {
                                        updateNickname(editNickName.getText().toString());
                                    }
                                    editNickName.setText("");
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create().show();

                }
            }
        });

        return view;
    }

    private void updateNickname(String nickname) {
        userService.updateNickname(FirebaseAuth.getInstance().getUid(), nickname).enqueue(new Callback<NicknameResponse>() {
            @Override
            public void onResponse(Call<NicknameResponse> call, Response<NicknameResponse> response) {
                nickName.setText(nickname);
                Toast.makeText(getContext(), "닉네임이 변경 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NicknameResponse> call, Throwable t) {

            }
        });
    }


    public void DialogClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("알림").setMessage("프로필 사진을 변경하시겠습니까?");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which) {
                goToAlbum();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getApplicationContext(),"취소되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //[이미지 설정] 1.앨범에서 이미지 가져오기
    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //[이미지 설정] 2
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();

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
                cursor = getContext().getContentResolver().query(photoUri, proj, null, null, null);

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

    //[이미지 설정] 3.tempFile을 bitmap으로 변환 후 ImageView에 설정한다.
    private void setImage() {

        /* 이미지 회전 방지 적용이 안 됨..
        ImageButton imageButton = (ImageButton) getView().findViewById(R.id.imageButton);
        Glide.with(this).load(photoUri).into(imageButton); */

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

        originalBm = ImageUtils.rotateBitmap(originalBm,orientation);

        imageButton.setImageBitmap(originalBm);
        String image = "";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        originalBm.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        byte[] byteArray = stream.toByteArray();
        image = ImageUtils.byteArrayToBinaryString(byteArray);

        updateImage(image);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }


    private void updateImage(String image) {
        UserImageUpdate userImageUpdate = new UserImageUpdate(image);
        userService.updateUserImage(uid,userImageUpdate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "프로필 사진이 변경 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    //[이미지 설정] 4. 권한 설정
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

        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
}
