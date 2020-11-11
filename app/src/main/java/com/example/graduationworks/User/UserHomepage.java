package com.example.graduationworks.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.User;
import com.example.graduationworks.toolkit.UHAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UserHomepage extends Fragment {
    private static final String TAG = "提示";
    EditText ed_search;
    RecyclerView T_list;
    TextView UFH_name,UFH_gold;
    private List<Teacher> mTeacherData = new ArrayList<>();
    private UHAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_homepage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        info();
        Teachers("");
        Ed_search();
    }
    private void initialize(View view){
        ed_search = view.findViewById(R.id.ed_search);
        T_list = view.findViewById(R.id.T_list);
        UFH_name=view.findViewById(R.id.UFH_name);
        UFH_gold=view.findViewById(R.id.UFH_gold);
        //设置布局管理器
        T_list.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置适配器
        adapter = new UHAdapter();
        T_list.setAdapter(adapter);
    }
    private void info(){
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereEqualTo("account", AppContext.account);
        synchronized (this){
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(e==null){
                        for (User bmobUser : list) {
                             UFH_name.setText(bmobUser.getName());
                             UFH_gold.setText(""+bmobUser.getGold());
                        }
                    }else{
                        int errorCode = e.getErrorCode();
                        Log.d(TAG,"失败："+e.getMessage() + "\t错误码 ==> " + errorCode);
                    }
                }
            });
        }
    }

    private void Ed_search(){
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String condition=ed_search.getText().toString();
                Teachers(condition);
            }
        });
    }

    private void Teachers(String condition){
        //设置数据
        BmobQuery<Teacher> query = new BmobQuery<>();
        if(!condition.equals("")){
            query.addWhereEqualTo("name",condition);
        }
        query.findObjects(new FindListener<Teacher>() {
            @Override
            public void done(List<Teacher> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "查询到数据==>" + list.size());
                    //封装数据
                    mTeacherData.clear();
                    for (Teacher tr : list) {
                        //实例化
                        Teacher t = new Teacher();
                        String account = tr.getAccount();
                        t.setAccount(account);
                        String teacherName = tr.getName();
                        t.setName(teacherName);
                        String site = tr.getSite();
                        t.setSite(site);
                        String course = tr.getCourse();
                        t.setCourse(course);
                        int price = tr.getPrice();
                        t.setPrice(price);
                        mTeacherData.add(t);
                        //设置数据
                        if (adapter != null) {
                            adapter.setData(mTeacherData);
                        }
                    }
                } else {
                    int errorCode = e.getErrorCode();
                    Log.d(TAG, "错误信息 ==> " + e.getMessage() + "\t错误码 ==> " + errorCode);
                }
            }
        });

        //
        adapter.setOnItemClickListener(new UHAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Teacher teacher = mTeacherData.get(position);
                String account = teacher.getAccount();
                Intent intent=new Intent(getContext(), U_TeacherInfoActivity.class);
                intent.putExtra("T_account",account);
                startActivity(intent);
            }
        });
    }
}
