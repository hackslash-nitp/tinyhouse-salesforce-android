package in.tinyhouse.salesforce.billing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import in.tinyhouse.salesforce.R;
import in.tinyhouse.salesforce.home.HomeActivity;

public class BillComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_complete);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(BillComplete.this, HomeActivity.class));
                finish();
            }
        }, 2000);
    }
}
