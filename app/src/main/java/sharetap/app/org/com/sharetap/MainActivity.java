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

        for(String name: tabResources) {
            baseTabLayout.addTab(baseTabLayout.newTab().setIcon());
        }
        baseTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        baseTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        baseTabLayout.setSelectedTabIndicatorHeight(10);
    //        baseTabLayout.addOnTabSelectedListener(tabSelectedListener);
        fragmentPager.setAdapter(new fragmetSwitcherAdapter(getSupportFragmentManager(),tabNames));
        baseTabLayout.setupWithViewPager(fragmentPager);
    }


public class fragmetSwitcherAdapter extends FragmentPagerAdapter {
    ArrayList<String> itemList= new ArrayList<>();
    @Override
    public Fragment getItem(int position) {
        Fragment myFragment = null;
        Log.i(AppConstants.APP_LOG_TAG,"The position selected is "+position);
        switch (position) {
            case 0:
                return new SampleFragment();
            default:
                Log.i(AppConstants.APP_LOG_TAG,"Did not find any tab for the selected item");
                return new SoonFragment();
        }
    }

    @Override
    public int getCount() {
        return itemList.size();
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
        return itemList.get(position);
    }
}
}
