package ie.mvo.newsreaderca2.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ie.mvo.newsreaderca2.adapters.NewsItemsAdapter;

public class DbHelper {
    private SQLiteDatabase dataBase;
    private static DbHelper instance;

    public DbHelper(SQLiteDatabase dataBase) {
        this.dataBase = dataBase;
    }

    public static synchronized DbHelper getInstance(SQLiteDatabase dataBase) {
        if (instance == null) {
            instance = new DbHelper(dataBase);
        }
        return instance;
    }

    public void insertInToDb(String title, String description, String content, String pub_date,
                             String image_url, String country, String category) {
        // Create a ContentValues object with the values to insert
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("content", content);
        values.put("pubDate", pub_date);
        values.put("imageUrl", image_url);
        values.put("country", country);
        values.put("category", category);
        // Insert the values into the "coins" table
        dataBase.insert(DataBase.TABLE_NAME, null, values);
    }

    public void deleteNews(String item_id) {
        // Define the WHERE clause
        String selection = "id = ?";
        String[] selectionArgs = {item_id};
        // Delete the rows that match the WHERE clause
        int count = dataBase.delete(DataBase.TABLE_NAME, selection, selectionArgs);
    }


    public ArrayList<NewsItemContainer> getAllNews() {
        ArrayList<NewsItemContainer> news = new ArrayList<>();

        // Define the columns to retrieve
        String[] projection = {"*"};

        // Define the WHERE clause
//        String selection = "age > ?";
//        String[] selectionArgs = {"20"};

        // Define the sort order
        String sortOrder = "id DESC";

        // Query the database and get a Cursor object
        Cursor cursor = dataBase.query(DataBase.TABLE_NAME, projection, null, null, null, null, sortOrder);

        // Iterate over the rows in the Cursor and extract the data
        while (cursor.moveToNext()) {
            NewsItemContainer newsItem = new NewsItemContainer();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String pub_date = cursor.getString(cursor.getColumnIndexOrThrow("pubDate"));
            String image_url = cursor.getString(cursor.getColumnIndexOrThrow("imageUrl"));
            String country = cursor.getString(cursor.getColumnIndexOrThrow("country"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            newsItem.id = id;
            newsItem.title = title;
            newsItem.description = description;
            newsItem.content = content;
            newsItem.pubDate = pub_date;
            newsItem.imageUrl = image_url;
            newsItem.country = country;
            newsItem.category = new JSONArray().put(category);
            news.add(newsItem);
            // Do something with the data...
        }
        // Close the Cursor when you're done
        cursor.close();
        return news;
    }



}
