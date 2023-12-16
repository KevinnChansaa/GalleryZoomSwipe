package edu.ftiuksw.mygallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AlbumActivity extends AppCompatActivity {
    private GridView albumGridView;
    private final ArrayList<HashMap<String, String>> albumList = new ArrayList<>();
    LoadInAlbum loadInAlbumTask;
    private String albumName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        albumGridView = findViewById(R.id.albumGridView);

        albumName = getIntent().getStringExtra("albumName");
        setTitle(albumName);

        loadInAlbumTask = new LoadInAlbum(this, albumList, albumName);
        loadInAlbumTask.execute();
    }

    public void setAdapter(SingleAlbumAdapter adapter) {
        albumGridView.setAdapter(adapter);
        albumGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Dapatkan path file media dari album yang dipilih
                String mediaFilePath = albumList.get(position).get(Function.KEY_PATH);
                File filePath = new File(albumList.get(position).get(Function.KEY_PATH));
                Uri mediaUri = FileProvider.getUriForFile(AlbumActivity.this, AlbumActivity.this.getApplicationContext().getPackageName() + ".provider", filePath);
                // Tentukan tipe media berdasarkan ekstensi file (misalnya, audio atau video)
                String mediaType = getMediaTypeFromFilePath(mediaFilePath);

                if (mediaType.equals("audio")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(mediaUri, "audio/*");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(intent);
                } else if (mediaType.equals("video")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(mediaUri, "video/*");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(intent);
                } else if (mediaType.equals("image")) {
                    Intent intent = new Intent(AlbumActivity.this, GalleryPreview.class);
                    intent.putStringArrayListExtra("imagePaths", getImagePaths());
                    intent.putExtra("position", position);
                    startActivity(intent);
                } else if (mediaType.equals("pdf")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(mediaUri, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(intent);
                }
            }
        });
    }
    private String getMediaTypeFromFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "unknown";
        }

        String lowerCasePath = filePath.toLowerCase();

        if (lowerCasePath.endsWith(".mp3") || lowerCasePath.endsWith(".wav") || lowerCasePath.endsWith(".ogg")) {
            return "audio";
        } else if (lowerCasePath.endsWith(".mp4") || lowerCasePath.endsWith(".3gp") || lowerCasePath.endsWith(".avi")|| lowerCasePath.endsWith(".mkv")) {
            return "video";
        } else if (lowerCasePath.endsWith(".jpg")|| lowerCasePath.endsWith(".jpeg") || lowerCasePath.endsWith(".png")){
            return "image";
        }else if (lowerCasePath.endsWith(".pdf")){
            return "pdf";
        } else{
            return "Unknown";
        }
    }

    public static class ImageFragment extends Fragment {
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

    // Inside AlbumActivity class
    private ArrayList<String> getImagePaths() {
        ArrayList<String> imagePaths = new ArrayList<>();

        for (HashMap<String, String> album : albumList) {
            String mediaType = album.get(Function.KEY_MEDIA_TYPE);
            if (mediaType != null && mediaType.equals("image")) {
                String path = album.get(Function.KEY_PATH);
                if (path != null) {
                    imagePaths.add(path);
                }
            }
        }

        return imagePaths;
    }

}