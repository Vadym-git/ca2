package ie.mvo.newsreaderca2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;

import ie.mvo.newsreaderca2.R;
import ie.mvo.newsreaderca2.data.NewsItemContainer;
import ie.mvo.newsreaderca2.databinding.LayoutSingleNewsItemBinding;

public class NewsItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NewsItemContainer> news;
    private View.OnClickListener listener;


    public NewsItemsAdapter(ArrayList<NewsItemContainer> news, View.OnClickListener itemClickListener){
        this.news = news;
        listener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleNewsItemBinding binding = LayoutSingleNewsItemBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(listener);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setTag(position);
        NewsItemContainer item = (NewsItemContainer) news.get(position);
        try {
            viewHolder.itemCategory.setText((String) item.category.get(0));
        } catch (JSONException e) {
            viewHolder.itemCategory.setText("ALL");
        }
        viewHolder.itemTitle.setText(item.title);
        if (item.imageUrl != null) {
            Glide.with(viewHolder.itemIcon)
                    .load(item.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(viewHolder.itemIcon);
        } else {
            viewHolder.itemIcon.setImageResource(R.drawable.ic_launcher_foreground);
        }
        viewHolder.itemText.setText(item.content);
        viewHolder.itemDate.setText(item.pubDate);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemCategory;
        ImageView itemIcon;
        TextView itemTitle;
        TextView itemText;
        TextView itemDate;

        public ViewHolder(LayoutSingleNewsItemBinding itemBinding) {
            super(itemBinding.getRoot());
            itemCategory = itemBinding.itemCategory;
            itemIcon = itemBinding.itemIcon;
            itemTitle = itemBinding.itemTitle;
            itemText = itemBinding.itemText;
            itemDate = itemBinding.itemDate;
        }
    }

}
