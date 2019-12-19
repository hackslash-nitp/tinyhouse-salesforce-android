package in.tinyhouse.salesforce.Profile;


import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.tinyhouse.salesforce.Models.User;

public class UserManager {
    private DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User");

    public UserManager() {}


    /**Method for  addition of details of a new user
     * in firebase database
     * @param user User object
     */
    public void createUser(User user){
        reff.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Write successfull
            }
        });
    }

    /**Event to fetch user detils (if any) from the Firebase database
     *
     * @param userId userId of a user
     * @return User object containing the details from the firebase
     */
    public User fetchUser(String userId){
        final User currentUser = new User();
        reff = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.child("id").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String phone = dataSnapshot.child("phoneNumber").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                currentUser.setId(id);
                currentUser.setName(name);
                currentUser.setPhoneNumber(phone);
                currentUser.setEmail(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return currentUser;
    }

    public interface OnCompleteListener{
        public void onUserCreated();
        public void onUserFetched(User user);
        public void onOperationFailed();

    }


}
