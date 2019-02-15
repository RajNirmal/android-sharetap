package sharetap.app.org.com.sharetap;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Permission;

public class SignInActivity extends Activity {
    SignInButton googleSignIn;
    GoogleSignInClient mGoogleSignInClient;
    Integer RC_SIGN_IN = 985; //Request code for activity result
    Integer RC_PERMISSION_CODE = 785;//RequestCode for the permission result
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(AppConstants.LOGGER_CONSTANT,"SignupActivity");
        checkForCameraPermission();
        if(AppUtil.getUtilInstance().isUserLoggedIn(getApplicationContext())){
            moveToMainPage();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initViews();
    }

    public void checkForCameraPermission(){
        if(ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(SignInActivity.this,new String[]{Manifest.permission.CAMERA},RC_PERMISSION_CODE);
        }else{
            Log.i(AppConstants.LOGGER_CONSTANT,"Camera permission already present");
        }


    }

    public void initViews(){
        googleSignIn = findViewById(R.id.sign_in_button);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(AppConstants.LOGGER_CONSTANT,"Clicked the sign in button");
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else if (requestCode == RC_PERMISSION_CODE){
            Log.i(AppConstants.LOGGER_CONSTANT,"Permission to use camera granted");
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getAccount().name);
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getAccount().type);
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getEmail());
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getDisplayName());
            JSONObject userDetails = new JSONObject();
            try {
                userDetails.put(AppConstants.USER_NAME, account.getDisplayName());
                userDetails.put(AppConstants.USER_MAIL, account.getEmail());
                AppUtil.getUtilInstance().storeMailDetails(getApplicationContext(),userDetails);
                moveToMainPage();
            }catch (JSONException exp){
                Log.e(AppConstants.LOGGER_CONSTANT,exp.getMessage());
                Toast.makeText(getApplicationContext(),"Error occured while signing in to google, close the app and try again",Toast.LENGTH_LONG).show();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(getApplicationContext(),"Error occured while signing in to google, close the app and try again",Toast.LENGTH_LONG).show();
            Log.w(AppConstants.LOGGER_CONSTANT, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void moveToMainPage(){
        Intent mainIntent = new Intent((SignInActivity)this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
