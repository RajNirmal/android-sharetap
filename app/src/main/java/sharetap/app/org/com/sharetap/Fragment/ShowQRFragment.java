package sharetap.app.org.com.sharetap.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import sharetap.app.org.com.sharetap.AppConstants;
import sharetap.app.org.com.sharetap.AppUtil;
import sharetap.app.org.com.sharetap.MainActivity;
import sharetap.app.org.com.sharetap.MainActivity2;
import sharetap.app.org.com.sharetap.R;
import sharetap.app.org.com.sharetap.UserDetails;

public class ShowQRFragment extends Fragment {

    FloatingActionButton fabButton ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_qr, container, false);
        ImageView qrImage = view.findViewById(R.id.user_qr_code);
        fabButton = view.findViewById(R.id.edit_details);
        String qrCodeContent = AppUtil.getUtilInstance().getUserJSON(getActivity().getApplicationContext()).toString();
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(qrCodeContent, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrImage.setImageBitmap(bmp);
        } catch (WriterException exp) {
            Log.e(AppConstants.LOGGER_CONSTANT, "Exception while generating QR");
            Log.e(AppConstants.LOGGER_CONSTANT, exp.toString());
        }
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEditStuff();
            }
        });
        return view;
    }
    public void callEditStuff(){
        Intent editIntent = new Intent((MainActivity)getActivity(), UserDetails.class);
        startActivity(editIntent);
    }
}
