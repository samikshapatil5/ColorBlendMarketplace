package com.example.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {
    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragmentdash();
            case 1:
                return new Fragmentadmin();
            case 2:
                return new Fragmentpainter();
            default:
                return new Fragmentdash();
        }
    }

    @Override
    public int getItemCount() {
        // Return the total number of fragments
        return 4;  // Corrected to 4
    }
}
