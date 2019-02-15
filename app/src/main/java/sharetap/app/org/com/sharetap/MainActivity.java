package sharetap.app.org.com.sharetap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import sharetap.app.org.com.sharetap.Fragment.ScanQRFragment;
import sharetap.app.org.com.sharetap.Fragment.ScannedItemsFragment;
import sharetap.app.org.com.sharetap.Fragment.ShowQRFragment;

public class MainActivity extends AppCompatActivity {
    TabLayout baseTabLayout;
    ViewPager fragmentPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        initView();
    }

    public void initView(){
        baseTabLayout = findViewById(R.id.switch_tablayout);
        fragmentPager = findViewById(R.id.fragment_holder);
        ArrayList<Integer> tabResources = new ArrayList<>();
        tabResources.add(R.drawable.ic_qrcode_scan);
        tabResources.add(R.drawable.ic_outline_view_list_24px);
        tabResources.add(R.drawable.ic_qrcode);
        baseTabLayout.setTabMode(TabLayout.MODE_FIXED);
        baseTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        baseTabLayout.setSelectedTabIndicatorHeight(15);
        fragmentPager.setAdapter(new fragmetSwitcherAdapter(getSupportFragmentManager()));
        baseTabLayout.setupWithViewPager(fragmentPager);
        for (int i = 0; i < tabResources.size() ; i++) {
            baseTabLayout.getTabAt(i).setIcon(tabResources.get(i));
        }
    }

    public class fragmetSwitcherAdapter extends FragmentPagerAdapter {
        ArrayList<Integer> itemList= new ArrayList<>();
        String names[] = new String[]{"tab1","tab2","tab3"};
        @Override
        public Fragment getItem(int position) {
            Log.i(AppConstants.LOGGER_CONSTANT,"The position selected is "+position);
            switch (position) {
                case 0:
                    return new ScanQRFragment();
                case 1:
                    return new ScannedItemsFragment();
                case 2:
                    return new ShowQRFragment();
                default:
                    Log.i(AppConstants.LOGGER_CONSTANT,"Did not find any tab for the selected item");
                    return new ShowQRFragment();
            }
        }

        @Override
        public int getCount() {
            return names.length;
        }

        public fragmetSwitcherAdapter(FragmentManager fm){
            super(fm);
        }

        public fragmetSwitcherAdapter(FragmentManager fm,ArrayList itemList){
            super(fm);
            this.itemList = itemList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
