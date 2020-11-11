package com.example.graduationworks.ForgetPassword;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.graduationworks.toolkit.AppContext;
import com.example.graduationworks.R;
import com.example.graduationworks.SQL.Teacher;
import com.example.graduationworks.SQL.User;
import java.util.Arrays;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class ChangeThePassword extends AppCompatActivity {
    private static final String TAG = "";
    EditText NewPassword,c_NewPassword;
    String NewPassword_T,c_NewPassword_t,user,identification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_the_password);
        instantiation();
    }
    private void instantiation() {
        NewPassword=findViewById(R.id.NewPassword);
        c_NewPassword=findViewById(R.id.c_NewPassword);
        user=AppContext.account;
        identification= AppContext.identification;
    }

    private void inquire(){
        NewPassword_T=NewPassword.getText().toString();
        if(identification.equals("user")){
            BmobQuery<User> cursor = new BmobQuery<>();
            String account[] = {user};
            cursor.addWhereContainsAll("account", Arrays.asList(account));
            //
            synchronized (this){
                cursor.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(e==null){
                            Log.d(TAG,"查询成功：共" + list.size() + "条数据。");
                            for (User bmobUser : list) {
                                String ID = bmobUser.getObjectId();
                                bmobUser.setValue("password",NewPassword_T);
                                bmobUser.update(ID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Log.i("bmob","成功");
                                        }else{
                                            Log.i("bmob","失败："+e.getMessage());
                                        }
                                    }
                                });
                            }
                        }else{
                            int errorCode = e.getErrorCode();
                            Log.d(TAG,"失败："+e.getMessage() + "\t错误码 ==> " + errorCode);

                        }
                    }
                });
            }
        }

        else {
            BmobQuery<Teacher> cursor = new BmobQuery<>();
            String TeacherName[] = {user};
            cursor.addWhereContainsAll("account", Arrays.asList(TeacherName));
            //
            synchronized (this){
                cursor.findObjects(new FindListener<Teacher>() {
                    @Override
                    public void done(List<Teacher> list, BmobException e) {
                        if(e==null){
                            Log.d(TAG,"查询成功：共" + list.size() + "条数据。");
                            for (Teacher bmobTeacher : list) {
                                String ID = bmobTeacher.getObjectId();
                                bmobTeacher.setValue("password",NewPassword_T);
                                bmobTeacher.update(ID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Log.i("bmob","成功");
                                        }else{
                                            Log.i("bmob","失败："+e.getMessage());
                                        }
                                    }
                                });
                            }
                        }else{
                            int errorCode = e.getErrorCode();
                            Log.d(TAG,"失败："+e.getMessage() + "\t错误码 ==> " + errorCode);

                        }
                    }
                });
            }
        }

    }
    //确认修改
    public void Changer(View view) {
        NewPassword_T=NewPassword.getText().toString();
        c_NewPassword_t=c_NewPassword.getText().toString();
        if (NewPassword_T.equals("")){
            c_NewPassword.setText("");
            NewPassword.setHint("新密码不能为空");
        }
        else if (NewPassword_T.equals(c_NewPassword_t)){
            inquire();
            AlertDialog.Builder dialog_R=new AlertDialog.Builder(ChangeThePassword.this);
            dialog_R.setTitle("提示");
            dialog_R.setMessage("修改成功");
            dialog_R.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ChangeThePassword.this.finish();
                }
            });
            dialog_R.show();
        }
        else {
            c_NewPassword.setText("");
            c_NewPassword.setHint("两次密码输入不一致");
        }
    }
}