package webb8.wathub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        if (user == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            if (user.getBoolean("emailVerified")) {
                Intent postIntent = new Intent(this, PostActivity.class);
                startActivity(postIntent);
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_verify_email, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
