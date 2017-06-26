package frozen.statuspictures;

import com.mz.ZAndroidSystemDK;

/**
 * Created by QuanNguy on 15/06/2017.
 */

public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ZAndroidSystemDK.initApplication(this, getPackageName());
    }
}
