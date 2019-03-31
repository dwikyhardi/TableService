package root.example.com.tableservice.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import root.example.com.tableservice.R;
import root.example.com.tableservice.RegistrationForm;
import root.example.com.tableservice.SignUp;

public class AddUser extends Fragment {
    private FragmentActivity listener;
    private EditText etEmail, etPassword, etRePassword,etName;
    private TextView tvSignIn;
    private Button btnSignUp;
    private ProgressDialog progress;
    private Spinner Selectprivileges;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference setUser;
    private FirebaseUser mFirebaseUser;
    private String UserId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = (EditText) getActivity().findViewById(R.id.etEmail);
        etPassword = (EditText) getActivity().findViewById(R.id.etPassword);
        etRePassword = (EditText) getActivity().findViewById(R.id.etRePassword);
        etName = (EditText) getActivity().findViewById(R.id.etName);
        tvSignIn = (TextView) getActivity().findViewById(R.id.tvSignIn);
        btnSignUp = (Button) getActivity().findViewById(R.id.btnSignUp);
        progress = new ProgressDialog(getActivity());
        Selectprivileges = (Spinner) getActivity().findViewById(R.id.SelectPrivileges);
        String[] Kelas = getResources().getStringArray(R.array.privilage);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_style, Kelas);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Selectprivileges.setAdapter(mArrayAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        setUser = mFirebaseDatabase.getReference().child("TableService").child("User");
        mFirebaseAuth = FirebaseAuth.getInstance();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setTitle("Please Wait");
                progress.setCancelable(false);
                progress.show();
                String email = etEmail.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String repassword = etRePassword.getText().toString().trim();
                String privileges = Selectprivileges.getSelectedItem().toString().trim();
                if (privileges.equals("Select Privileges")) {
                    Toast.makeText(getActivity(), "Please Select The User Privileges", Toast.LENGTH_SHORT).show();
                } else if (password.equals(repassword)) {
                    AddNewUser(email, password, privileges,name);
                } else {
                    Toast.makeText(getActivity(), "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AddNewUser(String email, String password, String privileges, final String name) {
        final String privilege = privileges;
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    UserId = user.getUid();
                    switch (privilege) {
                        case "Admin":
                            setUser.child("Admin").child(UserId).child("Level").setValue("1");
                            setUser.child("Admin").child(UserId).child("UserId").setValue(UserId);
                            setUser.child("Admin").child(UserId).child("Name").setValue(name);
                            Toast.makeText(getActivity(), "Success Creating Admin Account", Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            break;
                        case "Cashier":
                            setUser.child("Cashier").child(UserId).child("Level").setValue("3");
                            setUser.child("Cashier").child(UserId).child("UserId").setValue(UserId);
                            setUser.child("Cashier").child(UserId).child("Name").setValue(name);
                            Toast.makeText(getActivity(), "Success Creating Cashier Account", Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            break;
                        case "Customer":
                            setUser.child("Customer").child(UserId).child("Level").setValue("5");
                            setUser.child("Customer").child(UserId).child("UserId").setValue(UserId);
                            setUser.child("Customer").child(UserId).child("Name").setValue(name);
                            Toast.makeText(getActivity(), "Success Creating Customer Account", Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            break;
                        case "Owner":
                            setUser.child("Owner").child(UserId).child("Level").setValue("2");
                            setUser.child("Owner").child(UserId).child("UserId").setValue(UserId);
                            setUser.child("Owner").child(UserId).child("Name").setValue(name);
                            Toast.makeText(getActivity(), "Success Creating Owner Account", Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            break;
                        case "Waiter":
                            setUser.child("Waiter").child(UserId).child("Level").setValue("4");
                            setUser.child("Waiter").child(UserId).child("UserId").setValue(UserId);
                            setUser.child("Waiter").child(UserId).child("Name").setValue(name);
                            Toast.makeText(getActivity(), "Success Creating Waiter Account", Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                            break;
                    }
                    etEmail.setText("");
                    etName.setText("");
                    etPassword.setText("");
                    etRePassword.setText("");
                } else {
                    Toast.makeText(getActivity(), "Unable to Create New User", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Unable to Create New User", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    public static AddUser newInstance() {
        return new AddUser();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
