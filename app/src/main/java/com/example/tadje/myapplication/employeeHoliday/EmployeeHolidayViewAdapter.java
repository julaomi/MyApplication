package com.example.tadje.myapplication.employeeHoliday;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.R;
import com.example.tadje.myapplication.employee.EmployeeManager;
import com.example.tadje.myapplication.model.EmployeeHoliday;
import com.example.tadje.myapplication.ui.EmployeeHolidayFragment;
import java.util.List;

/**
 * Created by tadje on 18.04.2018.
 */

public class EmployeeHolidayViewAdapter extends RecyclerView.Adapter
        <EmployeeHolidayViewAdapter.ViewHolder> {

    List<EmployeeHoliday> aDataset;
    EmployeeHolidayFragment aParent;


    public EmployeeHolidayViewAdapter(List<EmployeeHoliday> employeeHolidayList, EmployeeHolidayFragment employeeHolidayFragment) {
        this.aDataset = employeeHolidayList;
        this.aParent = employeeHolidayFragment;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .view_holder_employee_holiday, parent, false);


        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EmployeeHoliday employeeHoliday = aDataset.get(position);
        holder.aHolidayDate.setText(employeeHoliday.getHolidaydate());


        holder.aHolidayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        v.setTag(position);
                        aParent.deleteHoliday(employeeHoliday);

                    }
                });


    }

    @Override
    public int getItemCount() {
        return this.aDataset.size();
    }

    public void setDataset(List<EmployeeHoliday> employeeHolidayList) {
        this.aDataset = employeeHolidayList;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView aHolidayDate;


        public ViewHolder(View itemView) {
            super(itemView);
            aHolidayDate = itemView.findViewById(R.id.textViewHolidayDate);
        }
    }

}
