package ie.mvo.newsreaderca2.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import ie.mvo.newsreaderca2.R;
import ie.mvo.newsreaderca2.data.DataBase;
import ie.mvo.newsreaderca2.data.DbHelper;
import ie.mvo.newsreaderca2.data.NewsItemContainer;
import ie.mvo.newsreaderca2.databinding.LayoutFragmentNewsItemBinding;

public class FragmentNewsDetails extends Fragment {
    public static final String DATA_KEY = "data";
    private NewsItemContainer data;
    private LayoutFragmentNewsItemBinding binding;
    private DbHelper dbHelper;
    private TabLayout tabLayout;

    public static FragmentNewsDetails newInstance(NewsItemContainer data) {
        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);
        FragmentNewsDetails fragment = new FragmentNewsDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getParcelable(DATA_KEY);
        this.dbHelper = DbHelper.getInstance(DataBase.getInstance(requireContext()).getWritableDatabase());
        tabLayout = getActivity().findViewById(R.id.tabsContainer);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutFragmentNewsItemBinding.inflate(inflater, container, false);
        binding.newsTitle.setText(data.title);
        binding.newsText.setText(data.content);
        if (data.imageUrl != null) {
            Glide.with(binding.newsIcon)
                    .load(data.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.newsIcon);
        } else {
            binding.newsIcon.setImageResource(R.drawable.ic_launcher_foreground);
        }
        try {
            binding.newsTopicTitle.setText(((String) data.category.get(0)).toUpperCase());
        } catch (JSONException e) {
            binding.newsTopicTitle.setText("ALL");
        }
        if (data.id != -1){
            binding.buttonSaveNewsItem.setImageResource(R.drawable.delete);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonBackNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.buttonSaveNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "";
                try {
                    category = ((String) data.category.get(0)).toUpperCase();
                } catch (JSONException e) {
                    category = "ALL";
                }
                if (data.id == -1) {
                    dbHelper.insertInToDb(data.title, data.description, data.content, data.pubDate, data.imageUrl, data.country, category);
                    binding.buttonSaveNewsItem.setVisibility(View.INVISIBLE);
                } else {
                    dbHelper.deleteNews(String.valueOf(data.id));
                    getActivity().onBackPressed();
                }
            }
        });
    }
}
