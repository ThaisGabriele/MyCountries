package u.icomp.mycountries.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import u.icomp.mycountries.Model.Country;

public class Operations {

    private SQLHelper helper;
    private SQLiteDatabase db;

    public Operations(Context ctx){
        helper = new SQLHelper(ctx);
    }

    public void insertAll(Country c){
        db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.COL_NAME, c.name);
        cv.put(SQLHelper.COL_CAPITAL, c.capital);
        cv.put(SQLHelper.COL_REGION, c.region);
        cv.put(SQLHelper.COL_SUB_REGION, c.subregion);
        long id = db.insert(SQLHelper.COUNTRIES_TABLE, null, cv);
        if(id != -1){

        }
        db.close();
    }

    public void deleteAll(){
        db = helper.getWritableDatabase();
        db.delete(SQLHelper.COUNTRIES_TABLE, null, null);
        db.close();
    }

    public List<Country> listCountries() {
        String sql = "SELECT * FROM " + SQLHelper.COUNTRIES_TABLE;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Country> list = new ArrayList();

        while (cursor.moveToNext()) {

            String name = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COL_NAME)
            );
            String capital = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COL_CAPITAL)
            );
            String region = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COL_REGION)
            );
            String subregion = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COL_SUB_REGION)
            );


            Country c = new Country(name, capital, region, subregion);
            list.add(c);
        }

        cursor.close();
        return list;
    }
}
