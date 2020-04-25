package in.tinyhouse.salesforce.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.tinyhouse.salesforce.R;
import in.tinyhouse.salesforce.ScannerActivity;
import in.tinyhouse.salesforce.billing.BillingActivity;
import in.tinyhouse.salesforce.models.Bill;

public class HomeActivity extends AppCompatActivity {
    //private variable for "salesforce" TextView
    private TextView mSalesforce;

    private static String getDate() {
        String currentDate = DateFormat.getDateInstance().format(new Date());
        String numDate = DateFormat.getDateInstance().format(new Date());
        int numIntDate = Integer.parseInt(numDate);
        if (numIntDate >= 11 && numIntDate <= 13)
            currentDate = currentDate + "th";
        else {
            switch (numIntDate % 10) {
                case 1:
                    currentDate = currentDate + "st";
                    break;
                case 2:
                    currentDate = currentDate + "nd";
                    break;
                case 3:
                    currentDate = currentDate + "rd";
                    break;
                default:
                    currentDate = currentDate + "th";
                    break;
            }
        }
        return currentDate;
    }

    private static String getTime() {
        return DateFormat.getTimeInstance().format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //assigning id to salesforce TextView
        mSalesforce = findViewById(R.id.salesforce);
        //assigning id to start a new bill textview
        //private variable to start a new bill
        TextView mStartNewBill = findViewById(R.id.new_bill_activity);
        //setting on click listner on new bill activity
        mStartNewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BillingActivity.class));
            }
        });
        //assigning id to scan a bill textview
        //private variable to scan a bill
        TextView mScanBill = findViewById(R.id.scan_bill);
        //setting on click listener on scan bill
        mScanBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for scanning new bill
                new IntentIntegrator(HomeActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });
        //assigning id to UserName TextView
        //private variable for userName TextView
        TextView mUserName = findViewById(R.id.user_name);
        //assigning id to initiate transaction button
        //private variable for new Bill Activity
        //assigning id to dateTime TextView
        //private variable for dateTime TextView
        TextView mDateTime = findViewById(R.id.date_time);
        mDateTime.setText(getDate() + " " + getTime());
        //assigning id to SlidingUpPanelLayout
        //private variable for "SlidingUpPanel"
        SlidingUpPanelLayout mSlidingUpPanel = findViewById(R.id.slideup);
        //FirebaseAuth to get the name of the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //private variable for currentLoggedInUser
        String mCurrentLoggedInUserName = user != null ? user.getDisplayName() : null;
        mUserName.setText("Welcome " + mCurrentLoggedInUserName + "!");
        //implementation for on setClickListener of slidingUpPanel layout
        mSlidingUpPanel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            //method for what layout to show on onPanelSlide
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Function for controls during panel slide
            }

            //method for what to do when state of Panel is changed that is either expanded or compressed
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if ("Collapsed".equalsIgnoreCase(newState.name())) {
                    mSalesforce.setText("salesforce");
                } else if ("Expanded".equalsIgnoreCase(newState.name())) {
                    mSalesforce.setText("Today's Activity");
                }

            }
        });
        RecyclerView recyclerView = findViewById(R.id.scrollable);
        //Arraylist for showing the transaction
        ArrayList<Bill> mtransactionBill = fetchTransactionList();
        RecyclerAdapterTransaction recyclerAdapter = new RecyclerAdapterTransaction(mtransactionBill);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Bill> fetchTransactionList() {
        //code for fetching the transaction list
        return new ArrayList<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                final String id = result.getContents();
                //Pass the bill id to billing Activity
                Intent intent = new Intent(getApplicationContext(), BillingActivity.class);
                intent.putExtra("bill_id", id);
                startActivity(intent);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
