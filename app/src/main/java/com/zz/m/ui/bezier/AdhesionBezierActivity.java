package com.zz.m.ui.bezier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zz.m.R;

import butterknife.ButterKnife;

public class AdhesionBezierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhesion_bezier);
        ButterKnife.bind(this);
    }
}
