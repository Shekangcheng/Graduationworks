package com.example.graduationworks.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.interaction;
import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.toolkit.UCAdapter;
import com.example.graduationworks.toolkit.UHAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UserCourse extends Fragment {
    RecyclerView course_list;
    private List<interaction> mTeacherData = new ArrayList<>();
    private static final String TAG = "提示";
    private UCAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_fragment_course,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        DATES();
        OnClick();
    }

    private void initialize(View view){
        course_list = view.findViewById(R.id.course_list);
        //设置布局管理器
        course_list.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置适配器
        adapter = new UCAdapter();
        course_list.setAdapter(adapter);
    }

    private void DATES() {
        //设置数据
        BmobQuery<interaction> query = new BmobQuery<>();
        query.addWhereEqualTo("U_account",AppContext.account);
        query.findObjects(new FindListener<interaction>() {
            @Override
            public void done(List<interaction> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "UserCou查询到数据==>" + list.size());
                    //封装数据
                    mTeacherData.clear();
                    for (interaction interaction : list) {
                        mTeacherData.add(interaction);
                        //设置数据
                        if (adapter != null) {
                            adapter.setUCata(mTeacherData);
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
    private void OnClick(){
        adapter.setOnItemClickListener(new UCAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                interaction teacher = mTeacherData.get(position);
                String T_account = teacher.getT_account();
                String ObjectId=teacher.getObjectId();
                Intent intent=new Intent(getContext(),U_C_teacherActivity.class);
                intent.putExtra("T_account",T_account);
                intent.putExtra("ObjectId",ObjectId);
                startActivity(intent);
            }
        });
    }
}
