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

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    //Declaration of the EditText variables that takes user email and password
    private EditText mEmail;
    private EditText mPassword;
    //Login button to log in the user if the details are correctn and user exists
    private Button btnLogin;
    //Signup textView to create a new user account
    private TextView btnSignUp;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tap_sign = findViewById(R.id.tap_sign);
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = getTheme().obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        tap_sign.setBackgroundResource(backgroundResource);

        email = findViewById(R.id.email);
       assignVariables();
        setup();


    }
    public void setup(){
        //Onclick listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String variables for user email and password
                 String userEmail;
                 String userPassword;
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
                finish();
            }
        });

    }
    //Method to assign all the varibles with their respective Ids
    public void assignVariables(){
        mEmail = findViewById(R.id.email);             //Initialize it adding its id that is in the xml layout file
        mPassword = findViewById(R.id.password);         //Initialize it adding its id that is in the xml layout file
        btnLogin = findViewById(R.id.button1);            //Initialize it adding its id that is in the xml layout file
        btnSignUp = findViewById(R.id.tap_sign);         //Initialize it adding its id that is in the xml layout file
    }


    /**Method to check whether the user has entered email and password or not
     *
     * @param email User email
     * @param password User password
     * @return returns true if user has filled both the fields else returns false
     */
    public boolean checkEntries(String email, String password){

        if("".equals(email)) {
            Toast.makeText(LoginActivity.this, "NAME cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if("".equals(password)){
            Toast.makeText(LoginActivity.this,"PASSWORD cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    /**Method to start the login process by verifying the details from the firebase
     * Takes the user to the home activity if the login is successful
     * else gives the reason for failure
     * @param email user email
     * @param password user password
     */

    public void loginUser(String email, String password){
        //Checking the status of the entries by the user
        boolean status = validateE();
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
                       // Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT);
                        Snackbar.make(findViewById(R.id.root_loginlayout), "Error " + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }
// function  to check status of inputted email
    private boolean validateE() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

}
