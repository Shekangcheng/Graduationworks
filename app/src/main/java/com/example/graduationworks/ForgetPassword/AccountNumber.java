package com.example.graduationworks.ForgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.User;
import java.util.Arrays;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
public class AccountNumber extends AppCompatActivity{
    private static final String TAG = "AccountNumber";
    EditText A_N_user;
    Button A_N_confirm;
    String E_user,identification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_number);
        instantiation();
    }

    //实例化
    private void instantiation() {
        A_N_user = findViewById(R.id.A_N_user);
        A_N_confirm = findViewById(R.id.A_N_confirm);
        identification= AppContext.identification;
    }

    public void A_P(View view) {
        E_user = A_N_user.getText().toString();
        if (!E_user.equals("")) {
            //用户修改
            if(identification.equals("user")){
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                String user[] = {E_user};
                bmobQuery.addWhereContainsAll("account", Arrays.asList(user));
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null && list.size()>0) {
                            Intent intent=new Intent(AccountNumber.this,PhoneNumber.class);
                            AppContext.account=E_user;
                            startActivity(intent);
                            AccountNumber.this.finish();
                            Log.d(TAG, "查询成功" + list.size());
                        } else {
                            int errorCode = e.getErrorCode();
                            Log.d(TAG, "错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                            if(errorCode == 9015){
                                A_N_user.setText("");
                                A_N_user.setHint("账号不存在");
                            }
                            else{
                                Toast.makeText(AccountNumber.this, "请检查网络设置", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }

            //教师修改
            else {
                BmobQuery<Teacher> bmobQuery = new BmobQuery<>();
                String Teacher[] = {E_user};
                bmobQuery.addWhereContainsAll("account", Arrays.asList(Teacher));
                bmobQuery.findObjects(new FindListener<Teacher>() {
                    @Override
                    public void done(List<Teacher> list, BmobException e) {
                        if (e == null && list.size()>0) {
                            Intent intent=new Intent(AccountNumber.this,PhoneNumber.class);
                            AppContext.account=E_user;
                            startActivity(intent);
                            AccountNumber.this.finish();
                            Log.d(TAG, "查询成功" + list.size());
                        } else {
                            int errorCode = e.getErrorCode();
                            Log.d(TAG, "错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                            if(errorCode == 9015){
                                A_N_user.setText("");
                                A_N_user.setHint("账号不存在");
                            }
                            else{
                                Toast.makeText(AccountNumber.this, "请检查网络设置", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }
        else {
            A_N_user.setHint("账号不能为空");
        }
    }

    public void A_F(View view) {
        this.finish();
    }
}