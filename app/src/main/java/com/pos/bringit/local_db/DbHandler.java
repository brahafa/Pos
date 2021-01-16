package com.pos.bringit.local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.pos.bringit.models.OrderModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tutlane on 06-01-2018.
 */


public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ordersdb";
    private static final String TABLE_Orders = "orderdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_BUSINESS_ID = "business_id";
    private static final String KEY_ACTION_TIME = "action_time";
    private static final String KEY_ORDER_TIME = "order_time";
    private static final String KEY_ADDED_BY = "added_by";
    private static final String KEY_IS_DELIVERY = "is_delivery";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TOTAL_PAID = "total_paid";
    private static final String KEY_IS_PAID = "is_paid";
    private static final String KEY_POSITION = "position";
    private static final String KEY_CHANGE_TYPE = "change_type";
    private static final String KEY_TABLE_ID = "table_id";
    private static final String KEY_DELIVERY_OPTION = "delivery_option";
    private static final String KEY_COLOR = "color";
    private static final String KEY_COOKING_TIME = "cooking_time";
    private static final String KEY_CHANGE_FOR_ORDER_ID = "change_for_order_id";
    private static final String KEY_IS_CANCELED = "is_canceled";
    private static final String KEY_IS_CHANGED = "is_changed";
    private static final String KEY_IS_CHANGE_CONFIRMED = "is_change_confirmed";
    private static final String KEY_TABLE_IS_ACTIVE = "table_is_active";
    private static final String KEY_CLIENT = "client";
    private static final String KEY_ADDED_BY_SYSTEM = "added_by_system";
    private static final String KEY_START_TIME_STR = "startTimeStr";
    private static final String KEY_ORDER_DATA = "order_data";

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Orders + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BUSINESS_ID + " TEXT,"
               // + KEY_BUSINESS_ID + " INTEGER,"
                + KEY_ACTION_TIME + " INTEGER,"
                + KEY_ORDER_TIME + " TEXT,"
                + KEY_ADDED_BY + " TEXT,"
                + KEY_IS_DELIVERY + " BOOLEAN,"
                + KEY_STATUS + " TEXT,"
                + KEY_TOTAL_PAID + " INTEGER,"
                + KEY_IS_PAID + " BOOLEAN,"
                + KEY_POSITION + " INTEGER,"
                + KEY_CHANGE_TYPE + " TEXT,"
                + KEY_TABLE_ID + " TEXT,"
                + KEY_DELIVERY_OPTION + " TEXT,"
                + KEY_COLOR + " TEXT,"
                + KEY_COOKING_TIME + " INTEGER,"
              //  + KEY_CHANGE_FOR_ORDER_ID + " TEXT,"
                + KEY_IS_CANCELED + " BOOLEAN,"
                + KEY_IS_CHANGED + " BOOLEAN,"
                + KEY_IS_CHANGE_CONFIRMED + " TEXT,"
                + KEY_TABLE_IS_ACTIVE + " TEXT,"
                + KEY_CLIENT + " TEXT,"
                + KEY_ADDED_BY_SYSTEM + " TEXT,"
                + KEY_START_TIME_STR + " TEXT,"
                + KEY_ORDER_DATA + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Orders);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //
    // Adding new User Details
    public void insertOrderDetails(OrderModel orderModel) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ID, orderModel.getId());
        cValues.put(KEY_BUSINESS_ID, orderModel.getBusinessId());
        cValues.put(KEY_ACTION_TIME, orderModel.getActionTime());
        cValues.put(KEY_ORDER_TIME, orderModel.getOrderTime());
        cValues.put(KEY_ADDED_BY, orderModel.getAddedBySystem());
        cValues.put(KEY_IS_DELIVERY, orderModel.isDelivery());
        cValues.put(KEY_STATUS, orderModel.getStatus());
        cValues.put(KEY_TOTAL_PAID, orderModel.getTotalPaid());
        cValues.put(KEY_IS_PAID, orderModel.getIsPaid());
        cValues.put(KEY_POSITION, orderModel.getPosition());
        cValues.put(KEY_CHANGE_TYPE, orderModel.getChangeType());
        cValues.put(KEY_TABLE_ID, orderModel.getTableId());
        cValues.put(KEY_DELIVERY_OPTION, orderModel.getDeliveryOption());
        cValues.put(KEY_COLOR, orderModel.getColor());
        cValues.put(KEY_COOKING_TIME, orderModel.getCookingTime());
       // cValues.put(KEY_CHANGE_FOR_ORDER_ID, orderModel.getC());
        cValues.put(KEY_IS_CANCELED, orderModel.getIsCanceled());
        cValues.put(KEY_IS_CHANGED, orderModel.isHasChanges());
        cValues.put(KEY_IS_CHANGE_CONFIRMED, "");
        cValues.put(KEY_TABLE_IS_ACTIVE, orderModel.isTableIsActive());
        Gson gson = new Gson();
        try {
            JSONObject userInfo = new JSONObject(gson.toJson(orderModel.getClient()));
            cValues.put(KEY_CLIENT, userInfo.toString());///???????

        } catch (JSONException e) {
            e.printStackTrace();
        }

        cValues.put(KEY_ADDED_BY_SYSTEM, orderModel.getAddedBySystem());
        cValues.put(KEY_START_TIME_STR, orderModel.getStartTimeStr());
       //cValues.put(KEY_ORDER_DATA, orderModel.getProductItemModel());//???????????
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Orders, null, cValues);
        db.close();
    }

    // Get User Details
    public List<OrderModel> GetOrders() {
        List<OrderModel> orderModelList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_Orders;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            OrderModel orderModel = new OrderModel();
            orderModel.setColor(cursor.getString(cursor.getColumnIndex(KEY_COLOR)));
            orderModel.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
            orderModel.setBusinessId(cursor.getString(cursor.getColumnIndex(KEY_BUSINESS_ID)));
            orderModel.setActionTime(cursor.getInt(cursor.getColumnIndex(KEY_ACTION_TIME)));
            orderModel.setAddedBySystem(cursor.getString(cursor.getColumnIndex(KEY_ADDED_BY)));
            orderModel.setIsDelivery(cursor.getInt(cursor.getColumnIndex(KEY_IS_DELIVERY)) == 1);
            orderModel.setStatus(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
            orderModel.setTotalPaid(cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_PAID)));
            orderModel.setIsPaid(cursor.getInt(cursor.getColumnIndex(KEY_IS_PAID)));
            orderModel.setPosition(cursor.getInt(cursor.getColumnIndex(KEY_POSITION)));
           // orderModel.setChangeType(cursor.getInt(cursor.getColumnIndex(KEY_CHANGE_TYPE)));
            orderModel.setTableId(cursor.getString(cursor.getColumnIndex(KEY_TABLE_ID)));
            orderModel.setDeliveryOption(cursor.getString(cursor.getColumnIndex(KEY_DELIVERY_OPTION)));
            orderModel.setCookingTime(cursor.getInt(cursor.getColumnIndex(KEY_COOKING_TIME)));
            // cValues.put(KEY_CHANGE_FOR_ORDER_ID, orderModel.getC());
            orderModel.setIsCanceled(cursor.getInt(cursor.getColumnIndex(KEY_IS_CANCELED)) == 1);
            orderModel.setHasChanges(cursor.getInt(cursor.getColumnIndex(KEY_IS_CHANGED)) == 1);
            orderModel.setTableIsActive(cursor.getString(cursor.getColumnIndex(KEY_TABLE_IS_ACTIVE)));
            //orderModel.setChangeConfirmed(cursor.getInt(cursor.getColumnIndex(KEY_IS_CHANGE_CONFIRMED)) == 1);
            orderModelList.add(orderModel);
        }
        return orderModelList;
    }

//    // Get User Details based on userid
//    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
//        String query = "SELECT name, location, designation FROM " + TABLE_Users;
//        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_LOC, KEY_DESG}, KEY_ID + "=?", new String[]{String.valueOf(userid)}, null, null, null, null);
//        if (cursor.moveToNext()) {
//            HashMap<String, String> user = new HashMap<>();
//            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
//            user.put("designation", cursor.getString(cursor.getColumnIndex(KEY_DESG)));
//            user.put("location", cursor.getString(cursor.getColumnIndex(KEY_LOC)));
//            userList.add(user);
//        }
//        return userList;
//    }

    // Delete User Details
    public void DeleteUser(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Orders, KEY_ID + " = ?", new String[]{String.valueOf(userid)});
        db.close();
    }

//    // Update User Details
//    public int UpdateUserDetails(String location, String designation, int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cVals = new ContentValues();
//        cVals.put(KEY_LOC, location);
//        cVals.put(KEY_DESG, designation);
//        int count = db.update(TABLE_Users, cVals, KEY_ID + " = ?", new String[]{String.valueOf(id)});
//        return count;
//    }
}