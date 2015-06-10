package project.ipromo.ParseActivity;

import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.ipromo.R;
import project.ipromo.activity.Login;
import project.ipromo.service.iPromoBeaconService;

import static project.ipromo.ParseActivity.Utils.getRoundedCornerBitmap;

public class Promociones extends ActionBarActivity {
	// Declare Variables
	ListView listview;
    Bundle bundle;
	List<ParseObject> ob;
    List<ParseObject> op;
	ProgressDialog mProgressDialog;
	ListViewAdapter adapter;
	private List<PromocionesTotal> promocionesList = null;
    ///menu lat///
    private String[] titulos;
    private DrawerLayout NavDrawerLayout;
    private ListView NavList;
    private ArrayList<Item_objct> NavItms;
    private TypedArray NavIcons;
    NavigationAdapter NavAdapter;
    Login login;
    SingleItemView simplist;
    ///////
    iPromoBeaconService ipromobeaconservice;
    BeaconsEmpresas beaconsEmpresas;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.listview_main);
        login = new Login();
        simplist = new SingleItemView();
        menuLateral();
        ipromobeaconservice = new iPromoBeaconService();
        beaconsEmpresas = new BeaconsEmpresas();
		// Execute RemoteDataTask AsyncTask
        Parse.initialize(this, "TL06Yd6EASdGw77EdMzyf2XG03f9vR0WdbMqC1vF", "oHlxDUfAhGWpaYDnKJU3aQefpQxWgJ7PQEececAS");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
		new RemoteDataTask().execute();

        bundle = getIntent().getExtras();



        EditText editText = (EditText)findViewById(R.id.search);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }


        });
	}

	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(Promociones.this);
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
            promocionesList = new ArrayList<PromocionesTotal>();
			try {
				// Locate the class table named "Country" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"Promocion");


				// Locate the column named
				// by ascending


				query.orderByAscending("createdAt");



                ob = query.find();
                for (ParseObject promo : ob) {
                    // Locate images
                    if(promo.get("Uuid").toString().equals(bundle.getString("id"))){
                        ParseFile image = (ParseFile) promo.get("Image");
                        ParseFile imageCodigo = (ParseFile) promo.get("ImageCodigo");
                        PromocionesTotal prom = new PromocionesTotal();
                        prom.setTitle((String) promo.get("Title"));
                        prom.setPrecio((String) promo.get("Precio"));
                        prom.setFecha((Date) promo.get("Date_end"));
                        prom.setDescripcion((String) promo.get("Description"));
                        prom.setId((String) promo.get("objectId"));
                        prom.setImageCodigo(imageCodigo.getUrl());
                        prom.setImage(image.getUrl());
                        promocionesList.add(prom);}
                }

			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(Promociones.this,
                    promocionesList);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}

	}
    public void menuLateral(){
        NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Lista
        NavList = (ListView) findViewById(R.id.lista);
        //Declaramos el header el caul sera el layout de header.xml
        View header = getLayoutInflater().inflate(R.layout.header, null);
        //Establecemos header
        NavList.addHeaderView(header);
        //Tomamos listado  de imgs desde drawable
        NavIcons = getResources().obtainTypedArray(R.array.navigation_iconos);
        //Tomamos listado  de titulos desde el string-array de los recursos @string/nav_options
        titulos = getResources().getStringArray(R.array.nav_options);
        //Listado de titulos de barra de navegacion
        NavItms = new ArrayList<Item_objct>();
        //Agregamos objetos Item_objct al array
        //Ocio
        NavItms.add(new Item_objct(titulos[0], NavIcons.getResourceId(0, -1)));
        //Restauracion
        NavItms.add(new Item_objct(titulos[1], NavIcons.getResourceId(1, -1)));


        //Declaramos y seteamos nuestrp adaptador al cual le pasamos el array con los titulos
        NavAdapter= new NavigationAdapter(this,NavItms);
        NavList.setAdapter(NavAdapter);
        //Siempre vamos a mostrar el mismo titulo
    }

    public void redondearimagen(){
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(getRoundedCornerBitmap(getResources().getDrawable(R.drawable.perfil), true));
    }



}