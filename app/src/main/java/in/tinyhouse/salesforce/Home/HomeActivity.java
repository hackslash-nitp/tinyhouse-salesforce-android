package in.tinyhouse.salesforce.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.tinyhouse.salesforce.Billing.BillingActivity;
import in.tinyhouse.salesforce.R;

public class HomeActivity extends AppCompatActivity {
    private SlidingUpPanelLayout mLayout;
    private TextView mText;

    //private variable for "salesforce" TextView
    private TextView mSalesforce;
    //private variable for userName TextView
    private TextView mUserName;
    //private variable for dateTime TextView
    private TextView mDateTime;
    //private variable for new Bill Activity
    private TextView mBillActivity;
    //private variable for "SlidingUpPanel"
    private SlidingUpPanelLayout mSlidingUpPanel;
    //private variable for currentLoggedInUser
    private String mCurrentLoggedInUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //assigning id to salesforce TextView
        mSalesforce=(TextView)findViewById(R.id.salesforce);
        //assigning id to UserName TextView
        mUserName=(TextView)findViewById(R.id.user_name);
        //assigning id to initiate transaction button
        mBillActivity=(TextView)findViewById(R.id.new_bill_activity);
        mBillActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BillingActivity.class));
            }
        });
        //assigning id to dateTime TextView
        mDateTime=(TextView)findViewById(R.id.date_time);
        mDateTime.setText(getDate()+" "+getTime());
        //assigning id to SlidingUpPanelLayout
        mSlidingUpPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_up_paneLayout);
        //FirebaseAuth to get the name of the current user
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        mCurrentLoggedInUserName=user.getDisplayName();
        mUserName.setText("Welcome "+mCurrentLoggedInUserName+"!");
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
                if("Collapsed".equalsIgnoreCase(newState.name().toString())){
                    mSalesforce.setText("salesforce");
                }
                else if("Expanded".equalsIgnoreCase(newState.name().toString())){
                    mSalesforce.setText("Today's Activity");
                }

            }
        });
    }
    private static String getDate(){
        SimpleDateFormat dateFormat= new SimpleDateFormat("MMMMM dd");
        SimpleDateFormat dateDay= new SimpleDateFormat("dd");
        String currentDate= dateFormat.getDateInstance().format(new Date());
        String numDate = dateDay.getDateInstance().format(new Date());
        int numIntDate= Integer.parseInt(numDate);
        if(numIntDate>=11 && numIntDate<=13)
            currentDate=currentDate+"th";
        else{
            switch(numIntDate%10){
                case 1:currentDate=currentDate+"st";
                break;
                case 2:currentDate=currentDate+"nd";
                break;
                case 3:currentDate=currentDate+"rd";
                break;
                default:currentDate=currentDate+"th";
                break;
            }
        }
        return currentDate;
    }
    private static String getTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String currentTime=timeFormat.getTimeInstance().format(new Date());
        return currentTime;
    }
}
