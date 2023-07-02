package ie.mvo.newsreaderca2.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.mvo.newsreaderca2.R;
import ie.mvo.newsreaderca2.databinding.LayoutTopicItemBinding;
import ie.mvo.newsreaderca2.interfaces.Subscriber;

public class NewsTopicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Subscriber {

    ArrayList<String> topics;
    private int back = R.drawable.rounded_corners;
    private int currentPosition = 0;

    private View.OnClickListener topicListener;

    public NewsTopicsAdapter(View.OnClickListener listener, ArrayList<String> data){
        topicListener = listener;
        topics = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutTopicItemBinding binding = LayoutTopicItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(topicListener);
        return new ViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.title.setText(topics.get(position).toUpperCase());
        if (position != currentPosition) {
            viewHolder.itemView.setBackgroundResource(R.drawable.rounded_corners);
        }else {
            viewHolder.itemView.setBackgroundResource(R.drawable.rounded_corners_topics);
        }
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    @Override
    public void updateItem(int position, int positionToRemove) {
        notifyItemChanged(positionToRemove);
        currentPosition = position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ViewHolder(LayoutTopicItemBinding itemBinding) {
            super(itemBinding.getRoot());
            title = itemBinding.topicTitle;
        }

    }

}
