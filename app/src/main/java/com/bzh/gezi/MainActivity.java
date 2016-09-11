package com.bzh.gezi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;

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

    private LinearLayoutManager linearLayoutManager;
    private boolean reverse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.recycler));
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final FibonacciDataSource fibonacciDataSource = new FibonacciDataSource();
        final MyAdapter adapter = new MyAdapter(fibonacciDataSource.getDataSource());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // AsyncTask是串行执行,不会导致斐波那契数列的结果乱序
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        swipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        fibonacciDataSource.addNewData(50);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }.execute();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void onReverse(View view) {
        reverse = !reverse;
        linearLayoutManager.setReverseLayout(reverse);
    }

    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


        private ArrayList<String> dataSource;

        public MyAdapter(ArrayList<String> dataSource) {

            this.dataSource = dataSource;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.content.setText("fib(" + position + ")=" + dataSource.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSource.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    // 斐波那契数列数据源
    // 利用额外的存储空间保存之前计算的结果,使新值计算的时间复杂度为BigInteger加法和乘法的复杂度 O(N)
    static class FibonacciDataSource {

        // 默认数据源数据量
        private final int DEFAULT_DATA_SOURCE_SIZE = 50;

        // 当前行数
        private int current_row;

        // 显示结果
        private ArrayList<String> dataSource = new ArrayList<>();

        // 主值列表
        private ArrayList<BigInteger> fibList1 = new ArrayList<>();

        // 副职列表
        private ArrayList<BigInteger> fibList2 = new ArrayList<>();

        private BigInteger fib_0 = new BigInteger("0");
        private BigInteger fib_1 = new BigInteger("1");

        // 1.0E10
        private BigInteger threshold = new BigInteger("10000000000");

        // 主值小系数
        private BigInteger primary_small_coffcient = new BigInteger("2");
        // 主值大系数
        private BigInteger primary_large_coffcient = new BigInteger("3");

        // 副值小系数
        private BigInteger secondary_small_cofficent = new BigInteger("1");
        // 副值大系数
        private BigInteger secondary_large_cofficent = new BigInteger("2");

        // 待加入值
        private BigInteger primary = new BigInteger("0");
        private BigInteger secondary = new BigInteger("0");

        // 固定值
        private BigInteger big_value_1 = new BigInteger("1");
        private BigInteger big_value_4 = new BigInteger("4");

        // 构造默认数据
        public FibonacciDataSource() {
            for (int i = 1; i <= DEFAULT_DATA_SOURCE_SIZE; i++) {
                current_row = i;
                updateDataSource(current_row);
            }
        }

        // 顺序添加count个新斐波那契数列值
        public void addNewData(int count) {
            int target = current_row + count;
            for (int i = current_row; i <= target; i++) {
                current_row = i;
                updateDataSource(current_row);
            }
        }

        // 根据前一行的斐波那契数列值计算当前行的斐波那契数列值
        private void updateDataSource(int row) {

            BigInteger to_be_calculated = new BigInteger(row * row + "");

            if (to_be_calculated.compareTo(big_value_1) == 0) {
                // 添加开始值
                fibList1.add(big_value_1);
                dataSource.add("1");
                return;
            } else {
                if (to_be_calculated.compareTo(big_value_4) == 0) {
                    // 计算起始副值
                    secondary = secondary_small_cofficent.multiply(fib_0).add(secondary_large_cofficent.multiply(fib_1));
                    fibList2.add(secondary);

                    // 计算起始主值
                    primary = primary_small_coffcient.multiply(fib_0).add(primary_large_coffcient.multiply(fib_1));
                    fibList1.add(primary);
                    dataSource.add(primary.toString());
                } else {
                    // 计算副值
                    secondary = secondary_small_cofficent.multiply(fibList2.get(fibList2.size() - 1)).add(secondary_large_cofficent.multiply(fibList1.get(fibList1.size() - 1)));
                    fibList2.add(secondary);

                    // 计算主值
                    primary = primary_small_coffcient.multiply(fibList2.get(fibList2.size() - 2)).add(primary_large_coffcient.multiply(fibList1.get(fibList1.size() - 1)));
                    fibList1.add(primary);
                    if (primary.compareTo(threshold) > 0) {

                        String temp = primary.toString();
                        StringBuilder sb = new StringBuilder();
                        sb.append(temp.substring(0, 1));
                        sb.append(".");
                        sb.append(temp.substring(1, 11));
                        sb.append("E");
                        sb.append(temp.length() - 1);

                        dataSource.add(sb.toString());
                    } else {
                        dataSource.add(primary.toString());
                    }
                }
            }

            // 更新系数
            BigInteger temp_secondary_large_cofficent = secondary_large_cofficent;
            secondary_small_cofficent = secondary_small_cofficent.add(secondary_large_cofficent);
            secondary_large_cofficent = secondary_small_cofficent.add(temp_secondary_large_cofficent);

            // 更新系数
            BigInteger temp_primary_large_coffcient = primary_large_coffcient;
            primary_small_coffcient = primary_small_coffcient.add(primary_large_coffcient);
            primary_large_coffcient = primary_small_coffcient.add(temp_primary_large_coffcient);
        }

        public ArrayList<String> getDataSource() {
            return dataSource;
        }
    }
}
