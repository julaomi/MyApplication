package com.example.tadje.myapplication.ui;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.R;
import com.example.tadje.myapplication.employee.EmployeeManager;
import com.example.tadje.myapplication.employeeHoliday.EmployeeHolidayViewAdapter;
import com.example.tadje.myapplication.holidays.DatePickerForAddHoliday;
import com.example.tadje.myapplication.holidays.HolidayManager;
import com.example.tadje.myapplication.model.Employee;
import com.example.tadje.myapplication.model.EmployeeHoliday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tadje on 18.04.2018.
 */


public class EmployeeHolidayFragment extends Fragment {

    public Button addEmpHolidayButton;
    Spinner employeeSpinner;

    RecyclerView aRecyclerView;
    private EmployeeHolidayViewAdapter aAdapter;
    private RecyclerView.LayoutManager aLayoutManager;
    private List<EmployeeHoliday> employeeHolidayList;
    EditText empdateEdit;
    String empdate;

    private EmployeeHolidayFragment.OnFragmentInteractionListener aListener;

    public EmployeeHolidayFragment() {
    }

    public static EmployeeHolidayFragment newInstance() {
        EmployeeHolidayFragment fragment = new EmployeeHolidayFragment();
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_holiday, container, false);
        addEmpHolidayButton = view.findViewById(R.id.addEmployeeHolidayButton);
        employeeSpinner = view.findViewById(R.id.spinnerEmployee);
        employeeHolidayList = AppDatabase.getInstance().EmployeeHolidaysDao().getAll();
        spinner(view, employeeSpinner);

        employeeSpinner.setSelection(EmployeeManager.getInstance().getEmployeePosition());


        if (employeeHolidayList != null) {
            aRecyclerView = view.findViewById(R.id.recyclerviewEmployeeHoliday);
            aRecyclerView.setHasFixedSize(true);

            aLayoutManager = new LinearLayoutManager(getActivity());
            aRecyclerView.setLayoutManager(aLayoutManager);

            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) activity;
                aAdapter = new EmployeeHolidayViewAdapter(employeeHolidayList, this);
            }

            aRecyclerView.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        }


        addEmpHolidayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addEmpHolidayDialog();
            }


        });

        return view;
    }

    private void spinner(View view, final Spinner employeeSpinner) {
        String[] strListOfEmployee = AppDatabase.getInstance().EmployeeDao().getEmplastname();
        String[] strListOfEmployeeFirst = AppDatabase.getInstance().EmployeeDao().getEmpFirstname();
        long[] longListOfEmployeeNumber = AppDatabase.getInstance().EmployeeDao().getEmpNumbers();

        final String[] listOfEmployee = new String[strListOfEmployee.length];

        for (int i = 0; i < strListOfEmployee.length; i++) {
            listOfEmployee[i] = (longListOfEmployeeNumber[i]) + " " + (strListOfEmployee[i]) + "," +
                    (strListOfEmployeeFirst[i]);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity()
                .getApplicationContext(), android.R.layout.simple_spinner_item, listOfEmployee);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(adapter);
        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //wenn etwas ausgewählt wurde
                String employeePosition = adapter.getItem(position).toString(); //declaration with
                EmployeeManager.getInstance().setEmployeePosition(position);
                String[] employeeNumberArray = (employeePosition.split("\\s+"));
                String employeeNumber = employeeNumberArray[0];
                EmployeeManager.getInstance().setEmployeeNumber(Long.parseLong(employeeNumber));


                employeeHolidayList = AppDatabase.getInstance()
                        .EmployeeHolidaysDao().findByEmpNumb(Long.parseLong(employeeNumber));
                listToAdapter(employeeHolidayList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void showDatePickerDialog() {

        DatePickerForAddHoliday datePickerDialogFragment = new DatePickerForAddHoliday();
        datePickerDialogFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                empdateEdit.setText(sdf.format(selectedDateCalendar.getTime())); //Calendar date
                // as text in date format
            }
        });
        datePickerDialogFragment.show(getActivity().getFragmentManager(), "datePicker");
    }


    public void addEmpHolidayDialog() {

        AlertDialog.Builder alertDialogAdd = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_emp_holiday, null);
        alertDialogAdd.setView(dialogView);
        empdateEdit = dialogView.findViewById(R.id.editDateHoli);



        showDatePickerDialog();

        final long employeeNumber = EmployeeManager.getInstance().getEmployeeNumber();
        alertDialogAdd.setTitle(R.string.addholiday);

        alertDialogAdd.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addHolidayEmp(employeeNumber);
                    }
                }
        );

        alertDialogAdd.setNegativeButton(getString(R.string.cancel), null);
        AlertDialog alertDialogA = alertDialogAdd.create();
        alertDialogA.show();
    }


    public void addHolidayEmp(long employeeNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        try {

            String inputDateString = empdateEdit.getText().toString();
            // parse to validate correctness of input
            sdf.parse(inputDateString);
            empdate = inputDateString;

            EmployeeHoliday employeeHolidays = new EmployeeHoliday(employeeNumber, empdate);
            AppDatabase.getInstance().EmployeeHolidaysDao().insertAll(employeeHolidays);


            employeeHolidayList = AppDatabase.getInstance()
                    .EmployeeHolidaysDao().findByEmpNumb(employeeNumber);
            listToAdapter(employeeHolidayList);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EmployeeHolidayFragment.OnFragmentInteractionListener) {
            aListener = (EmployeeHolidayFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        aListener = null;
    }

    public void listToAdapter(List<EmployeeHoliday> employeeHolidayList) {
        aAdapter.setDataset(employeeHolidayList);
        aAdapter.notifyDataSetChanged();
    }

    public void deleteHoliday(final EmployeeHoliday employeeHoliday) {
        final AlertDialog.Builder alertDialogDele = new AlertDialog.Builder(getContext());
        alertDialogDele.setTitle(R.string.deleteEmpHoliday);

        alertDialogDele.setPositiveButton((R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                AppDatabase.getInstance().EmployeeHolidaysDao().delete(employeeHoliday);
                long empNumb = EmployeeManager.getInstance().getEmployeeNumber();
                employeeHolidayList = AppDatabase.getInstance()
                        .EmployeeHolidaysDao().findByEmpNumb(empNumb);
                listToAdapter(employeeHolidayList);

            }
        });

        alertDialogDele.setNegativeButton((R.string.cancel), new DialogInterface
                .OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialogD = alertDialogDele.create();
        alertDialogD.show();
    }


    public interface OnFragmentInteractionListener {
    }

}

