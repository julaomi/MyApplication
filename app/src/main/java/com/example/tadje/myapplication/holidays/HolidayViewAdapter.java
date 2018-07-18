package com.example.tadje.myapplication.holidays;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tadje.myapplication.model.Holiday;
import com.example.tadje.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

/**
 * Created by tadje on 14.03.2018.
 */


public class HolidayViewAdapter extends RecyclerView.Adapter<HolidayViewAdapter.ViewHolder> {

    private ArrayList<Holiday> mDataset;
    String fileName;
    ArrayList<OnItemRemovedListener> listners;
    private FloatingActionButton deleteButton;
    ArrayList<Integer> positionList = new ArrayList<>();
    CheckBox checkBox;


    public interface OnItemRemovedListener {
        void itemRemoved(ArrayList<Integer> positionList);
    }

    public void addOnItemRemovedListener(OnItemRemovedListener listener) {
        listners.add(listener);
    }

    public void removeItemRemovedListener(OnItemRemovedListener listener) {
        listners.remove(listener);
    }

    private void informListners(ArrayList<Integer> position) {
        for (OnItemRemovedListener listner : listners) {
            listner.itemRemoved(positionList);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox mCheckBox;
        public TextView mDateTextView;
        public TextView mNameTextView;

        public ViewHolder(View v) {
            super(v);
            mDateTextView = v.findViewById(R.id.date_textview);
            mNameTextView = v.findViewById(R.id.name_textview);
            mCheckBox = v.findViewById(R.id.delete_checkbox);
        }
    }


    public HolidayViewAdapter(ArrayList<Holiday> myDataset, FloatingActionButton button) {
        mDataset = myDataset;
        this.fileName = fileName;
        this.listners = new ArrayList<>();
        this.deleteButton = button;

    }

    @Override
    public HolidayViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_layout, parent, false);
        checkBox = view.findViewById(R.id.delete_checkbox);

        this.deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogDele = new AlertDialog.Builder(v.getContext());
                alertDialogDele.setTitle(R.string.deleteholiday);

                alertDialogDele.setPositiveButton((R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        notifyDataSetChanged();
                        informListners(positionList);
                        positionList.clear();


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
        });


        return new ViewHolder(view);
    }


    private Map<String, Boolean> checkBoxStates = new HashMap<>();

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Holiday holiday = mDataset.get(position);

        if (holder.mCheckBox.isChecked()) {
            holder.mCheckBox.setChecked(false);
        }

        holder.mDateTextView.setText(holiday.getDate());
        holder.mNameTextView.setText(holiday.getName());
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (holder.mCheckBox.isChecked()) {
                    positionList.add(position);

                } else {
                    int notCheckt = positionList.indexOf(position);

                    positionList.remove(notCheckt);
                }

                if (positionList.isEmpty()) {
                    deleteButton.setVisibility(v.INVISIBLE);
                } else {
                    deleteButton.setVisibility(v.GONE);
                    deleteButton.setVisibility(v.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<Integer> getPositionList() {
        return positionList;
    }

    public void setDataset(ArrayList<Holiday> myDataset) {
        mDataset = myDataset;
    }
}
