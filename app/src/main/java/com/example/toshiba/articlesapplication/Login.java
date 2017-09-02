package com.example.toshiba.articlesapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button submit;
    Context context;
    SharedPreferences sharedPreferences; //class used to store data lightweight as session
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("articles", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(sharedPreferences.getBoolean("loggedIn",true)){
            Intent i=new Intent(Login.this,Home.class);
            startActivity(i);
        }
        email=(EditText) findViewById(R.id.edittext1);
        password=(EditText) findViewById(R.id.edittext2);
        submit=(Button) findViewById(R.id.button);
        context = getApplicationContext();
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String s1 = email.getText().toString();
                    String s2 = password.getText().toString();
                    Log.e("email",s1);
                    Log.e("password", s2);
                    if((!s1.matches("")) && (!s2.matches(""))){
                        editor.putBoolean("loggedIn", true); //edit in shared preferences
                        editor.commit();
                        Intent i=new Intent(Login.this,Home.class);
                        startActivity(i);
                    }
                    else {
                        editor.putBoolean("loggedIn", false);
                        editor.commit();
                        Toast toast = Toast.makeText(context, "TYPE EMAIL AND PASSWORD", Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
                catch(NumberFormatException e){
                    Toast toast = Toast.makeText(context, "TYPE THE EMAIL AND PASSWORD", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });



    }
}
