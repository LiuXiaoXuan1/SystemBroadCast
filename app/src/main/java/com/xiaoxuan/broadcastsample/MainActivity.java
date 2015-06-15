package com.xiaoxuan.broadcastsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xiaoxuan.broadcastsample.utils.Tools;

/**
 * 此广播时系统广播，Android内置了很多系统级别的广播，比如电池电量太低会发出一条是否开启省电模式的广播
 */

public class MainActivity extends ActionBarActivity {

    private IntentFilter intentFilter;

    private NetworkChangeReceiver networkChangeReceiver;//创建一个广播接收器的实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);//利用动态注册方法监听网络的变化
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {//重写注册方法
            /**
             * 通过getSystemService方法得到ConnectionManager的实例，这是一个系统服务类，专门管理网络连接
             * 接着调用NetworkInfo的isAvalilable方法判断当前是否有望
             * 要在AndroidManifest中加上查询系统网络的权限
             * 此处声明，不要在onReceive方法中添加过于复杂的逻辑或进行任何耗时的操作
             * 在广播接受器中不允许开启线程
             */
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Tools.ToolsToast(context, "网络连接成功");
            } else {
                Tools.ToolsToast(context, "网络没有连接，请检查你的网络");
            }
        }
    }
}
