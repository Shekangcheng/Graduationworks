package com.example.graduationworks.toolkit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import java.util.ArrayList;
import java.util.List;
public class UCAdapter extends RecyclerView.Adapter<UCAdapter.ViewHolder> {

    private Context context;
    private List<Teacher> mData = new ArrayList<>();
    @NonNull
    @Override
    public UCAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //加载布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UCAdapter.ViewHolder holder, int position) {
        //获取条目实例
        Teacher teacher = mData.get(position);
        holder.setItemData(teacher);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setTHData(List<Teacher> teachers) {
        mData.clear();
        mData.addAll(teachers);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View course_item;
        private TextView course_T_name;
        private TextView course_T_phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_item=itemView.findViewById(R.id.course_item);
            course_T_name=itemView.findViewById(R.id.course_T_name);
            course_T_phone=itemView.findViewById(R.id.course_T_phone);
        }

        public void setItemData(Teacher teacher) {
            //姓名
            String teacherName = teacher.getName();
            course_T_name.setText(teacherName);
            //电话
            String teacher_phone = teacher.getPhone();
            course_T_phone.setText(teacher_phone);
        }
    }
}
