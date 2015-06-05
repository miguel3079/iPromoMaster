package project.ipromo.ParseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

import project.ipromo.R;

public class SingleItemView extends Activity {
	// Declare Variables
	String title;
	String subtitle;
	String description;
	String image;
	String position;
    String fecha;
    String idPromo;
	ImageLoader imageLoader = new ImageLoader(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);

		Intent i = getIntent();
		// Get the result of rank
		title = i.getStringExtra("rank");
		// Get the result of country
		subtitle = i.getStringExtra("country");
		// Get the result of population
		description = i.getStringExtra("population");
		// Get the result of flag
		image = i.getStringExtra("flag");

        fecha = i.getStringExtra("fecha");

        idPromo = i.getStringExtra("idPromo");
		// Locate the TextViews in singleitemview.xml
		TextView txtrank = (TextView) findViewById(R.id.rank);
		TextView txtcountry = (TextView) findViewById(R.id.country);
		TextView txtpopulation = (TextView) findViewById(R.id.population);
        TextView txtfecha = (TextView)findViewById(R.id.textView);
        TextView txtid = (TextView)findViewById(R.id.txtId);
		// Locate the ImageView in singleitemview.xml
		ImageView imgflag = (ImageView) findViewById(R.id.flag);

		// Set results to the TextViews
		txtrank.setText(title);
		txtcountry.setText(subtitle);
		txtpopulation.setText(description);
        txtfecha.setText(fecha);
        txtid.setText(idPromo);

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(image, imgflag);
	}
    public void canjearCupo(View view){
        ParseObject object = new ParseObject("User_Prom");
        object.put("id_prom",idPromo);
        object.saveInBackground();

    }
}