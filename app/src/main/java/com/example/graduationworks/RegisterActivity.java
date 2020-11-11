package com.example.graduationworks;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.User;
import com.example.graduationworks.toolkit.AppContext;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText name_E,user_E,password_E,qr_password_E,phone_E,site_E;
    String name_T,user_T,password_T,phone_T,site_T,identification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        instantiation();
    }

    public void register(View view) {
        name_T=name_E.getText().toString();
        user_T=user_E.getText().toString();
        password_T=password_E.getText().toString();
        phone_T=phone_E.getText().toString();
        site_T=site_E.getText().toString();
        String qr_password_T=qr_password_E.getText().toString();


        //判断是否输入数据
        if(user_T.equals(""))user_E.setHint("用户账号不能为空");
        else if(password_T.equals(""))password_E.setHint("密码不能为空");
        else if(!qr_password_T.equals(password_T)){qr_password_E.setHint("两次密码输入不一致");qr_password_E.setText("");}
        else if(phone_T.equals(""))phone_E.setHint("手机号不能为空");
        else if(phone_T.length()!=11){phone_E.setText("");phone_E.setHint("手机号格式错误");}
        //数据写入数据库

        else {
            //用户注册
            if(identification.equals("user")){
                //
                User user = new User();
                user.setName(name_T);//名字
                user.setaccount(user_T);//账号
                user.setPassword(password_T);//密码
                user.setPhone(phone_T);//电话
                user.setSite(site_T);//地址
                user.setBirthday("");
                user.setGender("");//性别
                user.setAge(0);//年龄
                user.setGold(0);//金币
                user.setTeacher("");//老师
                user.setE_mail("");//邮箱
                user.setGrade("");//年级
                user.setDate("");//日期
                //加锁
                synchronized (this){
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.d(TAG,"添加成功");
                                //对话框组件
                                AlertDialog.Builder dialog_R=new AlertDialog.Builder(RegisterActivity.this);
                                dialog_R.setTitle("提示");
                                dialog_R.setMessage("注册成功");
                                dialog_R.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        RegisterActivity.this.finish();
                                    }
                                });
                                dialog_R.show();
                            }else{
                                int errorCode = e.getErrorCode();
                                Log.d(TAG,"错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                                if(errorCode == 401){
                                    user_E.setText("");
                                    phone_E.setText("");
                                    phone_E.setHint("账号或手机号已存在");
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }


            //教师注册
            else {
                //
                Teacher teacher = new Teacher();
                teacher.setName(name_T);
                teacher.setAccount(user_T);
                teacher.setPassword(password_T);
                teacher.setPhone(phone_T);
                teacher.setSite(site_T);
                teacher.setGender("");//性别
                teacher.setAge(0);//年龄
                teacher.setGold(0);//金币
                teacher.setStudent("");//学生
                teacher.setE_mail("");//邮箱
                teacher.setEducation("");//学历
                teacher.setCourse("");//课程
                teacher.setSignature("");//签名
                teacher.setCommendnum(0);//点赞数
                teacher.setTeachingnum(0);//授课数
                teacher.setBrowsenum(0);//浏览次数
                teacher.setPrice(0);//价格
                teacher.setBirthday("");
                //加锁
                synchronized (this){
                    teacher.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.d(TAG,"添加成功");
                                //对话框组件
                                AlertDialog.Builder dialog_R=new AlertDialog.Builder(RegisterActivity.this);
                                dialog_R.setTitle("提示");
                                dialog_R.setMessage("注册成功");
                                dialog_R.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        RegisterActivity.this.finish();
                                    }
                                });
                                dialog_R.show();
                            }else{
                                int errorCode = e.getErrorCode();
                                Log.d(TAG,"错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                                if(errorCode == 401){
                                    user_E.setText("");
                                    phone_E.setText("");
                                    phone_E.setHint("账号或手机号已存在");
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private void instantiation() {
        name_E=findViewById(R.id.R_name);
        user_E=findViewById(R.id.R_user);
        password_E=findViewById(R.id.R_password);
        qr_password_E=findViewById(R.id.R_qr_password);
        phone_E=findViewById(R.id.R_phone);
        site_E=findViewById(R.id.R_site);
        identification= AppContext.identification;
    }
}