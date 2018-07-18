package com.example.tadje.myapplication.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.model.Holiday;
import com.example.tadje.myapplication.R;
import com.example.tadje.myapplication.holidays.DatePickerForAddHoliday;
import com.example.tadje.myapplication.holidays.HolidayManager;
import com.example.tadje.myapplication.holidays.HolidayViewAdapter;
import com.example.tadje.myapplication.reader.HolidayJsonReader;
import com.example.tadje.myapplication.reader.HolidayTextReader;
import com.example.tadje.myapplication.reader.IHolidayReader;
//import com.example.tadje.myapplication.writer.HolidayJsonWriter;
import com.example.tadje.myapplication.writer.HolidayJsonWriter;
import com.example.tadje.myapplication.writer.HolidayTextWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.system.Os.read;


/**
 * A simple {@link HolidayFragmentFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HolidayFragmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HolidayFragmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HolidayFragment extends Fragment implements HolidayViewAdapter.OnItemRemovedListener {

    protected MainActivity app;
    Button addHolidayButton;
    EditText editDate;
    EditText editName;
    String editDateStr;
    String editNameStr;
    HolidayJsonWriter holidayJsonWriter;
    ToggleButton tbDatabaseFiles;


    private RecyclerView mRecyclerView;
    private HolidayViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Holiday> holidayList = HolidayManager.getInstance().getHolidayList();

    public HolidayFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TestFragment.
     */

    public static HolidayFragment newInstance() {
        HolidayFragment fragment = new HolidayFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_holiday, container, false);
        initializeHolidayButtonsEventHandler(view);
        Spinner spinner = view.findViewById(R.id.spinnerHolidays);
        tbDatabaseFiles=view.findViewById(R.id.tB_Database_Files);
        databaseOrFile(view, spinner);

        if((HolidayManager.getInstance().getListFrom())==9999){
            tbDatabaseFiles.setChecked(false);
        }else{
            tbDatabaseFiles.setChecked(true);
            spinner.setSelection(HolidayManager.getInstance().getListFrom());

        }

        if (holidayList != null) {
            mRecyclerView = view.findViewById(R.id.recyclerviewHolidays);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) activity;

                mAdapter = new HolidayViewAdapter(holidayList, mainActivity.getDeleteButton());
            }

            mRecyclerView.setAdapter(mAdapter);
        }

        return view; //return view to set the recyvlerview

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.addOnItemRemovedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.removeItemRemovedListener(this);
    }


   private void databaseOrFile(final View view, final Spinner spinner){
              tbDatabaseFiles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    spinner.setVisibility(View.GONE);
                    spinner.setVisibility(View.INVISIBLE);
                    holidayList = (ArrayList<Holiday>) AppDatabase.getInstance()
                            .holidaysDao().getAll();
                    HolidayManager.getInstance().setHolidayList(holidayList);
                    listToAdapter(holidayList);
                    HolidayManager.getInstance().setListFrom(9999);
                } else  {
                    spinner.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);
                    populateFileSpinner(view, spinner);
                }
            }
        });
    }


    private void populateFileSpinner(View view, Spinner spinner) {
        String[] listOfFiles;
        // List of Existing Files for the Spinner with Adapter

        try {
            listOfFiles = getActivity().getAssets().list("holidays");



        } catch (IOException e) {
            e.printStackTrace();
            listOfFiles = new String[0];
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, listOfFiles);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //wenn etwas ausgewählt wurde
                String fileName = adapter.getItem(position).toString(); //declaration with the
                HolidayManager.getInstance().setFileName(fileName);
                HolidayManager.getInstance().setListFrom(position);
                // position of Spinner
                initSingleton();
                initializeHolidayTextFileToast();
                read();


                }




            @Override
            public void onNothingSelected(AdapterView<?> parent) { //wenn nichts ausgewählt wurde
                HolidayManager.getInstance().getFileName();
            }


            //Reading files by type, transfer to TextView using adapter
            public void read() {
              String fileName =  HolidayManager.getInstance().getFileName();
                IHolidayReader reader = createHolidayReaderFromFileType(fileName);
                initializeHolidayListAdapter(reader.getHolidays(fileName));
            }

            private void initializeHolidayListAdapter(ArrayList<Holiday> holidayList) {
                listToAdapter(holidayList);
            }

            private IHolidayReader createHolidayReaderFromFileType(String fileName) {
                IHolidayReader reader = null;
                if (fileName.endsWith(".json")) {
                    reader = new HolidayJsonReader();
                } else if (fileName.endsWith(".txt")) {
                    reader = new HolidayTextReader();
                }
                return reader;
            }

        });
    }

    protected void initSingleton(){
        HolidayManager.getInstance();
    }

    private void initializeHolidayTextFileToast() {
        String fileName= HolidayManager.getInstance().getFileName();
        Toast.makeText(getActivity(), getString(R.string.holidaysFor) + " " + fileName + " " + getString(R.string.holidaysSelected), Toast.LENGTH_LONG).show();
    }

    private void initializeHolidayButtonsEventHandler(View view) {
        onAddHolidayButtonClicked(view);
    }

    private void onAddHolidayButtonClicked(View view) {
        addHolidayButton = view.findViewById(R.id.addHolidayButton);
        addHolidayButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    addDialog();
                                                }
                                            }
        );
    }


    public void showDatePickerDialog() {

        DatePickerForAddHoliday datePickerDialogFragment = new DatePickerForAddHoliday();
        datePickerDialogFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                editDate.setText(sdf.format(selectedDateCalendar.getTime())); //Calendar date as text in date format
            }
        });
        datePickerDialogFragment.show(getActivity().getFragmentManager(), "datePicker");
    }


    //Dialog to add a date and a name in a existing file
    public void addDialog() {
        showDatePickerDialog();

        AlertDialog.Builder alertDialogAdd = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_add, null);
        alertDialogAdd.setView(dialogView);

        editDate = dialogView.findViewById(R.id.empNumbEdit);
        editName = dialogView.findViewById(R.id.empLastnameEdit);

        alertDialogAdd.setTitle(R.string.addholiday);

        alertDialogAdd.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addHoliday();
                    }
                }
        );

        alertDialogAdd.setNegativeButton(getString(R.string.cancel), null);
        AlertDialog alertDialogA = alertDialogAdd.create();
        alertDialogA.show();
    }


    public String addHoliday() {

        OutputStreamWriter fileWriter = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        try {
            String inputDateString = editDate.getText().toString();
            // parse to validate correctness of input
            sdf.parse(inputDateString);

            editDateStr = inputDateString;
            editNameStr = (editName.getText().toString());

            if (editNameStr.trim().length() > 0) {

                String fileName= HolidayManager.getInstance().getFileName();
                holidayList = getDataFromFile(fileName);
                HolidayManager.getInstance().setHolidayList(holidayList);
                listToAdapter(holidayList);
            } else {
                Toast.makeText(getActivity(), getString(R.string.holidayNameNeeded), Toast
                        .LENGTH_LONG).show();
            }


        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.checkFormat), Toast
                    .LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeFileWriter(fileWriter);
        }

        return (editDateStr + editNameStr);
    }

    private void closeFileWriter(OutputStreamWriter fileWriter) {
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Holiday> getDataFromFile(String fileName) {
        ArrayList<Holiday> holidayList = new ArrayList<>();

        if (fileName.endsWith(".txt")) {
            holidayList = readFromText();

        } else if (fileName.endsWith(".json")) {
            holidayList = readFromJson();
        }
        return holidayList;
    }

    @Nullable
    private ArrayList<Holiday> readFromText() {
        HolidayTextWriter writer;
        IHolidayReader reader;
        String fileName= HolidayManager.getInstance().getFileName();
        writer = new HolidayTextWriter(fileName);
        writer.write(editDateStr, editNameStr);
        reader = new HolidayTextReader();
        return reader.getHolidays(fileName);
    }

    @Nullable
    private ArrayList<Holiday> readFromJson() {
        HolidayJsonWriter writerJson;
        IHolidayReader reader;
        String fileName= HolidayManager.getInstance().getFileName();
        writerJson = new HolidayJsonWriter(fileName);
        writerJson.write(editDateStr, editNameStr);
        reader = new HolidayJsonReader();
        return reader.getHolidays(fileName);
    }


    /*
    Depending on the end of the file name, the write class is called and then again the read class
    and the adapter so that the TextView is updated */
    public void deleteLine(ArrayList<Integer> positionList) {


        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;


            HolidayTextWriter holidayTextWriter = null;

            holidayJsonWriter = null;
            IHolidayReader reader = new HolidayTextReader();
            String fileName= HolidayManager.getInstance().getFileName();


            if (fileName.endsWith(".txt")) {
                deleteLineFromText(positionList, fileName);

            } else if (fileName.endsWith(".json")) {
                deleteLineFromJson(positionList, fileName);
            }

        }
    }


    @NonNull
    private void deleteLineFromJson(ArrayList<Integer> positionList, String fileName) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;

            holidayJsonWriter = new HolidayJsonWriter(fileName);
            holidayJsonWriter.delete(positionList, mainActivity.getDeleteButton());

            IHolidayReader reader;
            reader = new HolidayJsonReader();
            holidayList = reader.getHolidays(fileName);
            HolidayManager.getInstance().setHolidayList(holidayList);
            listToAdapter(holidayList);
        }

    }

    @NonNull
    private void deleteLineFromText(ArrayList<Integer> positionList, String fileName) {
        FragmentActivity activity = getActivity();
        fileName = HolidayManager.getInstance().getFileName();

        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;

            HolidayTextWriter holidayTextWriter;
            holidayTextWriter = new HolidayTextWriter(fileName);
            holidayTextWriter.delete(positionList, mainActivity.getDeleteButton());

            IHolidayReader reader;
            reader = new HolidayTextReader();
            holidayList = reader.getHolidays(fileName);
            HolidayManager.getInstance().setHolidayList(holidayList);
            listToAdapter(holidayList);
        }

    }

    private void listToAdapter(ArrayList<Holiday> holidayList) {
        mAdapter.setDataset(holidayList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void itemRemoved(ArrayList<Integer> positionList) {
        deleteLine(positionList);
    }

    public interface OnFragmentInteractionListener {
    }

}