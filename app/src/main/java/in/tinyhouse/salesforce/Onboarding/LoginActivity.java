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

    //String variables for user email and password
    private String userEmail, userPassword;
    //Declaration of the EditText variables that takes user email and password
    private EditText mEmail, mPassword;
    //Login button to log in the user if the details are correctn and user exists
    private Button btnLogin;
    //Signup textView to create a new user account
    private TextView btnSignUp;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        assignVariables();
        setup();


    }
    public void setup(){
        //Onclick listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //As the login but is clicked assigning the entered email
                //and  password to String from the EditText field and starting
                //the login method
                userEmail = mEmail.getText().toString();
                userPassword = mPassword.getText().toString();
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
    //Method to assign all the varibles with their respective Ids
    public void assignVariables(){
        mEmail = (EditText) findViewById(R.id.user_email);             //Initialize it adding its id that is in the xml layout file
        mPassword = (EditText) findViewById(R.id.user_password);         //Initialize it adding its id that is in the xml layout file
        btnLogin = (Button) findViewById(R.id.btn_login);            //Initialize it adding its id that is in the xml layout file
        btnSignUp = (TextView) findViewById(R.id.signup_textView);         //Initialize it adding its id that is in the xml layout file
    }


    /**Method to check whether the user has entered email and password or not
     *
     * @param email User email
     * @param password User password
     * @return returns true if user has filled both the fields else returns false
     */
    public boolean checkEntries(String email, String password){
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            return false;
        }
        else{ return  true;}
    }

    /**Method to start the login process by verifying the details from the firebase
     * Takes the user to the home activity if the login is successful
     * else gives the reason for failure
     * @param email user email
     * @param password user password
     */

    public void loginUser(String email, String password){
        //Checking the status of the entries by the user
        boolean status = checkEntries(email, password);
        //only if the status is true then starting the login process
        if(status){
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"User Logged In Successfully",Toast.LENGTH_SHORT);
                        //Creating intent to send user from Login Activity to Home Activity for successful login
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
