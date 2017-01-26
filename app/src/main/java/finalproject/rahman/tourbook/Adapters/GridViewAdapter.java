package finalproject.rahman.tourbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/14/2017.
 */

public class GridViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> list;
    ImageView iv;
    View view;
    int pos;


    public GridViewAdapter(Context context, ArrayList<String> list) {
        this.context=context;
        this.list=list;
    }



    @Override
    public int getCount() {
       // Toast.makeText(context, list.size()+"", Toast.LENGTH_SHORT).show();
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
        {
            view=new View(context);
            view = inflater.inflate(R.layout.grid_view_inner_layout,null);
            iv=(ImageView)view.findViewById(R.id.grid_view_image);
           Bitmap bitmap= BitmapFactory.decodeFile(list.get(position));
            //bitmap.in

             pos=position;
            //Bitmap bitmap=list.get(position);
            //iv.setImageBitmap(Bitmap.createScaledBitmap(bitmap,200,200,false));
            Glide.with(context).load("file://" +list.get(position)).override(200,200).into(iv);
        }
        else
        {
            view=(View)convertView;
        }

        return view;
    }


}
