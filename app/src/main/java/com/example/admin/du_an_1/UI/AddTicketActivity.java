package com.example.admin.du_an_1.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.DatePickerDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.admin.du_an_1.Adapter.TabAdapter;
import com.example.admin.du_an_1.Controller.CategoryService;
import com.example.admin.du_an_1.Controller.TicketService;
import com.example.admin.du_an_1.DAO.daoCategory;
import com.example.admin.du_an_1.R;
import com.example.admin.du_an_1.Repository.Category;
import com.example.admin.du_an_1.Repository.Product;
import com.example.admin.du_an_1.Repository.Ticket;


import java.util.ArrayList;

import java.util.Calendar;



public class AddTicketActivity extends AppCompatActivity implements View.OnClickListener {




    //TEST. ///

    private daoCategory DAOcat;
    private ArrayList<Category> arrCat;
    private Category category;

    //////////

    TicketService ticketService;
    EditText etTicketTittle, etQuantity, etProductname, etProductcode;
    Spinner spnCategory;
    Button btnAddTicket, btnCancel,  btnAddCategory, etDate;

    Product myProduct;
    Ticket myTicket;
    int mYear,mMonth,mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        ticketService= new TicketService(this);
        //CREATE FAKE DATA FOR TESTING

        myProduct= new Product();
        myTicket= new Ticket();


        btnAddCategory = findViewById( R.id.btnAddCategory );
        spnCategory = (Spinner) findViewById( R.id.spnCategory );


        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etDate = (Button) findViewById (R.id.etDate);
        etProductname = (EditText)findViewById(R.id.etProductName);
        etProductcode = (EditText)findViewById(R.id.etProductCode);
        spnCategory = (Spinner)findViewById(R.id.spnCategory);

        btnAddTicket= (Button)findViewById(R.id.btnAddTicket);

        btnAddTicket.setOnClickListener(this);

        btnAddCategory.setOnClickListener( this );

        etDate.setOnClickListener(this);
        final Calendar c = Calendar.getInstance();
         mYear = c.get(Calendar.YEAR);
         mMonth = c.get(Calendar.MONTH);
         mDay = c.get(Calendar.DAY_OF_MONTH);

        etDate.setText((mDay + "-" + mMonth + "-" + mYear));

    }

    @Override
    protected void onResume() {
        super.onResume();
        initial();


    }

    private void initial() {
        DAOcat = daoCategory.getInstance(getBaseContext());
        arrCat = DAOcat.getAllItem();
        ArrayList<String> arrName = new ArrayList<>();
        for (int i = 0; i < arrCat.size(); i++){
            arrName.add(arrCat.get(i).getName());
        }
        ArrayAdapter<String> adapterSpin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrName);
        adapterSpin.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapterSpin);
//        spnCategory.setOnItemSelectedListener(this);

        // Refresh Data. //
        adapterSpin.notifyDataSetChanged();

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btnAddTicket):
//                asdasdasdasd
//                        asdasdasdasdasdasd
//                        asdasdasdasdasdasd
//                                Product temp = new Product(id,name,iasd,asdasd,);
                if (onAddClicked(addTicket(), addproduct(), "Import")){
                    this.finish();
                }
                break;
            case (R.id.btnCancel):
                break;
            case (R.id.etDate):
                DatePicker();
                break;

            case R.id.btnAddCategory:
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate( R.layout.dialog_add_category,null );

                final EditText edtDialog_addCategory = (EditText)view.findViewById( R.id.edtDialog_addCategory );

                AlertDialog.Builder mBuilder = new AlertDialog.Builder( this );
                mBuilder.setView( view ).setTitle("ADD CATEGORY").setPositiveButton( "Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CategoryService ServiceCat = new CategoryService( getBaseContext() );
                        ServiceCat.addCategory( edtDialog_addCategory.getText().toString() );
                        finish();
                        startActivityForResult( new Intent( getBaseContext(),AddTicketActivity.class ),0 );
                    }
                } ).setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        dialogInterface.cancel();
                    }
                } ).create().show();
                break;
        }

    }
    public Product addproduct(){
        myProduct = new Product();
        myProduct.setCode(etProductcode.getText().toString());
        myProduct.setName(etProductname.getText().toString());
        myProduct.setCategory(spnCategory.getSelectedItem().toString());
        myProduct.setId(null);
        return myProduct;
    }

    public Ticket addTicket(){
        myTicket = new Ticket();
        myTicket.setDate(etDate.getText().toString());
        myTicket.setType(true);
        myTicket.setId(null);
        myTicket.setproductName(etProductname.getText().toString());
        myTicket.setQuantity(Integer.parseInt(etQuantity.getText().toString()));
        return myTicket;
    }

    public boolean onAddClicked(Ticket ticket, Product product, String tittle) {
        if (this.ticketService.addImport(ticket, product)){
            return true;
        }
        return false;
    }
    private void DatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void Dialog_Add(){



    }
}
