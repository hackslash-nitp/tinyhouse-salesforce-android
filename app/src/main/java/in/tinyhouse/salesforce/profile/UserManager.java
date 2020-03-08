package in.tinyhouse.salesforce.profile;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.tinyhouse.salesforce.models.User;

public class UserManager {
    private DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("users");
    private OnCompleteListener listener;

    public UserManager() {
        this.listener = null;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener){
        this.listener = onCompleteListener;
    }


    /**Method for  addition of details of a new user
     * in firebase database
     * @param user User object
     */
    public UserManager createUser(User user){
        user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onUserCreated();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onOperationFailed(e.getMessage());
                    }
                });
        return this;
    }

    /**Event to fetch user detils (if any) from the Firebase database
     *
     * @param userId userId of a user
     * @return User object containing the details from the firebase
     */
    public UserManager fetchUser(String userId){
        reff.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                listener.onUserFetched(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //If the firebase database operation got cancelled due to some reason
            }
        });
        return this;
    }


    public interface OnCompleteListener{
        void onUserCreated();

        void onUserFetched(User user);

        void onOperationFailed(String message);

    }


}
