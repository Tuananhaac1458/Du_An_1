package com.example.admin.du_an_1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.du_an_1.Repository.SQLiteHelper;
import com.example.admin.du_an_1.Repository.Ticket;


import java.util.ArrayList;

public class daoTicket {
    private SQLiteDatabase db;
    private static daoTicket instance;

    public daoTicket(Context context) {
        SQLiteHelper sqlHelper = new SQLiteHelper(context);
        db = sqlHelper.getWritableDatabase();
    }
    // GET ONE ITEM
    //
    //
    public ArrayList<Ticket> getDataModels(String sql, String... selectionArgs) {
        ArrayList<Ticket> result = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        Ticket temp;
        while (c.moveToNext()) {
            temp = new Ticket();
            temp.setId(c.getString(c.getColumnIndex(SQLiteHelper.TICKET_ID)));
            temp.setType(c.getInt((c.getColumnIndex(SQLiteHelper.TICKET_TYPE)))> 0 );
            temp.setproductName(c.getString(c.getColumnIndex(SQLiteHelper.TICKET_PRODUCTNAME)));
            temp.setQuantity(c.getInt(c.getColumnIndex(SQLiteHelper.TICKET_QUANTITY)));
            temp.setDate(c.getString(c.getColumnIndex(SQLiteHelper.TICKET_DATE)));
            result.add(temp);
        }
        return result;
    }
    //Get All List
    public ArrayList<Ticket> getAllItem() {
        String sql = "SELECT * FROM " + SQLiteHelper.TABLE_TICKET_NAME;
        return getDataModels(sql);
    }
    //get By Id
    public Ticket getById(String Id) {
        String sql = "SELECT * FROM " + SQLiteHelper.TABLE_TICKET_NAME + " WHERE ID=? ";
        ArrayList<Ticket> list = getDataModels(sql, Id);
        return list.get(0);
    }

    // get by name
    public Ticket getByName(String Name) {
        String sql = "SELECT * FROM " + SQLiteHelper.TABLE_TICKET_NAME + " WHERE ProductName=? ";
        ArrayList<Ticket> list = getDataModels(sql, Name);
        return list.get((list.size()-1));
    }

    // Add
    public long insertTicket(Ticket datamodel) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.TICKET_ID, datamodel.getId());
        values.put(SQLiteHelper.TICKET_TYPE, datamodel.getType());
        values.put(SQLiteHelper.TICKET_PRODUCTNAME, datamodel.getproductName());
        values.put(SQLiteHelper.TICKET_QUANTITY, datamodel.getQuantity());
        values.put(SQLiteHelper.TICKET_DATE, datamodel.getDate());
        return db.insert(SQLiteHelper.TABLE_TICKET_NAME, null, values);
    }
    //Update
    public int updateTicket(Ticket datamodel) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.TICKET_ID, datamodel.getId());
        values.put(SQLiteHelper.TICKET_TYPE, datamodel.getType());
        values.put(SQLiteHelper.TICKET_PRODUCTNAME, datamodel.getproductName());
        values.put(SQLiteHelper.TICKET_QUANTITY, datamodel.getQuantity());
        values.put(SQLiteHelper.TICKET_DATE, datamodel.getDate());
        return db.update(SQLiteHelper.TABLE_TICKET_NAME, values, "id=?", new String[]{String.valueOf(datamodel.getId())});
    }
    //Delete by Id
    public int deleteTicket(int id) {
        return db.delete(SQLiteHelper.TABLE_TICKET_NAME, "id=?", new String[]{String.valueOf(id)});
    }
    ///////////////////////////////////////////////////
    // CREATE INSTANCE
    public static daoTicket getInstance(Context context) {
        if (instance == null) {
            instance = new daoTicket(context);
        }
        return instance;
    }

}
