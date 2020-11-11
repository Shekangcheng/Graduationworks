package com.example.graduationworks.Teacher;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import com.example.graduationworks.User.UserInfo;
import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.toolkit.Popup;

import java.sql.RowId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherInfo extends Fragment implements View.OnClickListener {
    private static final String TAG = "TeacherHomepage";
    Button T_info_change, pop_modification_confirm;
    int Year, Month, Day, control;
    String time,birthday;
    TextView TF_i_gold, TI_commendnum, TI_teachingnum, TI_browsenum,//金币,点赞数,教学数,浏览量
            TI_i_price, TI_name, TI_signature, TI_gender, TI_age,TI_birthday,TI_phone, TI_site, TI_e_mail, TI_course, TI_education, modification_hint,T_suffix;
                //价格      名字          签名      性别     年龄        生日      电话      地址     邮箱    教学内容      学历
    EditText modification_content;
    ConstraintLayout outer;
    Popup pop;
    PopupWindow win;
    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_fragment_infor, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        info();
    }

    private void initialize(View view) {
        TI_i_price = view.findViewById(R.id.TI_i_price);
        TI_name = view.findViewById(R.id.TI_name);
        TI_signature = view.findViewById(R.id.TI_signature);
        TF_i_gold = view.findViewById(R.id.TF_i_gold);
        TI_commendnum = view.findViewById(R.id.TI_commendnum);
        TI_teachingnum = view.findViewById(R.id.TI_teachingnum);
        TI_browsenum = view.findViewById(R.id.TI_browsenum);
        TI_gender = view.findViewById(R.id.TI_gender);
        TI_age = view.findViewById(R.id.TI_age);
        TI_birthday=view.findViewById(R.id.TI_birthday);
        TI_phone = view.findViewById(R.id.TI_phone);
        TI_site = view.findViewById(R.id.TI_site);
        TI_e_mail = view.findViewById(R.id.TI_e_mail);
        TI_course = view.findViewById(R.id.TI_course);
        TI_education = view.findViewById(R.id.TI_education);
        T_info_change = view.findViewById(R.id.T_info_change);
        T_suffix=view.findViewById(R.id.T_suffix);
        view.findViewById(R.id.TI_i_price).setOnClickListener(this);
        view.findViewById(R.id.TI_name).setOnClickListener(this);
        view.findViewById(R.id.TI_signature).setOnClickListener(this);
        view.findViewById(R.id.linearLayout9).setOnClickListener(this);
        view.findViewById(R.id.linearLayout10).setOnClickListener(this);
        view.findViewById(R.id.linearLayout11).setOnClickListener(this);
        view.findViewById(R.id.linearLayout12).setOnClickListener(this);
        view.findViewById(R.id.linearLayout13).setOnClickListener(this);
        view.findViewById(R.id.linearLayout14).setOnClickListener(this);
        view.findViewById(R.id.TI_e_mail).setOnClickListener(this);
        view.findViewById(R.id.linearLayout15).setOnClickListener(this);
        view.findViewById(R.id.linearLayout16).setOnClickListener(this);
        view.findViewById(R.id.T_info_change).setOnClickListener(this);
        currentdate();
    }

    private void info() {
        BmobQuery<Teacher> query = new BmobQuery<>();
        query.addWhereEqualTo("account", AppContext.account);
        synchronized (this) {
            query.findObjects(new FindListener<Teacher>() {
                @Override
                public void done(List<Teacher> list, BmobException e) {
                    Log.d(TAG, "账号为" + AppContext.account);
                    if (e == null) {
                        for (Teacher bmobTeacher : list) {
                            TI_i_price.setText(bmobTeacher.getPrice() + "");
                            TI_name.setText(bmobTeacher.getName() + "");
                            TI_signature.setText(bmobTeacher.getSignature());
                            TF_i_gold.setText(bmobTeacher.getGold() + "");
                            TI_commendnum.setText(bmobTeacher.getCommendnum() + "");
                            TI_teachingnum.setText(bmobTeacher.getTeachingnum() + "");
                            TI_browsenum.setText(bmobTeacher.getBrowsenum() + "");
                            TI_gender.setText(bmobTeacher.getGender() + "");
                            TI_age.setText(bmobTeacher.getAge() + "");
                            TI_phone.setText(bmobTeacher.getPhone() + "");
                            TI_birthday.setText(bmobTeacher.getBirthday()+"");
                            TI_site.setText(bmobTeacher.getSite() + "");
                            TI_e_mail.setText(bmobTeacher.getE_mail() + "");
                            TI_course.setText(bmobTeacher.getCourse() + "");
                            TI_education.setText(bmobTeacher.getEducation() + "");
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


    private void modification_frame(View view, String hint, int Type) {
        pop = Popup.getPop(getContext(), R.layout.import_popup);
        win = pop.getWin();
        win.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);
        View phone_popView = pop.getView();
        modification_hint = phone_popView.findViewById(R.id.modification_hint);
        modification_hint.setText(hint);
        modification_content = phone_popView.findViewById(R.id.modification_content);
        modification_content.setInputType(Type);
        pop_modification_confirm = phone_popView.findViewById(R.id.pop_modification_confirm);
        pop_modification_confirm.setOnClickListener(new TeacherInfo.popItemClick(win));
        outer = phone_popView.findViewById(R.id.outer);
        outer.setOnClickListener(new TeacherInfo.popItemClick(win));
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
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TI_i_price:
                modification_frame(view, "请输入你的课时报价", InputType.TYPE_CLASS_NUMBER);
                control = 1;
                break;
            case R.id.TI_name:
                modification_frame(view, "请输入你的名字", InputType.TYPE_CLASS_TEXT);
                control = 2;
                break;
            case R.id.TI_signature:
                modification_frame(view, "请输入你的个性签名", InputType.TYPE_CLASS_TEXT);
                control = 3;
                break;
            case R.id.linearLayout9:
                pop = Popup.getPop(getContext(), R.layout.gender_popup);
                win = pop.getWin();
                win.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);
                View gender_popView = pop.getView();
                Button boy = gender_popView.findViewById(R.id.boy);
                boy.setOnClickListener(new TeacherInfo.popItemClick(win));
                Button girl = gender_popView.findViewById(R.id.girl);
                girl.setOnClickListener(new TeacherInfo.popItemClick(win));
                Button confidentiality = gender_popView.findViewById(R.id.confidentiality);
                confidentiality.setOnClickListener(new TeacherInfo.popItemClick(win));
                break;
            case R.id.linearLayout11:
                new DatePickerDialog(getContext(), 2, new DatePickerDialog.OnDateSetListener() {
                    // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthday = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        int age = 0;
                        TI_birthday.setText(birthday);
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
                        TI_age.setText(age + "");
                    }
                }
                        // 设置初始日期
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();


                break;
            case R.id.linearLayout12:
                modification_frame(view, "请输入你的电话", InputType.TYPE_CLASS_TEXT);
                control = 4;
                break;
            case R.id.linearLayout13:
                modification_frame(view, "请输入你的所在地址", InputType.TYPE_CLASS_TEXT);
                control = 5;
                break;
            case R.id.linearLayout14:
                modification_frame(view, "请输入你的邮箱", InputType.TYPE_CLASS_TEXT);
                control = 6;
                break;
            case R.id.TI_e_mail:
                modification_frame(view, "请输入你的邮箱", InputType.TYPE_CLASS_TEXT);
                control = 6;
                break;
            case R.id.linearLayout15:
                modification_frame(view, "请输入你的教学内容", InputType.TYPE_CLASS_TEXT);
                control = 7;
                break;
            case R.id.linearLayout16:
                modification_frame(view, "请输入你的学历", InputType.TYPE_CLASS_TEXT);
                control = 8;
                break;
            case R.id.T_info_change:
                BmobQuery<Teacher> cursor = new BmobQuery<>();
                String account[] = {AppContext.account};
                cursor.addWhereContainsAll("account", Arrays.asList(account));
                //
                synchronized (this) {
                    cursor.findObjects(new FindListener<Teacher>() {
                        @Override
                        public void done(List<Teacher> list, BmobException e) {
                            if (e == null) {
                                Log.d(TAG, "查询成功：共" + list.size() + "条数据。");
                                for (Teacher bmobTeacher : list) {
                                    String ID = bmobTeacher.getObjectId();
                                    bmobTeacher.setValue("price", Integer.valueOf(TI_i_price.getText().toString()).intValue());
                                    bmobTeacher.setValue("name", TI_name.getText().toString());
                                    bmobTeacher.setValue("signature", TI_signature.getText().toString());
                                    bmobTeacher.setValue("gender", TI_gender.getText().toString());
                                    bmobTeacher.setValue("age", Integer.valueOf(TI_age.getText().toString()).intValue());
                                    bmobTeacher.setValue("phone", TI_phone.getText().toString());
                                    bmobTeacher.setValue("birthday",TI_birthday.getText().toString());
                                    bmobTeacher.setValue("site", TI_site.getText().toString());
                                    bmobTeacher.setValue("e_mail", TI_e_mail.getText().toString()+T_suffix.getText().toString());
                                    bmobTeacher.setValue("course", TI_course.getText().toString());
                                    bmobTeacher.setValue("education", TI_education.getText().toString());
                                    bmobTeacher.update(ID, new UpdateListener() {
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


    class popItemClick implements View.OnClickListener {
        PopupWindow win;

        popItemClick(PopupWindow win) {
            this.win = win;
        }

        //保存信息
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.boy:
                    TI_gender.setText("男");
                    win.dismiss();
                    break;
                case R.id.girl:
                    TI_gender.setText("女");
                    win.dismiss();
                    break;
                case R.id.confidentiality:
                    TI_gender.setText("保密");
                    win.dismiss();
                    break;
                case R.id.pop_modification_confirm:
                    switch (control) {
                        case 1:
                            TI_i_price.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 2:
                            TI_name.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 3:
                            TI_signature.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 4:
                            if (modification_content.getText().toString().length() != 11) {
                                Toast.makeText(getContext(), "手机格式错误", Toast.LENGTH_SHORT).show();
                            } else {
                                TI_phone.setText(modification_content.getText().toString());
                                win.dismiss();
                            }
                            break;
                        case 5:
                            TI_site.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 6:
                            TI_e_mail.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 7:
                            TI_course.setText(modification_content.getText().toString());
                            win.dismiss();
                            break;
                        case 8:
                            TI_education.setText(modification_content.getText().toString());
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
