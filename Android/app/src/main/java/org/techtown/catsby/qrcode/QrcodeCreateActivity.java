package org.techtown.catsby.qrcode;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.WriterException;

import org.techtown.catsby.qrcode.data.model.BowlResponse;
import org.techtown.catsby.qrcode.data.service.QRBowlService;
import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrcodeCreateActivity extends AppCompatActivity {

    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private EditText dataEdt2;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String qrInfo, bowlInfo, bowlName, bowlAddress;

    private QRBowlService QRBowlService;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_qr);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QRBowlService = RetrofitClient.getQrBowlService();

        // initializing all variables.
        qrCodeIV = (ImageView)findViewById(R.id.idIVQrcode);
        dataEdt = (EditText)findViewById(R.id.idEdt);
        dataEdt2 = (EditText)findViewById(R.id.addEdt);
        generateQrBtn = (Button)findViewById(R.id.idBtnGenerateQR);

        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(dataEdt.getText().toString())) {

                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.
                    Toast.makeText(getApplicationContext(), "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    // below line is for getting
                    // the windowmanager service.
                    WindowManager manager = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);

                    // initializing a variable for default display.
                    Display display = manager.getDefaultDisplay();

                    // creating a variable for point which
                    // is to be displayed in QR Code.
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and
                    // height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height.
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    bowlName = dataEdt.getText().toString();
                    bowlAddress = dataEdt2.getText().toString();
                    bowlInfo = bowlName.concat(bowlAddress);
                    qrInfo = bowlAddress.concat("위치의 ".concat(bowlName));
                    qrgEncoder = new QRGEncoder(bowlInfo, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrcode in the form of bitmap.
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.

                        saveBowl(bowlInfo, bowlName, bowlAddress);
                    } catch (WriterException | IOException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString());
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveBowl(String info, String name, String address) throws IOException {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("info", toRequestBody(info));
        map.put("name", toRequestBody(name));
        map.put("address", toRequestBody(address));

        File temp = getApplication().getCacheDir();
        String fileNmae = bowlName + ".jpg";
        File image = new File(temp, fileNmae);
        image.createNewFile();
        OutputStream os = new FileOutputStream(image);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), requestFile);
        QRBowlService.saveBowl(map, body).enqueue(new Callback<BowlResponse>() {
            @Override
            public void onResponse(Call<BowlResponse> call, Response<BowlResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("qrcodeCreateActivity", "bowl id : " + response.body().getId());
                    qrCodeIV.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), qrInfo +" 밥그릇 큐알코드가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(getApplicationContext(), "밥그릇 이름이 중복되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BowlResponse> call, Throwable t) {
                Log.d("qrcodeCreateActivity", "error save bowl");
            }
        });
    }

    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
}
