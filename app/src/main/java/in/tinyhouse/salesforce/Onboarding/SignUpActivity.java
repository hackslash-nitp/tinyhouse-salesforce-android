package in.tinyhouse.salesforce.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import in.tinyhouse.salesforce.Models.User;
import in.tinyhouse.salesforce.Profile.UserManager;
import in.tinyhouse.salesforce.R;

public class SignUpActivity extends AppCompatActivity {

    //String variables for user name, phone, email and password
    private String userName, userPhone ,userEmail, userPassword;
    //Declaration of the EditText variables that takes user name, phone, email and password
    private EditText mName, mPhone ,mEmail, mPassword;
    //Login button to create new user account
    private Button btnSignUp;
    //Signup textView to sign in the user if user already exists
    private TextView btnLogin;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        assignVariables();
        setup();
    }

    //Method that contain click listeners for the buttons
    public void setup(){
        //click listener for signup button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //As the signup but is clicked assigning the entered name, phone,  email
                //and  password to String from the EditText field and starting
                //the signup method
                userName = mName.getText().toString();
                userPhone = mPhone.getText().toString();
                userEmail = mEmail.getText().toString();
                userPassword = mPassword.getText().toString();
                signUpUser(userName, userPhone, userEmail, userPassword);
                //Creating a new user object
                User user = new User();
                //Setting the user details in the user object
                user.setName(userName);
                user.setPhoneNumber(userPhone);
                user.setEmail(userEmail);
                UserManager userManager = new UserManager();
                userManager.createUser(user);




            }
        });
        //click listener for login textView
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sends user to login activity
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    //Method to assign all the variables with their respective Ids
    public void assignVariables(){
        mName = findViewById(R.id.user_name);
        mPhone = findViewById(R.id.user_phone);
        mEmail = findViewById(R.id.user_email);
        mPassword = findViewById(R.id.user_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    /**Method to check if the user has entered all the credentials
     * or not
     * @param name name of the user
     * @param phone phone no. of the user
     * @param email email address of the user
     * @param password password of the user
     * @return returns true if all the credentials are  entered by the user
     */

    public boolean checkEntries(String name, String phone, String email, String password){
        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password);
    }

    /**Method to start the sign up process
     * Takes the user to the home activity if the signup is successful
     * else gives the reason for failure
     *
     * @param name name of the user
     * @param phone phone phone no. of the user
     * @param email email address of the user
     * @param password password of the user
     */

    public void signUpUser(String name, String phone, String email, String password){
        //Checking if the user has entered the credentials or not
        boolean status = checkEntries(name, phone, email, password);
        //Starting the signup activity if and only if user has entered all the credentials
        if(status){
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        //If signup is successful sending user to home activity
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
