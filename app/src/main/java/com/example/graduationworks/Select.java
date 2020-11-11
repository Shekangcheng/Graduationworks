package com.example.graduationworks;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduationworks.toolkit.AppContext;

public class Select extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

    }

    public void select_t(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        AppContext.identification = "teacher";
        startActivity(intent);
    }

    public void select_u(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        AppContext.identification = "user";
        startActivity(intent);
    }
}