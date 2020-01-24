package ru.bearloggers.cfuvmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button but1 = null;
    private Button but2 = null;
    private Button but3 = null;
    private Button floor4 = null;
    private Button floor3 = null;
    private Button floor2 = null;
    private Button floor1 = null;
    private Button floor0 = null;
    private int x = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but1 = (Button) findViewById(R.id.but1);
        but2 = (Button) findViewById(R.id.but2);
        but3 = (Button) findViewById(R.id.but3);
        floor4 = (Button) findViewById(R.id.floor4);
        floor3 = (Button) findViewById(R.id.floor3);
        floor2 = (Button) findViewById(R.id.floor2);
        floor1 = (Button) findViewById(R.id.floor1);
        floor0 = (Button) findViewById(R.id.floor0);
        Animation amime = AnimationUtils.loadAnimation(this, R.anim.alpha);
        //buttID.setClickable(false);
    }


    public void ToastBetta ()
    {
          Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
    }


    public void korpus_A(View view)
    {
        if (x!= 0) {
            Animation anime = AnimationUtils.loadAnimation(this, R.anim.alpha);
            floor4.startAnimation(anime);
            floor3.startAnimation(anime);
            floor2.startAnimation(anime);
            floor1.startAnimation(anime);
            floor0.startAnimation(anime);
        }
        else if (x == 0)
        {
            x=1;
        }

        floor4.setVisibility(View.VISIBLE);
        floor3.setVisibility(View.VISIBLE);
        floor2.setVisibility(View.VISIBLE);
        floor1.setVisibility(View.VISIBLE);
        floor0.setVisibility(View.VISIBLE);
        floor0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, floor0A.class);
                startActivity(intent);
            }
        });
    }

    public void korpus_B(View view)
    {
        if (x!= 0) {
        Animation anime = AnimationUtils.loadAnimation(this, R.anim.alpha);
        floor4.startAnimation(anime);
        floor3.startAnimation(anime);
        floor2.startAnimation(anime);
        floor1.startAnimation(anime);
        floor0.startAnimation(anime);
        }
        else if (x == 0)
        {
            x=1;
        }



        floor4.setVisibility(View.VISIBLE);
        floor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor3.setVisibility(View.VISIBLE);
        floor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor2.setVisibility(View.VISIBLE);
        floor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor1.setVisibility(View.VISIBLE);
        floor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor0.setVisibility(View.VISIBLE);
        floor0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });
    }


    public void korpus_C(View view)
    {
        if (x!= 0) {
            Animation anime = AnimationUtils.loadAnimation(this, R.anim.alpha);
            floor4.startAnimation(anime);
            floor3.startAnimation(anime);
            floor2.startAnimation(anime);
            floor1.startAnimation(anime);
            floor0.startAnimation(anime);
        }
        else if (x == 0)
        {
            x=1;
        }



        floor4.setVisibility(View.VISIBLE);
        floor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor3.setVisibility(View.VISIBLE);
        floor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor2.setVisibility(View.VISIBLE);
        floor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor1.setVisibility(View.VISIBLE);
        floor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });

        floor0.setVisibility(View.VISIBLE);
        floor0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastBetta();
            }
        });
    }

    }
