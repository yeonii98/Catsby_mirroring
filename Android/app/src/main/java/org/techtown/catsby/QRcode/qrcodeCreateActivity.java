package org.techtown.catsby.QRcode;

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

import org.techtown.catsby.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class qrcodeCreateActivity extends AppCompatActivity {

    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private EditText dataEdt2;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String qrinfo;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_qr);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    qrinfo = dataEdt2.getText().toString().concat(dataEdt.getText().toString());
                    qrinfo = dataEdt2.getText().toString().concat("위치의 ".concat(dataEdt.getText().toString()));
                    qrgEncoder = new QRGEncoder(qrinfo, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrcode in the form of bitmap.
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        qrCodeIV.setImageBitmap(bitmap);
                        Toast.makeText(getApplicationContext(), qrinfo+" 밥그릇 큐알코드가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (WriterException e) {
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
}