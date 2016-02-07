package webb8.wathub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Carpool;
import webb8.wathub.models.Course;
import webb8.wathub.models.Favorite;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Message;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostType;
import webb8.wathub.models.Profile;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Profile.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(BookExchange.class);
        ParseObject.registerSubclass(Carpool.class);
        ParseObject.registerSubclass(Favorite.class);
        ParseObject.registerSubclass(GroupStudy.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(PostType.class);
        Parse.initialize(this);
        ParseUser user = ParseUser.getCurrentUser();

        if (user != null) {
            if (user.getBoolean("emailVerified")) {
                Intent postIntent = new Intent(this, PostActivity.class);
                startActivity(postIntent);
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_verify_email, Toast.LENGTH_LONG).show();
            }
        }

        Button logInButton = (Button) findViewById(R.id.action_log_in);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logInIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logInIntent);
            }
        });

        Button signUpButton = (Button) findViewById(R.id.action_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}
