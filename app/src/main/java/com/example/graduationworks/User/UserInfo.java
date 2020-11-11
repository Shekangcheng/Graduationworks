package com.example.graduationworks.User;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.toolkit.Popup;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.User;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class UserInfo extends Fragment implements View.OnClickListener {
    private static final String TAG = "UserInfo";
    Button top_up, U_info_change, pop_modification_confirm;
    int Year, Month, Day, control;
    String time, birthday;
    TextView U_F_i_name, U_F_i_gold, U_F_i_gender, U_F_i_age, U_F_i_phone, U_F_i_birthday, U_F_i_grade, U_F_i_e_mail, U_F_i_site, modification_hint;
    EditText modification_content;
    ConstraintLayout outer;
    Popup pop;
    PopupWindow win;
    Calendar calendar= Calendar.getInstance(Locale.CHINA);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_infor, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        info();
    }

    private void initialize(View view) {
        top_up = view.findViewById(R.id.top);
        U_F_i_name = view.findViewById(R.id.U_F_i_name);
        U_F_i_gold = view.findViewById(R.id.U_F_i_gold);
        U_F_i_gender = view.findViewById(R.id.U_F_i_gender);
        U_F_i_age = view.findViewById(R.id.U_F_i_age);
        U_F_i_phone = view.findViewById(R.id.U_F_i_phone);
        U_F_i_birthday = view.findViewById(R.id.U_F_i_birthday);
        U_F_i_grade = view.findViewById(R.id.U_F_i_grade);
        U_F_i_e_mail = view.findViewById(R.id.U_F_i_e_mail);
        U_F_i_site = view.findViewById(R.id.U_F_i_site);
        U_info_change = view.findViewById(R.id.U_info_change);
        view.findViewById(R.id.U_F_i_name).setOnClickListener(this);
        view.findViewById(R.id.linearLayout1).setOnClickListener(this);
        view.findViewById(R.id.linearLayout3).setOnClickListener(this);
        view.findViewById(R.id.linearLayout4).setOnClickListener(this);
        view.findViewById(R.id.linearLayout5).setOnClickListener(this);
        view.findViewById(R.id.linearLayout6).setOnClickListener(this);
        view.findViewById(R.id.linearLayout7).setOnClickListener(this);
        view.findViewById(R.id.U_info_change).setOnClickListener(this);
        currentdate();
    }

    private void info() {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("account", AppContext.account);
        synchronized (this) {
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    Log.d(TAG, "账号为" + AppContext.account);
                    if (e == null) {
                        for (User bmobUser : list) {
                            U_F_i_name.setText(bmobUser.getName() + "");
                            U_F_i_gold.setText(bmobUser.getGold() + "");
                            U_F_i_gender.setText(bmobUser.getGender());
                            U_F_i_age.setText(bmobUser.getAge() + "");
                            U_F_i_phone.setText(bmobUser.getPhone() + "");
                            U_F_i_birthday.setText(bmobUser.getBirthday() + "");
                            U_F_i_grade.setText(bmobUser.getGrade() + "");
                            U_F_i_e_mail.setText(bmobUser.getE_mail() + "");
                            U_F_i_site.setText(bmobUser.getSite() + "");
                        }
                    } else {
                        int errorCode = e.getErrorCode();
                        Log.d(TAG, "失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
                    }
                }
            });
        }
    }

    private void currentdate() {
        Calendar c = Calendar.getInstance();//获得当前日期
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);
        time = Year + "-" + Month + "-" + Day;
    }


    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.U_F_i_name:
                modification_frame(view, "请输入你的名字", InputType.TYPE_CLASS_TEXT);
                control = 1;
                break;
            case R.id.linearLayout1:
                pop = Popup.getPop(getContext(), R.layout.gender_popup);
                win = pop.getWin();
                win.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);
                View gender_popView = pop.getView();
                Button boy = gender_popView.findViewById(R.id.boy);
                boy.setOnClickListener(new popItemClick(win));
                Button girl = gender_popView.findViewById(R.id.girl);
                girl.setOnClickListener(new popItemClick(win));
                Button confidentiality = gender_popView.findViewById(R.id.confidentiality);
                confidentiality.setOnClickListener(new popItemClick(win));
                break;
            case R.id.linearLayout3:
                modification_frame(view, "请输入你的电话号码", InputType.TYPE_CLASS_NUMBER);
                control = 2;
                break;
            case R.id.linearLayout4:

                new DatePickerDialog(getContext(), 2, new DatePickerDialog.OnDateSetListener() {
                    // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthday = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        int age = 0;
                        U_F_i_birthday.setText(birthday);
                        if (Year - year > 0) {
                            age = Year - year;
                            if (monthOfYear > Month) {
                                age--;
                            } else if (monthOfYear == Month) {
                                if (dayOfMonth > Day) {
                                    age--;
                                }
                            }
                        }
                        U_F_i_age.setText(age + "");
                    }
                }
                        // 设置初始日期
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.linearLayout5:
                modification_frame(view, "请输入你当前学习阶段", InputType.TYPE_CLASS_TEXT);
                control = 3;
                break;
            case R.id.linearLayout6:
                modification_frame(view, "请输入你的邮箱",InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                control = 4;
                break;
            case R.id.linearLayout7:
                modification_frame(view, "请输入你的所在地址", InputType.TYPE_CLASS_TEXT);
                control = 5;
                break;
            //保存信息
            case R.id.U_info_change:
                BmobQuery<User> cursor = new BmobQuery<>();
                String account[] = {AppContext.account};
                cursor.addWhereContainsAll("account", Arrays.asList(account));
                //
                synchronized (this) {
                    cursor.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {
                                Log.d(TAG, "查询成功：共" + list.size() + "条数据。");
                                for (User bmobUser : list) {
                                    String ID = bmobUser.getObjectId();
                                    bmobUser.setValue("name", U_F_i_name.getText().toString());
                                    bmobUser.setValue("gender", U_F_i_gender.getText().toString());
                                    bmobUser.setValue("age", Integer.valueOf(U_F_i_age.getText().toString()).intValue());
                                    bmobUser.setValue("phone", U_F_i_phone.getText().toString());
                                    bmobUser.setValue("birthday", U_F_i_birthday.getText().toString());
                                    bmobUser.setValue("grade", U_F_i_grade.getText().toString());
                                    bmobUser.setValue("e_mail", U_F_i_e_mail.getText().toString());
                                    bmobUser.setValue("site", U_F_i_site.getText().toString());
                                    bmobUser.update(ID, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(getContext(), "以保存当前信息", Toast.LENGTH_SHORT).show();
                                                Log.i("bmob", "成功");
                                            } else {
                                                Log.i("bmob", "失败：" + e.getMessage());
                                                Toast.makeText(getContext(), "保存失败，请检查填写格式或网络", Toast.LENGTH_SHORT).show();
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
                break;
        }
    }

    private void modification_frame(View view, String hint, int Type) {
        pop = Popup.getPop(getContext(), R.layout.import_popup);
        win = pop.getWin();
        View phone_popView = pop.getView();
        modification_hint = phone_popView.findViewById(R.id.modification_hint);
        modification_hint.setText(hint);
        modification_content = phone_popView.findViewById(R.id.modification_content);
        modification_content.setInputType(Type);
        pop_modification_confirm = phone_popView.findViewById(R.id.pop_modification_confirm);
        pop_modification_confirm.setOnClickListener(new popItemClick(win));
        modification_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "onTextChanged: "+s.toString() );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean is = s.toString().contains(".");
                Log.e(TAG, "onTextChanged: "+is);
                if (is) {
                    int i = s.toString().indexOf(".");
                    modification_content.setText(s.toString().substring(0,i));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        outer = phone_popView.findViewById(R.id.outer);
        outer.setOnClickListener(new popItemClick(win));
        win.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);
    }


    class popItemClick implements View.OnClickListener {
        PopupWindow win;

        popItemClick(PopupWindow win) {
            this.win = win;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.boy:
                    U_F_i_gender.setText("男");
                    win.dismiss();
                    break;
                case R.id.girl:
                    U_F_i_gender.setText("女");
                    win.dismiss();
                    break;
                case R.id.confidentiality:
                    U_F_i_gender.setText("保密");
                    win.dismiss();
                    break;
                case R.id.pop_modification_confirm:
                    switch (control) {
                        case 1:
                            U_F_i_name.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 2:
                            if (modification_content.getText().toString().length() != 11) {
                                Toast.makeText(getContext(), "手机格式错误", Toast.LENGTH_SHORT).show();
                            } else {
                                U_F_i_phone.setText(modification_content.getText().toString());
                                win.dismiss();
                            }
                            break;
                        case 3:
                            U_F_i_grade.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 4:
                            U_F_i_e_mail.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 5:
                            U_F_i_site.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                    }
                    break;
                case R.id.outer:
                    win.dismiss();
                    break;
            }
        }
    }
}
