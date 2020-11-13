package com.example.graduationworks.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.interaction;
import com.example.graduationworks.Teacher.T_UserinfoActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class U_C_teacherActivity extends AppCompatActivity {
    String U_t_account,ObjectId,state="";
    TextView U_C_name, U_C_signature, U_C_commendnum, U_C_teachingnum, U_C_browsenum,
            U_C_gender, U_C_age, U_C_phone, U_C_site, U_C_e_mail, U_C_course, U_C_education;
    Button notarize;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_c_teacher);
        initialize();
        infor();
        inquire();
        ButtonText();
        Teaching();
    }

    private void infor() {
        BmobQuery<Teacher> query = new BmobQuery<>();
        query.addWhereEqualTo("account", U_t_account);
        synchronized (this) {
            query.findObjects(new FindListener<Teacher>() {
                @Override
                public void done(List<Teacher> list, BmobException e) {
                    if (e == null) {
                        for (Teacher bmobTeacher : list) {
                            U_C_name.setText(bmobTeacher.getName() + "");
                            U_C_signature.setText(bmobTeacher.getSignature() + "");
                            U_C_commendnum.setText(bmobTeacher.getCommendnum() + "");
                            U_C_teachingnum.setText(bmobTeacher.getTeachingnum() + "");
                            U_C_browsenum.setText(bmobTeacher.getBrowsenum() + "");
                            U_C_gender.setText(bmobTeacher.getGender() + "");
                            U_C_age.setText(bmobTeacher.getAge() + "");
                            U_C_phone.setText(bmobTeacher.getPhone() + "");
                            U_C_site.setText(bmobTeacher.getSite() + "");
                            U_C_e_mail.setText(bmobTeacher.getE_mail() + "");
                            U_C_course.setText(bmobTeacher.getCourse() + "");
                            U_C_education.setText(bmobTeacher.getEducation() + "");
                        }
                    } else {
                        int errorCode = e.getErrorCode();
                        Log.d(TAG, "失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
                    }
                }
            });
        }
    }

    private void initialize() {
        U_C_name = findViewById(R.id.U_C_name);
        U_C_signature = findViewById(R.id.U_C_signature);
        U_C_commendnum = findViewById(R.id.U_C_commendnum);
        U_C_teachingnum = findViewById(R.id.U_C_teachingnum);
        U_C_browsenum = findViewById(R.id.U_C_browsenum);
        U_C_gender = findViewById(R.id.U_C_gender);
        U_C_age = findViewById(R.id.U_C_age);
        U_C_phone = findViewById(R.id.U_C_phone);
        U_C_site = findViewById(R.id.U_C_site);
        U_C_e_mail = findViewById(R.id.U_C_e_mail);
        U_C_course = findViewById(R.id.U_C_course);
        U_C_education = findViewById(R.id.U_C_education);
        notarize=findViewById(R.id.notarize);
        Intent intent = getIntent();
        U_t_account= intent.getStringExtra("T_account");
        ObjectId=intent.getStringExtra("ObjectId");
    }

    private void Teaching(){
        notarize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inquire().equals("")){
                    Toast.makeText(U_C_teacherActivity.this, "等待老师授课", Toast.LENGTH_SHORT).show();
                }
                else if(inquire().equals("确认授课")){
                    modification("正在授课");
                    notarize.setText("正在授课");
                }
                else if(inquire().equals("正在授课")){
                    notarize.setText("正  在  授  课");
                    Toast.makeText(U_C_teacherActivity.this, "授课中", Toast.LENGTH_SHORT).show();
                }
                else if(inquire().equals("结束授课")){
                    notarize.setText("确  认  结  束");
                    interaction i = new interaction();
                    i.setObjectId(ObjectId);
                    i.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(U_C_teacherActivity.this, "授课结束", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(U_C_teacherActivity.this, "结束失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
                                        Toast.makeText(U_C_teacherActivity.this, "等待学生确认", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob", "成功");
                                    } else {
                                        Log.i("bmob", "失败：" + e.getMessage());
                                        Toast.makeText(U_C_teacherActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
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
            notarize.setText("等  待  老  师");
        }
        else if(inquire().equals("确认授课")){
            notarize.setText("确  认  授  课");
        }
        else if(inquire().equals("正在授课")){
            notarize.setText("正  在  授  课");
        }
        else if(inquire().equals("结束授课")){
            notarize.setText("确  认  结  束");
        }
    }
}