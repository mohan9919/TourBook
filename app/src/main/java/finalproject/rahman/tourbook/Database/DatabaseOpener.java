package finalproject.rahman.tourbook.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rahma on 1/6/2017.
 */

public class DatabaseOpener extends SQLiteOpenHelper {
    public DatabaseOpener(Context context) {
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Constants.CREATE_USER_INFO_TABLE);
            Log.d(Constants.TABLE_USER_INFOS,"CREATED");

        }catch (Exception e)
        {
            Log.d(Constants.TABLE_USER_INFOS,e.getMessage());
        }
        try
        {
            db.execSQL(Constants.CREATE_EVENT_INFO_TABLE);
            Log.d(Constants.TABLE_EVENT_INFOS,"CREATED");

        }catch (Exception e)
        {
            Log.d(Constants.TABLE_EVENT_INFOS,e.getMessage());

        }
        try
        {
            db.execSQL(Constants.CREATE_EVENT_EXPENSE_HISTORY_TABLE);
            Log.d(Constants.TABLE_EVENT_EXPENSE_HISTORY,"CREATED");

        }catch (Exception e)
        {
            Log.d(Constants.TABLE_EVENT_EXPENSE_HISTORY,e.getMessage());

        }




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
