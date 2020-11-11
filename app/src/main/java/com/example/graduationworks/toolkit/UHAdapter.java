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

public class UHAdapter extends RecyclerView.Adapter<UHAdapter.ViewHolder> {

    private Context context;
    private List<Teacher> mData = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //加载布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //获取条目实例
        Teacher teacher = mData.get(position);
        holder.setItemData(teacher);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 外部封装的数据会传到这个方法里来
     *
     * @param mTeacherData
     */
    public void setData(List<Teacher> mTeacherData) {
        mData.clear();
        mData.addAll(mTeacherData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View item;
        private TextView Tname;
        private TextView Tsite;
        private TextView Tcourse;
        private TextView Tprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tname = itemView.findViewById(R.id.Tname);
            Tsite = itemView.findViewById(R.id.Tsite);
            Tcourse = itemView.findViewById(R.id.Tcourse);
            Tprice = itemView.findViewById(R.id.Tprice);
            item = itemView.findViewById(R.id.item);
        }

        public void setItemData(Teacher teacher) {
            //姓名
            String teacherName = teacher.getName();
            Tname.setText(teacherName);
            //
            String site = teacher.getSite();
            Tsite.setText(site);
            //
            String course = teacher.getCourse();
            Tcourse.setText(course);
            //
            int price = teacher.getPrice();
            Tprice.setText(price+"");
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
