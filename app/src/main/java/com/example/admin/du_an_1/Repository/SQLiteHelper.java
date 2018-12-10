package com.example.admin.du_an_1.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static int DATABASE_VER= 1;
    public static String DATABASE_NAME ="MY_DATA_BASE";
    //
    //
    // USERS TABLE
    public static String TABLE_USER_NAME ="TABLE_USER";
    public static String USER_ID ="Id";
    public static String USER_UN="Username";
    public static String USER_PW="Password";
    //
    //
    // Product TABLE
    public static String TABLE_PRODUCT_NAME ="TABLE_PRODUCT";
    public static String PRODUCT_ID ="Id";
    public static String PRODUCT_NAME="Name";
    public static String PRODUCT_CODE="Code";
    public static String PRODUCT_CATEGORY="Category";
    //
    //
    // Category TABLE
    public static String TABLE_CATEGORY_NAME ="TABLE_CATEGORY";
    public static String CATEGORY_ID ="Id";
    public static String CATEGORY_NAME="Name";
    //
    //
    // Ticket TABLE
    public static String TABLE_TICKET_NAME ="TABLE_TICKET";
    public static String TICKET_ID="Id";
    public static String TICKET_DATE="Date";
    public static String TICKET_TYPE="Type";
    public static String TICKET_PRODUCTNAME="ProductName";
    public static String TICKET_QUANTITY="Quantity";

    /////////////////////////////////////////
    ////////////////////////////////////////
    //// QUERY TABLE
    ///////////////////////////////////////
    // TABLE USER
    /////////////////////////////////////
    public String CREATE_TABLE_USER= "CREATE TABLE " + TABLE_USER_NAME+" ( "+
                  USER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                  USER_UN+ " TEXT, "+
                  USER_PW+ " TEXT )";
    /////////////////////////////////////
    // TABLE PRODUCT
    /////////////////////////////////////
    public String CREATE_TABLE_PRODUCT= "CREATE TABLE " + TABLE_PRODUCT_NAME+" ( "+
            PRODUCT_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            PRODUCT_CODE+ " TEXT, "+
            PRODUCT_CATEGORY+ " TEXT, "+
            PRODUCT_NAME+ " TEXT )";
    /////////////////////////////////////
    // TABLE CATEGORY
    /////////////////////////////////////
    public String CREATE_TABLE_CATEGORY= "CREATE TABLE " + TABLE_CATEGORY_NAME+" ( "+
            CATEGORY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CATEGORY_NAME+ " TEXT )";
    /////////////////////////////////////
    // TABLE TICKET
    /////////////////////////////////////
    public String CREATE_TABLE_TICKET= "CREATE TABLE " + TABLE_TICKET_NAME+" ( "+
            TICKET_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            TICKET_DATE+ " DATE, "+
            TICKET_TYPE+ " BOOLEAN, "+
            TICKET_PRODUCTNAME+" TEXT, "+
            TICKET_QUANTITY+" INTEGER )";
    ///////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////
    public SQLiteHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_TICKET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
