package finalproject.rahman.tourbook.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import finalproject.rahman.tourbook.Controls.ErrorChecker;
import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Fragments.EventDetailFragment;
import finalproject.rahman.tourbook.R;

import static android.view.View.GONE;

/**
 * Created by rahma on 1/11/2017.
 */

public class AddNewExpenseDialog {
    public ErrorChecker errorchecker;
    private Context context;
    private Dialog dialog;
    private EditText expensetNameEt;
    private EditText expenseBudgetEt;
    private TextView addExpenseTv;
    private TextView addBudgetTv;

    private DatabaseManager databaseManager;

    private boolean updateFlagExpense;
    private boolean insertFlagHistory;

    private AddMoreBudgetDialog addBudgetdialog;

    public AddNewExpenseDialog(Context context)
    {
        this.context=context;
        //errorchecker=new ErrorChecker(context);
    }

    public void addNewExpenseDialog(final String eventName, final int budgetLeft, final int totalBudget, final int budgetSpent)
    {
        databaseManager = new DatabaseManager(context);
        dialog=new Dialog(this.context);
        addBudgetdialog=new AddMoreBudgetDialog(context);
        dialog.setContentView(R.layout.dialog_add_new_expense);
        expensetNameEt=(EditText)dialog.findViewById(R.id.dialogaddnewexpense_pick_expense_name_et);
        expenseBudgetEt=(EditText)dialog.findViewById(R.id.dialogaddnewexpense_pick_expense_et);
        dialog.setTitle("Add New Expense");
        addExpenseTv=(TextView) dialog.findViewById(R.id.dialogaddnewexpense_select_expense_tv);

        addExpenseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorchecker=new ErrorChecker(context);
                if(errorchecker.setBlankError(expenseBudgetEt)
                        |errorchecker.setBlankError(expensetNameEt))
                {

                }
                else
                {
                    int approcahedExpense=(int)Integer.valueOf(expenseBudgetEt.getText().toString());
                    if(approcahedExpense<=budgetLeft)
                    {
                        String expenseName=expensetNameEt.getText().toString();
                        int newBudgetleft=budgetLeft-approcahedExpense;
                        int newBudgetSpent=budgetSpent+approcahedExpense;
                        try
                        {
                            updateFlagExpense=databaseManager.updateBudgetInfo(eventName,newBudgetleft,newBudgetSpent);
                            // .makeText(context, "ADD new expense : "+updateFlagExpense+"", Toast.LENGTH_SHORT).show();
                            if(updateFlagExpense)
                            {
                                insertFlagHistory=databaseManager.insertBudgetHistory(eventName,expenseName,approcahedExpense);
                                //Toast.makeText(context, "History : "+insertFlagHistory+"", Toast.LENGTH_SHORT).show();

                            }
                            if((updateFlagExpense)&&(insertFlagHistory))
                            {
                                EventDetailFragment.setBudgetUpdates(totalBudget,newBudgetleft,newBudgetSpent);
                            }
                            else
                            {
                                Toast.makeText(context,"Not updated",Toast.LENGTH_LONG).show();
                            }


                            //Toast.makeText(context, insertFlagHistory+"", Toast.LENGTH_SHORT).show();
                            //EventDetailFragment.setBudgetStatus();
                        }catch(Exception e)
                        {
                            Log.d("ADD EXPENSE FAILED",e.getMessage());
                          //  Toast.makeText(context, "Failed adding expense", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                    else
                    {
                        expenseBudgetEt.setError("Low Budget");
                        addExpenseTv.setVisibility(View.GONE);
                        addBudgetTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        addBudgetTv=(TextView)dialog.findViewById(R.id.dialogaddnewexpense_add_budget_tv);
        addBudgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBudgetdialog.addMoreBudgetDialog(eventName,totalBudget,budgetLeft,budgetSpent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
