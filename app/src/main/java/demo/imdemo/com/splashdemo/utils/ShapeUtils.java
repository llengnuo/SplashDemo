package demo.imdemo.com.splashdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 2017/9/29.
 */

public class ShapeUtils {
    private static final String ENTER_PAGE="enter_page";//sp的文件名字

    public static void saveEnterBoolean(Context context,String key,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ENTER_PAGE,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key,value);
        edit.apply();
    }
    public static boolean getEnterBoolean(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ENTER_PAGE,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
}
