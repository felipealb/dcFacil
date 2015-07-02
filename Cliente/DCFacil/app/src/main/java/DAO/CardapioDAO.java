package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Modelo.Cardapio;
import Modelo.Noticia;

/**
 * Created by jonathan on 01/07/15.
 */
public class CardapioDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dataBaseCardapio.db";
    public static final int DATABASE_VERSION = 1;

    public CardapioDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public CardapioDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table cardapio (");
        sql.append("id integer primary key autoincrement,");
        sql.append("diaSemana text,");
        sql.append("cafe text,");
        sql.append("almoco text,");
        sql.append("janta text)");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("drop table if exists cardapio");
        onCreate(db);
    }

    public void create(Cardapio cardapio) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("diaSemana", cardapio.getDiaSemana());
        contentValues.put("cafe", cardapio.getCafe());
        contentValues.put("almoco", cardapio.getAlmoco());
        contentValues.put("janta", cardapio.getJanta());
        long id = db.insert("cardapio", null, contentValues);
        Log.v("SQLite", "create id = " + id);
    }

    public Cardapio retrieve(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from cardapio where id = ?", new String[] { Integer.toString(id) });
        Cardapio cardapio = null;
        if (result != null && result.getCount() > 0) {
            cardapio = new Cardapio();
            cardapio.setId(result.getInt(0));
            cardapio.setDiaSemana(result.getString(1));
            cardapio.setCafe(result.getString(2));
            cardapio.setAlmoco(result.getString(3));
            cardapio.setJanta(result.getString(4));
        }
        return cardapio;
    }

    public void update(Cardapio cardapio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("diaSemana", cardapio.getDiaSemana());
        contentValues.put("cafe", cardapio.getCafe());
        contentValues.put("almoco", cardapio.getAlmoco());
        contentValues.put("janta", cardapio.getJanta());
        db.update("cardapio", contentValues, " id = ? ", new String[]{Integer.toString(cardapio.getId())});
    }

    public void delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cardapio", " id = ? ", new String[] { Integer.toString(id) });
    }

    public void deletarTudo(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM cardapio");
    }

    public List<Cardapio> list() {
        List<Cardapio> lista = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from cardapio order by id", null);
        if (result != null && result.getCount() > 0) {
            lista = new ArrayList<Cardapio>();
            result.moveToFirst();
            while (result.isAfterLast() == false) {
                Cardapio cardapio = new Cardapio();
                cardapio.setId(result.getInt(0));
                cardapio.setDiaSemana(result.getString(1));
                cardapio.setCafe(result.getString(2));
                cardapio.setAlmoco(result.getString(3));
                cardapio.setJanta(result.getString(4));
                lista.add(cardapio);
                result.moveToNext();
            }
        }
        return lista;
    }
}
