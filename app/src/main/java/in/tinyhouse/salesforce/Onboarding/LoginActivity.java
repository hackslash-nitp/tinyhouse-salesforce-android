package in.tinyhouse.salesforce.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import in.tinyhouse.salesforce.Home.HomeActivity;
import in.tinyhouse.salesforce.R;

public class LoginActivity extends AppCompatActivity {

    String userEmail, userPassword;
    Button btnLogin;
    TextView btnSignUp;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        assignVariables();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(userEmail, userPassword);
            }
        });
        //By clicking the signup textview user is sent to signUp activity
        //Also creste a signUp activity named SignUpActivity to remove errors here
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

    }

    public void assignVariables(){
        EditText mEmail;             //Initialize it adding its id that is in the xml layout file
        userEmail = mEmail.getText().toString();        //String for user email
        EditText mPassword;         //Initialize it adding its id that is in the xml layout file
        userPassword = mPassword.getText().toString();   //String for user Password
        btnLogin;            //Initialize it adding its id that is in the xml layout file
        btnSignUp;         //Initialize it adding its id that is in the xml layout file


    }

    //Method to check the entries so that none of the field is blank
    public boolean checkEntries(String email, String password){
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            return false;
        }
        else{ return  true;}
    }


    public void loginUser(String email, String password){
        //Checking the status of the entries by the user
        boolean status = checkEntries(email, password);
        if(status){
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"User Logged In Successfully",Toast.LENGTH_SHORT);
                        //Creating intent to send user from Login Activity to Home Activity
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                    else{
                        //Message to the user if the login fails with reason
                        Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT);
                    }
                }
            });
        }

    }
}
