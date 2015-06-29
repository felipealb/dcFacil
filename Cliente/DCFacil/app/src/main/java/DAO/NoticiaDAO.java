package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Modelo.Noticia;

/**
 * Created by jonathan on 27/06/15.
 */
public class NoticiaDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dataBase.db";
    public static final int DATABASE_VERSION = 1;

    public NoticiaDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public NoticiaDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table noticia (");
        sql.append("id integer primary key autoincrement,");
        sql.append("titulo text,");
        sql.append("link text,");
        sql.append("data text)");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("drop table if exists noticia");
        onCreate(db);
    }

    public void create(Noticia noticia) {

        String data;
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
        data = formatar.format(d);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo", noticia.getTitulo());
        contentValues.put("link", noticia.getLink());
        contentValues.put("data", noticia.getData());
        long id = db.insert("noticia", null, contentValues);
        Log.v("SQLite", "create id = " + id);
    }

    public Noticia retrieve(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from noticia where id = ?", new String[] { Integer.toString(id) });
        Noticia noticia = null;
        if (result != null && result.getCount() > 0) {
            noticia = new Noticia();
            noticia.setId(result.getInt(0));
            noticia.setTitulo(result.getString(1));
            noticia.setLink(result.getString(2));
            noticia.setData(result.getString(3));
        }
        return noticia;
    }

    public void update(Noticia noticia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo", noticia.getTitulo());
        contentValues.put("link", noticia.getLink());
        contentValues.put("data", noticia.getData());
        db.update("noticia", contentValues, " id = ? ", new String[]{Integer.toString(noticia.getId())});
    }

    public void delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("noticia", " id = ? ", new String[] { Integer.toString(id) });
    }

    public List<Noticia> list() {
        List<Noticia> lista = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from noticia order by id", null);
        if (result != null && result.getCount() > 0) {
            lista = new ArrayList<Noticia>();
            result.moveToFirst();
            while (result.isAfterLast() == false) {
                Noticia noticia = new Noticia();
                noticia.setId(result.getInt(0));
                noticia.setTitulo(result.getString(1));
                noticia.setLink(result.getString(2));
                noticia.setData(result.getString(3));
                lista.add(noticia);
                result.moveToNext();
            }
        }
        return lista;
    }
}
