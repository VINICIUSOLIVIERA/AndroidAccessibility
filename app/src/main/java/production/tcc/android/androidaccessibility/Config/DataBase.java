package production.tcc.android.androidaccessibility.Config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "accessibility", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_user = "CREATE TABLE user(id INTEGER PRIMARY KEY NOT NULL, user_id INTEGER NOT NULL, name TEXT NOT NULL, user TEXT NOT NULL, password TEXT NOT NULL, date_birth TEXT NOT NULL, gender INTEGER NOT NULL, deficiency INTEGER NOT NULL, cep INTEGER NOT NULL, address TEXT, email TEXT NOT NULL);";
        db.execSQL(create_table_user);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int current_version) {
        db.execSQL("DROP TABLE IF EXISTS user;");
        this.onCreate(db);
    }
}
