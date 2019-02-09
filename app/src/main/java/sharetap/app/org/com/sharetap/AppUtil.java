package sharetap.app.org.com.sharetap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.util.jar.Attributes;

public class AppUtil {
    public static AppUtil utilInstance = new AppUtil();

    public static AppUtil getUtilInstance(){
        return utilInstance;
    }
/*
This is the sample json structure that will be stored in DB / shown in QR
{
    "user_name":"name",
    "user_mail":"mail@domain.com",
    "fb_details":{
        "fb_profile":""
    },
    "insta_details":{
        "insta_profile":""
    }
}
*/
    public static Intent getFacebookIntent(PackageManager pm, String url) {
        url = "nirmal.raj.7923";
        Uri uri = Uri.parse(url);
        String facebookUrl = "https://www.facebook.com/"+url;
        String facebookFinalUrl = "";
        try {
            int versionCode = pm.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                facebookFinalUrl =  "fb://facewebmodal/f?href=" + facebookUrl;
            } else { //older versions of fb app
                facebookFinalUrl = "fb://page/" + url;
            }


        } catch (PackageManager.NameNotFoundException ignored) {
            Log.e(AppConstants.LOGGER_CONSTANT,"Resolution app not found for facebook "+ignored.getMessage());
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(facebookFinalUrl));
        return facebookIntent;
    }

    public static Intent getInstagramIntent(PackageManager pm, String url) {
        url = "nirmalmraj";
        String instagramUrl = "http://instagram.com/_u//"+url;
        Uri uri = Uri.parse(instagramUrl);
        String facebookFinalUrl = "";
        try {
            int versionCode = pm.getPackageInfo("com.instagram.android", 0).versionCode;
            if (versionCode != 0) {
                //Instagram app is present so return intent
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW,uri);
                instagramIntent.setPackage("com.instagram.android");
                return instagramIntent;
            } else {
                return new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+url));
            }

        }catch (PackageManager.NameNotFoundException exp){
            Log.e(AppConstants.LOGGER_CONSTANT,"Resolution app not found for instagram"+exp.getMessage());
            return null;
        }
    }

    public String getUserJSON(Context context){
        SharedPreferences myPrefs = context.getSharedPreferences(AppConstants.USER_SHARED_PREFS,Context.MODE_PRIVATE);
        return myPrefs.getString(AppConstants.SHARED_PREFS_KEY,AppConstants.NO_DETAILS);
    }

    private void storeUserJson(Context context, JSONObject data){
        SharedPreferences myPrefs = context.getSharedPreferences(AppConstants.USER_SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = myPrefs.edit();
        prefsEdit.putString(AppConstants.SHARED_PREFS_KEY,data.toString());
        prefsEdit.apply();
    }

    public boolean isUserLoggedIn(Context context){
        String result = getUserJSON(context);
        return (!result.equalsIgnoreCase(AppConstants.NO_DETAILS));
    }

    public void storeMailDetails(Context context, JSONObject data){
        storeUserJson(context,data);
    }
}
