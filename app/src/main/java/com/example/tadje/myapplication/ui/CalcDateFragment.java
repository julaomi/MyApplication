package com.example.tadje.myapplication.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tadje.myapplication.CalcDates.DatePickerFrom;
import com.example.tadje.myapplication.CalcDates.DatePickerTo;
import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.employee.EmployeeManager;
import com.example.tadje.myapplication.model.Calc;
import com.example.tadje.myapplication.R;
import com.example.tadje.myapplication.model.Holiday;

import com.example.tadje.myapplication.holidays.HolidayManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.media.CamcorderProfile.get;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalcDateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalcDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcDateFragment extends Fragment {

    private EditText from;
    private EditText to;
    private Button button;
    private TextView calculatedIntervalTV;
    private TextView error;
    private Date dateFrom;
    private Date dateTo;
    private long employeeNumber = EmployeeManager.getInstance().getEmployeeNumber();
    private List<String> employeeHolidayList = AppDatabase.getInstance().EmployeeHolidaysDao()
            .getHolidaydate(employeeNumber);
    private double workinghours = AppDatabase.getInstance().EmployeeDao().getWorkingTime
            (employeeNumber);
    private double workinghoursDay;
    Spinner spinnerEmployee;
    private TextView datumint;
    private ArrayList<String> holidayStringList = new ArrayList<String>();
    ArrayList<Holiday> holidayList = HolidayManager.getInstance().getHolidayList();

    private boolean isRunning = false;

    public static final double CALCULATION_ERROR_DATEFORMAT = -1.0;
    public static final double CALCULATION_ERROR_DATE_BEFORE= -2.0;

    public CalcDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalcDateFragment newInstance() {
        CalcDateFragment fragment = new CalcDateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calc_date_fragment, container, false);

        button = view.findViewById(R.id.button);
        from = view.findViewById(R.id.editText);
        to = view.findViewById(R.id.editText2);
        this.error = view.findViewById(R.id.textView4);
        this.calculatedIntervalTV = view.findViewById(R.id.textView4);
        datumint = view.findViewById(R.id.textView4);
        spinnerEmployee = view.findViewById(R.id.spinnerEmployeeForCalc);
        spinner(view, spinnerEmployee);
        spinnerEmployee.setSelection(EmployeeManager.getInstance().getEmployeePosition());

        int i;
        for (i = 0; i < holidayList.size(); i++) {
            holidayStringList.add(holidayList.get(i).getDate().toString());
        }


        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          if (!isRunning) {
                                              isRunning = true;

                                              button.setVisibility(View.INVISIBLE);
                                              button.invalidate();

                                              Thread thread = new Thread() {
                                                  @Override
                                                  public void run() {
                                                      final double calculatedInterval =
                                                              dateInterval();

                                                      CalcDateFragment.this.getActivity().runOnUiThread(new Runnable() {
                                                          public void run() {
                                                              button.setVisibility(View.VISIBLE);
                                                              if(calculatedInterval == CALCULATION_ERROR_DATEFORMAT){
                                                                  setError(getString(R.string.checkFormat)); //get String because id is passed with
                                                                  // R.string from the String CheckFormat
                                                              }else if(calculatedInterval == CALCULATION_ERROR_DATE_BEFORE){
                                                                  setError(getString(R.string.dateAfter));
                                                              } else {
                                                                  calculatedIntervalTV.setText(String
                                                                          .format("%.3f",
                                                                                  calculatedInterval, "Stunden"));
                                                              }
                                                          }
                                                      });

                                                      isRunning = false;
                                                  }
                                              };

                                              thread.start();
                                          }

                                      }
                                  }
        );


        from.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDatePickerDialog();
            }
        }));

        to.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToDatePickerDialog();
            }
        }));


        return view;

    }

    private void spinner(View view, final Spinner spinnerEmployee) {

        String[] strListOfEmployee = AppDatabase.getInstance().EmployeeDao().getEmplastname();
        String[] strListOfEmployeeFirst = AppDatabase.getInstance().EmployeeDao().getEmpFirstname();
        long[] longListOfEmployeeNumber = AppDatabase.getInstance().EmployeeDao().getEmpNumbers();

        final String[] listOfEmployee = new String[strListOfEmployee.length];

        for (int i = 0; i < strListOfEmployee.length; i++) {
            listOfEmployee[i] = (longListOfEmployeeNumber[i]) + " " + (strListOfEmployee[i]) + "," +
                    (strListOfEmployeeFirst[i]); //Employee Number, Lastname and Firstname for
            // Spinner
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity()
                .getApplicationContext(), android.R.layout.simple_spinner_item, listOfEmployee);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(adapter);
        spinnerEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //wenn etwas ausgewählt wurde
                String employeePosition = adapter.getItem(position).toString(); //get position of
                // spinner item
                EmployeeManager.getInstance().setEmployeePosition(position);
                String[] employeeNumberArray = (employeePosition.split("\\s+"));
                String employeeNumber = employeeNumberArray[0];
                EmployeeManager.getInstance().setEmployeeNumber(Long.parseLong(employeeNumber));
                Toast.makeText(getActivity(), ("Es werden die Urlaubstage von dem " +
                                "Mitarbeiter mit der Nummer " + EmployeeManager.getInstance().getEmployeeNumber() +
                                " " +
                                "beachtet."),
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /**
     * Controls entered format of the date, sets the date in a calendar form.
     * Split with spaces, so array [0] date, array [1] holiday name.
     * Difference between two dates less Saturdays, Sundays and holidays. Difference through milliseconds / milliseconds
     *
     * @param view
     */

    public double dateInterval() {

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CalcDateFragment.this.calculatedIntervalTV.setText(R.string.calculation_in_progress);
            }
        });

        int weekendDays = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.default_date_format));


        try {
            dateFrom = sdf.parse(this.from.getText().toString());
            dateTo = sdf.parse(this.to.getText().toString());


        } catch (ParseException e) {
            e.printStackTrace();
            return CALCULATION_ERROR_DATEFORMAT;
        }

        Calendar calendarFrom = new GregorianCalendar();
        calendarFrom.setTime(dateFrom);

        Calendar calendarTo = new GregorianCalendar();
        calendarTo.setTime(dateTo);

        long fromMilli = dateFrom.getTime();
        long toMilli = dateTo.getTime();
        int addStartday = 1;

         if (calendarFrom.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendarFrom.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || checkHolidayList
                (calendarFrom)==true || checkEmployeeHolidayList(calendarFrom)) {
            addStartday--;
        } else if (calendarTo.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendarTo.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || checkHolidayList
                (calendarTo)==true || checkEmployeeHolidayList(calendarTo)
                ){  addStartday --;
        }


        while (calendarTo.after(calendarFrom)) {

            if (calendarFrom.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendarFrom.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                weekendDays++;
            } else if (checkHolidayList(calendarFrom) == true) {
                weekendDays++;
            } else if (checkEmployeeHolidayList(calendarFrom) == true) {
                weekendDays++;
            }

            calendarFrom.add(calendarFrom.DATE, 1); //set a day on calendar, continue with a new date in while
        }


        employeeNumber = EmployeeManager.getInstance().getEmployeeNumber();
        workinghours = AppDatabase.getInstance().EmployeeDao().getWorkingTime
                (employeeNumber);

        workinghoursOnADay();






        double interval = 0.0;
        if (fromMilli <= toMilli) {
            long intervalfromto = (toMilli - fromMilli) / (1000 * 60 * 60 * 24)+addStartday ;
            interval = (intervalfromto - weekendDays) * workinghoursDay;

            final Calc calc = new Calc(fromMilli, toMilli, interval);
            addCalc(calc);

        } else {
            return CALCULATION_ERROR_DATE_BEFORE;
        }
        return interval;
    }

    public void workinghoursOnADay() {
        long workingDays = 5;
        workinghoursDay = (workinghours / workingDays);
    }

    private boolean checkEmployeeHolidayList(Calendar calendarFrom) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        return employeeHolidayList.contains(sdf.format(calendarFrom.getTime()));
    }


    private Calc addCalc(Calc calc) {
        AppDatabase.getInstance().CalcDao().insertAll(calc);

        return calc;
    }

    private boolean checkHolidayList(Calendar calendarFrom) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        return holidayStringList.contains(sdf.format(calendarFrom.getTime()));
    }


    public void setCalculatedIntervalTV(double calculatedIntervalTV) {
        datumint.setText(calculatedIntervalTV + " " + getString(R.string.hours));
    }

    public void setError(final String error) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CalcDateFragment.this.error.setText(error);
            }
        });
    }


    public void showFromDatePickerDialog() {
        DatePickerFrom datePickerDialogFragment = new DatePickerFrom();
        datePickerDialogFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                from.setText(sdf.format(selectedDateCalendar.getTime()));
                //Calendar date as text in date format
            }
        });
        datePickerDialogFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    public void showToDatePickerDialog() {

        DatePickerTo datePickerDialogFragment = new DatePickerTo();
        datePickerDialogFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                to.setText(sdf.format(selectedDateCalendar.getTime())); //Calendar date as text in
                // date format
            }
        });
        datePickerDialogFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    public interface OnFragmentInteractionListener {
    }
}

