package uk.ac.acnh585city.searchmoviesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ResultScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        final Button search=(Button) findViewById(R.id.button2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchMoviesApp.class));
            }
        });

        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Could not connect to server. Connect to internet and try again.");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                       Toast.makeText(getApplicationContext(),"Application retrying", Toast.LENGTH_SHORT).show();
                    }
                });

        String keyword=getIntent().getExtras().getString("searchTerm");

        SQLiteMoviesdb db=new SQLiteMoviesdb(this);
        keyword =keyword.replaceAll("\\s", "+");
        String jsonResult=db.getResults(keyword);
        if(jsonResult=="") {
            jsonResult = runSearch(keyword);
            if (runSearch(keyword) != null) {
                db.insertData(keyword, jsonResult);
            } else {
                alertDialog.show();
            }
        }



        ArrayList<String> searchResults=getValuesFromJSON(jsonResult);

        final ListView listView=(ListView) findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,searchResults);
            listView.setAdapter(arrayAdapter);
    }
    private String runSearch(String keyword){
        String jsonResult=null;
        try{
            URL url=new URL("http://www.omdbapi.com/?s="+keyword);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream input=httpURLConnection.getInputStream();
            Scanner scanner=new Scanner(input,"UTF-8").useDelimiter("\\A");
            jsonResult=scanner.hasNext() ? scanner.next():"";
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;
    }
    private ArrayList<String> getValuesFromJSON(String jsonResult){
        ArrayList<String> results=new ArrayList<>();
        try{
            JSONObject jsonObject=new JSONObject(jsonResult);
            JSONArray jsonArray=jsonObject.getJSONArray("Search");
             for(int i=0;i<jsonArray.length();i++){
                JSONObject result=jsonArray.getJSONObject(i);
                StringBuilder sb=new StringBuilder();
                sb.append(result.getString("Title"));
                sb.append(" - ");
                sb.append(result.getString("Year"));
                results.add(sb.toString());
             }
        }catch(Exception e){
            e.printStackTrace();
        }
        return results;
    }

    }

