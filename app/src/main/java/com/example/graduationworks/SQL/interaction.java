package com.example.graduationworks.SQL;
import cn.bmob.v3.BmobObject;

public class interaction extends BmobObject {
    private String U_account;
    private String T_account;
    private String T_name;
    private String T_phone;
    private String date;
    private String state;
    private String Teaching;

    public String getU_account() {
        return U_account;
    }

    public void setU_account(String u_account) {
        U_account = u_account;
    }

    public String getT_account() {
        return T_account;
    }

    public void setT_account(String t_account) {
        T_account = t_account;
    }

    @Override
    public String toString() {
        return "interaction{" +
                "U_account='" + U_account + '\'' +
                ", T_account='" + T_account + '\'' +
                ", T_name='" + T_name + '\'' +
                ", T_phone='" + T_phone + '\'' +
                ", date='" + date + '\'' +
                ", state='" + state + '\'' +
                ", Teaching='" + Teaching + '\'' +
                '}';
    }

    public String getT_name() {
        return T_name;
    }

    public void setT_name(String t_name) {
        T_name = t_name;
    }

    public String getT_phone() {
        return T_phone;
    }

    public void setT_phone(String t_phone) {
        T_phone = t_phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTeaching() {
        return Teaching;
    }

    public void setTeaching(String teaching) {
        Teaching = teaching;
    }
}
