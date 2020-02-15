package ru.bearloggers.cfuvmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private Button but1 = null;
    private Button but2 = null;
    private Button but3 = null;
    private Button floor4 = null;
    private Button floor3 = null;
    private Button floor2 = null;
    private Button floor1 = null;
    private Button floor0 = null;
    private Button searchEnter = null;
    private int x = 0;
    private TextView SearchText = null;

    private int neskvik = 0;


    View.OnClickListener lis(final String imageName, final int floor)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, FloorViewer.class);
                intent.putExtra("IMAGE_NAME", imageName);
                intent.putExtra("FLOOR", floor);
                intent.putExtra("ROOM", -1);
                startActivity(intent);
            }
        };
    }

    View.OnClickListener inBeta()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ToastBeta();
            }
        };
    }

    Button[] floorButtons = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) neskvik = 10;

        SearchText = findViewById(R.id.SearchText);
        searchEnter = findViewById(R.id.searchEnter);
        but1 = (Button) findViewById(R.id.but1);
        but2 = (Button) findViewById(R.id.but2);
        but3 = (Button) findViewById(R.id.but3);
        floor4 = (Button) findViewById(R.id.floor4);
        floor3 = (Button) findViewById(R.id.floor3);
        floor2 = (Button) findViewById(R.id.floor2);
        floor1 = (Button) findViewById(R.id.floor1);
        floor0 = (Button) findViewById(R.id.floor0);
        floorButtons = new Button[]{floor4, floor3, floor2, floor1, floor0};
        Animation amime = AnimationUtils.loadAnimation(this, R.anim.alpha);
        //buttID.setClickable(false);

        SearchText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        SearchText.setImeActionLabel("Несквик с пивом", KeyEvent.KEYCODE_ENTER);
        SearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    searchEnter.callOnClick();
                }
                return true;
            }
        });
    }


    public void ToastBeta()
    {
          Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
    }

    public void Find (View view)
    {
        String kabinet = SearchText.getText().toString();

        if (SearchText.getText().length() == 4) {
            Intent intent = new Intent(MainActivity.this, FloorViewer.class);
            int floor = charToInt( kabinet.charAt(0) );
            intent.putExtra("FLOOR", floor);

            int room = charToInt( kabinet.charAt(1) )*10 + charToInt( kabinet.charAt(2) );
            intent.putExtra("ROOM", room);
            char corpus = kabinet.charAt(3);
            switch (corpus) {
                case 'а':
                case 'А':   // Русская
                case 'A':   // Английская
                case 'a':
                    korpus_A(view);
                        switch (floor){
                            case 0:
                                intent.putExtra("IMAGE_NAME", "tsokolo1betta");
                                startActivity(intent);
                                break;
                            case 1:
                                intent.putExtra("IMAGE_NAME", "sadcat");
                                startActivity(intent);
                                break;
                            case 2:
                            case 3:
                            case 4:
                                ToastBeta();
                                break;
                            default:
                                Toast.makeText(this, "Введен несуществующий этаж", Toast.LENGTH_SHORT).show();
                        }
                    break;
                case 'б':
                case 'Б':
                    ToastBeta();
                    korpus_B(view);
                    break;
                case 'в':
                case 'В':
                    ToastBeta();
                    korpus_C(view);
                    break;
                default:
                    Toast.makeText(this, "Введен несуществующий корпус", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Некорректо введен номер кабинета", Toast.LENGTH_SHORT).show();
        }
    }

    public void korpus_A(View view)
    {
        neskvik++;
        if (neskvik == 10) {
            Toast.makeText(this, "Да вы человек культуры", Toast.LENGTH_LONG).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else if (neskvik == 20) {
            Toast.makeText(this, "Помните про культ несквика с пивом . . .", Toast.LENGTH_SHORT).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            neskvik = 0;
        }

        if (x != 0)
        {
            Animation anime = AnimationUtils.loadAnimation(this, R.anim.alpha);
            for (Button floor : floorButtons)
            {
                floor.setAnimation(anime);
            }
        }
        else
        {
            x = 1;
        }

        for (Button floor : floorButtons)
        {
            floor.setVisibility(View.VISIBLE);
        }
        floor3.setOnClickListener(lis("floor3", 3));
        floor2.setOnClickListener(lis("floor2", 2));
        floor1.setOnClickListener(lis("sadcat", 1));
        floor0.setOnClickListener(lis("tsokolo1betta", 0));

    }

    public void korpus_B(View view)
    {
        if (x != 0)
        {
            Animation anime = AnimationUtils.loadAnimation(this, R.anim.alpha);
            for (Button floor : floorButtons)
            {
                floor.startAnimation(anime);
            }
        }
        else
        {
            x = 1;
        }


        for (Button floor : floorButtons)
        {
            floor.setVisibility(View.VISIBLE);
            floor.setOnClickListener(inBeta());
        }
    }


    public void korpus_C(View view)
    {

        /*
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_layout);

        Button close_dialog = (Button) dialog.findViewById(R.id.close);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
         */

        if (x!= 0)
        {
            Animation anime = AnimationUtils.loadAnimation(this, R.anim.alpha);
            for (Button floor : floorButtons)
            {
                floor.startAnimation(anime);
            }
        }
        else
        {
            x = 1;
        }

        for (Button floor : floorButtons)
        {
            floor.setVisibility(View.VISIBLE);
            floor.setOnClickListener(inBeta());
        }


    }


    private int charToInt(char c) {
        return c - '0';
    }
}
