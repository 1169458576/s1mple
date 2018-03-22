package com.zz.m.ui.bezier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zz.m.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BezierStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_sample);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_bezier_easy,R.id.btn_bezier_balls})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bezier_easy:
                startActivity(new Intent(BezierStartActivity.this,
                        SimpleBezierActivity.class));
                break;
            case R.id.btn_bezier_balls:
                startActivity(new Intent(BezierStartActivity.this,
                        BezierBallsActivity.class));
                break;
        }
    }
}
