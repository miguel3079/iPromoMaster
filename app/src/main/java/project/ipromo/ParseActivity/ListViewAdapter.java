package project.ipromo.ParseActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.ipromo.R;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	private List<PromocionesTotal> promocionesList = null;
	private ArrayList<PromocionesTotal> arraylist;

	public ListViewAdapter(Context context,
			List<PromocionesTotal> promocionesList) {
		this.context = context;
		this.promocionesList = promocionesList;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<PromocionesTotal>();
		this.arraylist.addAll(promocionesList);
		imageLoader = new ImageLoader(context);
	}

	public class ViewHolder {
		TextView title;
		TextView precio;
        TextView fecha;
        TextView id;
		ImageView image;
	}

	@Override
	public int getCount() {
		return promocionesList.size();
	}

	@Override
	public Object getItem(int position) {
		return promocionesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.precio = (TextView) view.findViewById(R.id.precio);

			// Locate the ImageView in listview_item.xml
			holder.image = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.title.setText(promocionesList.get(position).getTitle());
       // holder.fecha.setText((worldpopulationlist.get(position).getFecha()).toString());
		holder.precio.setText(promocionesList.get(position).getPrecio());
		//holder.subtitle.setText(worldpopulationlist.get(position).getPopulation());
       // holder.id.setText(worldpopulationlist.get(position).getId());
		// Set the results into ImageView
		imageLoader.DisplayImage(promocionesList.get(position).getImage(),
				holder.image);
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(context, SingleItemView.class);

				intent.putExtra("title",
						(promocionesList.get(position).getTitle()));

				intent.putExtra("subtitle",
						(promocionesList.get(position).getSubtitle()));

				intent.putExtra("description",
						(promocionesList.get(position).getDescripcion()));
                intent.putExtra("fecha",
                        (promocionesList.get(position).getFecha().toString()));

				intent.putExtra("image",
						(promocionesList.get(position).getImage()));

                intent.putExtra("imageCodigo",
                        (promocionesList.get(position).getImageCodigo()));
                // Id
                intent.putExtra("idPromo",(promocionesList.get(position).getId()));
				// Start SingleItemView Class
				context.startActivity(intent);
			}
		});
		return view;
	}

}
