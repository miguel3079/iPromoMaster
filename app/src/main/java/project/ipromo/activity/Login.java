package project.ipromo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import project.ipromo.ParseActivity.BeaconsEmpresas;
import project.ipromo.ParseActivity.Promociones;
import project.ipromo.R;

public class Login extends Activity {
    Promociones mainActivity = new Promociones();
    ParseObject parseobject;
    String Email;
    String Pass;
    List<ParseObject> ob;
    EditText email;
    EditText pass;
    Button btnLogin;
    Button btnResgitro;

    public String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.initialize(this, "TL06Yd6EASdGw77EdMzyf2XG03f9vR0WdbMqC1vF", "oHlxDUfAhGWpaYDnKJU3aQefpQxWgJ7PQEececAS");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
        btnLogin  = (Button) findViewById(R.id.btnLogin);
        email = (EditText) findViewById(R.id.txtName);
        pass = (EditText) findViewById(R.id.txtPass);
        btnResgitro = (Button)findViewById(R.id.btnRegistro);
        /////
        btnLogin.setOnClickListener(new View.OnClickListener() {
            ParseObject pObject = new ParseObject("User");
            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                Email = email.getText().toString();
                Pass = pass.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(Email, Pass,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                   idUser = (String) pObject.get("objectId");
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            Login.this,
                                            BeaconsEmpresas.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exist, please signup",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    public void registrarse(View view){
        Intent pasar = new Intent(Login.this,Registro.class);
        startActivity(pasar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
