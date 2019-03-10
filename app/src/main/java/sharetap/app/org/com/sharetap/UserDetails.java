package sharetap.app.org.com.sharetap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetails extends AppCompatActivity {
    TextView userName,userMail;
    EditText fbDetails, instaDetails, snapDetails;
    Button saveButton, backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_details);
        initViews();
    }

    private void initViews(){
        userName = findViewById(R.id.user_name);
        userMail = findViewById(R.id.user_mail);
        fbDetails = findViewById(R.id.fb_profile_id);
        instaDetails = findViewById(R.id.insta_profile_id);
        snapDetails = findViewById(R.id.snap_profile_id);
        saveButton = findViewById(R.id.save_user_details);
        backButton = findViewById(R.id.open_main_page);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAllDetails();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });
        populateViews();
    }

    private void saveAllDetails(){
        String fbNewProfileId = fbDetails.getText().toString();
        String instaNewProfileId = instaDetails.getText().toString();
        String snapNewProfileId = snapDetails.getText().toString();
        if(!fbNewProfileId.isEmpty()){
            AppUtil.getUtilInstance().setFbId(UserDetails.this,fbNewProfileId);
        }
        if(!instaNewProfileId.isEmpty()){
            AppUtil.getUtilInstance().setInstaId(UserDetails.this,instaNewProfileId);
        }
        if (!snapNewProfileId.isEmpty()) {
            AppUtil.getUtilInstance().setSnapId(UserDetails.this, snapNewProfileId);
        }
        Toast.makeText(UserDetails.this," Details saved successfully",Toast.LENGTH_SHORT).show();
    }

    private void populateViews(){
        String userName = AppUtil.getUtilInstance().getUserName(UserDetails.this);
        String userMail = AppUtil.getUtilInstance().getUserMail(UserDetails.this);
        String fbUserName = AppUtil.getUtilInstance().getFBProfileID(UserDetails.this);
        String instaProfileID = AppUtil.getUtilInstance().getInstaProfileID(UserDetails.this);
        String snapProfileID = AppUtil.getUtilInstance().getSnapProfileID(UserDetails.this);
        fbDetails.setText(fbUserName);
        instaDetails.setText(instaProfileID);
        snapDetails.setText(snapProfileID);
        this.userName.setText(userName);
        this.userMail.setText(userMail);
    }

    private void openMainPage(){
        Intent mainIntent = new Intent(UserDetails.this,MainActivity.class);
        startActivity(mainIntent);
    }
}
