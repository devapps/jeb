package org.vsoft.omni.upi.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.vsoft.omni.upi.fragments.ApprovalFragment;
import org.vsoft.omni.upi.fragments.CollectFragment;
import org.vsoft.omni.upi.fragments.DashboardFragment;
import org.vsoft.omni.upi.fragments.HistoryFragment;
import org.vsoft.omni.upi.fragments.MoreFragment;
import org.vsoft.omni.upi.fragments.PayFragment;

/**
 * Created by amishra on 04/03/16.
 */
public class HomeActivityFragmentsAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public HomeActivityFragmentsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DashboardFragment tab0 = new DashboardFragment();
                return tab0;
            case 1:
                PayFragment tab1 = new PayFragment();
                return tab1;
            case 2:
                CollectFragment tab2 = new CollectFragment();
                return tab2;
            case 3:
                ApprovalFragment tab3 = new ApprovalFragment();
                return tab3;
            case 4:
                HistoryFragment tab4 = new HistoryFragment();
                return tab4;
            case 5:
                MoreFragment tab5 = new MoreFragment();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
