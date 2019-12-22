package in.tinyhouse.salesforce.Onboarding;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.tinyhouse.salesforce.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText signupemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView tapsignup = findViewById(R.id.tapsignup);
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = getTheme().obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        tapsignup.setBackgroundResource(backgroundResource);
        Button btnsignup;
        signupemail = findViewById(R.id.signupemail);
        btnsignup = findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
            }
        });
    }

    private boolean validateEmail() {
        String emailInput = signupemail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            signupemail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            signupemail.setError("Please enter a valid email");
            return false;
        } else {
            signupemail.setError(null);
            return true;

        }
    }
}
