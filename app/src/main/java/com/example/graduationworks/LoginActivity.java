package com.example.graduationworks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.graduationworks.ForgetPassword.AccountNumber;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.User;
import com.example.graduationworks.Teacher.TeacherActivity;
import com.example.graduationworks.User.UserActivity;
import com.example.graduationworks.toolkit.AppContext;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText L_user, L_password;
    String E_user, E_password,identification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instantiation();
    }

    private void instantiation() {
        L_user = findViewById(R.id.L_user);
        L_password = findViewById(R.id.L_password);
        identification= AppContext.identification;
    }

    public void Login(View view) {
        E_user = L_user.getText().toString();
        E_password = L_password.getText().toString();
        //用户登录
        if(identification.equals("user")){
            if (!E_user.equals("")) {
                BmobQuery<User> cursor = new BmobQuery<>();
                cursor.addWhereEqualTo("account", E_user);
                cursor.addWhereEqualTo("password", E_password);
                synchronized (this) {
                    cursor.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null && list.size() > 0) {
                                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                AppContext.account = E_user;
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else {
                                int errorCode = e.getErrorCode();
                                Log.d(TAG, "错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                                if(e.getMessage().equals("createSubscription failed:The network is not available,please check your network!(9016)")){
                                    Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                }
                                else {Toast.makeText(LoginActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();}
                            }
                        }
                    });
                }
            }else {
                Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            }
        }



        //教师登录
        else{
            if (!E_user.equals("")) {
                BmobQuery<Teacher> cursor = new BmobQuery<>();
                cursor.addWhereEqualTo("account", E_user);
                cursor.addWhereEqualTo("password", E_password);
                cursor.findObjects(new FindListener<Teacher>() {
                    @Override
                    public void done(List<Teacher> list, BmobException e) {
                        if (e == null && list.size() > 0) {
                            Intent intent = new Intent(LoginActivity.this, TeacherActivity.class);
                            AppContext.account=E_user;
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            int errorCode = e.getErrorCode();
                            Log.d(TAG, "错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                            if(e.getMessage().equals("createSubscription failed:The network is not available,please check your network!(9016)")){
                                Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            }
                            else {Toast.makeText(LoginActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();}
                        }
                    }
                });
            } else {
                Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //注册
    public void Register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //忘记密码
    public void L_FP(View view) {
        Intent intent = new Intent(this, AccountNumber.class);
        startActivity(intent);
    }
}