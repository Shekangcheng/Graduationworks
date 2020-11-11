package com.example.graduationworks.User;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.graduationworks.toolkit.MyPagerAdapter;
import com.example.graduationworks.R;
import java.util.ArrayList;
public class UserActivity extends AppCompatActivity {
    UserHomepage Uh= new UserHomepage();
    UserCourse Uc=new UserCourse();
    UserInfo Ui=new UserInfo();
    ArrayList<Fragment> fragments=new ArrayList<>();
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        fragments.add(Uh);
        fragments.add(Uc);
        fragments.add(Ui);
         viewPager=findViewById(R.id.viewpager);//获取ViewPager
        //设置Adapter
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fragments));
    }

    public void user_homepage(View view) {
        viewPager.setCurrentItem(0);
    }

    public void user_course(View view) {
        viewPager.setCurrentItem(1);
    }

    public void user_info(View view) {
        viewPager.setCurrentItem(2);
    }
}