package com.bzh.gezi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-8-11<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * <p/>
 * 要求:
 * - 请实现一个可以显示斐波那列表的activity<br>
 * - 请编写文档介绍你都做了那些很酷的事情<br>
 * - 项目必须可以在Android SDK 4.0以上的版本与环境下运行<br>
 * - 提交一个完整的,可运行的Android项目包<br>
 * - 压缩打包代码和文档,命名为[名字]_android.zip<br>
 * <p/>
 * 斐波那契数列定义(Fibonacci)<br>
 * F(0) = 0;<br>
 * F(1) = 1;<br>
 * F(n) = F(n-1) + F(n-2);<br>
 * <p/>
 * 题目:<br>
 * a.1.请写一个仅有一个列表页面的Android应用<br>
 * a.2.列表从0开始,从小到大,每行显示一个斐波那器数字<br>
 * a.3.列表第n行显示F(n^2),至少显示到F(450^2)<br>
 * a.4.列表必须保持流利滑动<br>
 * a.5.当数值超过10^10时,用科学计数法表示Fibonacci的值<br>
 * a.6.不允许计算完所有的值之后再显示<br>
 * a.7.可以使用BigInteger来存储F(n)的值<br>
 * <p/>
 * b.1.在界面上价一个切换顺序的按钮<br>
 * b.2.点击按钮会在"从小达大"和"从大到小"排序之间切换.<br>
 * ==========================================================<br>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
