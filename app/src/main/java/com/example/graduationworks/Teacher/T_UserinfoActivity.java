package com.example.graduationworks.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.graduationworks.R;
import com.example.graduationworks.SQL.User;
import com.example.graduationworks.toolkit.AppContext;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class T_UserinfoActivity extends AppCompatActivity {
    private static final String TAG = "T_UserinfoActivity";
    String U_account;
    TextView TUI_name, TUI_gold, TUI_gender, TUI_age, TUI_phone, TUI_birthday, TUI_grade, TUI_e_mail, TUI_site;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_userinfo);
        initialize();
        info();
    }
    private void initialize() {
        TUI_name =findViewById(R.id.TUI_name);
        TUI_gender =findViewById(R.id.TUI_gender);
        TUI_age =findViewById(R.id.TUI_age);
        TUI_phone =findViewById(R.id.TUI_phone);
        TUI_birthday =findViewById(R.id.TUI_birthday);
        TUI_grade =findViewById(R.id.TUI_grade);
        TUI_e_mail =findViewById(R.id.TUI_e_mail);
        TUI_site =findViewById(R.id.TUI_site);
        U_account=getIntent().getStringExtra("U_account");
    }
    private void info() {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("account",U_account);

        synchronized (this) {
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    Log.d(TAG, "账号为" + U_account);
                    if (e == null) {
                        for (User bmobUser : list) {
                            TUI_name.setText(bmobUser.getName());
                            TUI_gender.setText(bmobUser.getGender());
                            TUI_age.setText(bmobUser.getAge() + "");
                            TUI_phone.setText(bmobUser.getPhone() + "");
                            TUI_birthday.setText(bmobUser.getBirthday() + "");
                            TUI_grade.setText(bmobUser.getGrade() + "");
                            TUI_e_mail.setText(bmobUser.getE_mail() + "");
                            TUI_site.setText(bmobUser.getSite() + "");
                        }
                    } else {
                        int errorCode = e.getErrorCode();
                        Log.d(TAG, "失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
                    }
                }
            });
        }
    }
}