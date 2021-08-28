package org.techtown.catsby.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.Result;

import org.techtown.catsby.MainActivity;
import org.techtown.catsby.qrcode.data.model.BowlResponse;
import org.techtown.catsby.qrcode.data.model.BowlUserRequest;
import org.techtown.catsby.qrcode.data.service.QRBowlService;
import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView codeScannerView;

    private QRBowlService QRBowlService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);

        QRBowlService = RetrofitClient.getQrBowlService();

        codeScannerView = (CodeScannerView) findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, codeScannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        saveBowlUser(result.getText(), latitude, longitude);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        Toast.makeText(getApplicationContext(), "밥그릇 등록이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCamera();
    }

    private void requestCamera() {
        codeScanner.startPreview();
    }

    private void saveBowlUser(String bowlInfo, double latitude, double longitude) {
        QRBowlService.saveBowlUser(FirebaseAuth.getInstance().getUid(), new BowlUserRequest(bowlInfo, latitude, longitude)).enqueue(new Callback<BowlResponse>() {
            @Override
            public void onResponse(Call<BowlResponse> call, Response<BowlResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("scannerActivity", "bowl id : " + response.body().getId());
                }
            }

            @Override
            public void onFailure(Call<BowlResponse> call, Throwable t) {
                Log.d("scannerActivity", "error save bowl User : " + t.getMessage());
            }
        });
    }
}