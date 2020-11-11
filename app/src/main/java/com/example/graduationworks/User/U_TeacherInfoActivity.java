package com.example.graduationworks.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class U_TeacherInfoActivity extends AppCompatActivity {
    private static final String TAG = "U_TeacherInforActivity";
    String U_t_account;
    TextView U_t_name, U_t_signature, U_t_commendnum, U_t_teachingnum, U_t_browsenum,
            U_t_gender, U_t_age, U_t_phone, U_t_site, U_t_e_mail, U_t_course, U_t_education;
    private int mCurrentBrows = 0;//默认浏览量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_teacher_infor);
        U_t_account = getIntent().getStringExtra("T_account");
        initialize();
        infor();
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
                            U_t_name.setText(bmobTeacher.getName() + "");
                            U_t_signature.setText(bmobTeacher.getSignature() + "");
                            U_t_commendnum.setText(bmobTeacher.getCommendnum() + "");
                            U_t_teachingnum.setText(bmobTeacher.getTeachingnum() + "");
                            int browsenum = bmobTeacher.getBrowsenum();//获取数据库浏览量
                            mCurrentBrows = browsenum;//赋给默认值
                            mCurrentBrows++;//默认值
                            U_t_browsenum.setText(mCurrentBrows + "");
                            U_t_gender.setText(bmobTeacher.getGender() + "");
                            U_t_age.setText(bmobTeacher.getAge() + "");
                            U_t_phone.setText(bmobTeacher.getPhone() + "");
                            U_t_site.setText(bmobTeacher.getSite() + "");
                            U_t_e_mail.setText(bmobTeacher.getE_mail() + "");
                            U_t_course.setText(bmobTeacher.getCourse() + "");
                            U_t_education.setText(bmobTeacher.getEducation() + "");
                            browse();
                        }
                    } else {
                        int errorCode = e.getErrorCode();
                        Log.d(TAG, "失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
                    }
                }
            });
        }
    }

    public void U_t_commend(View view) {
        int i = Integer.valueOf(U_t_commendnum.getText().toString()).intValue();
        String account[] = {U_t_account};
        i++;
        BmobQuery<Teacher> cursor = new BmobQuery<>();
        cursor.addWhereContainsAll("account", Arrays.asList(account));
        synchronized (this) {
            int I = i;
            cursor.findObjects(new FindListener<Teacher>() {
                @Override
                public void done(List<Teacher> list, BmobException e) {
                    if (e == null) {
                        for (Teacher bmobTeacher : list) {
                            String ID = bmobTeacher.getObjectId();
                            bmobTeacher.setValue("commendnum", I);
                            bmobTeacher.update(ID, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.i("", "成功");
                                    } else {
                                        Log.i("", "失败：" + e.getMessage());
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
        U_t_commendnum.setText(i + "");
    }


    private void initialize() {
        U_t_name = findViewById(R.id.U_t_name);
        U_t_signature = findViewById(R.id.U_t_signature);
        U_t_commendnum = findViewById(R.id.U_t_commendnum);
        U_t_teachingnum = findViewById(R.id.U_t_teachingnum);
        U_t_browsenum = findViewById(R.id.U_t_browsenum);
        U_t_gender = findViewById(R.id.U_t_gender);
        U_t_age = findViewById(R.id.U_t_age);
        U_t_phone = findViewById(R.id.U_t_phone);
        U_t_site = findViewById(R.id.U_t_site);
        U_t_e_mail = findViewById(R.id.U_t_e_mail);
        U_t_course = findViewById(R.id.U_t_course);
        U_t_education = findViewById(R.id.U_t_education);
    }


    private void browse() {
        String account[] = {U_t_account};
        BmobQuery<Teacher> cursor = new BmobQuery<>();
        cursor.addWhereContainsAll("account", Arrays.asList(account));
        synchronized (this) {
            cursor.findObjects(new FindListener<Teacher>() {
                @Override
                public void done(List<Teacher> list, BmobException e) {
                    if (e == null) {
                        Log.d(TAG, "账号查询成功");
                        for (Teacher teacher : list) {
                            //获取ID
                            String objectId = teacher.getObjectId();
                            Log.d(TAG, "获取到的账号信息 ==> " + objectId);
                            teacher.setBrowsenum(mCurrentBrows);
                            teacher.update(objectId, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.d(TAG, "修改成功");
                                    } else {
                                        int errorCode = e.getErrorCode();
                                        Log.d(TAG, "修改失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
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

    public void reservation(View view) {
        Intent intent =new Intent(U_TeacherInfoActivity.this,ReservationActivity.class);
        intent.putExtra("T_account",U_t_account);
        startActivity(intent);
    }
}