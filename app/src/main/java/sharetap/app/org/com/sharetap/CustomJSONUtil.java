package sharetap.app.org.com.sharetap;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomJSONUtil {
    public static CustomJSONUtil util = new CustomJSONUtil();

    public static CustomJSONUtil getUtil(){
        return util;
    }

    public String getMail(JSONObject Json){
        String mail = "";
        try {
            mail = (String)Json.get(AppConstants.USER_MAIL);
        }catch (Exception exp){
            Log.e(AppConstants.LOGGER_CONSTANT,"Exception while fetching email from JSON "+exp.getMessage());
        }
        return mail;
    }

    public String getMail(String jsonObject){
        try {
            return getMail(new JSONObject(jsonObject));
        }catch (Exception ex){
            Log.e(AppConstants.LOGGER_CONSTANT,"Exception while converting string to json for getting mail");
            Log.e(AppConstants.LOGGER_CONSTANT,ex.getMessage());
        }
        return "a@a.com";
    }


    public String getUserName(JSONObject object){
        String name = "";
        try {
            name = (String) object.get(AppConstants.USER_NAME);
        }catch (Exception exp){
            Log.e(AppConstants.LOGGER_CONSTANT,"Exception while fetching name from JSON "+exp.getMessage());
        }
        return name;
    }

    public String getUserName(String jsonObject){
        try {
            return getUserName(new JSONObject(jsonObject));
        }catch (Exception ex){
            Log.e(AppConstants.LOGGER_CONSTANT,"Exception while converting string to json for getting name");
            Log.e(AppConstants.LOGGER_CONSTANT,ex.getMessage());
        }
        return "user";
    }

    public String getFBId(String json){
        try {
            JSONObject userDetails = new JSONObject(json);
            if(userDetails.has(AppConstants.FB_DETAILS)){
                return(String)((JSONObject)userDetails.get(AppConstants.FB_DETAILS)).get(AppConstants.FB_PROFILE_DETAILS);
            }else{
                Log.i(AppConstants.LOGGER_CONSTANT,"FB details are not found in this JSON");
                return "";
            }

        }catch (JSONException exp){
            Log.i(AppConstants.LOGGER_CONSTANT,"Exception while fetching FB details from JSON");
            Log.e(AppConstants.LOGGER_CONSTANT,exp.getMessage());
            return "";
        }
    }

    public String getInstaId(String json){
        try {
            JSONObject userDetails = new JSONObject(json);
            if(userDetails.has(AppConstants.INSTA_DETAILS)){
                return(String)((JSONObject)userDetails.get(AppConstants.INSTA_DETAILS)).get(AppConstants.INSTA_PROFILE_DETAILS);
            }else{
                Log.i(AppConstants.LOGGER_CONSTANT,"Insta details are not found in this JSON");
                return "";
            }

        }catch (JSONException exp){
            Log.i(AppConstants.LOGGER_CONSTANT,"Exception while fetching Insta details from JSON");
            Log.e(AppConstants.LOGGER_CONSTANT,exp.getMessage());
            return "";
        }
    }
}
