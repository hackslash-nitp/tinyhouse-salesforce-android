package in.tinyhouse.salesforce.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import in.tinyhouse.salesforce.R;

public class HomeActivity extends AppCompatActivity {
    private SlidingUpPanelLayout mLayout;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mLayout = findViewById(R.id.slideup);
        mText = findViewById(R.id.salesforce);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Function for controls during panel slide
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    mText.setText("Today's activity");
                } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    mText.setText("salesforce");
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mLayout != null && mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
