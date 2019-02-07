package sharetap.app.org.com.sharetap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends Activity {
    SignInButton googleSignIn;
    GoogleSignInClient mGoogleSignInClient;
    Integer RC_SIGN_IN = 985; //Request code for activity result
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(AppConstants.LOGGER_CONSTANT,"SignupActivity");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initViews();
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
}
