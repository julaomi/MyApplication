package com.example.tadje.myapplication.ui;

/**
 * Created by tadje on 13.04.2018.
 */


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.R;
import com.example.tadje.myapplication.employee.EmployeeManager;
import com.example.tadje.myapplication.employee.EmployeeViewAdapter;
import com.example.tadje.myapplication.model.Employee;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link HolidayFragmentFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HolidayFragmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HolidayFragmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {

    protected MainActivity app;
    private Button addEmployeeButton;
    private EditText numberEmp;
    private EditText lastnameEmp;
    private EditText firstnameEmp;
    private EditText workingTimeEmp;
    private EditText roleEmp;

    private RecyclerView eRecyclerView;
    private EmployeeViewAdapter eAdapter;
    private RecyclerView.LayoutManager eLayoutManager;
    public ArrayList<Employee> employeeList;
    private ImageView imageViewEmployee;
    public Uri uri;
    byte[] imageByteEmployee;
    boolean buttonClickToLoadAImage = false;
    public static final int IMAGE_FROM_GALLERY = 0;
    public static final int IMAGE_FROM_CAMERA = 1;


    private EmployeeFragment.OnFragmentInteractionListener eListener;

    public EmployeeFragment() {
        // Required empty public constructor
    }

    public static EmployeeFragment newInstance() {
        EmployeeFragment fragment = new EmployeeFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        addEmployeeButton = view.findViewById(R.id.addEmployeeButton);

        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmployeeDialog();
            }
        });

        employeeList = (ArrayList<Employee>) AppDatabase.getInstance().EmployeeDao().getAll();

        if (employeeList != null) {
            eRecyclerView = view.findViewById(R.id.recyclerviewEmployee);
            eRecyclerView.setHasFixedSize(true);

            eLayoutManager = new LinearLayoutManager(getActivity());
            eRecyclerView.setLayoutManager(eLayoutManager);

            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) activity;
                eAdapter = new EmployeeViewAdapter(employeeList, this);
            }

            eRecyclerView.setAdapter(eAdapter);
            eAdapter.notifyDataSetChanged();
        }

        return view;
    }


    public void updateEmployeeDialog(Employee employee) {
        AlertDialog.Builder updateEmployeeDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View updateViewEmployee = inflater.inflate(R.layout.alertdialogemployee,
                null);
        updateEmployeeDialog.setView(updateViewEmployee);


        numberEmp = updateViewEmployee.findViewById(R.id.empNumbEdit);
        numberEmp.setEnabled(false);
        lastnameEmp = updateViewEmployee.findViewById(R.id.empLastnameEdit);
        firstnameEmp = updateViewEmployee.findViewById(R.id.empFirstnameEdit);
        workingTimeEmp = updateViewEmployee.findViewById(R.id.workingTimeEdit);
        roleEmp = updateViewEmployee.findViewById(R.id.roleEdit);
        imageViewEmployee = updateViewEmployee.findViewById(R.id.imageView2);
        Button addImage = updateViewEmployee.findViewById(R.id.addImageButton);

        fillDialogWithEmployeeData(employee);

        updateEmployeeDialog.setTitle(R.string.newemployee);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });

        updateEmployeeDialog.setPositiveButton(getString(R.string.change), new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        updateEmployee();
                    }
                }
        );

        updateEmployeeDialog.setNegativeButton(getString(R.string.cancel), null);
        AlertDialog alertDialogEmp = updateEmployeeDialog.create();
        alertDialogEmp.show();
    }

    @SuppressLint("DefaultLocale")
    private void fillDialogWithEmployeeData(Employee employeeToEdit) {
        long empNumb = employeeToEdit.getEmpNumb();
        String lastname = employeeToEdit.getLastname();
        String firstname = employeeToEdit.getFirstname();
        double workingTime = employeeToEdit.getWorkingTime();
        String role = employeeToEdit.getRole();
        byte[] image = employeeToEdit.getImage();

        numberEmp.setText(String.format("%d", empNumb));
        firstnameEmp.setText(firstname);
        lastnameEmp.setText(lastname);
        workingTimeEmp.setText(String.format(String.valueOf(workingTime)));
        roleEmp.setText(role);

        if (image != null) {
            imageViewEmployee.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        } else {
            imageViewEmployee.setImageResource(R.drawable.ic_menu_gallery);
        }
    }


    private void updateEmployee() {

        long numberEmpLong = Long.parseLong(String.valueOf(numberEmp.getText()));
        String lastnameEmpStr = lastnameEmp.getText().toString();
        String firstnameEmpString = firstnameEmp.getText().toString();
        double workingTimeEmpLong = Double.parseDouble((String.valueOf(workingTimeEmp.getText())));
        String roleEmpString = roleEmp.getText().toString();

        if (buttonClickToLoadAImage == true) {
            buttonClickToLoadAImage = false;
            Bitmap bitmap = ((BitmapDrawable) imageViewEmployee.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            imageByteEmployee = baos.toByteArray();
        }

        Employee employee = new Employee(numberEmpLong, lastnameEmpStr, firstnameEmpString,
                workingTimeEmpLong, roleEmpString, imageByteEmployee);
        AppDatabase.getInstance().EmployeeDao().update(employee);

        employeeList = (ArrayList<Employee>) AppDatabase.getInstance().EmployeeDao().getAll();
        listToAdapter(employeeList);

    }


    public void addEmployeeDialog() {

        AlertDialog.Builder addEmployeeDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogViewEmployee = inflater.inflate(R.layout.alertdialogemployee,
                null);
        addEmployeeDialog.setView(dialogViewEmployee);


        numberEmp = dialogViewEmployee.findViewById(R.id.empNumbEdit);
        lastnameEmp = dialogViewEmployee.findViewById(R.id.empLastnameEdit);
        firstnameEmp = dialogViewEmployee.findViewById(R.id.empFirstnameEdit);
        workingTimeEmp = dialogViewEmployee.findViewById(R.id.workingTimeEdit);
        roleEmp = dialogViewEmployee.findViewById(R.id.roleEdit);
        Button addImage = dialogViewEmployee.findViewById(R.id.addImageButton);
        imageViewEmployee = dialogViewEmployee.findViewById(R.id.imageView2);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });


        addEmployeeDialog.setTitle(R.string.newemployee);

        addEmployeeDialog.setPositiveButton(getString(R.string.add), new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addEmployee();
                    }
                }
        );

        addEmployeeDialog.setNegativeButton(getString(R.string.cancel), null);
        AlertDialog alertDialogEmp = addEmployeeDialog.create();
        alertDialogEmp.show();
    }

    public void addEmployee() {
        long numberEmpLong;
        String lastnameEmpStr;
        String firstnameEmpString;
        double workingTimeEmpLong;
        String roleEmpString;

        try {
            numberEmpLong = Long.parseLong(String.valueOf(numberEmp.getText()));
            lastnameEmpStr = lastnameEmp.getText().toString();
            firstnameEmpString = firstnameEmp.getText().toString();
            workingTimeEmpLong = Double.parseDouble((String.valueOf(workingTimeEmp.getText())));
            roleEmpString = roleEmp.getText().toString();
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), ("Bitte füllen Sie alle Felder aus!"), Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
            return;
        }

        if ((TextUtils.isEmpty(numberEmp.getText()))
                || (TextUtils.isEmpty(lastnameEmp.getText()))
                || (TextUtils.isEmpty(firstnameEmp.getText()))
                || (TextUtils.isEmpty(workingTimeEmp.getText()))
                || (TextUtils.isEmpty(roleEmp.getText()))){

            Toast.makeText(getActivity(), ("Bitte füllen Sie alle Felder aus!"), Toast.LENGTH_LONG)
                    .show();

        } else if (buttonClickToLoadAImage) {

            buttonClickToLoadAImage = false;
            Bitmap bitmap = ((BitmapDrawable) imageViewEmployee.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            imageByteEmployee = baos.toByteArray();

        }

        Employee employee = new Employee(numberEmpLong, lastnameEmpStr, firstnameEmpString,
                workingTimeEmpLong, roleEmpString, imageByteEmployee);

        boolean employeeNumberExists = employeeNumberExists(numberEmpLong);

        if (employeeNumberExists) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                    .checkEmployeeNumber), Toast
                    .LENGTH_SHORT).show();
        } else {
            AppDatabase.getInstance().EmployeeDao().insertAll(employee);
            employeeList = (ArrayList<Employee>) AppDatabase.getInstance().EmployeeDao().getAll();
            listToAdapter(employeeList);
        }
    }

    private boolean employeeNumberExists(long empNumb) {
        for (Employee employee : this.employeeList) {
            if (employee.getEmpNumb() == empNumb) {
                return true;
            }
        }
        return false;
    }


    public void loadImage() {
        buttonClickToLoadAImage = true;
        final AlertDialog.Builder imageAlertDialog = new AlertDialog.Builder(
                getActivity());
        imageAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                buttonClickToLoadAImage = false;
            }
        });
        imageAlertDialog.setTitle(getString(R.string.uploadPic));
        imageAlertDialog.setMessage(getString(R.string.howSetPic));

        imageAlertDialog.setPositiveButton(getString(R.string.gallery),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(pickPhoto, "Wähle App"),
                                IMAGE_FROM_GALLERY);


                    }
                });

        imageAlertDialog.setNegativeButton(getString(R.string.camera),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        int counter = 0;
                        counter++;
                        String imageFileName = "JPEG_" + counter; //make a better file name
                        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File image = null;
                        try {
                            image = File.createTempFile(imageFileName, ".jpg", storageDir);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        uri = Uri.fromFile(image);

                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(takePicture, IMAGE_FROM_CAMERA);

                        employeeList = (ArrayList<Employee>) AppDatabase.getInstance().EmployeeDao().getAll();
                        listToAdapter(employeeList);

                    }
                });
        imageAlertDialog.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IMAGE_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imageViewEmployee.setImageURI(selectedImage);
                }

                break;
            case IMAGE_FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    imageViewEmployee.setImageURI(uri);

                }
                break;
        }
        if(resultCode == 0){
            buttonClickToLoadAImage = false;
        }
    }


    public void onButtonPressed(Uri uri) {
        if (eListener != null) {
            eListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EmployeeFragment.OnFragmentInteractionListener) {
            eListener = (EmployeeFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        eListener = null;
    }


    public void deleteEmployeeDialog(final Employee employee) {
        AlertDialog.Builder empDeleteAlertDialog = new AlertDialog.Builder(
                getActivity());
        empDeleteAlertDialog.setTitle(getString(R.string.deleteEmployee));
        empDeleteAlertDialog.setMessage(getString(R.string.deleteEmpQuest));

        empDeleteAlertDialog.setPositiveButton(getString(R.string.delete),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AppDatabase.getInstance().EmployeeDao().delete(employee);

                        employeeList = (ArrayList<Employee>) AppDatabase.getInstance().EmployeeDao().getAll();
                        listToAdapter(employeeList);
                    }
                });

        empDeleteAlertDialog.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        empDeleteAlertDialog.show();

    }


    private void listToAdapter(ArrayList<Employee> employeeList) {
        eAdapter.setDataset(employeeList);
        eAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}