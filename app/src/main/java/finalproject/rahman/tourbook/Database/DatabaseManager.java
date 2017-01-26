package finalproject.rahman.tourbook.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rahma on 1/6/2017.
 */

public class DatabaseManager {
    private Context context;
    private SQLiteDatabase database;
    private DatabaseOpener databaseOpener;
    private ContentValues contentValues;
    private Cursor cursor;

    private String userName;
    private String userPass;
    private String userMail;
    private String userPhone;

    private long insertFlag;
    private int updateFlag;

    private String eventName;
    private String startPlace;
    private String destination;
    private int startDateDay;
    private int startDateMonth;
    private int startDateYear;
    private int startTimeHour;
    private int startTimeMin;
    private int budget;
    private int budgetLeft;
    private int budgetSpent;
    private String startTimeMillis;
    private String  createdOn;


    private int deleteFlag;

    public DatabaseManager(Context context) {
        this.context=context;
        databaseOpener=new DatabaseOpener(this.context);
    }
    private SQLiteDatabase openDatabaseWrite()
    {
        return databaseOpener.getWritableDatabase();
    }

    private SQLiteDatabase openDatabaseRead()
    {
        return databaseOpener.getReadableDatabase();
    }

    public void closeDatabase()
    {
        databaseOpener.close();
    }

    public boolean insertUserInfo(Bundle bundle)
    {
        database=openDatabaseWrite();
        contentValues=new ContentValues();

        userName=bundle.getString(Constants.USER_NAME);
        userPass=bundle.getString(Constants.PASSWORD);
        userMail=bundle.getString(Constants.EMAIL);
        userPhone=bundle.getString(Constants.PHONE);

        contentValues.put(Constants.USER_NAME,userName);
        contentValues.put(Constants.PASSWORD,userPass);
        contentValues.put(Constants.EMAIL,userMail);
        contentValues.put(Constants.PHONE,userPhone);

        insertFlag=database.insertOrThrow(Constants.TABLE_USER_INFOS,null,contentValues);
        closeDatabase();

        return (insertFlag>0);
    }
    public Cursor getAllUserInfos()
    {
        database=openDatabaseRead();
        cursor=database.query(Constants.TABLE_USER_INFOS,null,null,null,null,null,null);
        //closeDatabase();
        return cursor;
    }

    public boolean insertEventInfo(Bundle bundle)
    {
        database=openDatabaseWrite();
        contentValues=new ContentValues();

        userName=bundle.getString(Constants.USER_NAME);
        eventName=bundle.getString(Constants.EVENT_NAME);
        startPlace=bundle.getString(Constants.START_LOCATION);
        destination=bundle.getString(Constants.DESTINATION);
        startDateDay=bundle.getInt(Constants.START_DATE_DAY);
        startDateMonth=bundle.getInt(Constants.START_DATE_MONTH);
        startDateYear=bundle.getInt(Constants.START_DATE_YEAR);
        startTimeHour=bundle.getInt(Constants.START_TIME_HOUR);
        startTimeMin=bundle.getInt(Constants.START_TIME_MIN);
        budget=bundle.getInt(Constants.BUDGET);
        startTimeMillis=bundle.getString(Constants.STRAT_TIME_MILLIS);
        createdOn=bundle.getString(Constants.CREATED_ON);


        contentValues.put(Constants.USER_NAME,userName);
        contentValues.put(Constants.EVENT_NAME,eventName);
        contentValues.put(Constants.START_LOCATION,startPlace);
        contentValues.put(Constants.DESTINATION,destination);
        contentValues.put(Constants.START_DATE_DAY,startDateDay);
        contentValues.put(Constants.START_DATE_MONTH,startDateMonth);
        contentValues.put(Constants.START_DATE_YEAR,startDateYear);
        contentValues.put(Constants.START_TIME_HOUR,startTimeHour);
        contentValues.put(Constants.START_TIME_MIN,startTimeMin);
        contentValues.put(Constants.BUDGET,budget);
        contentValues.put(Constants.BUDGET_LEFT,budget);
        contentValues.put(Constants.BUDGET_SPENT,0);
        contentValues.put(Constants.STRAT_TIME_MILLIS,startTimeMillis);
        contentValues.put(Constants.CREATED_ON,createdOn);


        insertFlag=database.insertOrThrow(Constants.TABLE_EVENT_INFOS,null,contentValues);
        closeDatabase();

        return (insertFlag>0);
    }

    public Cursor getEventInfos(String userName)
    {
        String selection=Constants.USER_NAME+"=?";
        String[] arguments={userName};
        database=openDatabaseRead();
        cursor=database.query(Constants.TABLE_EVENT_INFOS,null,selection,arguments,null,null,null);
        //closeDatabase();
        return cursor;
    }

    public Cursor getEventDeatailInfos(String eventName)
    {
        String selection=Constants.EVENT_NAME+"=?";
        String[] arguments={eventName};
        database=openDatabaseRead();
        cursor=database.query(Constants.TABLE_EVENT_INFOS,null,selection,arguments,null,null,null);
        Toast.makeText(context, cursor.getCount()+"", Toast.LENGTH_SHORT).show();
        //closeDatabase();
        return cursor;
    }

    public Cursor getBudgetInfo(String eventName)
    {
        String [] projections = {Constants.BUDGET,Constants.BUDGET_LEFT,Constants.BUDGET_SPENT};
        String selection=Constants.EVENT_NAME+"=?";
        String[] arguments={eventName};
        database=openDatabaseRead();
        cursor=database.query(Constants.TABLE_EVENT_INFOS,projections,selection,arguments,null,null,null);
        //closeDatabase();
        return cursor;
    }

