package ie.mvo.newsreaderca2.webUtils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import ie.mvo.newsreaderca2.data.NewsItemContainer;

public class NewsApi {
    public static final String BASE_URL = "https://newsdata.io/";
    private static final String API_VERSION = "api/1/news";
    private static final String API_KEY = "pub_252571c9ab4f9437c9a35b51cea3f75d5d0da";

    public static ArrayList<NewsItemContainer> getTopHeadNews(String category, String request) {
        String response = "";
        Uri.Builder base_uri = generateBaseUrl();
        base_uri.appendQueryParameter("apikey", API_KEY);
        base_uri.appendQueryParameter("language", "en");
        if (!category.contains("all")) base_uri.appendQueryParameter("category", category.toLowerCase());
        if (!request.isEmpty()) base_uri.appendQueryParameter("q", request.toLowerCase());
        URL convertedUrl = convertUrl(base_uri);
        try {
            response = getResponse(convertedUrl);
//            Log.d("iks", "response: "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isEmpty()){
            return null;
        }else {
            return parseTopHeadNews(response);
        }
    }

    private static Uri.Builder generateBaseUrl() {
        return Uri.parse(BASE_URL+API_VERSION).buildUpon();
    }

    private static URL convertUrl(Uri.Builder rawUrl) {
        URL finalUrl = null;
        try {
            finalUrl = new URL(rawUrl.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return finalUrl;
    }

    private static String getResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            StringBuilder responseBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                responseBuilder.append(scanner.nextLine());
            }
            return responseBuilder.toString();
        } finally {
            urlConnection.disconnect();
        }
    }


    private static ArrayList<NewsItemContainer> parseTopHeadNews(String data) {
        JSONArray newsList;
        String status = "";
        ArrayList<NewsItemContainer> news = new ArrayList<>();
        if (data.isEmpty()) {
            return null;
        }
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            return null;
        }
        try {
            status = jsonObject.getString("status");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if (status.equals("success")){
            try {
                newsList = jsonObject.getJSONArray("results");
                for (int i = 0; i < newsList.length(); i++) {
                    JSONObject newsObjet = newsList.getJSONObject(i);
                    NewsItemContainer newsItem = new NewsItemContainer();
                    newsItem.id = -1;
                    newsItem.title = newsObjet.getString("title");
                    newsItem.description = newsObjet.getString("description");
                    newsItem.content = newsObjet.getString("content");
                    newsItem.pubDate = newsObjet.getString("pubDate");
                    newsItem.imageUrl = newsObjet.getString("image_url");
                    newsItem.country = newsObjet.getString("country");
                    newsItem.category = newsObjet.getJSONArray("category");
                    news.add(newsItem);
                }
                return news;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

// https://newsdata.io/api/1/news/top-headlines?apikey=pub_252571c9ab4f9437c9a35b51cea3f75d5d0da
// https://newsdata.io/api/1/news?apikey=pub_252571c9ab4f9437c9a35b51cea3f75d5d0da