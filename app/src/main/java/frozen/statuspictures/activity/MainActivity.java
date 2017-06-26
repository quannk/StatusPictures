package frozen.statuspictures.activity;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.mz.A;
import com.mz.ZAndroidSystemDK;

import frozen.statuspictures.R;
import frozen.statuspictures.databse.DatabaseStatus;
import frozen.statuspictures.fragment.EditPhotoFragment;
import frozen.statuspictures.fragment.ImageFragment;
import frozen.statuspictures.fragment.MenuFragment;

public class MainActivity extends Activity  {
    public static ImageFragment imageFragment;
    public static EditPhotoFragment editPhotoFragment;
    public static DatabaseStatus databaseStatus;
    public static MenuFragment menuFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu);
        openDB();
        initFragment();
        showFragment(menuFragment);
        ZAndroidSystemDK.init(this);
        A.f(this);
    }

    private void openDB() {
        databaseStatus = new DatabaseStatus();
        databaseStatus.copyDatabase(this);
    }

    private void initFragment() {
        imageFragment = new ImageFragment();
        menuFragment = new MenuFragment();
    }

    public void addFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.e("TAG","show fragment");
    }
}
