package com.deilsky.remember;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deilsky.awakening.widget.AwakeningView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private TextView textView;
    private CountDownTimer timer;
    private ImageView imageView;
    private int[] res = new int[]{R.mipmap.a104, R.mipmap.a109, R.mipmap.a110, R.mipmap.a111, R.mipmap.a118};
    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initComponent();
    }

    private void initComponent() {
        timer = new CountDownTimer(30000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                current++;
                if (current < res.length) {
                    imageView.setBackgroundResource(res[current]);
                } else {
                    startIntent();
                }
            }

            @Override
            public void onFinish() {
                startIntent();
            }
        };
        timer.start();
        findViewById(R.id.tvJump).setOnClickListener(this);
        imageView = findViewById(R.id.bg);
        AwakeningView.RectangleBuilder.create()
                .fillColor(R.color.transparent)
                .strokeSize(1)
                .strokeColor(R.color.red)
                .connerAll(10)
                .build().target(findViewById(R.id.tvJump)).build();
        textView = findViewById(R.id.tvWelcome);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long begin = sdf.parse("2013-03-10").getTime();
            long now = new Date().getTime();
            long result = now - begin;
            Log.d("times", "times:" + result);
            int day = (int) (result / 1000 / 3600 / 24);
            Log.d("times", "days:" + day);
            String str = String.format(getString(R.string.dear_fmt), day);
            SpannableString spannableString = new SpannableString(str);
            int start = str.indexOf("第") + 1;
            int len = str.length() - 1;
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new AbsoluteSizeSpan(80), start, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(spannableString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.tvJump:
                startIntent();
                break;
        }
    }

    private void startIntent() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
