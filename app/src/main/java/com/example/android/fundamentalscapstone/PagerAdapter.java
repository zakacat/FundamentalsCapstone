package com.example.android.fundamentalscapstone;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

//FragmentStateAdapter is the newer version that I should be using.
//This PagerAdapter simply returns a new fragment when the
//corresponding tab is selected.
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    //When getItem is called with the position number, a new fragment instance of the tab fragments
    //is created and returned.
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : {
                return new AbcTabFragment();
            }
            case 1 : {
                return new RegionTabFragment();
            }
            case 2 : {
                return new MealTabFragment();
            }
            default: return null;
        }
    }

    /**
     * Return the number of views available.
     */

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
