package com.example.tadje.myapplication.employee;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tadje.myapplication.R;
import com.example.tadje.myapplication.model.Employee;
import com.example.tadje.myapplication.ui.EmployeeFragment;

import java.util.ArrayList;

/**
 * Created by tadje on 16.04.2018.
 */

public class EmployeeViewAdapter extends RecyclerView.Adapter<EmployeeViewAdapter.ViewHolder> {
    ArrayList<Employee> eDataset;
    EmployeeFragment mParent;


    public EmployeeViewAdapter(ArrayList<Employee> employeeList, EmployeeFragment parent) {
        this.eDataset = employeeList;
        this.mParent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_employee,
                parent, false);


        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Employee employeeForList = eDataset.get(position);
        holder.eEmpNumb.setText( String.format("%d", employeeForList.getEmpNumb()));
        holder.eFirstname.setText(employeeForList.getFirstname());
        holder.eLastname.setText(employeeForList.getLastname());


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 holder.itemView.setTag(position);

                mParent.updateEmployeeDialog(employeeForList);
            }
        });

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.itemView.setTag(position);
                mParent.deleteEmployeeDialog(employeeForList);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.eDataset.size();
    }

    public void setDataset(ArrayList<Employee> employeeList) {
        eDataset = employeeList;
    }

    public interface OnItemRemoveListener {
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView eEmpNumb;
        TextView eLastname;
        TextView eFirstname;
        ViewGroup container;

        ViewHolder(View itemView) {
            super(itemView);
            eEmpNumb = itemView.findViewById(R.id.textViewNumb);
            eFirstname = itemView.findViewById(R.id.textViewFirstname);
            eLastname = itemView.findViewById(R.id.textViewLastname);
            container = itemView.findViewById(R.id.viewHolderContainer);
        }
    }
}
