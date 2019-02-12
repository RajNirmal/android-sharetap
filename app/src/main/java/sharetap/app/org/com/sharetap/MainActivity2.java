package sharetap.app.org.com.sharetap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    SignInButton googleSignIn;
    Button fbLogin,instaLogin;
    FloatingActionButton detailsButton;
    Integer RC_SIGN_IN = 985; //Request code for activity result
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initView();
        JSONObject obj = AppUtil.getUtilInstance().getUserJSON(getApplicationContext());
        Log.i(AppConstants.LOGGER_CONSTANT,obj.toString());
    }

    public void initView(){
        googleSignIn = findViewById(R.id.sign_in_button);
        fbLogin = findViewById(R.id.fb_button);
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFBStuff();
            }
        });
        instaLogin = findViewById(R.id.insta_button);
        instaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callInstaStuff();
            }
        });
        googleSignIn.setOnClickListener(signInButton);
        detailsButton = findViewById(R.id.edit_details);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEditStuff();
            }
        });
    }

    View.OnClickListener signInButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(AppConstants.LOGGER_CONSTANT,"Clicked the sign in button");
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getAccount().name);
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getAccount().type);
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getEmail());
            Log.i(AppConstants.LOGGER_CONSTANT,"Account details "+ account.getDisplayName());
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(AppConstants.LOGGER_CONSTANT, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void callFBStuff(){
        PackageManager pm = getApplicationContext().getPackageManager();
        Intent fbIntent = AppUtil.getFacebookIntent(pm,"");
        startActivity(fbIntent);
    }

    public void callInstaStuff(){
        PackageManager pm = getApplicationContext().getPackageManager();
        Intent fbIntent = AppUtil.getInstagramIntent(pm,"");
        startActivity(fbIntent);
    }

    public void callEditStuff(){
        Intent editIntent = new Intent(MainActivity2.this,UserDetails.class);
        startActivity(editIntent);
    }
}
