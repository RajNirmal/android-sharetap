package sharetap.app.org.com.sharetap.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sharetap.app.org.com.sharetap.DBHelper.DBHandler;
import sharetap.app.org.com.sharetap.DBHelper.ScannedUserDetails;
import sharetap.app.org.com.sharetap.R;

public class ScannedItemsFragment extends Fragment {
    TextView myText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_details,container,false);
        myText = view.findViewById(R.id.dummy_holder);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<ScannedUserDetails> detas = new DBHandler(getContext()).getAllUserDetails();
        for (ScannedUserDetails i : detas) {
            myText.setText(i.toString());
        }
    }
}
