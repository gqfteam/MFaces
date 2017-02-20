package com.wgc.mfaces.constant;

import android.content.Context;

/**
 * 常量类  app工具使用
 * @author
 */
public class Constants {
    /* 全局上下文变量存储 */
    public static Context mContext;

    /* APP全局日志打印标识 */
    public static final String App_Tag = "Test_Q";

    /* APP轻存储文件名 */
    public static final String Preference_Name = "htConfig";
    public static final String PNK_Uid = "uid";
    public static final String PNK_Uic = "uic";
    public static final String PNK_UName = "uName";
    public static final String PNK_UTel = "uTel";
    public static final String PNK_ULand = "uLand";
    public static final String PNK_UToken = "token";
    public static final String PNK_Nic = "newIc";
    public static final String PNK_Nmenu = "newMenu";
    public static final String PNK_Nmess = "newMess";
    public static final String PNK_Nhistory = "newHistory";
    public static final String PNK_NTool = "newTool";
    public static final String PNK_NVideo = "newVideo";
    public static final String PNK_NCircle = "newCiecle";
    public static final String PNK_NPub = "newPub";

    /* APP在手机SD卡根目录下的路径名称 */
    public static final String App_File_Path = "htl";
    public static final String New_Img_Path = App_File_Path + "/img/";
    public static final String New_Down_Path = App_File_Path + "/down";
    public static final String New_Apk_Path = App_File_Path + "/apk";
    public static final String New_Apk_Name = "htlx.apk";

  /*  *//* 短信验证APP Key *//*
    public static final String Mob_AK = "17b281182fd9f";
    *//* 短信验证APP Secret *//*
    public static final String Mob_AS = "a051c339708c445d12171408afcf4f79";

	*//* http基本访问域名 *//*
    public static final String Http_Base = "https://www.baidu.com/";
    *//* 检查app更新的接口 *//*
    public static final String Http_Api_CheckUpdate = Http_Base + "welcome/checkupdate";
    *//* 自动登录的接口--[User、token] *//*
    public static final String Http_Api_Login_UT = Http_Base + "welcome/loginut";
    *//* 获取手机验证码的接口 *//*
    public static final String Http_Api_GetKey = Http_Base + "login/getkey";
    *//* 登录接口 *//*
    public static final String Http_Api_Login = Http_Base + "login";
    *//* 动态圈上传接口 *//*
    public static final String Http_Api_Circle_Add = Http_Base + "circle/add";*/

}
