package ie.mvo.newsreaderca2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ie.mvo.newsreaderca2.screens.FragmentGeneralNews;
import ie.mvo.newsreaderca2.screens.FragmentSavedNews;

public class TabViewAdapter extends FragmentStateAdapter {
    public TabViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentGeneralNews();
            case 1:
                return new FragmentSavedNews();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
