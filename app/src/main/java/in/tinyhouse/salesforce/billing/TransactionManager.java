package in.tinyhouse.salesforce.billing;

//Class starts from here

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.tinyhouse.salesforce.models.Bill;

public class TransactionManager{
    //Firebase database refernce variable
    private DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("transactions");
    private OnCompleteListener listener;

    //Constructor initialising the listener to null
    public TransactionManager() {
        this.listener = null;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener){
        this.listener = onCompleteListener;
    }

    /**Method for  addition of details of a new trasaction
     * in firebase database
     * @param bill Bill object
     */
    public TransactionManager createTransaction(Bill bill){
        reff.child(bill.getId()).setValue(bill).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onTransactionCreated();
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

    /**Event to fetch transaction detils (if any) from the Firebase database
     *
     * @param transactionId transactionId of a user
     * @return bill object containing the details from the firebase
     */
    public TransactionManager fetchTransaction(String transactionId){
        reff.child(transactionId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                listener.onTransactionFetched(bill);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //If the firebase database operation got cancelled due to some reason
            }
        });
        return this;
    }


    public interface OnCompleteListener{
        void onTransactionCreated();

        void onTransactionFetched(Bill bill);

        void onOperationFailed(String message);

    }


}