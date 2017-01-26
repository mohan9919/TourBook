package finalproject.rahman.tourbook.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;

import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.R;

//import android.icu.util.Calendar;

/**
 * Created by rahma on 1/6/2017.
 */

public class TimePickerCustomDialog {

    private Context context;
    private Dialog dialog;

    private TimePicker timePicker;
    private int hour;
    private int min;
    private int currentHour;
    private int currentMin;
    public TimePickerCustomDialog(Context context) {
        this.context=context;

    }

    public void timePickerCustomDialog(final EditText editText)
    {

        dialog=new Dialog(this.context);
        dialog.setContentView(R.layout.dialog_time_picker_custom);
        timePicker=(TimePicker) dialog.findViewById(R.id.dialogtimepickercustom_departure_time_dp);
        final TextView selectTime=(TextView)dialog.findViewById(R.id.dialogtimepickercustom_select_time_tv);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(calendar.getTimeInMillis());

                currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                currentMin=calendar.get(Calendar.MINUTE);
                //Toast.makeText(context, ""+view.getCurrentHour(), Toast.LENGTH_SHORT).show();
                hour=hourOfDay;
                min=minute;
            }
        });
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variables.EDIT_TIME_PICKED=true;
                editText.setText(hour+":"+min);
                dialog.dismiss();

            }
        });

        dialog.setTitle("Departure Time");
        dialog.show();
    }

    public int getHour()
   {
       return hour;
   }
    public int getMinute()
    {
        return min;
    }
    public int getCurrentHour()
    {
        return currentHour;
    }
    public int getCurrentMinute()
    {
        return currentMin;
    }
}
