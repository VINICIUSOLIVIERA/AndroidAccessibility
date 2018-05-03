package production.tcc.android.androidaccessibility.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import production.tcc.android.androidaccessibility.Config.DataBase;
import production.tcc.android.androidaccessibility.Models.User;

public class DataBaseUtil {

    private DataBase dbconfig;
    private SQLiteDatabase dbconfigw;
    private SQLiteDatabase dbconfigr;

    public DataBaseUtil(Context context) {
        this.dbconfig  = new DataBase(context);
        this.dbconfigw = dbconfig.getWritableDatabase();
        this.dbconfigr = dbconfig.getReadableDatabase();
    }

    public boolean insert(String table, ContentValues values){
        try{
            dbconfigw.insert(table, null, values);
            return true;
        }catch (Exception e){
            Log.d("DATABASEUTIL_INSERT", e.getMessage());
            return false;
        }
    }

    public boolean destroy(String table, String key, String value, String operation){
        try{
            dbconfigw.execSQL("DELETE FROM '"+table+"' WHERE '"+key+"' "+operation+" '"+value+"';");
            return true;
        }catch (Exception e){
            Log.d("DATABASEUTIL_DESTROY", e.getMessage());
            return false;
        }
    }

    public boolean destroyAll(String table){
        try{
            dbconfigw.execSQL("DELETE FROM '"+table+"';");
            return true;
        }catch (Exception e){
            Log.d("DATABASEUTIL_DESTROYALL", e.getMessage());
            return false;
        }
    }

    public boolean insertRaw(String sql) {
        try {
            dbconfigw.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.d("DATABASEUTIL_INSERTRAW", e.getMessage());
            return false;

        }
    }

    public User getUser(){
        try{
            User user = null;
            String sql    = "SELECT * FROM user";
            Cursor values = dbconfigr.rawQuery(sql, null);
            while (values.moveToNext()){
                user = new User(values.getLong(1), values.getString(2), values.getString(3), values.getString(4), values.getString(5), values.getInt(6), values.getInt(7), values.getInt(8), values.getString(9), values.getString(10));
            }
            return user;
        }catch (Exception e){
            Log.d("DATABASEUTIL_GETUSER", e.getMessage());
            return null;
        }
    }

}
