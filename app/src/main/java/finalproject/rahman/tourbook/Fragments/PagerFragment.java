package finalproject.rahman.tourbook.Fragments;

//import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/14/2017.
 */

public class PagerFragment extends Fragment {
    ArrayList<String> list;
    int pos;
    Bitmap bitmap;
    ImageView imageView;
    public PagerFragment() {
    }

    public static PagerFragment getImages(ArrayList<String> list,int position) {
        PagerFragment fragment=new PagerFragment();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("list",list);
        bundle.putInt("pos",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pager,container,false);
        imageView=(ImageView)view.findViewById(R.id.fragmentpager_image_iv);
        this.list=getArguments().getStringArrayList("list");
        this.pos=getArguments().getInt("pos");
        //bitmap = BitmapFactory.decodeFile(list.get(pos));
        //imageView.setImageBitmap(bitmap);
        Picasso.with(getActivity()).load("file://"+list.get(pos)).into(imageView);
        PreMainActivity.collapsingToolbarLayout.setTitle("Slide Show");
        return view;
    }
}
