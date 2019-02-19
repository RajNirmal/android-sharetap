package sharetap.app.org.com.sharetap.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sharetap.app.org.com.sharetap.Adapter.UserAdapter;
import sharetap.app.org.com.sharetap.AppConstants;
import sharetap.app.org.com.sharetap.DBHelper.DBHandler;
import sharetap.app.org.com.sharetap.DBHelper.ScannedUserDetails;
import sharetap.app.org.com.sharetap.R;

public class ScannedItemsFragment extends Fragment {
    TextView noScannedItemsText;
    RecyclerView userItems;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_details,container,false);
        noScannedItemsText = view.findViewById(R.id.no_scanned_items);
        userItems = view.findViewById(R.id.scanned_users_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        userItems.setLayoutManager(layoutManager);
        userItems.setItemAnimator(new DefaultItemAnimator());
        userItems.setHasFixedSize(false);
        return view;
    }

    @Override
    public void onResume() {
        Log.i(AppConstants.LOGGER_CONSTANT, "Resuming the fragment");
        super.onResume();
        initViews();
    }

    @Override
    public void onStart() {
        Log.i(AppConstants.LOGGER_CONSTANT, "Starting the fragment");
        initViews();
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.i(AppConstants.LOGGER_CONSTANT, "user is pausing the fragment");
        super.onPause();
    }

    private void initViews() {
        if (!new DBHandler(getContext()).isUserDetailsAvailable()) {
            userItems.setVisibility(View.GONE);
            noScannedItemsText.setVisibility(View.VISIBLE);
        } else {
            noScannedItemsText.setVisibility(View.GONE);
            userItems.setVisibility(View.VISIBLE);
            ArrayList<ScannedUserDetails> details = new DBHandler(getContext()).getAllUserDetails();
            Log.i(AppConstants.LOGGER_CONSTANT, "ScannedItemsFragment :  The number of users in DB = " + details.size());
            UserAdapter adapter = new UserAdapter(details);
            userItems.setAdapter(adapter);
        }
    }
}
