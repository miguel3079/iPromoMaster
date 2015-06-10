package project.ipromo.ParseActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import project.ipromo.R;

public class GridViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<EmpresaList> empresaList = null;
    private ArrayList<EmpresaList> arraylist;
    public String id;

    public GridViewAdapter(Context context, List<EmpresaList> phonearraylist) {
        this.context = context;
        this.empresaList = phonearraylist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<EmpresaList>();
        this.arraylist.addAll(phonearraylist);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        ImageView empresa;
    }

    @Override
    public int getCount() {
        return empresaList.size();
    }

    @Override
    public Object getItem(int position) {
        return empresaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.gridview_item, null);
            // Locate the ImageView in gridview_item.xml
            holder.empresa = (ImageView) view.findViewById(R.id.phone);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Load image into GridView
        imageLoader.DisplayImage(empresaList.get(position).getImagen(),
                holder.empresa);
        // Capture GridView item click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(context, Promociones.class);
                intent.putExtra("id",empresaList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });
        return view;
    }
}