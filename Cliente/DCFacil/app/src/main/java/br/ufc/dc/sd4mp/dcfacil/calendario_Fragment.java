package br.ufc.dc.sd4mp.dcfacil;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by jonathan on 03/06/15.
 */
public class calendario_Fragment extends Fragment {
    View rootView;
    CalendarView calendar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        rootView = inflater.inflate(R.layout.calendario_layout,container,false);

        calendar = (CalendarView) rootView.findViewById(R.id.calendar);
        // sets whether to show the week number.

        calendar.setShowWeekNumber(false);
        // sets the first day of week according to Calendar.

        // here we set Monday as the first day of the Calendar

        calendar.setFirstDayOfWeek(2);
        //The background color for the selected week.

        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.

        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.

        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));



        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.

        calendar.setSelectedDateVerticalBar(R.color.darkgreen);



        //sets the listener to be notified upon selected date change.

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override

            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                Toast.makeText(getActivity(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

            }

        });

        return rootView;

    }
}
