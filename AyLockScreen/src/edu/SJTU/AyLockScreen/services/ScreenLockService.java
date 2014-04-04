package edu.SJTU.AyLockScreen.services;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import edu.SJTU.AyLockScreen.activities.MainActivity;

/**
 * Created by Yang Zhang on 2014/4/4.
 */
public class ScreenLockService extends Service {

    private static String TAG = "SCREEN_LOCK_SERVICE";
    private static String KEYGUARD_TAG = "AY_LOCK_SCREEN_1";

    private static String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    private static String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";

    private Intent screenLockIntent;
    private KeyguardManager mKeyguardManager;
    private KeyguardManager.KeyguardLock mKeyguardLock;

    // 广播：屏幕变亮，隐藏默认锁屏
    private BroadcastReceiver screenOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_SCREEN_ON)) {
                Log.d(TAG, ACTION_SCREEN_ON);
            }
        }
    };

    // 广播：屏幕变暗/变亮，调用KeyguardManager解除屏幕锁定
    private BroadcastReceiver screenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_SCREEN_OFF) || intent.getAction().equals(ACTION_SCREEN_ON)) {
                mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                mKeyguardLock = mKeyguardManager.newKeyguardLock(KEYGUARD_TAG);
                mKeyguardLock.disableKeyguard();
                startActivity(screenLockIntent);
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        screenLockIntent = new Intent(ScreenLockService.this, MainActivity.class);
        screenLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // 注册广播
        IntentFilter screenOnFilter = new IntentFilter(ACTION_SCREEN_ON);
        IntentFilter screenOffFilter = new IntentFilter(ACTION_SCREEN_OFF);

        ScreenLockService.this.registerReceiver(screenOnReceiver, screenOnFilter);
        ScreenLockService.this.registerReceiver(screenOffReceiver, screenOffFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ScreenLockService.this.unregisterReceiver(screenOnReceiver);
        ScreenLockService.this.unregisterReceiver(screenOffReceiver);
        // 重启服务
        startService(new Intent(ScreenLockService.this, ScreenLockService.class));
    }

}
