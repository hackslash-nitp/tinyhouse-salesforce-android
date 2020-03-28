package in.tinyhouse.salesforce.billing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.tinyhouse.salesforce.R;
import in.tinyhouse.salesforce.models.Product;

public class BillEditRecyclerAdapter extends RecyclerView.Adapter<BillEditRecyclerAdapter.BillEditHolder> {
    private final ArrayList<String> list;
    private final ListItemClick mListener;
    private DatabaseReference mReference;
    private double finalPrice = 0;


    /*Constructor of the adapter class that takes
     * an arraylist of Product class*/
    public BillEditRecyclerAdapter(ArrayList<String> arrayList, ListItemClick listener) {
        list = arrayList;
        mListener = listener;
    }

    public Double getPrice() {
        return finalPrice;
    }

    /*This method inflates the recycler_bill layout file
     * and then returns a BillEditHolder object*/
    @NonNull
    @Override
    public BillEditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_bill, parent, false);
        return new BillEditHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillEditHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ListItemClick {
        void onListItemClick(ArrayList<String> arrayList);
    }
    /*Holder class*/

    public class BillEditHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemNameTv;
        private TextView mItemPriceTv;
        private TextView mQuantityTv;
        private Button btnIncrement;
        private Button btnDecrement;
        private Button btnClose;
        private Product po;

        /*Constructor to initialize all the views*/
        public BillEditHolder(@NonNull View itemView) {
            super(itemView);
            mItemNameTv = itemView.findViewById(R.id.recycler_bill_item);
            mItemPriceTv = itemView.findViewById(R.id.recycler_bill_price_textview);
            mQuantityTv = itemView.findViewById(R.id.recycler_bill_quantity_textview);
            btnIncrement = itemView.findViewById(R.id.recycler_bill_inc_button);
            btnDecrement = itemView.findViewById(R.id.recycler_bill_dec_button);
            btnClose = itemView.findViewById(R.id.recycler_bill_cross_button);
        }

        /*bind function takes an int as variable and
         * show the data from the array list in the views based
         * on the position of views*/
        public void bind(int position) {
            mReference = FirebaseDatabase.getInstance().getReference();
            String key = list.get(position);
            mReference.child("store").child("inventory").child(key)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            po = dataSnapshot.getValue(Product.class);
                            String name = po.getProductName();
                            String price = po.getPrice();
                            mItemNameTv.setText(name);
                            mItemPriceTv.setText("Rs. " + price);
                            mQuantityTv.setText("1");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //If database operation is failed
                        }
                    });

            btnIncrement.setOnClickListener(this);
            btnDecrement.setOnClickListener(this);
            btnClose.setOnClickListener(this);
            finalPrice += Double.parseDouble(po.getPrice());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v.getId() == btnIncrement.getId()) {
                //implementation of increment button
                int nItem = Integer.parseInt(mQuantityTv.getText().toString());
                nItem++;
                mQuantityTv.setText(String.valueOf(nItem));
                double eachItemPrice = Double.parseDouble(po.getPrice());
                double totalItemPrice = eachItemPrice * nItem;
                mItemPriceTv.setText("Rs. " + totalItemPrice);
                finalPrice += eachItemPrice;
            } else if (v.getId() == btnDecrement.getId()) {
                //implementation of decrement button
                int nItem = Integer.parseInt(mQuantityTv.getText().toString());
                if (nItem > 1) {
                    nItem--;
                    mQuantityTv.setText(String.valueOf(nItem));
                    double eachItemPrice = Double.parseDouble(po.getPrice());
                    double totalItemPrice = eachItemPrice * nItem;
                    mItemPriceTv.setText("Rs. " + totalItemPrice);
                    finalPrice -= eachItemPrice;
                }
            } else if (v.getId() == btnClose.getId()) {
                String priceString = mItemPriceTv.getText().toString();
                String[] div = priceString.split(" ");
                Double price = Double.parseDouble(div[1]);
                finalPrice -= price;
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                mListener.onListItemClick(list);
            }
        }
    }
}
