package sharetap.app.org.com.sharetap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject getUserJSON(Context context){
        SharedPreferences myPrefs = context.getSharedPreferences(AppConstants.USER_SHARED_PREFS,Context.MODE_PRIVATE);
        String details = myPrefs.getString(AppConstants.SHARED_PREFS_KEY,AppConstants.NO_DETAILS);
        try {
            return new JSONObject(details);
        }catch (JSONException exp){
            return new JSONObject();
        }
    }


    private String getUserDetails(Context context){
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
        String result = getUserDetails(context);
        return (!result.equalsIgnoreCase(AppConstants.NO_DETAILS));
    }

    public void storeMailDetails(Context context, JSONObject data){
        storeUserJson(context,data);
    }

    public String getInstaProfileID(Context context){
        try{
            String instaProfileName = getUserJSON(context).getJSONObject(AppConstants.INSTA_DETAILS).getString(AppConstants.INSTA_PROFILE_DETAILS);
            if (instaProfileName != null){
                return instaProfileName;
            }else{
                return "";
            }
        }catch (Exception exp){
            Log.e(AppConstants.LOGGER_CONSTANT,"Insta details not found in JSON");
            Log.e(AppConstants.LOGGER_CONSTANT,exp.getMessage());
            return "";
        }
    }

    public String getFBProfileID(Context context){
        try{
            String fbProfileName = getUserJSON(context).getJSONObject(AppConstants.FB_DETAILS).getString(AppConstants.FB_PROFILE_DETAILS);
            if (fbProfileName != null){
                return fbProfileName;
            }else{
                return "";
            }
        }catch (Exception exp){
            Log.e(AppConstants.LOGGER_CONSTANT,"Insta details not found in JSON");
            Log.e(AppConstants.LOGGER_CONSTANT,exp.getMessage());
            return "";
        }
    }

    public String getUserName(Context context){
        String userName;
        try {
            userName = getUserJSON(context).getString(AppConstants.USER_NAME);
        }catch (JSONException exp){
            Log.e(AppConstants.LOGGER_CONSTANT,"Cannot find user name in JSON "+ exp.getMessage());
            userName = "";
        }
        return userName;
    }

    public String getUserMail(Context context){
        String userMail;
        try {
            userMail = getUserJSON(context).getString(AppConstants.USER_MAIL);
        }catch (JSONException exp){
            Log.e(AppConstants.LOGGER_CONSTANT,"Cannot find user mail in JSON "+ exp.getMessage());
            userMail = "";
        }
        return userMail;
    }

    public void setInstaId(Context context, String instaId){
        Log.i(AppConstants.LOGGER_CONSTANT," Setting Insta data");
        JSONObject currentData = getUserJSON(context);
        JSONObject instaJson = new JSONObject();
        try {
            instaJson.put(AppConstants.INSTA_PROFILE_DETAILS, instaId);
            currentData.put(AppConstants.INSTA_DETAILS, instaJson);
            Log.i(AppConstants.LOGGER_CONSTANT,currentData.toString());
            storeUserJson(context, currentData);
        }catch (JSONException exp){
            Log.e(AppConstants.LOGGER_CONSTANT," The exception while storing the insta ID is "+exp.getMessage());
        }
    }

    public void setFbId(Context context, String fbId){
        Log.i(AppConstants.LOGGER_CONSTANT," Setting FB data");
        JSONObject currentData = getUserJSON(context);
        JSONObject fbJson = new JSONObject();
        try {
            fbJson.put(AppConstants.FB_PROFILE_DETAILS, fbId);
            currentData.put(AppConstants.FB_DETAILS, fbJson);
            Log.i(AppConstants.LOGGER_CONSTANT,currentData.toString());
            storeUserJson(context, currentData);
        }catch (JSONException exp){
            Log.e(AppConstants.LOGGER_CONSTANT," The exception while storing the insta ID is "+exp.getMessage());
        }
    }
}
