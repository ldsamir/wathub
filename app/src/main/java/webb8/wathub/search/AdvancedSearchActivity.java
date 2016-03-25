package webb8.wathub.search;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import webb8.wathub.R;
import webb8.wathub.search.fragments.AdvancedSearchFragment;

public class AdvancedSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.search_fragment_container, new AdvancedSearchFragment()).commit();
    }
}
