package ie.mvo.newsreaderca2.screens;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONException;

import java.util.ArrayList;

import ie.mvo.newsreaderca2.MainActivity;
import ie.mvo.newsreaderca2.R;
import ie.mvo.newsreaderca2.adapters.NewsItemsAdapter;
import ie.mvo.newsreaderca2.adapters.NewsTopicsAdapter;
import ie.mvo.newsreaderca2.data.NewsItemContainer;
import ie.mvo.newsreaderca2.databinding.LayoutFragmentNewsItemBinding;
import ie.mvo.newsreaderca2.databinding.LayoutGeneralNewsBinding;
import ie.mvo.newsreaderca2.interfaces.MyObserver;
import ie.mvo.newsreaderca2.interfaces.Subscriber;
import ie.mvo.newsreaderca2.webUtils.NewsApi;

public class FragmentGeneralNews extends Fragment implements MyObserver {

    private LayoutGeneralNewsBinding binding;
    private ArrayList<NewsItemContainer> data;
    private NewsItemsAdapter adapter;
    private NewsTopicsAdapter topicsAdapter;
    private ArrayList<Subscriber> subscribers;
    private ArrayList<String> topics;
    private int currentPosition = 0;
    private String topic = "all";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<NewsItemContainer>();
        topics = new ArrayList<>();
        topics.add("all");
        topics.add("world");
        topics.add("business");
        topics.add("entertainment");
        topics.add("environment");
        topics.add("food");
        topics.add("health");
        topics.add("politics");
        topics.add("science");
        topics.add("sports");
        topics.add("technology");
        topics.add("top");
        topics.add("tourism");
        subscribers = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutGeneralNewsBinding.inflate(inflater, container, false);
        View.OnClickListener newsItemClickListener = new View.OnClickListener() {
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
        adapter = new NewsItemsAdapter(data, newsItemClickListener);
        View.OnClickListener topicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Drawable drawable = ContextCompat.getDrawable(v.getContext(), R.drawable.rounded_corners_topics);
                v.setBackground(drawable);
                notifySubscribers(position, currentPosition);
                topic = topics.get(position);
                updateData(topic, ""); // request should be empty becouse search field could be not empty
                currentPosition = position;
            }
        };
        topicsAdapter = new NewsTopicsAdapter(topicListener, topics);
        addSubscriber(topicsAdapter);
        binding.newsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.newsList.setAdapter(adapter);
        binding.topicsList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.topicsList.setAdapter(topicsAdapter);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(topic, binding.searchNewsField.getText().toString());
            }
        });
        updateData(topic, "");
    }

    private void updateData(String ntopic, String request) {
        binding.searchNewsField.setText("");
        binding.progressBarGeneralNews.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                data.clear();
                data.addAll(NewsApi.getTopHeadNews(ntopic, request));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        binding.progressBarGeneralNews.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(int currentPosition, int positionToRemove) {
        for (Subscriber subscriber: subscribers) {
            subscriber.updateItem(currentPosition, positionToRemove);
        }
    }
}
