package edu.ftiuksw.mygallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GalleryPagerAdapter extends PagerAdapter {

    private final Context context;
    private final ArrayList<HashMap<String, String>> albumList;
    private final LayoutInflater inflater;

    public GalleryPagerAdapter(Context context, ArrayList<HashMap<String, String>> albumList) {
        this.context = context;
        this.albumList = albumList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.gallery_preview_item, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        String path = albumList.get(position).get(Function.KEY_PATH);
        Glide.with(context).load(new File(path)).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
