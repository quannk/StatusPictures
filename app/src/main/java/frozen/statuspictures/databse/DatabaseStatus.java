package frozen.statuspictures.databse;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import frozen.statuspictures.AppConst;
import frozen.statuspictures.item.ItemStatus;

/**
 * Created by QuanNguy on 01/06/2017.
 */

public class DatabaseStatus {
    private static final String DATABASE_PATH = Environment
            .getDataDirectory().getAbsolutePath()
            + "/data/frozen.statuspictures/";


    private SQLiteDatabase sqLiteDatabase;

    public DatabaseStatus() {
    }

    public void copyDatabase(Context context) {
        try {
            // Mở file để đọc
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("dataFunfact.sqlite");

            // Mở file để ghi
            String path = DATABASE_PATH + AppConst.DATABASE_NAME;
            File file = new File(path);
            if (file.exists()) {
                return;
            }

            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            byte[] bytes = new byte[1024];
            int length = 0; // biến này lưu số lượng byte đọc được

            // Copy dữ liệu
            while ((length = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
            }

            inputStream.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(
                    DATABASE_PATH + AppConst.DATABASE_NAME,
                    null,
                    SQLiteDatabase.OPEN_READONLY
            );
        }
    }

    public void closeDatabase() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }



    public ArrayList<ItemStatus> getAll(String table) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + table
                        + " ORDER BY " + AppConst.COLUMN_CONTENT + " ASC",
                null);
        if (cursor == null) {
            closeDatabase();
            return null;
        }

        if (cursor.getCount() == 0) {
            cursor.close();
            closeDatabase();
            return new ArrayList<>();
        }

        ArrayList<ItemStatus> list = new ArrayList<>();
        cursor.moveToFirst();
        int indexId = cursor.getColumnIndex(AppConst.COLUMN_STATUS_ID);
        int indexContent = cursor.getColumnIndex(AppConst.COLUMN_CONTENT);

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(indexId);
            String content = cursor.getString(indexContent);
            list.add(new ItemStatus(content));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return list;
    }
}
