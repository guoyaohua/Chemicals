package com.guoyaohua.chemicals;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import android.support.v7.app.AppCompatActivity;

/**
 * Created by John Kwok on 2016/7/15.
 */
public class Welcome extends Activity {

    // 打开数据库
    public static SQLiteDatabase sqLite = null;
    AssetManager asset;
    private SQLiteOpenHelper helper = null;
    private Boolean importFinished = false;
    private Boolean startFinished = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_layout);

        asset = getAssets();

        //程序首次运行时 将数据库导入手机中
        helper = new MyDatabaseHelper(this);
        sqLite = helper.getReadableDatabase();
        Thread importRES = new Thread() {
            @Override
            public void run() {
                importDB();
                importPDF();
                importFinished = true;
                if(Welcome.this!=null&&startFinished){
                    Welcome.this.finish();
                    Log.i("thread111","end Activity from thread");
                }
            }
        };
        importRES.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(getApplication(), MainActivity.class));
                if (importFinished) {
                    Welcome.this.finish();
                    Log.i("thread111","end Activity from handler");
                }else{
                    startFinished = true;
                }
//                finish();
            }

        }, 3000);
    }

    private void importPDF() {
        File file = new File(Environment.getExternalStorageDirectory() + "/ChemicalPDF/test.pdf");
        // 判断是否存在  //此处逻辑应该更改。
        if (!file.exists()) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/ChemicalPDF");
            //先创建目录
            dir.mkdir();
            try {
                //再创建文件
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = asset.open("test.pdf");
                fos = new FileOutputStream(file);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.i("PDF", "文件大小" + file.length());
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void importDB() {

        InputStream is = null;
        FileOutputStream fos = null;
        int size = -1;
        File file = new File("/data/data/com.guoyaohua.chemicals/databases", MyDatabaseHelper.DATABASENAME);
        try {
             is = asset.open(MyDatabaseHelper.DATABASENAME);
            fos= new FileOutputStream(file);
            size = is.available();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (file.length()!= size) {
            Log.i("import_db", "文件大小:" + file.length());
            Log.i("import_db", "size:"+size);

            // 使用AssetManager类来访问assets文件夹
//            AssetManager asset = getAssets();

            try {
//                is = asset.open(MyDatabaseHelper.DATABASENAME);
//                fos = new FileOutputStream(file);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.i("import_db", "文件大小" + file.length());
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.i("import_db", "数据库文件已导入");
        }
    }
}
