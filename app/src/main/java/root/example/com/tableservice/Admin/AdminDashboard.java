package root.example.com.tableservice.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import root.example.com.tableservice.MainActivity;
import root.example.com.tableservice.R;

public class AdminDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "AdminDashboard";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabaseReference;
    private String UserId;

    private long backPressedTime;
    private Toast backToast;
    private Boolean AddUserFragmentOpened = false;
    private Boolean AddOrderFragmentOpened = false;
    private Boolean AddMenuFragmentOpened = false;
    private Boolean AddTransactionFragmentOpened = false;
    private TextView NamaHeader, EmailHeader;

    AddUser mAddUser;
    AddOrder mAddOrder;
    AddMenu mAddMenu;
    AddTransaction mAddTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Dashboard");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        NamaHeader = (TextView) header.findViewById(R.id.TvNama);
        EmailHeader = (TextView) header.findViewById(R.id.TvEmail);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAddUser = new AddUser().newInstance();
        mAddOrder = new AddOrder().newInstance();
        mAddMenu = new AddMenu().newInstance();
        mAddTransaction = new AddTransaction().newInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (AddMenuFragmentOpened) {
            removeFragments(mAddMenu);
        } else if (AddUserFragmentOpened) {
            removeFragments(mAddUser);
        } else if (AddOrderFragmentOpened) {
            removeFragments(mAddOrder);
        } else if (AddTransactionFragmentOpened) {
            removeFragments(mAddTransaction);
        } else if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press Back Again to Exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            mFirebaseAuth.signOut();
            startActivity(new Intent(AdminDashboard.this, MainActivity.class));
            finish();
        } else if (id == R.id.AddUser) {
            mFragmentTransaction.replace(R.id.fragment_container, mAddUser, "Add User");
            mFragmentTransaction.attach(mAddUser);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
            AddUserFragmentOpened = true;

        } else if (id == R.id.AddOrder) {
            mFragmentTransaction.replace(R.id.fragment_container, mAddOrder, "Add User");
            mFragmentTransaction.attach(mAddOrder);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
            AddOrderFragmentOpened = true;

        } else if (id == R.id.AddReference) {
            mFragmentTransaction.replace(R.id.fragment_container, mAddMenu, "Add User");
            mFragmentTransaction.attach(mAddMenu);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
            AddMenuFragmentOpened = true;

        } else if (id == R.id.AddTransaction) {
            mFragmentTransaction.replace(R.id.fragment_container, mAddTransaction, "Add User");
            mFragmentTransaction.attach(mAddTransaction);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
            AddTransactionFragmentOpened = true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void removeFragments(Fragment mFragment) {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.detach(mFragment).commit();
        if (AddMenuFragmentOpened) {
            AddMenuFragmentOpened = false;
        } else if (AddUserFragmentOpened) {
            AddUserFragmentOpened = false;
        } else if (AddOrderFragmentOpened) {
            AddOrderFragmentOpened = false;
        } else if (AddTransactionFragmentOpened) {
            AddTransactionFragmentOpened = false;
        }
    }
}
