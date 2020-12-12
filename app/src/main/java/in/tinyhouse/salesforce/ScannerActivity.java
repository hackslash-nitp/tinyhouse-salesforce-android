package in.tinyhouse.salesforce;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

//import android.view.View;
//import android.widget.Button;

public class ScannerActivity extends AppCompatActivity  {
   // private Button mBackArrow;
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        //initialize the back arrow but as there is no back arrow in the layout so I am commenting all the lines as the checks would fail
        /** mBackArrow = findViewById(R.id.back_arrow);
         mBackArrow.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        onBackPressed();
        }
        });
         **/

        //Initialize barcode scanner view
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        View mover = findViewById(R.id.mover);
        TranslateAnimation animation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.RELATIVE_TO_PARENT, -0.13f, TranslateAnimation.RELATIVE_TO_PARENT, 0.13f);
        animation.setDuration(1000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        mover.setAnimation(animation);
        //start capture
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        //To open 'edit_bill_summary' when 'edit' is pressed
        LinearLayout edit = findViewById(R.id.edit_bill_summary);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(ScannerActivity.this, EditScannerBill.class);
                startActivity(t);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed()
    {

        super.onBackPressed();
    }
}
