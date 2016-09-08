package com.szxb.tangren.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.szxb.tangren.myapplication.R;

public class WelcomActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.welcome);
        JumpMain();
    }

    private void JumpMain() {
        Animation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF,
                0.3f);
        animation.setFillAfter(true);
        animation.setDuration(2000);
        imageView.startAnimation(animation);

        textView.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomActivity.this,Main.class));
                finish();
            }
        }, 2000);
    }
}
