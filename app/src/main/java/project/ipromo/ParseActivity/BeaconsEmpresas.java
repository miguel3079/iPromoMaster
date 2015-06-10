package project.ipromo.ParseActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridView;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import project.ipromo.R;

public class BeaconsEmpresas extends ActionBarActivity {
    public String id;
    // Declare Variables
    GridView gridview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    GridViewAdapter adapter;
    private List<EmpresaList> empresasList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(this, "TL06Yd6EASdGw77EdMzyf2XG03f9vR0WdbMqC1vF", "oHlxDUfAhGWpaYDnKJU3aQefpQxWgJ7PQEececAS");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
        // Get the view from gridview_main.xml
        setContentView(R.layout.activity_beacons_empresas);
        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(BeaconsEmpresas.this);
            // Set progressdialog title
            mProgressDialog.setTitle("iPromo");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            empresasList = new ArrayList<EmpresaList>();

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Beacons");
            // Locate the column named "position" in Parse.com and order list
            // by ascending
            query.orderByAscending("position");
            try {
                ob = query.find();
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
            for (ParseObject country : ob) {
                ParseFile image = (ParseFile) country.get("Imagen");
                id = (String) country.get("Uuid");
                EmpresaList map = new EmpresaList();
                map.setImagen(image.getUrl());
                map.setId(id);
                empresasList.add(map);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the gridview in gridview_main.xml
            gridview = (GridView) findViewById(R.id.gridview);
            // Pass the results into ListViewAdapter.java
            adapter = new GridViewAdapter(BeaconsEmpresas.this,
                    empresasList);
            // Binds the Adapter to the ListView
            gridview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
