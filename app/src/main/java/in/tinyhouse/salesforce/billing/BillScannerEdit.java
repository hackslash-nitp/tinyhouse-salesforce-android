package in.tinyhouse.salesforce.billing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import in.tinyhouse.salesforce.R;

public class BillScannerEdit extends AppCompatActivity implements BillEditRecyclerAdapter.ListItemClick {
    private ArrayList<String> arrayList = new ArrayList<String>();
    private RecyclerView mBillEditRecycler;
    private TextView itemsQuantityTv;
    private SharedPreferences sharedPreferences;
    private BillEditRecyclerAdapter adapter;
    private TextView backBtn;

    @Override
    public void onBackPressed() {
        dataSave();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_scanner_edit);
        Set<String> idSet = new HashSet<String>();
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        idSet = sharedPreferences.getStringSet("ids", null);
        if (idSet != null) {
            arrayList.addAll(idSet);
        }
        setup();
        itemsQuantityTv.setText(arrayList.size() + " items");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mBillEditRecycler.setLayoutManager(manager);
        mBillEditRecycler.setHasFixedSize(true);
        adapter = new BillEditRecyclerAdapter(arrayList, this);
        mBillEditRecycler.setAdapter(adapter);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSave();
                Intent intent = new Intent(BillScannerEdit.this, BillScanner.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setup() {
        itemsQuantityTv = (TextView) findViewById(R.id.items_quantity);
        mBillEditRecycler = (RecyclerView) findViewById(R.id.edit_bill_rv);
        backBtn = (TextView) findViewById(R.id.back_btn);
    }

    @Override
    public void onListItemClick(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        itemsQuantityTv.setText((this.arrayList).size() + " items");
    }

    private void dataSave() {
        Double price = adapter.getPrice();
        Set<String> finalIds = new HashSet<String>();
        finalIds.addAll(arrayList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("totalPrice", String.valueOf(price));
        editor.putStringSet("finalIds", finalIds);
        editor.apply();
    }
}
