package loopback.edina.ac.uk.loopbackclient;

import android.app.Application;

import com.parse.Parse;
import com.strongloop.android.loopback.RestAdapter;

public class LoopbackApplication extends Application {
    RestAdapter adapter;

    String nativeUrl = "http://129.215.169.232:3001/api";
    //String emulatorUrl = "http://10.0.2.2:3000/api";
    public RestAdapter getLoopBackAdapter() {
        if (adapter == null) {
            // Instantiate the shared RestAdapter. In most circumstances,
            // you'll do this only once; putting that reference in a singleton
            // is recommended for the sake of simplicity.
            // However, some applications will need to talk to more than one
            // server - create as many Adapters as you need.
            adapter = new RestAdapter(
                    getApplicationContext(), nativeUrl);

        }
        return adapter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        String clientKey = BuildConfig.CLIENT_KEY;

        Parse.initialize(this, "IaBkLFLDm1QxZuxrq0haylgtTvhWPt0cMNgEJJLZ", clientKey);

    }
}
