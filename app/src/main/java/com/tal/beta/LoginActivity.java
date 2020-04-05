package com.tal.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.INVISIBLE;
import static com.tal.beta.FBref.refAuth;
import static com.tal.beta.FBref.refUsers;

public class LoginActivity extends AppCompatActivity {

    TextView tvTitle, tvRegister;
    EditText etName, etPhone, etEmail, etPass;
    CheckBox cbStayconnect, cbUType;
    Button btnLogin;

    String name, phone, email, password, uid;
    User userdb;
    Boolean stayConnect, registered, firstrun, uTypeBoo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        etPhone = (EditText) findViewById(R.id.etPhone);
        cbUType = (CheckBox) findViewById(R.id.cbUType);
        cbStayconnect = (CheckBox) findViewById(R.id.cbStayconnect);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        stayConnect = false;
        registered = true;

        SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        firstrun = settings.getBoolean("firstRun", false);
        Toast.makeText(this, "" + firstrun, Toast.LENGTH_SHORT).show();
        if (firstrun) {
            tvTitle.setText("Register");
            etName.setVisibility(View.VISIBLE);
            etPhone.setVisibility(View.VISIBLE);
            btnLogin.setText("Register");
            registered = false;
            logoption();
        } else regoption();
    }

    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     * <p>
     */
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        Boolean isChecked = settings.getBoolean("stayConnect", false);
        Intent si = new Intent(LoginActivity.this, ListActivity.class);
        if (refAuth.getCurrentUser() != null && isChecked) {
            stayConnect = true;
            startActivity(si);
        }
    }

    /**
     * On activity pause - If logged in & asked to be remembered - kill activity.
     * <p>
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    private void regoption() {
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tvTitle.setText("Register");
                etName.setVisibility(View.VISIBLE);
                etPhone.setVisibility(View.VISIBLE);
                btnLogin.setText("Register");
                registered = false;
                logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegister.setText(ss);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tvTitle.setText("Login");
                etName.setVisibility(View.INVISIBLE);
                etPhone.setVisibility(View.INVISIBLE);
                btnLogin.setText("Login");
                registered = true;
                regoption();
            }
        };
        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegister.setText(ss);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Logging in or Registering to the application
     * Using:   Firebase Auth with email & password
     * Firebase Realtime database with the object User to the branch Users
     * If login or register process is Ok saving stay connect status & pass to next activity
     * <p>
     */
    public void logorreg(View view) {
        email = etEmail.getText().toString();
        password = etPass.getText().toString();
        if (registered) {
            final ProgressDialog pd = ProgressDialog.show(this, "Login", "Connecting...", true);
            refAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("stayConnect", cbStayconnect.isChecked());
                                editor.commit();
                                Log.d("MainActivity", "signinUserWithEmail:success");
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                Intent si = new Intent(LoginActivity.this, ListActivity.class);
                                startActivity(si);
                            } else {
                                Log.d("MainActivity", "signinUserWithEmail:fail");
                                Toast.makeText(LoginActivity.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            name = etName.getText().toString();
            phone = etPhone.getText().toString();
            uTypeBoo = cbUType.isChecked();

            final ProgressDialog pd = ProgressDialog.show(this, "Register", "Registering...", true);
            refAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("stayConnect", cbStayconnect.isChecked());
                                editor.putBoolean("firstRun", false);
                                editor.commit();
                                Log.d("MainActivity", "createUserWithEmail:success");
                                FirebaseUser user = refAuth.getCurrentUser();
                                uid = user.getUid();
                                userdb = new User(name, email, phone, uTypeBoo, uid);
                                refUsers.child(name).setValue(userdb);
                                Toast.makeText(LoginActivity.this, "Successful registration", Toast.LENGTH_LONG).show();
                                Intent si = new Intent(LoginActivity.this, ListActivity.class);
                                startActivity(si);
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(LoginActivity.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                                else {
                                    Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "User create failed.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogin) {
            Intent si = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(si);
        }
        return true;
    }
}
