package com.zz.m;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zz.m.ui.bezier.BezierStartActivity;
import com.zz.m.widget.RulerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.horizontalScale)
    RulerView ruler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ruler.setOnValueChangedListener(new RulerView.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                txt.setText(value+"");
            }
        });
    }

    @OnClick({R.id.btn_bezier})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bezier:
                startActivity(new Intent(MainActivity.this,
                        BezierStartActivity.class));
                break;
        }
    }

}
