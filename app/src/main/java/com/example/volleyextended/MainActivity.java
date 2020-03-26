package com.example.volleyextended;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText searchBar;
    ImageView posterImageView;
    TextView plotTextView;
    TextView ratedTextView;
    TextView runtimeTextView;
    TextView imdbRatingTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_bar);
        posterImageView = findViewById(R.id.poster_iv);
        plotTextView = findViewById(R.id.plot_tv);
        ratedTextView = findViewById(R.id.rated_tv);
        runtimeTextView = findViewById(R.id.runtime_tv);
        imdbRatingTextView = findViewById(R.id.imdb_rating_tv);
    }

    public void fetchData(View view) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.omdbapi.com/?apikey=388d5f18&t=" + searchBar.getText();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //store the string response as a JSONObject
                            JSONObject responseObject = new JSONObject(response);
                            //Extract desired data values using keys from the JSONObject
                            String posterUrl = responseObject.getString("Poster");
                            String plotText = responseObject.getString("Plot");
                            String ratedText = responseObject.getString("Rated");
                            String runtimeText = responseObject.getString("Runtime");
                            String imdbRatingText = responseObject.getString("imdbRating");

                            //set data on the corresponding Views
                            Picasso.get().load(posterUrl).into(posterImageView);
                            plotTextView.setText(plotText);
                            ratedTextView.setText("Rated: " + ratedText);
                            runtimeTextView.setText("Runtime: " + runtimeText);
                            imdbRatingTextView.setText("IMDB Rating: " + imdbRatingText);
                        } catch (Exception e) {
                            plotTextView.setText("There was an error getting the movie data. Is the title spelled correctly?");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                plotTextView.setText("There was a connection error.");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
