package com.example.assignment3;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyAdapter extends ArrayAdapter<Company> implements Filterable {
    List<Company> filteredCompanies = new ArrayList<>();
    List<Company> allCompany;
    List<Company> originalCompanyList;

    public CompanyAdapter(Context applicationContext, List<Company> companyData) {
        super(applicationContext, R.layout.company_list_view_item, companyData);

        allCompany = companyData;
        originalCompanyList = new ArrayList<>(allCompany);
    }

    @Override
    public View getView(int i, View view, ViewGroup parent){
        Company company = getItem(i);

        if(view == null){
            view = LinearLayout.inflate(getContext(), R.layout.company_list_view_item, null);
        }

        ImageView image = view.findViewById(R.id.company_list_view_item_image);
        TextView name = view.findViewById(R.id.company_list_view_item_name);
        TextView address = view.findViewById(R.id.company_list_view_item_address);
        TextView carsSold = view.findViewById(R.id.company_list_view_item_cars_sold);
//        TextView totalProfit = view.findViewById(R.id.company_list_view_item_total_profit);

        name.setText(company.getName());
        address.setText(company.getAddress());
        carsSold.setText(company.getCarsSold());
//        totalProfit.setText((int) company.getTotalProfit());

        File file = new File(company.getImage());

        if(file.exists()){
            image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        } else {
            image.setImageResource(R.drawable.company_main);
        }

        return view;
    }

    public void myFilter(String text){
        if(text.length() == 0){
            allCompany.clear();
            allCompany.addAll(originalCompanyList);
            notifyDataSetChanged();
            return;
        }

        filteredCompanies.clear();
        filteredCompanies.addAll(originalCompanyList.stream().filter(c -> c.getMatch(text)).collect(Collectors.toList()));

        allCompany.clear();
        allCompany.addAll(filteredCompanies);
        notifyDataSetChanged();
    }
}
