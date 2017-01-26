package finalproject.rahman.tourbook.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import finalproject.rahman.tourbook.Adapters.ExpenseHistoryListViewAdapater;
import finalproject.rahman.tourbook.Fragments.EventDetailFragment;
import finalproject.rahman.tourbook.Model.Expense;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/12/2017.
 */

public class ShowAllExpenseHistoryDialog {
    private Context context;
    private Dialog dialog;
    private ListView expenseListLv;
    private TextView okayTv;
    private ExpenseHistoryListViewAdapater adapater;

    public ShowAllExpenseHistoryDialog(Context context) {
        this.context=context;

    }

    public void showAllExpenseHistoryDialog(ArrayList<Expense> expenseList)
    {
        dialog=new Dialog(this.context);
        dialog.setContentView(R.layout.dialog_show_all_expense_history);

        expenseListLv=(ListView) dialog.findViewById(R.id.dialogshowallevent_budget_history_list_lv);

        adapater=new ExpenseHistoryListViewAdapater(context,expenseList);
        //expenseListLv.setAdapter(null);
        expenseListLv.setAdapter(adapater);



        okayTv=(TextView) dialog.findViewById(R.id.dialogshowallevent_okay_tv);
        dialog.setTitle("Expense History");
        okayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // EventDetailFragment.expenseList.clear();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
