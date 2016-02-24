package webb8.wathub.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import webb8.wathub.R;
import webb8.wathub.models.Profile;

public class SignUpActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Set up the sign up form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mFirstNameView = (EditText) findViewById(R.id.first_name);
        mLastNameView = (EditText) findViewById(R.id.last_name);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    signUp();
                    return true;
                }
                return false;
            }
        });

        // set sign up button
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    // TODO: 20/02/16 split this function
    // check user inputs
    private boolean checkFields(String firstName, String lastName, String email, String password) {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            mEmailView.requestFocus();
            return false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            mEmailView.requestFocus();
            return false;
        }

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            mPasswordView.requestFocus();
            return false;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            mPasswordView.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    // sign up the user
    private void signUp() {
        final String firstName = mFirstNameView.getText().toString();
        final String lastName = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean check = checkFields(firstName, lastName, email, password);

        if (check) {
            ParseUser user = new ParseUser();
            user.setUsername(email.substring(0, email.indexOf("@")));
            user.setEmail(email);
            user.setPassword(password);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        System.out.println("signed up");
                        Profile profile = new Profile();
                        profile.setFirstName(firstName);
                        profile.setLastName(lastName);
                        profile.setOwner(ParseUser.getCurrentUser());
                        profile.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    System.out.println("profile saved");
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.error_creating_profile, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Intent loginActivity = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(loginActivity);
                    } else {
                        System.out.println("error signing up");
                        Toast.makeText(getApplicationContext(), R.string.error_signing_up, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // TODO: 20/02/16 improve email check and move this to other class
    private boolean isEmailValid(String email) {
        return email.contains("@uwaterloo.ca");
    }

    // TODO: 20/02/16 improve password check and move this to other class
    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }
}
