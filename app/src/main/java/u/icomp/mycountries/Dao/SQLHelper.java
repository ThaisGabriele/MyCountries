package u.icomp.mycountries.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String BD_NAME = "dbCountry";
    private static final int BD_VERSION = 1;
    public static final String COUNTRIES_TABLE = "countries_table";
    public static final String COL_NAME = "name";
    public static final String COL_CAPITAL = "capital";
    public static final String COL_REGION = "region";
    public static final String COL_SUB_REGION = "subregion";



    public SQLHelper(Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + COUNTRIES_TABLE + " ( " +
                        COL_NAME + " TEXT, " +
                        COL_CAPITAL + " TEXT, " +
                        COL_REGION + " TEXT, " +
                        COL_SUB_REGION + " TEXT)"
        );

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // para as próximas versões
    }
}
