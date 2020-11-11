package com.example.graduationworks.SQL;
import cn.bmob.v3.BmobObject;

public class Teacher extends BmobObject {
    private String name;//名字
    private String account;//账号
    private String password;//密码
    private String gender;//性别
    private int age;//年龄
    private String site;//地址
    private String phone;//手机号
    private int gold;//金币
    private String student;//学生
    private String e_mail;//邮箱
    private String education;//受教育水平
    private String course;//课程
    private String signature;//签名
    private int commendnum;//点赞数
    private int teachingnum;//授课数
    private int browsenum;//浏览次数
    private int price;//价格
    private String birthday;//生日

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getTeachingnum() {
        return teachingnum;
    }

    public void setTeachingnum(int teachingnum) {
        this.teachingnum = teachingnum;
    }

    public int getBrowsenum() {
        return browsenum;
    }

    public int getCommendnum() {
        return commendnum;
    }

    public void setCommendnum(int commendnum) {
        this.commendnum = commendnum;
    }

    public void setBrowsenum(int browsenum) {
        this.browsenum = browsenum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
