package finalproject.rahman.tourbook.Dialogs;

import android.app.Dialog;
import android.content.Context;
//import android.icu.util.Calendar;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import java.util.Date;

import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/6/2017.
 */

public class DatePickerCustomDialog {

    Context context;
    Dialog dialog;
    java.util.Calendar calendar;
    private DatePicker datePicker;
    public DatePickerCustomDialog(Context context) {
        this.context=context;

    }

    public void datePickerCustomDialog(final EditText editText)
    {
        dialog=new Dialog(this.context);
        dialog.setContentView(R.layout.dialog_date_picker_custom);
        datePicker=(DatePicker)dialog.findViewById(R.id.dialogdatepickercustom_departure_date_dp);

        calendar = java.util.Calendar.getInstance();
        calendar.set(calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH));
        calendar.getTimeInMillis();
        datePicker.setMinDate((int)calendar.getTimeInMillis());

        //datePicker.setMaxDate((int)System.currentTimeMillis());
        final TextView selectDate=(TextView)dialog.findViewById(R.id.dialogdatepickercustom_select_date_tv);
        dialog.setTitle("Departure Date");

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variables.EDIT_DATE_PICKED=true;
                editText.setText(getDay()+"/"+getMonth()+"/"+getYear());
               //Toast.makeText(context, getCurrentDay()+""+getCurrentMonth()+""+getCurrentYear(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, calendar.getTimeInMillis()+"", Toast.LENGTH_SHORT).show();
               // Toast.makeText(context,"Time:"+calendar.getTimeInMillis()+" Current : "+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public int getDay()
    {
        return datePicker.getDayOfMonth();
    }
    public int getMonth()
    {
        return datePicker.getMonth()+1;
    }
    public int getYear()
    {
        return datePicker.getYear();
    }
    public int getCurrentYear()
    {
        return calendar.get(calendar.YEAR);
    }
    public int getCurrentMonth()
    {
        return calendar.get(calendar.MONTH)+1;
    }
    public int getCurrentDay()
    {
        return calendar.get(calendar.DAY_OF_MONTH);
    }
}
