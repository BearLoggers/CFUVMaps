package ru.bearloggers.cfuvmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class FloorViewer extends AppCompatActivity {

    String imageName = null;
    ImageView mainImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floorviewer);
        imageName = getIntent().getStringExtra("IMAGE_NAME");
        Toast.makeText(this, imageName, Toast.LENGTH_SHORT).show();

        mainImage = findViewById(R.id.luckyID);
        Resources resources = getResources();
        try {
            final int resourceId = resources.getIdentifier(imageName, "drawable", getPackageName());
            mainImage.setImageResource(resourceId);
        }
        catch (Resources.NotFoundException e) {
            Toast.makeText(this, "Картинка не найдена!", Toast.LENGTH_SHORT).show();
        }
    }
}
