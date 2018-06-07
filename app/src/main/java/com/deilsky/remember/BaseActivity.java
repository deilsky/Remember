package com.deilsky.remember;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {
    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(this, getString(R.string.two_pressed), Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            } else {
                finish();
            }
        }
        return false;
    }
}
