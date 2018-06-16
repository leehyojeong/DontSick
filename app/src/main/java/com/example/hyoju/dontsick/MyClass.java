package com.example.hyoju.dontsick;

import android.app.Application;

import android.app.Application;


public class MyClass extends Application {
    public String userInfo;
    public String hos[][]= {{"안과"},//눈
            {"이비인후과"},//코
            {"치과","이비인후과"},//입
            {"이비인후과"},//귀
            {"치과"},//이
            {"내과","이비인후과"},//목
            {"내과","외과","피부과","소아청소년과"},//팔
            {"내과","외과","피부과","소아청소년과"},//다리
            {"내과","외과","피부과","소아청소년과"},//가슴
            {"내과","외과","피부과","소아청소년과"}//배
             };
    public String part;
    public int hosIndex;
}
