package sharetap.app.org.com.sharetap.DBHelper;

public class ScannedUserDetails {
    String userMail;
    String userDetails;

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }


    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public ScannedUserDetails(String userMail, String userDetails){
        this.userMail = userMail;
        this.userDetails = userDetails;
    }

    @Override
    public String toString() {
        return userDetails;
    }
}
