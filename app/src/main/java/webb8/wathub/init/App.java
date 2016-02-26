package webb8.wathub.init;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseObject;

import java.io.File;

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
 * Created by mismayil on 08/02/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
        //Course.loadCourses(this);
    }
}
