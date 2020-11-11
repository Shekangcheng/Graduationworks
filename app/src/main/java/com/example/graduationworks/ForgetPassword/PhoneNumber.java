package com.example.graduationworks.ForgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class PhoneNumber extends AppCompatActivity {
    private static final String TAG = "PhoneNumber";
    EditText FP_phone;
    TextView FP_hint;
    String E_phone,user,hint,hints,identification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("012345".substring(2,4));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        instantiation();
        queryBmob();
    }

    private void queryBmob() {
        if(identification.equals("user")){
            BmobQuery<User> cursor = new BmobQuery<>();
            String account[] = {user};
            cursor.addWhereContainsAll("account", Arrays.asList(account));
            //
            synchronized (this){
                cursor.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(e==null){
                            Log.d(TAG,"查询成功：共" + list.size() + "条数据。");
                            for (User bmobUser : list) {
                                String phone = bmobUser.getPhone();
                                hint=phone.substring(0,3);
                                hint+="*****";
                                hint+=phone.substring(9,11);
                                FP_hint.setText(hint);
                                hints=phone;
                            }
                        }else{
                            int errorCode = e.getErrorCode();
                            Log.d(TAG,"失败："+e.getMessage() + "\t错误码 ==> " + errorCode);
                        }
                    }
                });
            }
        }


        else {
            BmobQuery<Teacher> cursor = new BmobQuery<>();
            String TeacherName[] = {user};
            cursor.addWhereContainsAll("account", Arrays.asList(TeacherName));
            //
            synchronized (this){
                cursor.findObjects(new FindListener<Teacher>() {
                    @Override
                    public void done(List<Teacher> list, BmobException e) {
                        if(e==null){
                            Log.d(TAG,"查询成功：共" + list.size() + "条数据。");
                            for (Teacher bmobThread : list) {
                                String phone = bmobThread.getPhone();
                                hint=phone.substring(0,3);
                                hint+="*****";
                                hint+=phone.substring(9,11);
                                FP_hint.setText(hint);
                                hints=phone;
                            }
                        }else{
                            int errorCode = e.getErrorCode();
                            Log.d(TAG,"失败："+e.getMessage() + "\t错误码 ==> " + errorCode);
                        }
                    }
                });
            }
        }
    }

    private void instantiation() {
        FP_phone=findViewById(R.id.FP_phone);
        FP_hint=findViewById(R.id.FP_hint);
        user=AppContext.account;
        identification= AppContext.identification;
    }

    public void change(View view) {
        E_phone=FP_phone.getText().toString();
        if(E_phone.equals("")){
            FP_phone.setHint("请输入手机号");
        }
        else if (E_phone.equals(hints)){
            Intent intent=new Intent(PhoneNumber.this,ChangeThePassword.class);
            startActivity(intent);
            this.finish();
        }
        else {
            FP_phone.setText("");
            FP_phone.setHint("号码输入错误");
        }
    }
}