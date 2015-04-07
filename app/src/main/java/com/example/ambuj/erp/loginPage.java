package com.example.ambuj.erp;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;


public class loginPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final EditText usernamefield = (EditText)findViewById(R.id.usernamefield);
        final EditText passwordfield = (EditText)findViewById(R.id.passwordfield);

        final Button loginbutton = (Button)findViewById(R.id.LoginButton);
        loginbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = usernamefield.getText().toString();
                String password = passwordfield.getText().toString();
                try{
                    if(username.length() > 0 && password.length() >0)
                    {
                        Database_Helper dbUser = new Database_Helper(loginPage.this);
                        dbUser.openDataBase();

                        if(dbUser.Login(username, password))
                        {
                            Toast.makeText(loginPage.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(loginPage.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                        }
                        dbUser.close();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(loginPage.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}
