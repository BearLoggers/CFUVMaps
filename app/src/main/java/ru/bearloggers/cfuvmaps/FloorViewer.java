package ru.bearloggers.cfuvmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FloorViewer extends AppCompatActivity {

    private ImageView mainImage = null;

    private RelativeLayout myLayout = null;

    private float px = -1;
    private float py = -1;
    private float X;
    private float Y;

    private float scale = 3f;
    private final float ScaleMin = 3f, ScaleMax = 10f;

    private ScaleGestureDetector SGD;
    //private Matrix matrix = new Matrix();

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            if (scale < ScaleMin) scale = ScaleMin;
            if (scale > ScaleMax) scale = ScaleMax;

            mainImage.setScaleX(scale);
            mainImage.setScaleY(scale);

            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floorviewer);
        String imageName = getIntent().getStringExtra("IMAGE_NAME");
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

        /* Инициализация для зума */
        SGD = new ScaleGestureDetector(this, new ScaleListener());

        /* Код для перемещения */
        myLayout = findViewById(R.id.myLayout);

        X = mainImage.getX();
        Y = mainImage.getY();

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Код для зума
                SGD.onTouchEvent(event);

                float x = event.getX();
                float y = event.getY();
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    float dX = x - px;
                    float dY = y - py;

                    if (px != -1 && py != -1) {
                        X += dX;
                        Y += dY;

                        float screen_width = myLayout.getMeasuredWidth();
                        float screen_height = myLayout.getMeasuredHeight();
                        float img_width = mainImage.getWidth();
                        float img_height = mainImage.getHeight();
                        float scale_x = mainImage.getScaleX();
                        float scale_y = mainImage.getScaleY();

                        float ax = (img_width  * (scale_x - 1)) / 2;
                        float ay = (img_height * (scale_y - 1)) / 2;

                        if (X - ax > 0) {
                            X = ax;
                        }
                        if (X + img_width + ax < screen_width) {
                            X = screen_width - img_width - ax;
                        }

                        if (Y - ay > 0) {
                            Y = ay;
                        }
                        if (Y + img_height + ay < screen_height) {
                            Y = screen_height - img_height - ay;
                        }

                        mainImage.setX(X);
                        mainImage.setY(Y);
                    }
                    px = x;
                    py = y;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    px = -1;
                    py = -1;
                }
                return true;
            }
        });
    }
}
