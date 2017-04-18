package com.arny.lubereckiy.utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    private static TextToSpeech textToSpeech = null;
    private static boolean speechReady = false;

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return !(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable());
    }

    public static Map<String, String> jsonToMap(JSONObject json) throws JSONException {
        Map<String, String> retMap = new HashMap<String, String>();
        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, String> toMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, String.valueOf(value));
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * Воспроизвести звук
     *
     * @param resourceId ID звукового ресурса
     * @param context    Контекст
     */
    public static void playSound(int resourceId, Context context) {
        try {
            MediaPlayer mp = MediaPlayer.create(context, resourceId);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Повибрировать :)
     *
     * @param duration Длительность в ms, например, 500 - полсекунды
     */
    public static void vibrate(Context context, int duration) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

    public static ArrayList<String> sortDates(ArrayList<String> dates, final String format) {
        Collections.sort(dates, new Comparator<String>() {
            private SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

            @Override
            public int compare(String o1, String o2) {
                int result = -1;
                try {
                    result = sdf.parse(o1).compareTo(sdf.parse(o2));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                return result;
            }
        });
        return dates;
    }

    public static String match(String where, String pattern, int groupnum) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(where);
        while (m.find()) {
            if (!m.group(groupnum).equals("")) {
                return m.group(groupnum);
            }
        }
        return null;
    }

    public static String getDateTime(long milliseconds, String format) {
        milliseconds = (milliseconds == 0) ? Calendar.getInstance().getTimeInMillis() : milliseconds;
        format = (format == null) ? "dd MMM yyyy HH:mm:ss.sss" : format;
        return (new SimpleDateFormat(format,
                Locale.getDefault())).format(new Date(milliseconds));
    }

    public static String getDateTime(long milliseconds) {
        milliseconds = (milliseconds == 0) ? Calendar.getInstance().getTimeInMillis() : milliseconds;
        return (new SimpleDateFormat("dd MMM yyyy HH:mm:ss.sss",
                Locale.getDefault())).format(new Date(milliseconds));
    }

    public static String getDateTime() {
        return (new SimpleDateFormat("dd MMM yyyy HH:mm:ss.sss",
                Locale.getDefault())).format(new Date(Calendar.getInstance().getTimeInMillis()));
    }

    public static long convertTimeStringToLong(String myTimestamp, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        Date date;
        try {
            date = formatter.parse(myTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date.getTime();
    }

    /**
     * @param date
     * @param format
     * @return String datetime
     */
    public static String getDateTime(Date date, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            long milliseconds = calendar.getTimeInMillis();
            format = (format == null || format.trim().equals("")) ? "dd MMM yyyy HH:mm:ss.sss" : format;
            return (new SimpleDateFormat(format, Locale.getDefault())).format(new Date(milliseconds));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int randInt(int min, int max) {
        Random rnd = new Random();
        int range = max - min + 1;
        return rnd.nextInt(range) + min;
    }

    public static double round(double val, int scale) {
        return new BigDecimal(val).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean empty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof String) {
                String s = (String) obj;
                return s.trim().equals("null") || s.trim().isEmpty();
            } else {
                return false;
            }
        }
    }

    /**
     * дробная часть числа
     *
     * @param x
     * @return
     */
    public static double fracal(double x) {
        return x - (int) x;
    }

    /**
     * целая часть числа
     *
     * @param x
     * @return
     */
    public static int intact(double x) {
        return (int) x;
    }

    public static String getExtendTime(long ms){
        String def = ms + " ms";
        if (ms>=1000 && ms<60000) {
            int sec = (int)(ms/1000);
            double mss = (double)ms;
            mss = ((mss/1000) - sec)*1000;
            int ms2 = (int)mss;
            def = sec +  " sec " + ms2 + " ms";
        }
        return def;
    }

    public static String trimInside(String text) {
        return text.trim().replace(" ", "");
    }

    public static synchronized boolean isServiceRunning(Class<?> serviceClass, Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static Collection<Field> getFields(Class<?> clazz) {
        ArrayList<String> excluded=new ArrayList<>();
        excluded.add("CREATOR");
        excluded.add("shadow$_klass_");
        excluded.add("serialVersionUID");
        excluded.add("$change");
        excluded.add("shadow$_monitor_");
        Map<String, Field> fields = new HashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!fields.containsKey(field.getName())) {
                    if (!excluded.contains(field.getName())) {
                        fields.put(field.getName(), field);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields.values();
    }

    public static void confirmDialog(Context context, String title, final DialogConfirmListener dialogConfirmListener){
        new AlertDialog.Builder(context).setTitle(title + "?")
                .setNegativeButton(context.getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialogConfirmListener.onCancel();
                    }
                }).setPositiveButton(context.getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogConfirmListener.onConfirm();
            }
        }).show();
    }

}
