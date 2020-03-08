package in.tinyhouse.salesforce.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.tinyhouse.salesforce.R;
import in.tinyhouse.salesforce.models.Bill;

public class RecyclerAdapterTransaction extends RecyclerView.Adapter<RecyclerAdapterTransaction.ViewHolder> {

    private List<Bill> mBill;

    RecyclerAdapterTransaction(List<Bill> bill) {
        mBill = bill;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View transactionRecyclerView = layoutInflater.inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(transactionRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = mBill.get(position);

        TextView billNo = holder.mBillNo;
        billNo.setText(bill.getBillNumber());

        TextView billDate = holder.mBillDateTime;
        billDate.setText(bill.getTimeStamp());

        TextView custName = holder.mCustomerName;
        custName.setText(bill.getCustomerName());

        TextView tAmount = holder.mTotalAmount;
        tAmount.setText(String.valueOf(Double.valueOf(bill.getPaidAmount()) - Double.valueOf(bill.getReturnedAmount())));

        TextView viewBill = holder.mViewBill;
        viewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for viewing the bill
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mBillNo;
        private TextView mBillDateTime;
        private TextView mCustomerName;
        private TextView mTotalAmount;
        private TextView mViewBill;

        ViewHolder(View itemView) {
            super(itemView);
            mBillNo = itemView.findViewById(R.id.Bill_Number);
            mBillDateTime = itemView.findViewById(R.id.Bill_Time);
            mCustomerName = itemView.findViewById(R.id.customer_Name);
            mTotalAmount = itemView.findViewById(R.id.total_Amount);
            mViewBill = itemView.findViewById(R.id.labelclick);
        }
    }

    @Override
    public int getItemCount() {
        return mBill.size();
    }
}


