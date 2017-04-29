package com.tenpa_mf.timer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

public class Timer extends Activity {
    EditText timeCount;
    Button button;
    CountDownTimer countDownTimer;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timer);

        timeCount=(EditText)findViewById(R.id.editText);
        timeCount.setHint("ミリ秒で記述");
        button = (Button)findViewById(R.id.button);
        image = (ImageView)findViewById(R.id.imageView3);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bmp1);
        image.setImageBitmap(bitmap);

        final int imageWidth = bitmap.getWidth();
        final int imageHeight = bitmap.getHeight();

        //Matrixンスタンスの生成
        final Matrix matrix = new Matrix();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                   long time = Long.parseLong(timeCount.getText().toString());
                    final long startTime = time;
                //画像の表示
                //image.setImageBitmap();
                //image.setImageResource(R.drawable.bmp1);

                countDownTimer = new CountDownTimer(time,1200) {

                    @Override
                    //インターバルで呼ばれる。
                    public void onTick(long millisUntilFinished) {
                        //残り時間を分、秒、ミリに分割
                        long mm = millisUntilFinished/1000/60;
                        long ss = millisUntilFinished/1000%60;
                        long ms = millisUntilFinished -ss *1000-mm*1000*60;
                        timeCount.setText(String.format("%1$02d:%2$02d.%3$03d", mm, ss, ms));

                        Random r = new Random();
                        int n = r.nextInt(10)+1;

                        if(n==1) {
                            float rate = (float) millisUntilFinished / startTime;
                            matrix.preScale(rate, rate);
                            Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, imageWidth, imageHeight, matrix, true);
                            image.setImageBitmap(bitmap1);
                        }

                    }

                    @Override
                    //完了時に呼ばれる。
                    public void onFinish() {
                        timeCount.setText("0:00.00");
                        image.setImageResource(R.drawable.bmp2);
                    }
                }.start();
            }
        });


    }

}
