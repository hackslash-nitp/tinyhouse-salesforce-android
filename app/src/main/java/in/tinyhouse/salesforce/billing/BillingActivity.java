package in.tinyhouse.salesforce.billing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import in.tinyhouse.salesforce.R;
import in.tinyhouse.salesforce.models.Bill;

public class BillingActivity extends AppCompatActivity {
    //private variable  for customer name
    private TextView mCustomerName;
    //private variable for  date
    private TextView mDate;
    //private variable for  Store name
    private TextView mStoreName;
    //private variable for Bill Number
    private TextView mBillNumber;
    //private variable for  Total Price
    private TextView mTotalPrice;
    //private variable for print
    private TextView mprint;
    //private variable for share
    private TextView mshare;
    // Bill object
    private Bill bill;
    private LinearLayout llPdf; //private variable for linear layout containing bill details
    private Bitmap bitmap;
    private String billId;
    private File file;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems= new ArrayList<>();
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        //Initializing the private variables using ids present in xml layout file
        llPdf = findViewById(R.id.llPdf);
        mCustomerName = findViewById(R.id.customerName);
        mDate = findViewById(R.id.date);
        mStoreName = findViewById(R.id.storeName);
        mBillNumber = findViewById(R.id.bill_num);
        mTotalPrice = findViewById(R.id.total_price);
        //private variable for list of items
        ListView mItemsList = findViewById(R.id.items_list);
        //private variable for back option
        TextView backButton = findViewById(R.id.back);
        mprint = findViewById(R.id.print);
        mshare = findViewById(R.id.share);
        mItemsList = findViewById(R.id.items_list);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        mItemsList.setAdapter(adapter);

        //intent to receive bill id
        Intent intentContainingBillId = getIntent();
        //check if intent contains extras
        if(intentContainingBillId.hasExtra("bill_id")){
            //variable to store the received id
            billId = intentContainingBillId.getStringExtra("bill_id");
            //Retrieving an instance of database and getting the reference of location
            //private variable for Firebase Database Reference
            DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
            //Entering the received bill id as child of "bills" present in database
            // Read from the database
            mReference.child("bills").child(billId).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bill = dataSnapshot.getValue(Bill.class);
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //Declaring and initializing variables with bill data from firebase
                    assert bill != null;
                    String customerName = bill.getCustomerName();
                    String date = bill.getTimeStamp();
                    String StoreName = bill.getStoreName();
                    String billNumber = bill.getBillNumber();
                    String totAmt = bill.getTotalAmount().toString();
                    HashMap<String, Integer> listV = bill.getProductList();
                    // Inserting bill data in respective elements of xml layout
                    mCustomerName.setText("for " + customerName);
                    mDate.setText(date);
                    mStoreName.setText(StoreName);
                    mBillNumber.setText("Bill No. " + billNumber);
                    mTotalPrice.setText("Rs. " + totAmt);
                    mCustomerName.setText(customerName);
                    //fetching item and its price as key value pair from firebase  and then adding it to list
                    for (String key : listV.keySet()) {
                        Integer value = listV.get(key);
                        listItems.add(key + "\t\t\t Rs " + value);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Add message when fails to read value
                }
            });
        }
        //on clicking back option returns  to the activity which called this activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //write Permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //print option  opens print dialog box
                        mprint.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("size", " " + llPdf.getWidth() + "  " + llPdf.getWidth());
                                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                                createPdf();
                                printPDF();
                            }
                        });
                        //  share option opens share dialog box
                        mshare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("size", " " + llPdf.getWidth() + "  " + llPdf.getWidth());
                                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                                createPdf();
                                sharePDF();
                            }
                        });
                    }
                    //On permission denied
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }
    //function to close the current activity which works on clicking back
    public void onBackPressed(){
        this.finish();

    }
    //function to create pdf
    private void createPdf() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHeight = (int) height;
        int convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        File dir = getApplicationContext().getFilesDir();
        file = new File(dir, billId);
        try {
            Log.d("Create File ", "THe file path=" + file.getAbsolutePath());
            file.createNewFile();
            document.writeTo(new FileOutputStream(file.getAbsolutePath()));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();
    }
    //share the pdf
    private void sharePDF() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri selectedUri = Uri.parse(file.getAbsolutePath());
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_STREAM, selectedUri);
        startActivity(Intent.createChooser(intent, "Share File"));
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }
    //Print  pdf
    private void printPDF() {
        PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(BillingActivity.this, file.getAbsolutePath());
            assert printManager != null;
            printManager.print("Document",printDocumentAdapter,new PrintAttributes.Builder().build());
        }catch (Exception ex)
        {
            Log.e("Salesforce",""+ex.getMessage());
        }
    }
}
