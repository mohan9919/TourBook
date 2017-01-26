package finalproject.rahman.tourbook.Dialogs;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import finalproject.rahman.tourbook.Controls.ErrorChecker;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Fragments.EventDetailFragment;
import finalproject.rahman.tourbook.Fragments.ShowAllEventsFragment;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/11/2017.
 */

public class AddMoreBudgetDialog {
    public ErrorChecker errorchecker;
    private Context context;
    private Dialog dialog;
    private EditText addBudgetEt;
    private TextView addBudgetTv;
    private Bundle bundle;

    private DatabaseManager databaseManager;

    private boolean updateFlag=false;
    private int newTotalBudget;
    private int newBudgetLeft;

    //private Fragment fragment;

    public static final String ADDED_BUDGET="added_budget";
    public static final String NEW_BUDGET="new_budget";


    public AddMoreBudgetDialog(Context context) {
        this.context=context;
        bundle = new Bundle();
        //errorchecker=new ErrorChecker(context);
    }

    public void addMoreBudgetDialog(final String eventName, final int totalBudget, final int budgetLeft, final int budgetSpent)
    {
        databaseManager = new DatabaseManager(context);
        dialog=new Dialog(this.context);
        dialog.setContentView(R.layout.dialog_add_more_budget);
        addBudgetEt=(EditText)dialog.findViewById(R.id.dialogaddmorebudget_add_budget_et);
        addBudgetTv=(TextView) dialog.findViewById(R.id.dialogaddmorebudget_add_tv);
        dialog.setTitle("Add More Budget");

        addBudgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorchecker=new ErrorChecker(context);
                if(errorchecker.setBlankError(addBudgetEt))
                {

                }
                else
                {
                    int addedBudget=Integer.valueOf(addBudgetEt.getText().toString());
                    newTotalBudget=addedBudget+totalBudget;
                    newBudgetLeft=addedBudget+budgetLeft;
                    try
                    {
                        updateFlag=databaseManager.updateNewBudget(eventName,newTotalBudget,newBudgetLeft);
                       // Toast.makeText(context,"ADD Budget : "+ updateFlag, Toast.LENGTH_SHORT).show();
                        if(updateFlag)
                        {
                            EventDetailFragment.setBudgetUpdates(newTotalBudget,newBudgetLeft,budgetSpent);
                        }
                        else
                        {
                            Toast.makeText(context, "Not updated", Toast.LENGTH_SHORT).show();

                        }

                        //Toast.makeText(context, updateFlag+"", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        Log.d("BUDGET ADDITION FAILED",e.getMessage());
                        //Toast.makeText(context, e.getMessage()+"try", Toast.LENGTH_SHORT).show();
                    }



                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }
    public Bundle getBudgetBundle()
    {
        return bundle;
    }

}
