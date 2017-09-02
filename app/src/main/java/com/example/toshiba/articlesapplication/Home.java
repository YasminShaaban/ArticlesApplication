package com.example.toshiba.articlesapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    SharedPreferences sharedPreferences; //class used to store data lightweight as session
    SharedPreferences.Editor editor;
    //StringBuilder jsonstring;
    String jsonstr;
   // Articles article1;
    //Reviews r1,r2;
    //ArrayList<Reviews> reviewlist;
    ArrayList<Data> datapub;
    RecyclerViewAdapter adapt;
    RecyclerView rview;
    JSONParser parser;
    String image;
    String text1;
    String text2;
    //Data d;
    //Data d2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("articles", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        new UseAsync().execute();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                if(sharedPreferences.getBoolean("loggedIn",true)){
                    Intent i=new Intent(Home.this,Login.class);
                    startActivity(i);
                    editor.putBoolean("loggedIn",false);
                    editor.commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    StringBuilder connect( String path){
        URL url;
        StringBuilder stringBuilder=null;
        try {
            url = new URL(path);
            HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            //urlConnection.setDoOutput(true);
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            stringBuilder=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }
    class UseAsync extends AsyncTask<String,String,String>{
        String url;
        StringBuilder jsonresult;
        //RecyclerView recyclev;
        @Override
        protected String doInBackground(String... strings) {
            jsonresult=connect(url);
            return  jsonresult.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url="http://api.nytimes.com/svc/topstories/v2/home.json?api-key=4f469631f35b4f8cadf20521881cf003";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //parsing json
            JSONParser j =new JSONParser(jsonresult.toString());
            datapub=j.parseJSON();
            rview=(RecyclerView)findViewById(R.id.recycleview);
            rview.setLayoutManager(new LinearLayoutManager(Home.this));
            adapt=new RecyclerViewAdapter(datapub);
            rview.setAdapter(adapt);
        }
    }


    class JSONParser{
        JSONObject jobject;
        JSONArray jarray;
        String jsonstring;
        JSONObject resultobject;
        String nameresult;
        String bodyresult;
        JSONArray imagearray;
        JSONObject imageobject;
        String imagestring;
        ArrayList<Data> data;
        Data d;
        Toast t;
        public JSONParser(String jsonstring){
            this.jsonstring=jsonstring;
            data=new ArrayList<Data>();
        }
        public JSONParser(){
            //data=new ArrayList<Data>();
        }
        public ArrayList<Data> parseJSON(){
            try {

                jobject = new JSONObject(jsonstring);
                jarray = jobject.getJSONArray("results");
                for(int i=0;i<jarray.length();i++){

                    resultobject=jarray.getJSONObject(i);
                    nameresult=resultobject.getString("title");
                    bodyresult=resultobject.getString("abstract");
                    imagearray=resultobject.getJSONArray("multimedia");
                    if(imagearray.length()== 0){
                        d=new Data("",nameresult,bodyresult);
                        data.add(d);
                    }
                    else{
                        imageobject=imagearray.getJSONObject(0);
                        imagestring=imageobject.getString("url");
                        d=new Data(imagestring,nameresult,bodyresult);
                        data.add(d);
                    }
                }

               /* t=Toast.makeText(getApplicationContext(),title,Toast.LENGTH_LONG);
                t.show();
                t=Toast.makeText(getApplicationContext(),body,Toast.LENGTH_LONG);
                t.show();
                t=Toast.makeText(getApplicationContext(),namereview,Toast.LENGTH_LONG);
                t.show();*/
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error occurs",Toast.LENGTH_LONG);
            }
            return data;
        }

    }

}
