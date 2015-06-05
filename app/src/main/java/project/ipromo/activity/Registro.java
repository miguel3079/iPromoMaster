package project.ipromo.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

import project.ipromo.R;

public class Registro extends ActionBarActivity {
    EditText name;
    EditText pass;
    EditText email;
    EditText rpass;
    Button Registro;
    ///variables
    String Name;
    String Pass;
    String Rpass;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ///parse con
        Parse.initialize(this, "TL06Yd6EASdGw77EdMzyf2XG03f9vR0WdbMqC1vF", "oHlxDUfAhGWpaYDnKJU3aQefpQxWgJ7PQEececAS");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ////


        name = (EditText)findViewById(R.id.txtName);
        pass = (EditText)findViewById(R.id.txtPass);
        rpass = (EditText)findViewById(R.id.txtRPass);
        email = (EditText)findViewById(R.id.txtEmail);
        Registro = (Button)findViewById(R.id.btnRegistro);
        Registro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                Name = name.getText().toString();
                Pass = pass.getText().toString();
                Rpass = rpass.getText().toString();
                Email = email.getText().toString();
                // Force user to fill up the form
                if (Name.equals("") && Pass.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                }else if(!Pass.equals(Rpass)){
                    Toast.makeText(getApplicationContext(),
                            "Las contraseñas son distintas",
                            Toast.LENGTH_LONG).show();
                }  else{
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(Name);
                    user.setPassword(Pass);
                    user.setEmail(Email);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }

                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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
