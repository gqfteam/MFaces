package com.wgc.mfaces.activity;

/**
 * Created by Administrator on 2016/12/11.
 */

public class InitiateSigninMsg {
    String signinScope;  //签到范围
    String signinStime;  //签到时间
    String signinLocal;  //签到地点
    String signinPurpose; //签到目的
    String[] signinClass;  //签到班级

    double a;//经
    double b;//纬

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public String getSigninScope() {
        return signinScope;
    }

    public void setSigninScope(String signinScope) {
        this.signinScope = signinScope;
    }

    public String getSigninStime() {
        return signinStime;
    }

    public void setSigninStime(String signinStime) {
        this.signinStime = signinStime;
    }

    public String getSigninLocal() {
        return signinLocal;
    }

    public void setSigninLocal(String signinLocal) {
        this.signinLocal = signinLocal;
    }

    public String getSigninPurpose() {
        return signinPurpose;
    }

    public void setSigninPurpose(String signinPurpose) {
        this.signinPurpose = signinPurpose;
    }

    public String[] getSigninClass() {
        return signinClass;
    }

    public void setSigninClass(String[] signinClass) {
        this.signinClass = signinClass;
    }







}
