package com.example.admin.du_an_1.UI.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.du_an_1.Adapter.ProductAdapter;
import com.example.admin.du_an_1.DAO.daoProducts;
import com.example.admin.du_an_1.DAO.daoTicket;
import com.example.admin.du_an_1.R;
import com.example.admin.du_an_1.Repository.Product;
import com.example.admin.du_an_1.Repository.Ticket;
import com.example.admin.du_an_1.UI.AddTicketActivity;
import com.example.admin.du_an_1.UI.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

import static com.example.admin.du_an_1.Adapter.ProductAdapter.remoAccent;

public class Fragment_List extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    FloatingActionButton fab;
    ListView productList;
    Context context;
    daoProducts DaoProducts;
    daoTicket DaoTicket;
    EditText etsearch;
    List<Product> listProduct;
    List<Ticket> listTeck;
    Boolean isAdmin= false;
    int soluongproduct;

    ProductAdapter productAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        DaoProducts = daoProducts.getInstance(context);
        DaoTicket = daoTicket.getInstance(context);
        isAdmin= getArguments().getBoolean("isAdmin");
        Log.i(">>>>",""+ isAdmin);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        fab= (FloatingActionButton) view.findViewById(R.id.btnFAB_Add);
        productList= (ListView) view.findViewById(R.id.lv_product);
        etsearch = (EditText)view.findViewById(R.id.etsearch);

        fab.setOnClickListener(this);
        // add products up list
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               List<Product>  listproduct1 = DaoProducts.getAllItem();
               //List<Ticket>  listticket1 = DaoTicket.getAllItem();
               final Product productdilog = listproduct1.get(i);
                final Ticket ticket1 = DaoTicket.getByName(productdilog.getName());

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.item_option_product);
                dialog.setCancelable(true);

                TextView tvname = (TextView)dialog.findViewById(R.id.tvname);
                TextView tvcode = (TextView)dialog.findViewById(R.id.tvcode);
                TextView tvsoluong = (TextView)dialog.findViewById(R.id.tvsoluong);
                Button btnsua = (Button) dialog.findViewById(R.id.btnhuy);
                final Button btnxoa = (Button)dialog.findViewById(R.id.btnxoa);
                Button btnxuat = (Button) dialog.findViewById(R.id.btnxuat);

                tvname.setText(productdilog.getName());
                tvcode.setText(productdilog.getCode());
                soluongproduct = ticket1.getQuantity();
                tvsoluong.setText(String.valueOf(soluongproduct));


                //btn xuat kho
                btnxuat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        final Dialog dialogxuat = new Dialog(getActivity());
                        dialogxuat.setContentView(R.layout.item_product_xuatkho);
                        dialogxuat.setCancelable(true);
                        TextView tvnamexuat = (TextView)dialogxuat.findViewById(R.id.tvnamexuat);
                        TextView tvcodexuat = (TextView)dialogxuat.findViewById(R.id.tvcodexuat);
                        TextView tvsoluongbandau = (TextView)dialogxuat.findViewById(R.id.tvsoluongbandau);
                        final EditText etsoluongxuat = (EditText)dialogxuat.findViewById(R.id.etsoluongxuat);
                        Button btnxuatxuat = (Button)dialogxuat.findViewById(R.id.btnxuatxuat);
                        Button btncancelxuat = (Button)dialogxuat.findViewById(R.id.btncancelxuat);
                        tvnamexuat.setText(productdilog.getName());
                        tvcodexuat.setText(productdilog.getCode());
                        tvsoluongbandau.setText(String.valueOf(soluongproduct));
                        //btn xuat xuat
                        btnxuatxuat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (validate(soluongproduct,Integer.parseInt(etsoluongxuat.getText().toString()))){
                                }else {
                                    Ticket temp = new Ticket();
                                    // lay ngay xuat
                                    final Calendar c = Calendar.getInstance();
                                    int  mYear = c.get(Calendar.YEAR);
                                    int mMonth = c.get(Calendar.MONTH);
                                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                                    String date = (mDay + "-" + mMonth + "-" + mYear);

                                    temp.setId(null);
                                    temp.setType(false);
                                    temp.setproductName(productdilog.getName());
                                    temp.setQuantity((ticket1.getQuantity()-Integer.parseInt(etsoluongxuat.getText().toString())));
                                    temp.setDate(date);
                                    DaoTicket.insertTicket(temp);
                                    Toast.makeText(getActivity(), "Xuat kho thanh cong", Toast.LENGTH_SHORT).show();
                                    dialogxuat.cancel();
                                }

                            }
                        });


                        // btn cancel xuat
                        btncancelxuat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogxuat.cancel();
                            }
                        });
                        dialogxuat.show();
                    }
                });


                        btnxoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // btn xoa
                        if(isAdmin) {
                            DaoProducts.deleteproduct(productdilog);
                            listProduct = DaoProducts.getAllItem();
                            productAdapter = new ProductAdapter(context, listProduct);
                            productList.setAdapter(productAdapter);
                            productList.deferNotifyDataSetChanged();
                            dialog.cancel();
                        } else{
                            Toast.makeText(context, "Only Admin can Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
// button huy
                Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }



            public boolean validate(int a, int b){
                if(a<b){
                    Toast.makeText(getActivity(), "So luong product khong du", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

    @Override
    public void onResume() {
        listTeck = DaoTicket.getAllItem();
        listProduct = DaoProducts.getAllItem();
        productAdapter = new ProductAdapter(context,listProduct);
        productList.setAdapter(productAdapter);
        productList.deferNotifyDataSetChanged();

        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String setSearch = remoAccent(charSequence.toString());
                productAdapter.filter(setSearch.trim());
                productAdapter.notifyDataSetChanged();
                productList.setAdapter(productAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String setSearch = remoAccent(editable.toString());
                productAdapter.filter(setSearch.trim());
                productAdapter.notifyDataSetChanged();
                productList.setAdapter(productAdapter);
            }
        });

        super.onResume();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), AddTicketActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

//    public void DialogInfo(Inflater inflater){
//        View view =
//    }
}
