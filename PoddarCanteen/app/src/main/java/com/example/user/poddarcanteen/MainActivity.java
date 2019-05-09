package com.example.user.poddarcanteen;

import android.content.Intent;
import android.Manifest;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;



public class MainActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            Intent i=new Intent(MainActivity.this,LogIn.class);
            finish();
            startActivity(i);
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()

                .putBoolean("isFirstRun", false).commit();



        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorPrimaryDark)
                        .image(R.drawable.canteen)
                        .title("Welcome to Podder Canteen")
                        .description("Order food online")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(MainActivity.this,LogIn.class);
                        startActivity(i);
                    }
                }, "Get Start"));



        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorPrimaryDark)
                        .image(R.drawable.foodmenu)
                        .title("Find food you love from menu")
                        .description("Variety of food items")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(MainActivity.this,LogIn.class);
                        startActivity(i);
                    }
                }, "Get Start"));

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorPrimaryDark)
                        .image(R.drawable.queue)
                        .title("Avoid long queues at the food counter")
                        .description("Get food status updates")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(MainActivity.this,LogIn.class);
                        startActivity(i);
                    }
                }, "Get Start"));


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorPrimaryDark)
                        .image(R.drawable.payment)
                        .title("Easy, cashless digital ordering")
                        .description("Pay online or cash on delivery")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(MainActivity.this,LogIn.class);
                        startActivity(i);
                    }
                }, "Get Start"));







    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent i=new Intent(MainActivity.this,LogIn.class);
        startActivity(i);
    }
}
        /*

    Button btn,btn1;
        AppCompatActivity {
                ImageView img;
    int[] imageId={R.drawable.c,R.drawable.f,R.drawable.p,R.drawable.r,R.drawable.v,R.drawable.a};

    int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btn=findViewById(R.id.click_me);
//        img=findViewById(R.id.imageView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(imageId[currentIndex]);
                currentIndex++;
                currentIndex=currentIndex%imageId.length;
            }
        });
        btn1=findViewById(R.id.next);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,LogIn.class);
                startActivity(i);
                finish();
            }
        });
    }
}

*/