    public boolean updateBudgetInfo(String eventName,int budgetLeft,int budgetSpent)
    {
        String where =Constants.EVENT_NAME+"=?" ;
        String[] arguments={eventName};

        database=openDatabaseWrite();
        contentValues=new ContentValues();
        contentValues.put(Constants.BUDGET_LEFT,budgetLeft);
        contentValues.put(Constants.BUDGET_SPENT,budgetSpent);
        updateFlag=database.update(Constants.TABLE_EVENT_INFOS,contentValues,where,arguments);
        closeDatabase();

        return (updateFlag>0);
    }

    public boolean updateNewBudget(String eventName,int newBudget,int newBudgetLeft)
    {
        String where =Constants.EVENT_NAME+"=?" ;
        String[] arguments={eventName};

        database=openDatabaseWrite();
        contentValues=new ContentValues();

        contentValues.put(Constants.BUDGET,newBudget);
        contentValues.put(Constants.BUDGET_LEFT,newBudgetLeft);

        updateFlag=database.update(Constants.TABLE_EVENT_INFOS,contentValues,where,arguments);
        closeDatabase();

        return (updateFlag>0);
    }

    public boolean insertBudgetHistory(String eventName,String expenseName,int expenseAmount)
    {

        database=openDatabaseWrite();
        contentValues=new ContentValues();

        contentValues.put(Constants.EVENT_NAME,eventName);
        contentValues.put(Constants.EXPENSE_NAME,expenseName);
        contentValues.put(Constants.EXPENSE_AMOUNT,expenseAmount);
       // Toast.makeText(context, "Exp name: " +expenseName, Toast.LENGTH_SHORT).show();

        insertFlag=database.insertOrThrow(Constants.TABLE_EVENT_EXPENSE_HISTORY,null,contentValues);
        closeDatabase();

        return (insertFlag>0);
    }

    public Cursor getBudgetHistoryInfo(String eventName)
    {
        String [] projections = {Constants.EXPENSE_NAME,Constants.EXPENSE_AMOUNT};
        String selection=Constants.EVENT_NAME+"=?";
        String[] arguments={eventName};
        database=openDatabaseRead();
        cursor=database.query(Constants.TABLE_EVENT_EXPENSE_HISTORY,projections,selection,arguments,null,null,null);
        //closeDatabase();
        return cursor;

    }
    public boolean deleteEvent(String eventName)
    {
        String where =Constants.EVENT_NAME+"=?" ;
        String[] arguments={eventName};

        database=openDatabaseWrite();
        deleteFlag=database.delete(Constants.TABLE_EVENT_INFOS,where,arguments);
        database.close();
        Toast.makeText(context, "Deleted Event :"+deleteFlag, Toast.LENGTH_SHORT).show();


        return (deleteFlag>0);
    }
    public boolean deleteExpenseHistory(String eventName)
    {
        String where =Constants.EVENT_NAME+"=?" ;
        String[] arguments={eventName};

        database=openDatabaseWrite();
        deleteFlag=database.delete(Constants.TABLE_EVENT_EXPENSE_HISTORY,where,arguments);
        database.close();
        Toast.makeText(context, "Deleted Expense History :"+deleteFlag, Toast.LENGTH_SHORT).show();

        return (deleteFlag>0);
    }

    public boolean updateEventInfo(String eventNameProvided,Bundle bundle)
    {
        database=openDatabaseWrite();
        contentValues=new ContentValues();

        //userName=bundle.getString(Constants.USER_NAME);
        String eventName = bundle.getString(Constants.EVENT_NAME);
        startPlace=bundle.getString(Constants.START_LOCATION);
        destination=bundle.getString(Constants.DESTINATION);
        startDateDay=bundle.getInt(Constants.START_DATE_DAY);
        startDateMonth=bundle.getInt(Constants.START_DATE_MONTH);
        startDateYear=bundle.getInt(Constants.START_DATE_YEAR);
        startTimeHour=bundle.getInt(Constants.START_TIME_HOUR);
        startTimeMin=bundle.getInt(Constants.START_TIME_MIN);
        budget=bundle.getInt(Constants.BUDGET);
        budgetLeft=bundle.getInt(Constants.BUDGET_LEFT);
        budgetSpent=bundle.getInt(Constants.BUDGET_SPENT);
        startTimeMillis=bundle.getString(Constants.STRAT_TIME_MILLIS);

       // contentValues.put(Constants.USER_NAME,userName);
        contentValues.put(Constants.EVENT_NAME,eventName);
        contentValues.put(Constants.START_LOCATION,startPlace);
        contentValues.put(Constants.DESTINATION,destination);
        contentValues.put(Constants.START_DATE_DAY,startDateDay);
        contentValues.put(Constants.START_DATE_MONTH,startDateMonth);
        contentValues.put(Constants.START_DATE_YEAR,startDateYear);
        contentValues.put(Constants.START_TIME_HOUR,startTimeHour);
        contentValues.put(Constants.START_TIME_MIN,startTimeMin);
        contentValues.put(Constants.BUDGET,budget);
        contentValues.put(Constants.BUDGET_LEFT,budgetLeft);
        contentValues.put(Constants.BUDGET_SPENT,budgetSpent);
        contentValues.put(Constants.STRAT_TIME_MILLIS,startTimeMillis);

        String where=Constants.EVENT_NAME+"=?";
        String[] arguments={eventNameProvided};
        updateFlag=database.update(Constants.TABLE_EVENT_INFOS,contentValues,where,arguments);
        closeDatabase();

        return (updateFlag>0);
    }

}
