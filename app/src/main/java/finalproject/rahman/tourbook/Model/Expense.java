package finalproject.rahman.tourbook.Model;

/**
 * Created by rahma on 1/12/2017.
 */

public class Expense {
    private String expenseName;
    private int expense;

    public Expense(String expenseName, int expense) {

        this.expenseName = expenseName;
        this.expense = expense;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }


}
