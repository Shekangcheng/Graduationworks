package com.example.graduationworks.Teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.graduationworks.toolkit.MyPagerAdapter;
import com.example.graduationworks.R;
import com.example.graduationworks.User.UserCourse;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {
    TeacherHomepage Th= new TeacherHomepage();
    TeacherInfo Ti=new TeacherInfo();
    ArrayList<Fragment> fragments=new ArrayList<>();
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        //去掉标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        fragments.add(Th);
        fragments.add(Ti);
        viewPager=findViewById(R.id.viewpager);//获取ViewPager
        //设置Adapter
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fragments));
    }
}