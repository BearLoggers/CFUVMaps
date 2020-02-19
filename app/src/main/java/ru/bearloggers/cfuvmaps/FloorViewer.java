package ru.bearloggers.cfuvmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
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
    private int floor_for_mark = -1;
    private float screen_width = -1;
    private float screen_height = -1;

    private float scale = 3.5f;
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
    boolean insadee(int froom) {
        final Dialog dialog = new Dialog(FloorViewer.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCancelable(false);
        dialog.show();


        Button close_dialog = (Button) dialog.findViewById(R.id.close);

        switch (froom) {
            case 0:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.lupa));
                break;
            case 1:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.vernadsiy));
                break;
            case 2:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.lab9));
                break;
            case 3:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.lupa));
                break; // 8 lab
            case 4:
                 close_dialog.setBackground(this.getResources().getDrawable(R.drawable.lab7));
                 break; // 7 lab
            case 5:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.zal));
                break; // aktoviy
            case 6:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.floor2holl));
                break; // 41-45
            case 7:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.lilsadcat));
                break; // 302
            case 8:
                close_dialog.setBackground(this.getResources().getDrawable(R.drawable.phictech));
                break; // phistech
        }

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


        if (imageName == "floor0")
        {
            floor_for_mark = 0;
        }
        if (imageName == "floor1")
        {
            floor_for_mark = 1;
        }
        if (imageName == "floor2")
        {
            floor_for_mark = 2;
        }
        if (imageName == "floor3")
        {
            floor_for_mark = 3;
        }
        if (imageName == "tsokolo1betta")
        {
            floor_for_mark = 4;
        }


        Toast.makeText(this, imageName, Toast.LENGTH_SHORT).show();

        myLayout = findViewById(R.id.myLayout);

        screen_width = myLayout.getMeasuredWidth();
        screen_height = myLayout.getMeasuredHeight();

        floorN = in.getIntExtra("FLOOR", -1);
        roomN = in.getIntExtra("ROOM", -1);

        mainImage = findViewById(R.id.luckyID);

        switch (floorN) {
            case 0:
                floor_for_mark = 0;
                rooms = new Room[] {
                        new Room(-1, -1, -1, -1, 791,-306, 1),
                        new Room(-1, -1, -1, -1, 791,-306, 2),
                        new Room(-1, -1, -1, -1, 791,-306, 3),
                        new Room(-1, -1, -1, -1, 603,-306, 4),

                        new Room(-1, -1, -1, -1, 441,-306, 6),
                        new Room(-1, -1, -1, -1, 554,-306, 7),
                        new Room(-1, -1, -1, -1, 421,-306, 8),
                        new Room(-1, -1, -1, -1, 267,-306, 9),
                        new Room(-1, -1, -1, -1, 282,-306, 10),
                        new Room(-1, -1, -1, -1, 135,-306, 11),

                        new Room(-1, -1, -1, -1, 135,-306, 13),

                        new Room(-1, -1, -1, -1, -139,-306, 14),
                        new Room(-1, -1, -1, -1, -271,-306, 15),
                        new Room(-1, -1, -1, -1, -500,-306, 16),
                        new Room(-1, -1, -1, -1, -500,-306, 17),
                        new Room(-1, -1, -1, -1, -341,-272, 18),
                        new Room(-1, -1, -1, -1, -144,-269, 19),
                        new Room(-1, -1, -1, -1, -335,-112, 20),
                        new Room(-1, -1, -1, -1, -150,-112, 21),
                        new Room(-1, -1, -1, -1, -331,250, 22),
                        new Room(-1, -1, -1, -1, -331,250, 23),
                        new Room(-1, -1, -1, -1, -140,8, 24),
                        new Room(-1, -1, -1, -1, -140,235, 25),
                        new Room(-1, -1, -1, -1, -333,8, 26),
                        new Room(-1, -1, -1, -1, -333,479, 27),

                        new Room(-1, -1, -1, -1, -131,820, 30),
                        new Room(-1, -1, -1, -1, -342,720, 31),
                        new Room(-1, -1, -1, -1, -142,675, 32),

                        new Room(-1, -1, -1, -1, -144,-269, 37),

                };
                break;
            case 1:
                floor_for_mark = 1;
                rooms = new Room[] {
                        new Room(-1, -1, -1, -1, -368,-306, 1),
                        new Room(-1, -1, -1, -1, -368,-306, 2),
                        new Room(-1, -1, -1, -1, -213,-306, 3),
                        new Room(-1, -1, -1, -1, -213,-306, 4),

                        new Room(-1, -1, -1, -1, -213,-306, 6),
                        new Room(-1, -1, -1, -1, -360,-306, 11),
                        new Room(-1, -1, -1, -1, -213,-306, 12),
                        new Room(-1, -1, -1, -1, -360,-306, 13),
                        new Room(-1, -1, -1, -1, -213,-306, 14),
                        new Room(-1, -1, -1, -1, -360,-306, 15),
                        new Room(-1, -1, -1, -1, -213,-306, 16),
                        new Room(-1, -1, -1, -1, -360,-306, 17),
                        new Room(-1, -1, -1, -1, -213,-306, 18),
                        new Room(-1, -1, -1, -1, -350,46, 19),
                        new Room(-1, -1, -1, -1, -213,-306, 20),
                        new Room(-1, -1, -1, -1, -350,215, 21),
                        new Room(-1, -1, -1, -1, -190,80, 22),
                        new Room(-1, -1, -1, -1, -353,307, 23),
                        new Room(-1, -1, -1, -1, -190,286, 24),
                        new Room(-1, -1, -1, -1, -350,407, 25),
                        new Room(-1, -1, -1, -1, -220, 431, 26),

                        new Room(-1, -1, -1, -1, 16, 683, 28),
                        new Room(-1, -1, -1, -1, -33, 774, 29),
                        new Room(-1, -1, -1, -1, 90, 690, 30),
                        new Room(-1, -1, -1, -1, 203, 693, 32),
                        new Room(-1, -1, -1, -1, 203, 693, 33),
                        new Room(-1, -1, -1, -1, 312, 725, 34),
                        new Room(-1, -1, -1, -1, 387, 712, 35),
                        new Room(-1, -1, -1, -1, 270, 801, 36),
                        new Room(-1, -1, -1, -1, 687, 744, 37),
                        new Room(-1, -1, -1, -1, 597, 712, 38),
                        new Room(-1, -1, -1, -1, 687, 744, 39),
                        new Room(-1, -1, -1, -1, 687, 744, 40),

                };
                break;
            case 2:
                floor_for_mark = 2;
                rooms = new Room[] {
                        new Room(-1, -1, -1, -1, 86,-276, 1),
                        new Room(-1, -1, -1, -1, -122,-296, 2),
                        new Room(-1, -1, -1, -1, -122,-296, 3),
                        new Room(-1, -1, -1, -1, -267,-276, 4),
                        new Room(-1, -1, -1, -1, -267,-276, 5),
                        new Room(-1, -1, -1, -1, -188,-306, 6),

                        new Room(-1, -1, -1, -1, -188,-306, 8),
                        new Room(-1, -1, -1, -1, -420,-306, 9),
                        new Room(-1, -1, -1, -1, -189,-134, 10),
                        new Room(-1, -1, -1, -1, -420,-306, 11),
                        new Room(-1, -1, -1, -1, -189,-134, 12),

                        new Room(-1, -1, -1, -1, -186,34, 14),
                        new Room(-1, -1, -1, -1, -351,-304, 15),
                        new Room(-1, -1, -1, -1, -176,334, 16),
                        new Room(-1, -1, -1, -1, -176,334, 17),
                        new Room(-1, -1, -1, -1, -176,334, 18),
                        new Room(-1, -1, -1, -1, -333,-221, 19),
                        new Room(-1, -1, -1, -1, -195,342, 20),
                        new Room(-1, -1, -1, -1, -341,-157, 21),
                        new Room(-1, -1, -1, -1, -195,342, 22),
                        new Room(-1, -1, -1, -1, -331,-60, 23),

                        new Room(-1, -1, -1, -1, -350,32, 25),

                        new Room(-1, -1, -1, -1, -352,188, 27),
                        new Room(-1, -1, -1, -1, 349,723, 28),
                        new Room(-1, -1, -1, -1, -352,288, 29),
                        new Room(-1, -1, -1, -1, 397,756, 30),
                        new Room(-1, -1, -1, -1, -352,288, 31),

                        new Room(-1, -1, -1, -1, -341,460, 33),
                        new Room(-1, -1, -1, -1, 566,753, 34),
                        new Room(-1, -1, -1, -1, -341,460, 35),
                        new Room(-1, -1, -1, -1, 566,753, 36),
                        new Room(-1, -1, -1, -1, -260,595, 37),
                        new Room(-1, -1, -1, -1, -260,595, 38),
                        new Room(-1, -1, -1, -1, -260,595, 39),

                        new Room(-1, -1, -1, -1, 855,781, 42),
                        new Room(-1, -1, -1, -1, 288,823, 43),
                        new Room(-1, -1, -1, -1, 855,781, 44),
                        new Room(-1, -1, -1, -1, 350,830, 45),

                        new Room(-1, -1, -1, -1, 428,806, 47),

                        new Room(-1, -1, -1, -1, 428,806, 49),

                        new Room(-1, -1, -1, -1, 678,827, 55),

                        new Room(-1, -1, -1, -1, 855,781, 61),

                        new Room(-1, -1, -1, -1, 855,781, 63),

                };
                break;
            case 3:
                floor_for_mark = 3;
                rooms = new Room[] {
                        new Room(-1, -1, -1, -1, -385,-306, 1),
                        new Room(-1, -1, -1, -1, -640,-306, 2),
                        new Room(-1, -1, -1, -1, -490,-306, 3),
                        new Room(-1, -1, -1, -1, -490,-120, 4),
                        new Room(-1, -1, -1, -1, -274,-306, 5),
                        new Room(-1, -1, -1, -1, -270,-47, 6),
                        new Room(-1, -1, -1, -1, -481,82, 7),
                        new Room(-1, -1, -1, -1, -482,235, 8),
                        new Room(-1, -1, -1, -1, -280,221, 9),
                        new Room(-1, -1, -1, -1, -500,446, 10),
                        new Room(-1, -1, -1, -1, -286,68, 11),
                        new Room(-1, -1, -1, -1, -465,586, 12),
                        new Room(-1, -1, -1, -1, -465,586, 13),
                        new Room(-1, -1, -1, -1, -465,586, 14),
                        new Room(-1, -1, -1, -1, -278,465, 15),
                        new Room(-1, -1, -1, -1, -278,465, 16),
                        new Room(-1, -1, -1, -1, -465,586, 17),
                        new Room(-1, -1, -1, -1, -496,888, 18),
                        new Room(-1, -1, -1, -1, -288,690, 19),
                        new Room(-1, -1, -1, -1, -496,888, 20),
                        new Room(-1, -1, -1, -1, -496,1022, 21),
                        new Room(-1, -1, -1, -1, -274,969, 22),
                        new Room(-1, -1, -1, -1, -381,1200, 23),
                        new Room(-1, -1, -1, -1, -153,1200, 24),
                        new Room(-1, -1, -1, -1, 38,1200, 25),
                        new Room(-1, -1, -1, -1, 191,1200, 26),
                        new Room(-1, -1, -1, -1, 86,1200, 27),
                        new Room(-1, -1, -1, -1, 191,1200, 28),
                        new Room(-1, -1, -1, -1, 191,1200, 29),
                        new Room(-1, -1, -1, -1, 353,1200, 30),
                        new Room(-1, -1, -1, -1, 353,1200, 31),
                        new Room(-1, -1, -1, -1, 576,1200, 32),
                        new Room(-1, -1, -1, -1, 576,1200, 33),
                        new Room(-1, -1, -1, -1, 689,1200, 34),
                        new Room(-1, -1, -1, -1, 850,1200, 35),

                        new Room(-1, -1, -1, -1, 576,1200, 37),
                        new Room(-1, -1, -1, -1, 474,1200, 38),
                        new Room(-1, -1, -1, -1, 576,1200, 39),
                        new Room(-1, -1, -1, -1, 576,1200, 40),

                };
                break;
            case 4:
                floor_for_mark = 4;
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


                if (event.getAction() == MotionEvent.ACTION_UP ){
                    debug(ImageX, ImageY);                          //тут я узнавал кооры углов комнат
                }





                if( (floor_for_mark == 4) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=217 && ImageX<= 231) && (ImageY>=362 && ImageY<=390 ) ){
                    insadee(0);  // кот на 4м этаже
                }
                if(  (floor_for_mark == 1) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=257 && ImageX<= 265) && (ImageY>=177 && ImageY<=186 )  ){
                    insadee(1);  // холл(хохол)
                }
                if(  (floor_for_mark == 0) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=186 && ImageX<= 194) && (ImageY>=386 && ImageY<=395 )  ){
                    insadee(2);  // 9ая лаба
                }
                if(  (floor_for_mark == 0) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=173 && ImageX<= 183) && (ImageY>=336 && ImageY<=346 )  ){
                    insadee(3);  // 8ая лаба
                }
                if(  (floor_for_mark == 0) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=156 && ImageX<= 163) && (ImageY>=336 && ImageY<=346 )  ){
                    insadee(4);  // 7ая лаба
                }
                if(  (floor_for_mark == 2) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=266 && ImageX<= 272) && (ImageY>=98 && ImageY<=108 )  ){
                    insadee(5);  // актовый
                }
                if(  (floor_for_mark == 2) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=262 && ImageX<= 270) && (ImageY>=156 && ImageY<=165 )  ){
                    insadee(6);  // 41-45
                }
                if(  (floor_for_mark == 3) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=305 && ImageX<= 313) && (ImageY>=331 && ImageY<=341 )  ){
                    insadee(7);  // 302
                }
                if(  (floor_for_mark == 3) && (event.getAction() == MotionEvent.ACTION_DOWN) && (ImageX>=270 && ImageX<= 278) && (ImageY>=88 && ImageY<=98 )  ){
                    insadee(8);  // phistech
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
