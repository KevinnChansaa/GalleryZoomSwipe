// GalleryPreview.java
package edu.ftiuksw.mygallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class GalleryPreview extends AppCompatActivity {
    private ViewPager viewPager;
    private ArrayList<String> imagePaths;
    private int currentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_preview);

        Intent intent = getIntent();
        imagePaths = intent.getStringArrayListExtra("imagePaths");
        currentPosition = intent.getIntExtra("position", 0);

        viewPager = findViewById(R.id.viewPager);
        ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), imagePaths);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(currentPosition);
    }

    private static class ImagePagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<String> imagePaths;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> imagePaths) {
            super(fm);
            this.imagePaths = imagePaths;
        }

        @Override
        public Fragment getItem(int position) {
            return AlbumActivity.ImageFragment.newInstance(imagePaths.get(position));
        }

        @Override
        public int getCount() {
            return imagePaths.size();
        }
    }
}
