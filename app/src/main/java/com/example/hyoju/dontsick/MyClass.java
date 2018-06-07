package com.example.hyoju.dontsick;

import android.app.Application;

import android.app.Application;


public class MyClass extends Application {
    public String userInfo;
    public String hos[][]= new String[10][];
    public String part;
    public int hosIndex;

    public void Init(){
        hos[0] = new String[]{"안과,그 외"};//눈
        hos[1] = new String[]{"이비인후과,그 외"};//코
        hos[2] = new String[]{"치과,이빈후과,그 외"};//입
        hos[3] = new String[]{"이비인후과,그 외"};//귀
        hos[4] = new String[]{"치과,그 외"};//이
        hos[5] = new String[]{"내과,이비인후과,그 외"};//목
        hos[6] = new String[]{"내과,외과,피부과,소아청소년과,그 외"};//팔
        hos[7] = new String[]{"내과,외과,피부과,소아청소년과,그 외"};//다리
        hos[8] = new String[]{"내과,외과,피부과,소아청소년과,그 외"};//가슴
        hos[9] = new String[]{"내과,외과,피부과,소아청소년솨,그 외"};//배
    }

}
