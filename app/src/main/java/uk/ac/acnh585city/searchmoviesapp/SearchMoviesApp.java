/**
 * In the CinemasToLocation activity, I have implemented a feature that will fetch the
 * user's gps location (showing it with the blue marker) and show the location
 * of the Odeon cinemas in London.
 * I first signed up for a Google Maps API Key and entered it in the google_maps_api.xml,
 * then I entered the coarse and fine location permissions to the Android Manifest, in order
 * to access the location of the device used.
 * In the OnCreate() method, I set up the Client and the connection callbacks and listener,
 * as well as the location that will store the device location once it is fetched and the
 * location request.
 * In OnConnected() method I check if the location is null, then the location will request
 * location updates. If it is not null, it will handle the new location.
 * In OnResume() method I have a call to connect the client.
 * In OnPause() method I have the code that will disconnect the device from location
 * services when the activity is paused, which will reconnect if the activity is resumed. It
 * will also remove updates after requesting them.
 * In the setUpMap method I have entered the custom markers with the Odeon cinemas in London.
 * In the onConnectionSuspended method I have logged in the fact that the location services
 * are suspended.
 * The onLocationChanged method is called every time a new location is detected.
 * In onConnectionFailed method I start an activity that tries to resolve the error and if
 * nothing was available, it displays a dialog with the error.
 * The tutorial and code I used can be found at
 * http://blog.teamtreehouse.com/beginners-guide-location-android
 *
 * I have tested this project on my own android device. I have also tested it on both AVD
 * and GenyMotion but have been unsuccessful in doing so. After lots of research online
 * I concluded that the virtual devices don't come with Google Play Services and it is not
 * feasible (or very tedious/ difficult) to install them and have decided to leave it at this.
 * If needed, I am happy to demonstrate my project on any android device that has Google Play
 * Services and is connected to a google account.
 * References on my research on this issue include:
 * http://stackoverflow.com/a/14115230/2551800
 * http://stackoverflow.com/a/22141815/2551800
 * http://stackoverflow.com/a/20137324/2551800
 * http://stackoverflow.com/a/19088589/2551800
 * http://stackoverflow.com/a/27887682/2551800
 */

package uk.ac.acnh585city.searchmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SearchMoviesApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies_app);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void transition(View view){
        EditText movieTitle=(EditText) findViewById(R.id.movie_title);
        String title=movieTitle.getText().toString();

        Intent intent=new Intent(this, ResultScreen.class);
        intent.putExtra("searchTerm", title);
        startActivity(intent);
    }

    public void goToMap(View view){
        Intent intent=new Intent(this, CinemasToLocation.class);
        startActivity(intent);}

    }



