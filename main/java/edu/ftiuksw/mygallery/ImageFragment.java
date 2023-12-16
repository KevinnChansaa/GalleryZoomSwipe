// ImageFragment.java
package edu.ftiuksw.mygallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.File;

public class ImageFragment extends Fragment {
    private String imagePath;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(String path) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString("path", path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imagePath = getArguments().getString("path");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(this).load(new File(imagePath)).into(imageView);
        return view;
    }
}
