package ru.bearloggers.cfuvmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FloorViewer extends AppCompatActivity {

    private ImageView mainImage = null;

    private RelativeLayout myLayout = null;

    private Room[] rooms;
    // Комната, по которой ведется поиск (-1 если не ведется)
    private int roomN = -1;
    private int floorN = -1;

    private float px = -1;
    private float py = -1;
    private float X = 200;
    private float Y = 300;

    private float screen_width = -1;
    private float screen_height = -1;

    private float scale = 3f;
    private final float ScaleMin = 3f, ScaleMax = 7f;

    private boolean isDrag = false;

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

    //современные решения
    void debug(float x, float y) {
        Toast.makeText(this, String.format("X= %f Y= %f", x, y), Toast.LENGTH_SHORT).show();
    }

    // диалог это новый layout
    boolean insadee() {
        final Dialog dialog = new Dialog(FloorViewer.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCancelable(false);
        dialog.show();
        Button close_dialog = (Button) dialog.findViewById(R.id.close);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent in = getIntent();

        setContentView(R.layout.activity_floorviewer);
        String imageName = in.getStringExtra("IMAGE_NAME");
        Toast.makeText(this, imageName, Toast.LENGTH_SHORT).show();

        myLayout = findViewById(R.id.myLayout);

        screen_width = myLayout.getMeasuredWidth();
        screen_height = myLayout.getMeasuredHeight();

        floorN = in.getIntExtra("FLOOR", -1);
        roomN = in.getIntExtra("ROOM", -1);

        mainImage = findViewById(R.id.luckyID);

        switch (floorN) {
            case 0:
                rooms = new Room[] {
                        new Room(198, 205, 250, 234, 1250,1013, 1),
                        new Room(154, 206, 197, 234,954, 1116, 2),
                        new Room(422, 547, 480, 586, -658, -879, 3),
                        new Room(217, 362, 231, 390, 25, -705, 4)  //кабинет по координатам с моего телефона

                };
                break;

            default:
                rooms = new Room[] { };
        }

        Resources resources = getResources();
        try {
            final int resourceId = resources.getIdentifier(imageName, "drawable", getPackageName());
            mainImage.setImageResource(resourceId);
        }
        catch (Resources.NotFoundException e) {
            Toast.makeText(this, "Картинка не найдена!", Toast.LENGTH_SHORT).show();
        }

        boolean isRoomFound = false;
        if (roomN != -1) {
            for (Room r: rooms) {
                if (r.number == roomN) {
                    scale = ScaleMax;
                    X = r.absX;
                    Y = r.absY;

                    mainImage.setScaleX(scale);
                    mainImage.setScaleY(scale);

                    isRoomFound = true;
                    break;
                }
            }
        }
        if (!isRoomFound && roomN != -1) {
            Toast.makeText(this, String.format("Аудитория %d не найдена на этаже %d", roomN, floorN), Toast.LENGTH_SHORT).show();
        }


        /* Инициализация для зума */
        SGD = new ScaleGestureDetector(this, new ScaleListener());

        /* Код для перемещения */
        /*if (roomN == -1) {
            X = mainImage.getX();
            Y = mainImage.getY();
        }*/

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here

                        mainImage.setX(X);
                        mainImage.setY(Y);
                    }
                },
                1500
        );



        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Код для зума
                SGD.onTouchEvent(event);

                float x = event.getX();
                float y = event.getY();


                float screen_width = myLayout.getMeasuredWidth();
                float screen_height = myLayout.getMeasuredHeight();
                float img_width = mainImage.getWidth();
                float img_height = mainImage.getHeight();
                float scale_x = mainImage.getScaleX();
                float scale_y = mainImage.getScaleY();


                float ax = (img_width  * (scale_x - 1)) / 2;
                float ay = (img_height * (scale_y - 1)) / 2;

                float ImageX = (x - X + ax) / scale_x;  // перенесенные relativeImageX
                float ImageY = (y - Y + ay) / scale_y;

                /*
                if (event.getAction() == MotionEvent.ACTION_UP ){
                    debug(ImageX, ImageY);                          //тут я узнавал кооры углов комнат
                }
                */


                if((ImageX>=217 && ImageX<= 231) && (ImageY>=362 && ImageY<=390 ) ){
                    insadee();  // да, я глупый и не смог разобраться с (clickPosition.isInsideRoom(r))
                }


                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    isDrag = true;
                    float dX = x - px;
                    float dY = y - py;



                    if (px != -1 && py != -1) {
                        X += dX;
                        Y += dY;

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

                        Log.v("test", String.format("X, Y: %f, %f", X, Y));

                    }
                    px = x;
                    py = y;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    px = -1;
                    py = -1;

                    if (!isDrag) {
                        /*
                        float relativeImageX = (x - X + ax) / scale_x; //я их перенес выше и назвал чуть иначе
                        float relativeImageY = (y - Y + ay) / scale_y;

                         */

                        Log.v("TESTS", String.format("rX, rY: %f, %f", ImageX, ImageY));
                        Point clickPosition = new Point(ImageX, ImageY);

                        for (Room r : rooms) {
                            if (clickPosition.isInsideRoom(r)) {
                                Log.v("TESTS", String.format("Inside %d!", r.number));
                            }
                        }


                    }

                    isDrag = false;
                }
                else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.v("TESTS", String.format("x, y: %f, %f", x, y));
                }
                return true;
            }
        });
    }

    // Helper classes
    class Room {
        float x1, y1, x2, y2, absX, absY;
        int number;

        Room(float x1, float y1, float x2, float y2, float absX, float absY, int number) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;

            this.absX = absX;
            this.absY = absY;

            this.number = number;
        }
    }

    class Point {
        private float x;
        private float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        boolean isInsideRect(float rectX1, float rectY1, float rectX2, float rectY2) {
            return (this.x >= rectX1  &&  this.y >= rectY1  &&
                    this.x <= rectX2 && this.y <= rectY2);
        }

        boolean isInsideRoom(Room room) {
            return isInsideRect(room.x1, room.y1, room.x2, room.y2);
        }
    }
}
