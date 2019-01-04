package se.kth.id1212.taskmanagerandroidclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import se.kth.id1212.taskmanagerandroidclient.R;
import se.kth.id1212.taskmanagerandroidclient.net.APIResponseErrorException;
import se.kth.id1212.taskmanagerandroidclient.controller.Controller;
import se.kth.id1212.taskmanagerandroidclient.model.User;


/**
 * First activity that the user is presented with. Controls all the UI logic and views for creating
 * and log in to accounts.
 */
public class EmailPasswordActivity extends Activity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;
    private Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailpassword);
        this.controller = new Controller();
        // Views
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        // Buttons
        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            new CreateAccount().execute(user.getEmail(),user.getUid());
                        } else {
                            showToast(task.getException().getMessage());
                        }
                    }
                });
    }

    private void signIn(String email, String password){
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            User loggedInUser = new User(user.getEmail(),user.getUid());
                            loggedInSuccessful(loggedInUser);
                        } else {
                            showToast(task.getException().getMessage());
                        }
                    }
                });
    }


    /**
     * When logged in is successful change to MainActivity and send in the logged in user.
     * @param loggedInUser is therecently logged in user
     */
    private void loggedInSuccessful(User loggedInUser){
        Intent myIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LOGGED_IN_USER",loggedInUser);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }

    /**
     * Validates that the user has entered correct info into the required fields.
     * @return
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.emailSignInButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }

    /**
     * Helper function that can be called so that toasts are being created on UI thread.
     * @param message to be displayed
     */
    private void runOnUi(String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(message);
            }
        });
    }
    private void showToast(String message){
        Toast.makeText(EmailPasswordActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Doing API rest calls on another thread then the UI thread.
     */
    private class CreateAccount extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                controller.createAccount(strings[0], strings[1]);
            }catch(IOException ex){
                runOnUi("Network problem");
            }catch (APIResponseErrorException ex){
                runOnUi(ex.getMsg());
            }
            runOnUi("Account created");
            return null;
        }

    }

}
