package com.example.intership_project.SqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//import com.example.intership_project.order_list_recview.Model_order_list;


public class DBHelper extends SQLiteOpenHelper {
    final static String DBNAME = "new database.db";
    final static int DBVERSION = 1;


    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table orders" +
                "(id integer primary key autoincrement," +
                "name text," +
                "phone text," +
                "price real," +
                "image integer," +
                "description text," +
                "foodname text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists orders");
        //again create database
        onCreate(db);
    }

    public boolean insertOrder(String name, String phone, Double price, int image, String desc, String foodname) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("price", price);
        cv.put("image", image);
        cv.put("description", desc);
        cv.put("foodname", foodname);
        long id = database.insert("orders", null, cv);
        if (id <= 0) {
            return false;
        } else {
            return true;
        }

    }

    //getorders from mysqlite database
//    public  ArrayList<Model_order_list> getorders(){
//        ArrayList<Model_order_list> orders=new ArrayList<>();
//        SQLiteDatabase database=this.getWritableDatabase();
//        Cursor cursor=database.rawQuery("select id,foodname,image,price from  " +
//                " orders",null);
//        if (cursor.moveToNext()) {
//            while (cursor.moveToNext())
//            {
//                Model_order_list model=new Model_order_list();
//                model.setItem_name_orderlist(cursor.getString(1));
//                model.setItem_price_order_list(cursor.getInt(3));
//                model.setOrder_quantity_order_list(cursor.getInt(2));
//                model.setImagename_orderlist(cursor.getInt(0));
//                orders.add(model);
//
//            }
//        }
//        cursor.close();
//        database.close();
//        return  orders;
//    }
}
