package ie.mvo.newsreaderca2.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

public class NewsItemContainer implements Parcelable {

    public int id;
    public String title;
    public String description;
    public String content;
    public String pubDate;
    public String imageUrl;
    public String country;
    public JSONArray category;

    public NewsItemContainer() {
        // Empty constructor is required for Parcelable
    }

    // Parcelable constructor
    private NewsItemContainer(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        content = in.readString();
        pubDate = in.readString();
        imageUrl = in.readString();
        country = in.readString();
        try {
            String jsonString = in.readString();
            category = new JSONArray(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Parcelable CREATOR
    public static final Creator<NewsItemContainer> CREATOR = new Creator<NewsItemContainer>() {
        @Override
        public NewsItemContainer createFromParcel(Parcel in) {
            return new NewsItemContainer(in);
        }

        @Override
        public NewsItemContainer[] newArray(int size) {
            return new NewsItemContainer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeString(pubDate);
        dest.writeString(imageUrl);
        dest.writeString(country);
        dest.writeString(category.toString());
    }
}
