package com.indiahacks.lisd;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CarbonActivity extends AppCompatActivity {

    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon);

        display=(TextView)findViewById(R.id.textView2);

        int array_count[]={200,400,628,400,654,897,231,876};
        int array_max[]={1000,2000,2500,1000,2000,2500,1000,2000,2500};


        Intent i=getIntent();
        int vehicle_id=i.getIntExtra("vehicle_id",0);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(array_max[vehicle_id]);
        Log.d("carbon", "onCreate: "+String.valueOf(array_max[vehicle_id]));
        ObjectAnimator animation = ObjectAnimator.ofInt (progressBar, "progress", 0,array_count[vehicle_id]); // see this max value coming back here, we animale towards that value
        Log.d("carbon", "onCreate: "+String.valueOf(array_count[vehicle_id])+"\n Carbon Credits Used");
        display.setText(String.valueOf(array_count[vehicle_id])+"/"+String.valueOf(array_max[vehicle_id]));
        animation.setDuration (5000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
