package org.techtown.catsby.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.Result;

import org.techtown.catsby.qrcode.data.model.BowlResponse;
import org.techtown.catsby.qrcode.data.model.BowlUserRequest;
import org.techtown.catsby.qrcode.data.service.QRBowlService;
import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    TextView txt;
    CodeScanner codeScanner;
    CodeScannerView codeScannerView;

    private QRBowlService bowlService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        bowlService = RetrofitClient.getQrBowlService();

        txt = (TextView) findViewById(R.id.textView);
        codeScannerView = (CodeScannerView) findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, codeScannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt.setText(result.getText());
                        saveBowlUser(result.getText());
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

    private void saveBowlUser(String bowlInfo) {
        bowlService.saveBowlUser(FirebaseAuth.getInstance().getUid(), new BowlUserRequest(bowlInfo)).enqueue(new Callback<BowlResponse>() {
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