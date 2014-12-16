package uk.co.sruiz.budget;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Holds global application state using Android's Application object
 * that is specified in the AndroidManifest.xml
 * @author kenyee
 */
public class MyApplication extends Application {
    /** Android application context */
    private static Context sContext = null;

    /** Saves current party ID to share between fragments/activities **/
    private static String sSelectedPartyId = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();
        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * Initializes any singleton classes
     */
    protected void initSingletons() {
        // Initialize App DDP State Singleton
        MyDDPState.initInstance(MyApplication.sContext);
    }

    /**
     * Gets application context
     * @return Android application context
     */
    public static Context getAppContext() {
        return MyApplication.sContext;
    }

    /**
     * Gets currently selected Party ID
     * @return current party ID
     */
    public static String getSelectedPartyId() {
        return sSelectedPartyId;
    }

    /**
     * Sets currently selected Party ID
     * @param id current party ID
     */
    public static void setSelectedPartyId(String id) {
        sSelectedPartyId = id;
    }
}