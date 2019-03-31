package root.example.com.tableservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import root.example.com.tableservice.Admin.AdminDashboard;
import root.example.com.tableservice.Cashier.CashierDashboard;
import root.example.com.tableservice.Customer.CustomerDashboard;
import root.example.com.tableservice.Owner.OwnerDashboard;
import root.example.com.tableservice.Waiter.WaiterDashboard;

public class RegistrationForm extends AppCompatActivity {
    private final String TAG = "RegistrationForm";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference setUser;
    private FirebaseUser mFirebaseUser;
    private String UserId,Privilages;

    private EditText etName,etPhoneNumber;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        Privilages = getIntent().getStringExtra("Privilages");
        etName = (EditText) findViewById(R.id.etName);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        setUser = mFirebaseDatabase.getReference().child("TableService").child("User");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        UserId = user.getUid();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = etName.getText().toString().trim();
                masukData(Name);
            }
        });
    }

    private void masukData(String name) {
        setUser.child(Privilages).child(UserId).child("Nama").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(RegistrationForm.this, CustomerDashboard.class));
                /*if (Privilages.equals("Admin")){
                    startActivity(new Intent(RegistrationForm.this, AdminDashboard.class));
                }else if(Privilages.equals("Cashier")){
                    startActivity(new Intent(RegistrationForm.this, CashierDashboard.class));
                }else if (Privilages.equals("Customer")){
                    startActivity(new Intent(RegistrationForm.this, CustomerDashboard.class));
                }else if (Privilages.equals("Owner")){
                    startActivity(new Intent(RegistrationForm.this, OwnerDashboard.class));
                }else if (Privilages.equals("Waiter")){
                    startActivity(new Intent(RegistrationForm.this, WaiterDashboard.class));
                }*/
            }
        });
    }
}
