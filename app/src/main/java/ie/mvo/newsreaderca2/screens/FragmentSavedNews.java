package ie.mvo.newsreaderca2.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ie.mvo.newsreaderca2.R;
import ie.mvo.newsreaderca2.adapters.NewsItemsAdapter;
import ie.mvo.newsreaderca2.data.DataBase;
import ie.mvo.newsreaderca2.data.DbHelper;
import ie.mvo.newsreaderca2.data.NewsItemContainer;
import ie.mvo.newsreaderca2.databinding.LayoutSavedNewsBinding;

public class FragmentSavedNews extends Fragment {
    LayoutSavedNewsBinding binding;
    ArrayList<NewsItemContainer> data;
    private DbHelper dbHelper;
    private NewsItemsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbHelper = DbHelper.getInstance(DataBase.getInstance(requireContext()).getWritableDatabase());
        data = dbHelper.getAllNews();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutSavedNewsBinding.inflate(inflater, container, false);
        binding.savedNewsListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                getParentFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.mainFragmentContainer, FragmentNewsDetails.newInstance(data.get(position)))
                        .commit();
            }
        };
        adapter = new NewsItemsAdapter(data, listener);
        binding.savedNewsListView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        data.addAll(dbHelper.getAllNews());
        adapter.notifyDataSetChanged();
        Log.d("iks", "resume");
    }
}
