package sharetap.app.org.com.sharetap;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.util.jar.Attributes;

public class AppUtil {
    public static AppUtil utilInstance = new AppUtil();

    public static AppUtil getUtilInstance(){
        return utilInstance;
    }

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
}
