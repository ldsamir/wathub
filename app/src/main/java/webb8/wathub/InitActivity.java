package webb8.wathub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseObject;

import webb8.wathub.models.BookExchange;
import webb8.wathub.models.Carpool;
import webb8.wathub.models.Course;
import webb8.wathub.models.Favorite;
import webb8.wathub.models.GroupStudy;
import webb8.wathub.models.Message;
import webb8.wathub.models.Post;
import webb8.wathub.models.PostType;
import webb8.wathub.models.Profile;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialization
        ParseObject.registerSubclass(Profile.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(BookExchange.class);
        ParseObject.registerSubclass(Carpool.class);
        ParseObject.registerSubclass(Favorite.class);
        ParseObject.registerSubclass(GroupStudy.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(PostType.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
