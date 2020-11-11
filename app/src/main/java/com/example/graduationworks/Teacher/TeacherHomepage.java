package com.example.graduationworks.Teacher;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationworks.R;
import com.example.graduationworks.RegisterActivity;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.User;
import com.example.graduationworks.SQL.interaction;
import com.example.graduationworks.User.U_TeacherInfoActivity;
import com.example.graduationworks.User.UserInfo;
import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.toolkit.Popup;
import com.example.graduationworks.toolkit.THAdapter;
import com.example.graduationworks.toolkit.UHAdapter;

import java.text.DateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherHomepage extends Fragment {
    private static final String TAG = "提示";
    RecyclerView T_date_list;
    private List<interaction> interactionData = new ArrayList<>();
    private THAdapter adapter;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    Button T_date_button;
    View view;
    String time;
    int list_position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.teacher_fragement_homepage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        Button();
        ListonClick();
    }

    private void initialize(View view) {
        T_date_button = view.findViewById(R.id.T_date_button);
        T_date_list = view.findViewById(R.id.T_date_list);
        //设置布局管理器
        T_date_list.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置适配器
        adapter = new THAdapter();
        T_date_list.setAdapter(adapter);
        DATES();
    }

    private void DATES() {

        //设置数据
        BmobQuery<interaction> query = new BmobQuery<>();
        query.addWhereEqualTo("T_account", AppContext.account);
        query.findObjects(new FindListener<interaction>() {
            @Override
            public void done(List<interaction> list, BmobException e) {
                if (e == null) {
                    interactionData.clear();
                    Log.d(TAG, "查询到数据==>" + list);
                    //封装数据
                    //interactionData.clear();
                    for (interaction interaction : list) {
                        //实例化
                        interaction i = new interaction();
                        String date = interaction.getDate();
                        i.setDate(date);
                        String state = interaction.getState();
                        i.setState(state);
                        String id=interaction.getObjectId();
                        i.setObjectId(id);
                        interactionData.add(i);
                        //设置数据
                        if (adapter != null) {
                            adapter.setTHData(interactionData);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    int errorCode = e.getErrorCode();
                    Log.d(TAG, "错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                }
            }
        });
    }

    private void Button() {
        T_date_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), 2, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (year >= calendar.get(Calendar.YEAR)) {
                            if (monthOfYear >= calendar.get(Calendar.MONTH)) {
                                if (dayOfMonth >= calendar.get(Calendar.DAY_OF_MONTH)) {
                                    time = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";

                                    BmobQuery<Teacher> query = new BmobQuery<>();
                                    query.addWhereEqualTo("account", AppContext.account);
                                    synchronized (this) {
                                        query.findObjects(new FindListener<Teacher>() {
                                            @Override
                                            public void done(List<Teacher> list, BmobException e) {
                                                if (e == null) {
                                                    for (Teacher bmobTeacher : list) {
                                                        interaction i = new interaction();
                                                        i.setT_name(bmobTeacher.getName());
                                                        i.setT_phone(bmobTeacher.getPhone());
                                                        i.setDate(time);
                                                        i.setState("未被预约");
                                                        i.setT_account(AppContext.account);
                                                        i.setU_account("");

                                                        synchronized (this) {
                                                            i.save(new SaveListener<String>() {
                                                                @Override
                                                                public void done(String s, BmobException e) {
                                                                    if (e == null) {
                                                                        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                                                        DATES();
                                                                    } else {
                                                                        int errorCode = e.getErrorCode();
                                                                        Log.d(TAG, "错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                } else {
                                                    int errorCode = e.getErrorCode();
                                                    Log.d(TAG, "失败：" + e.getMessage() + "\t错误码 ==> " + errorCode);
                                                }
                                            }
                                        });
                                    }

                                } else {
                                    Toast.makeText(getContext(), "你选择的日期已过期", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "你选择的日期已过期", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "你选择的日期已过期", Toast.LENGTH_SHORT).show();
                        }
                    }
                }       // 设置初始日期
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void ListonClick() {
        adapter.setOnItemClickListener(new THAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Popup pop = Popup.getPop(getContext(), R.layout.date_pop_delete);
                PopupWindow win = pop.getWin();
                win.showAtLocation(view, Gravity.CENTER, 0, 0);
                View gender_popView = pop.getView();
                Button date_pop_f = gender_popView.findViewById(R.id.date_pop_f);
                date_pop_f.setOnClickListener(new TeacherHomepage.popItemClick(win));
                Button date_pop_t = gender_popView.findViewById(R.id.date_pop_t);
                date_pop_t.setOnClickListener(new TeacherHomepage.popItemClick(win));
                ConstraintLayout date_outer=gender_popView.findViewById(R.id.date_outer);
                date_outer.setOnClickListener(new TeacherHomepage.popItemClick(win));
                list_position=position;
            }
        });
    }


    class popItemClick implements View.OnClickListener {
        PopupWindow win;
        popItemClick(PopupWindow win) {
            this.win = win;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.date_pop_f:
                    win.dismiss();
                    break;
                case R.id.date_pop_t:
                    interaction i = new interaction();
                    String s = interactionData.get(list_position).getObjectId();
                    i.setObjectId(s);
                    i.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                DATES();
                            }else{
                                Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    win.dismiss();
                    break;
                case R.id.date_outer:
                    win.dismiss();
                    break;
            }
        }
    }
}
