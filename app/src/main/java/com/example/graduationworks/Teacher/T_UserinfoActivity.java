package com.example.graduationworks.Teacher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.User;
import com.example.graduationworks.SQL.interaction;
import com.example.graduationworks.toolkit.AppContext;
import java.util.Arrays;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class T_UserinfoActivity extends AppCompatActivity {
    private static final String TAG = "T_UserinfoActivity";
    String ObjectId,U_account,state="";
    Button Start_teaching;
    TextView TUI_name,TUI_gender, TUI_age, TUI_phone, TUI_birthday, TUI_grade, TUI_e_mail, TUI_site;
    private boolean run = false;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_userinfo);
        initialize();
        info();
        ButtonText();
        Teaching();
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
        Start_teaching=findViewById(R.id.Start_teaching);
        //一秒刷新一次
        run = true;
        handler.postDelayed(task, 1000);
        ObjectId=getIntent().getStringExtra("ObjectId");
        U_account=getIntent().getStringExtra("U_account");
    }
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 1000);
            }
        }
    };
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

    private void Teaching(){
        Start_teaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inquire().equals("")){
                    modification("确认授课");
                    Start_teaching.setText("等待对方确认");
                }
                else if(inquire().equals("确认授课")){
                    Start_teaching.setText("等待对方确认开始");
                    Toast.makeText(T_UserinfoActivity.this, "等待对方确认", Toast.LENGTH_SHORT).show();
                }
                else if(inquire().equals("正在授课")){
                    Start_teaching.setText("结  束  授  课");
                    modification("结束授课");
                }
                else if(inquire().equals("结束授课")){
                    Start_teaching.setText("等待对方确认结束");
                    Toast.makeText(T_UserinfoActivity.this, "等待对方确认", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void modification(String text){
        BmobQuery<interaction> cursor = new BmobQuery<>();
        cursor.addWhereEqualTo("objectId",ObjectId);
        synchronized (this) {
            cursor.findObjects(new FindListener<interaction>() {
                @Override
                public void done(List<interaction> list, BmobException e) {
                    if (e == null) {
                        Log.d(TAG, "查询成功：共" + list.size() + "条数据。");
                        for (interaction bmobinteraction : list) {
                            String ID = bmobinteraction.getObjectId();
                            bmobinteraction.setValue("Teaching",text);
                            bmobinteraction.update(ID, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(T_UserinfoActivity.this, "等待学生确认", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob", "成功");
                                    } else {
                                        Log.i("bmob", "失败：" + e.getMessage());
                                        Toast.makeText(T_UserinfoActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        int errorCode = e.getErrorCode();
                        Log.d(TAG, "失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
                    }
                }
            });
        }
    }

    private String inquire(){
        BmobQuery<interaction> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",ObjectId);
        synchronized (this) {
            query.findObjects(new FindListener<interaction>() {
                @Override
                public void done(List<interaction> list, BmobException e) {
                    if (e == null) {
                        for (interaction bmobinteraction : list) {
                            state=bmobinteraction.getTeaching();
                        }
                    } else {
                        int errorCode = e.getErrorCode();
                        Log.d(TAG, "失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
                    }
                }
            });
        }
        return state;
    }

    private void ButtonText(){
        if(inquire().equals("")){
            Start_teaching.setText("开  始  授  课");
        }
        else if(inquire().equals("确认授课")){
            Start_teaching.setText("等待对方确认");
        }
        else if(inquire().equals("正在授课")){
            Start_teaching.setText("结  束  授  课");
        }
        else if(inquire().equals("结束授课")){
            Start_teaching.setText("等待对方确认结束");
        }
    }
}