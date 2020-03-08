package in.tinyhouse.salesforce.onboarding;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import in.tinyhouse.salesforce.R;
import in.tinyhouse.salesforce.home.HomeActivity;
import in.tinyhouse.salesforce.models.User;
import in.tinyhouse.salesforce.profile.UserManager;

public class SignUpActivity extends AppCompatActivity {

    //String variables for user name, email and password
    private String userName;
    private String userEmail;
    private String userPassword;

    //Declaration of the EditText variables that takes user name, phone, email and password
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mPassword;

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
    public void setup() {
        //click listener for signup button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //As the signup but is clicked assigning the entered name, phone,  email
                //and  password to String from the EditText field and starting
                //the signup method
                userName = mName.getText().toString();
                String userPhone = mPhone.getText().toString();
                userEmail = mEmail.getText().toString();
                userPassword = mPassword.getText().toString();
                signUpUser(userName, userPhone, userEmail, userPassword);


            }
        });
        //click listener for login textView
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sends user to login activity
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });


        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = getTheme().obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        btnLogin.setBackgroundResource(backgroundResource);
    }

    //Method to assign all the variables with their respective Ids
    public void assignVariables() {
        mName = findViewById(R.id.user_name);
        mPhone = findViewById(R.id.user_phone);
        mEmail = findViewById(R.id.signupemail);
        mPassword = findViewById(R.id.user_password);
        btnLogin = findViewById(R.id.tapsignup);
        btnSignUp = findViewById(R.id.btnsignup);
    }


    /**
     * Method to start the sign up process
     * Takes the user to the home activity if the signup is successful
     * else gives the reason for failure
     *
     * @param name     name of the user
     * @param phone    phone phone no. of the user
     * @param email    email address of the user
     * @param password password of the user
     */
  
    public void signUpUser(final String name, final String phone, final String email, final String password){
        //Checking if the user has entered the credentials or not
        boolean status = validateEmail() && checkEntries(name, phone, email, password);
        //Starting the signup activity if and only if user has entered all the credentials
        if (status) {
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        //Creating a new user object
                        User user = new User();
                        //Setting the user details in the user object
                        user.setName(name);
                        user.setPhoneNumber(phone);
                        user.setEmail(email);
                        //Creating a user manager object
                        UserManager userManager = new UserManager();
                        //Using the user manager object to save
                        //the deatils of the user in firebase database
                        userManager.createUser(user).setOnCompleteListener(new UserManager.OnCompleteListener() {

                            @Override
                            public void onUserCreated() {
                                //If signup and user creation is successful sending user to home activity
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }

                            @Override
                            public void onUserFetched(User user) {
                                //if the user details are fetched from the firebase database
                            }

                            @Override
                            public void onOperationFailed(String message) {
                                //if the firebase database operation gets failed
                                failedSignUpSnackbarMessage(message);
                            }
                        });

                    } else {
                        Snackbar.make(findViewById(R.id.root_signuplayout), "Error " + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validateEmail() {
        String emailInput = mEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            mEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mEmail.setError("Please enter a valid email");
            return false;
        } else {
            mEmail.setError(null);
            return true;

        }
    }
    /**
     * Method to check if the user has entered all the credentials
     * or not
     *
     * @param name     name of the user
     * @param phone    phone no. of the user
     * @param email    email address of the user
     * @param password password of the user
     * @return returns true if all the credentials are  entered by the user
     */

    public boolean checkEntries(String name, String phone, String email, String password) {

        if ("".equals(name)) {
            Toast.makeText(SignUpActivity.this, "NAME cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(phone)) {
            Toast.makeText(SignUpActivity.this, "PHONE cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(email)) {
            Toast.makeText(SignUpActivity.this, "EMAIL cannot be empty", Toast.LENGTH_SHORT).show();

            return false;
        
        } else if ("".equals(password)) {
            Toast.makeText(SignUpActivity.this, "PASSWORD cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }


    public void failedSignUpSnackbarMessage(String message) {
        Snackbar.make(findViewById(R.id.root_signuplayout), "Error " + message, Snackbar.LENGTH_SHORT).show();
    }


}
