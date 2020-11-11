package com.example.graduationworks.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graduationworks.LoginActivity;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.User;
import com.example.graduationworks.SQL.interaction;
import com.example.graduationworks.Teacher.TeacherActivity;
import com.example.graduationworks.Teacher.TeacherHomepage;
import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.toolkit.Popup;
import com.example.graduationworks.toolkit.THAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ReservationActivity extends AppCompatActivity {
    private static final String TAG = "提示";
    RecyclerView U_date_list;
    private List<interaction> interactionData = new ArrayList<>();
    private THAdapter adapter;
    String U_t_account;
    View view;
    int list_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        U_t_account = getIntent().getStringExtra("T_account");
        initialize();
        DATES();
        ListonClick();
    }

    private void initialize() {
        U_date_list = findViewById(R.id.U_date_list);
        //设置布局管理器
        U_date_list.setLayoutManager(new LinearLayoutManager(ReservationActivity.this));
        //设置适配器
        adapter = new THAdapter();
        U_date_list.setAdapter(adapter);
        view = U_date_list.getRootView();
    }

    private void DATES() {
        //设置数据
        BmobQuery<interaction> query = new BmobQuery<>();
        query.addWhereEqualTo("T_account", U_t_account);
        query.findObjects(new FindListener<interaction>() {
            @Override
            public void done(List<interaction> list, BmobException e) {
                if (e == null) {
                    interactionData.clear();
                    Log.d(TAG, "查询到数据==>" + list);
                    for (interaction interaction : list) {
                        //实例化
                        interaction i = new interaction();
                        String date = interaction.getDate();
                        i.setDate(date);
                        String state = interaction.getState();
                        i.setState(state);
                        String id = interaction.getObjectId();
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


    public void ListonClick() {
        adapter.setOnItemClickListener(new THAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                list_position = position;
                String s = interactionData.get(position).getObjectId();
                BmobQuery<interaction> cursor = new BmobQuery<>();
                cursor.addWhereEqualTo("objectId", s);
                cursor.addWhereEqualTo("state","已被预约");
                synchronized (this) {
                    cursor.findObjects(new FindListener<interaction>() {
                        @Override
                        public void done(List<interaction> list, BmobException e) {
                            if (e == null && list.size() > 0) {
                                Toast.makeText(ReservationActivity.this, "该时间已被预约", Toast.LENGTH_SHORT).show();
                            } else {
                                Popup pop = Popup.getPop(ReservationActivity.this, R.layout.appointment_pop);
                                PopupWindow win = pop.getWin();
                                win.showAtLocation(view, Gravity.CENTER, 0, 0);
                                View gender_popView = pop.getView();
                                TextView appointment_date = gender_popView.findViewById(R.id.appointment_date);
                                Button cancel = gender_popView.findViewById(R.id.cancel);
                                cancel.setOnClickListener(new ReservationActivity.popItemClick(win));
                                Button appointment = gender_popView.findViewById(R.id.appointment);
                                appointment.setOnClickListener(new ReservationActivity.popItemClick(win));
                                ConstraintLayout appointment_outer = gender_popView.findViewById(R.id.appointment_outer);
                                appointment_outer.setOnClickListener(new ReservationActivity.popItemClick(win));
                            }
                        }
                    });
                }
                }
            });
    }





    class popItemClick implements View.OnClickListener {
        PopupWindow win;

        popItemClick(PopupWindow win) {
            this.win = win;
        }

        @Override
        public void onClick(android.view.View v) {
            switch (v.getId()) {
                case R.id.cancel:
                    win.dismiss();
                    break;
                case R.id.appointment:
                    String s = interactionData.get(list_position).getObjectId();
                    BmobQuery<interaction> cursor = new BmobQuery<>();
                    String account[] = {s};
                    cursor.addWhereContainsAll("objectId", Arrays.asList(account));
                    //
                    synchronized (this) {
                        cursor.findObjects(new FindListener<interaction>() {
                            @Override
                            public void done(List<interaction> list, BmobException e) {
                                if (e == null) {
                                    Log.d(TAG, "查询成功：共" + list.size() + "条数据。");
                                    for (interaction bmobinteraction : list) {
                                        String ID = bmobinteraction.getObjectId();
                                        bmobinteraction.setValue("U_account", AppContext.account);
                                        bmobinteraction.setValue("state", "已被预约");
                                        bmobinteraction.update(ID, new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(ReservationActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
                                                    DATES();
                                                    Log.i("bmob", "成功");
                                                } else {
                                                    Log.i("bmob", "失败：" + e.getMessage());
                                                    Toast.makeText(ReservationActivity.this, "预约失败", Toast.LENGTH_SHORT).show();
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
                    win.dismiss();
                    break;
                case R.id.appointment_outer:
                    win.dismiss();
                    break;
            }
        }
    }
}