package project.ipromo.ParseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import project.ipromo.R;

public class SingleItemView extends ActionBarActivity {
	// Declare Variables
	String title;
	String subtitle;
	String description;
	String image;
    String imageCodigo;
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
		title = i.getStringExtra("title");
		// Get the result of country
		subtitle = i.getStringExtra("subtitle");
		// Get the result of population
		description = i.getStringExtra("description");
		// Get the result of flag
		image = i.getStringExtra("image");
        imageCodigo = i.getStringExtra("imageCodigo");

        fecha = i.getStringExtra("fecha");

        idPromo = i.getStringExtra("idPromo");
		// Locate the TextViews in singleitemview.xml
		TextView txttitle = (TextView) findViewById(R.id.title);

		TextView txtdescription = (TextView) findViewById(R.id.description);


		// Locate the ImageView in singleitemview.xml
		ImageView imageCupon = (ImageView) findViewById(R.id.image);
        ImageView imagenCodigo = (ImageView)findViewById(R.id.imageCodigo);
		// Set results to the TextViews
        txttitle.setText(title);
		txtdescription.setText(description);


		imageLoader.DisplayImage(image, imageCupon);
        imageLoader.DisplayImage(imageCodigo, imagenCodigo);
	}

}