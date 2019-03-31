package root.example.com.tableservice.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import root.example.com.tableservice.R;

public class AddUser extends Fragment {
    private FragmentActivity listener;
    private EditText etEmail, etPassword, etRePassword;
    private TextView tvSignIn;
    private Button btnSignUp;
    private ProgressDialog progress;

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
        tvSignIn = (TextView) getActivity().findViewById(R.id.tvSignIn);
        btnSignUp = (Button) getActivity().findViewById(R.id.btnSignUp);
        progress = new ProgressDialog(getActivity());

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
