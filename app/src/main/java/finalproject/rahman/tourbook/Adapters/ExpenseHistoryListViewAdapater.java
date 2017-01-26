package finalproject.rahman.tourbook.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import finalproject.rahman.tourbook.Model.Expense;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/12/2017.
 */

public class ExpenseHistoryListViewAdapater extends ArrayAdapter<Expense>{
    private Context context;
    private ArrayList<Expense> expenseList;
    private TextView expenseNameTv;
    private TextView expenseTv;
    private Expense expense;

    public ExpenseHistoryListViewAdapater(Context context, ArrayList<Expense> expenseList) {
        super(context, R.layout.material_design_expense_list_view_row_layout,expenseList);
        this.context=context;
        this.expenseList=expenseList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.material_design_expense_list_view_row_layout,parent,false);
        expenseNameTv = (TextView)convertView.findViewById(R.id.expenselistviewrowlayout_expense_name_tv);
        expenseTv = (TextView)convertView.findViewById(R.id.expenselistviewrowlayout_expense_tv);

        expense=expenseList.get(position);

        expenseNameTv.setText(expense.getExpenseName());
        expenseTv.setText(String.valueOf(expense.getExpense()));
        return convertView;
    }
}
