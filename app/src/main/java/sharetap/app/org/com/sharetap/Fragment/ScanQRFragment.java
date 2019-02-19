package sharetap.app.org.com.sharetap.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import sharetap.app.org.com.sharetap.AppConstants;
import sharetap.app.org.com.sharetap.AppUtil;
import sharetap.app.org.com.sharetap.CustomJSONUtil;
import sharetap.app.org.com.sharetap.DBHelper.DBHandler;
import sharetap.app.org.com.sharetap.DBHelper.ScannedUserDetails;
import sharetap.app.org.com.sharetap.R;

public class ScanQRFragment extends Fragment {

    private CodeScanner codeScanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        Log.i(AppConstants.LOGGER_CONSTANT,"Starting QR Fragment");
        View root = inflater.inflate(R.layout.fragment_scan_qr, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(activity, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String userDetails = result.getText();
                        Log.i(AppConstants.LOGGER_CONSTANT, userDetails);
                        new DBHandler(getContext()).addOrUpdateScannedItem(new ScannedUserDetails(CustomJSONUtil.getUtil().getMail(userDetails),userDetails));
                        showAlertBox(userDetails);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
        String mail = "mail@domain.com";
        Log.i(AppConstants.LOGGER_CONSTANT, " Is mail already present : " + new DBHandler(getContext()).getUserDetailsByMail(mail).toString());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private void showAlertBox(final String userDetails){

        //Creating a new custom dialog box for showing details
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog = dialogBuilder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_layout,null);
        alertDialog.setView(dialogView);
        alertDialog.show();

        //Get required details to show in View
        String userMail = "";
        String userName = "";
        try {
            JSONObject details = new JSONObject(userDetails);
            userMail = CustomJSONUtil.getUtil().getMail(details);
            userName = CustomJSONUtil.getUtil().getUserName(details);
        }catch (JSONException exp){
            Log.e(AppConstants.LOGGER_CONSTANT," JSON Exception while showing alert");
            Log.e(AppConstants.LOGGER_CONSTANT,exp.getMessage());
        }

        TextView nameInUi = dialogView.findViewById(R.id.card_name);
        TextView mailInUi = dialogView.findViewById(R.id.card_mail);
        ImageView fbImage = dialogView.findViewById(R.id.fb_image);
        ImageView instaImage = dialogView.findViewById(R.id.insta_image);
        nameInUi.setText(userName);
        mailInUi.setText(userMail);
        fbImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppUtil.getFacebookIntent(getContext().getPackageManager(),CustomJSONUtil.getUtil().getFBId(userDetails)));
            }
        });
        instaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppUtil.getInstagramIntent(getContext().getPackageManager(),CustomJSONUtil.getUtil().getInstaId(userDetails)));
            }
        });

    }
}
