package webb8.wathub.init;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

import webb8.wathub.hub.HubActivity;
import webb8.wathub.login.LoginActivity;
import webb8.wathub.R;
import webb8.wathub.login.SignUpActivity;
import webb8.wathub.models.Parsable;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {

    // UI fields
    private Button mLogInBtnView;
    private Button mSignUpBtnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ParseUser user = ParseUser.getCurrentUser();

        // check if user already logged in and email verified, then skip login
        if (user != null) {
            if (user.getBoolean(Parsable.KEY_EMAIL_VERIFIED)) {
                Intent postIntent = new Intent(this, HubActivity.class);
                startActivity(postIntent);
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_verify_email, Toast.LENGTH_LONG).show();
            }
        }

        // set login button
        mLogInBtnView = (Button) findViewById(R.id.action_log_in);
        mLogInBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logInIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logInIntent);
            }
        });

        // set sign up button
        mSignUpBtnView = (Button) findViewById(R.id.action_sign_up);
        mSignUpBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}
