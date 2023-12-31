package ie.mvo.newsreaderca2.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ie.mvo.newsreaderca2.adapters.TabViewAdapter;
import ie.mvo.newsreaderca2.databinding.LayoutFragmentMainBinding;

public class FragmentMain extends Fragment {

    LayoutFragmentMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutFragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Set up the tabs
        TabLayout tabLayout = binding.tabsContainer;
        tabLayout.setTabIndicatorFullWidth(true);
        ViewPager2 viewPager = binding.fragmentContainer;
        viewPager.setAdapter(new TabViewAdapter(requireActivity()));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("News");
                    break;
                case 1:
                    tab.setText("Saved");
                    break;
                default:
                    break;
            }
        }).attach();

        // Set the default tab
        viewPager.setCurrentItem(0);
        return view;
    }
}
