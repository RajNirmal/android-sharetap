package sharetap.app.org.com.sharetap.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sharetap.app.org.com.sharetap.AppUtil;
import sharetap.app.org.com.sharetap.CustomJSONUtil;
import sharetap.app.org.com.sharetap.DBHelper.ScannedUserDetails;
import sharetap.app.org.com.sharetap.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    ArrayList<ScannedUserDetails> dataSet;

    public UserAdapter(ArrayList<ScannedUserDetails> myDataSet) {
        this.dataSet = myDataSet;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_dialog_layout, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        TextView userName = holder.userName;
        TextView userMail = holder.userMail;
        userName.setText(CustomJSONUtil.getUtil().getUserName(dataSet.get(position).getUserDetails()));
        userMail.setText(dataSet.get(position).getUserMail());
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userMail, userName;
        ImageView fbIcon, instaIcon, snapIcon;
        Context context;
        View.OnClickListener fbListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fbProfileId = CustomJSONUtil.getUtil().getFBId(dataSet.get(getAdapterPosition()).getUserDetails());
                Intent fbIntent = AppUtil.getFacebookIntent(context.getPackageManager(), fbProfileId);
                context.startActivity(fbIntent);
            }
        };
        View.OnClickListener instaListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instaProfileId = CustomJSONUtil.getUtil().getInstaId(dataSet.get(getAdapterPosition()).getUserDetails());
                Intent instaIntent = AppUtil.getInstagramIntent(context.getPackageManager(), instaProfileId);
                context.startActivity(instaIntent);
            }
        };
        View.OnClickListener snapListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snapProfileId = CustomJSONUtil.getUtil().getSnapId(dataSet.get(getAdapterPosition()).getUserDetails());
                Intent snapchatIntent = AppUtil.getSnapchatIntent(context.getPackageManager(), snapProfileId);
                context.startActivity(snapchatIntent);
            }
        };
        public UserViewHolder(View singleItem) {
            super(singleItem);
            userMail = singleItem.findViewById(R.id.card_mail);
            userName = singleItem.findViewById(R.id.card_name);
            fbIcon = singleItem.findViewById(R.id.fb_image);
            instaIcon = singleItem.findViewById(R.id.insta_image);
            snapIcon = singleItem.findViewById(R.id.snap_image);
            context = singleItem.getContext();
            fbIcon.setOnClickListener(fbListener);
            instaIcon.setOnClickListener(instaListener);
            snapIcon.setOnClickListener(snapListener);
        }

        public void updateDataSet(ArrayList<ScannedUserDetails> newDataSet) {
            dataSet = newDataSet;
            notifyDataSetChanged();
        }
    }
}
