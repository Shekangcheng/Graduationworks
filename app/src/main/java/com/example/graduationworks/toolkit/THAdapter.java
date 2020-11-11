package com.example.graduationworks.toolkit;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.interaction;
import java.util.ArrayList;
import java.util.List;

public class THAdapter extends RecyclerView.Adapter<THAdapter.ViewHolder> {
    private Context context;
    private List<interaction> mData = new ArrayList<>();
    @NonNull
    @Override
    public THAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //加载布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull THAdapter.ViewHolder holder, int position) {
        interaction interaction = mData.get(position);
        holder.setItemData(interaction);
        holder.D_L_item.setOnClickListener(new View.OnClickListener() {
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

    public void setTHData(List<interaction> interactionData) {
        mData.clear();
        mData.addAll(interactionData);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private View D_L_item;
        private TextView D_L_date;
        private TextView D_L_state;
        private LinearLayout state_back;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            D_L_item=itemView.findViewById(R.id.D_L_item);
            D_L_date=itemView.findViewById(R.id.D_L_date);
            D_L_state=itemView.findViewById(R.id.D_L_state);
            state_back=itemView.findViewById(R.id.state_back);
        }

        public void setItemData(interaction interaction) {
            //
            String date = interaction.getDate();
            D_L_date.setText(date);
            //
            String state = interaction.getState();
            D_L_state.setText(state);
            if(state.equals("未被预约")){
                D_L_state.setText("");
                state_back.setBackground(context.getResources().getDrawable(R.drawable.state_f));
            }
            if(state.equals("已被预约")){
                D_L_state.setText("");
                state_back.setBackground(context.getResources().getDrawable(R.drawable.state_t));
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private THAdapter.OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(THAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
