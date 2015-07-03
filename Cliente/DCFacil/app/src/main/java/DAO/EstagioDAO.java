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

import Modelo.Estagio;
import Modelo.Noticia;

/**
 * Created by jonathan on 27/06/15.
 */
public class EstagioDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dataBaseEstagio.db";
    public static final int DATABASE_VERSION = 1;

    public EstagioDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public EstagioDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table estagio (");
        sql.append("id integer primary key autoincrement,");
        sql.append("local text,");
        sql.append("descricao text,");
        sql.append("contato text)");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("drop table if exists estagio");
        onCreate(db);
    }

    public void deletarTudo(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM estagio");
    }

    public void create(Estagio estagio) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("local", estagio.getLocal());
        contentValues.put("descricao", estagio.getDescricao());
        contentValues.put("contato", estagio.getContato());
        long id = db.insert("estagio", null, contentValues);
        Log.v("SQLite", "create id = " + id);
    }

    public Estagio retrieve(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from estagio where id = ?", new String[] { Integer.toString(id) });
        Estagio estagio = null;
        if (result != null && result.getCount() > 0) {
            estagio = new Estagio();
            estagio.setId(result.getInt(0));
            estagio.setLocal(result.getString(1));
            estagio.setDescricao(result.getString(2));
            estagio.setContato(result.getString(3));
        }
        return estagio;
    }

    /*public Noticia procurarPorTitulo(String titulo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from noticia where titulo = ?", new String[] { titulo });
        Noticia noticia = null;
        if (result != null && result.getCount() > 0) {
            result.moveToNext();
            noticia = new Noticia();
            noticia.setId(result.getInt(0));
            noticia.setTitulo(result.getString(1));
            noticia.setLink(result.getString(2));
            noticia.setData(result.getString(3));
        }
        return noticia;
    }*/

    public void update(Estagio estagio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("local", estagio.getLocal());
        contentValues.put("descricao", estagio.getDescricao());
        contentValues.put("contato", estagio.getContato());
        db.update("estagio", contentValues, " id = ? ", new String[]{Integer.toString(estagio.getId())});
    }

    public void delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("estagio", " id = ? ", new String[] { Integer.toString(id) });
    }

    public List<Estagio> list() {
        List<Estagio> lista = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from estagio order by id", null);
        if (result != null && result.getCount() > 0) {
            lista = new ArrayList<Estagio>();
            result.moveToFirst();
            while (result.isAfterLast() == false) {
                Estagio estagio = new Estagio();
                estagio.setId(result.getInt(0));
                estagio.setLocal(result.getString(1));
                estagio.setDescricao(result.getString(2));
                estagio.setContato(result.getString(3));
                lista.add(estagio);
                result.moveToNext();
            }
        }
        return lista;
    }
}
