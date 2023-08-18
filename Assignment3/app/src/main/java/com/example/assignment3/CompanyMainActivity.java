package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.stream.Stream;

public class CompanyMainActivity extends BaseActivity {

    CompanyAdapter companyAdapter;
    ListView companyList;
    SearchView searchCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);

        TextView msg = findViewById(R.id.company_main_activity_message);
        searchCompany = findViewById(R.id.company_main_activity_search);

        AlertDialog.Builder confirmClearContentsDialog = new AlertDialog.Builder(this);
        confirmClearContentsDialog.setTitle("Are you sure?");

        confirmClearContentsDialog.setMessage("Clear all contents? This will erase all data");
        confirmClearContentsDialog.setCancelable(false);

        confirmClearContentsDialog.setPositiveButton("Yes", (arg0, arg1) -> {
            try {
                clearContents();
                readFileContents();
                companyAdapter.notifyDataSetChanged();
                readFileContents();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        confirmClearContentsDialog.setNegativeButton("No", (dialog, which) -> Toast.makeText(CompanyMainActivity.this,"No clicked",Toast.LENGTH_SHORT).show());
        confirmClearContentsDialog.setNeutralButton("Cancel", (dialog, which) -> Toast.makeText(getApplicationContext(),"Cancel clicked",Toast.LENGTH_SHORT).show());

        AlertDialog alertDialog = confirmClearContentsDialog.create();

        View.OnClickListener onClickListener = view -> alertDialog.show();

        findViewById(R.id.company_main_activity_clear_button).setOnClickListener(onClickListener);

        try{

            readFileContents();

        }
        catch (Exception e){
            msg.setText("Company file not yet created");
        }

        Button button = findViewById(R.id.company_main_activity_reload_button);
        button.setOnClickListener(view -> {

            try{
                companyData.clear();
                readFileContents();
                companyAdapter.notifyDataSetChanged();
            }
            catch (Exception e){
                Toast.makeText(CompanyMainActivity.this, "Unable to complete function", Toast.LENGTH_SHORT).show();
            }

        });


        if(companyData.size() == 0){
            msg.setText("No companies entries");
        }
        else{
            msg.setText("Welcome to the company Listing Program");
        }

        Stream<Company> filtered = companyData.stream().filter(v -> v.getName().length() <= 3);

        companyList = findViewById(R.id.company_main_activity_list);
        companyAdapter = new CompanyAdapter(getApplicationContext(), companyData);
        companyList.setAdapter(companyAdapter);

        companyList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), ViewCompanyActivity.class);
            intent.putExtra("Name", companyData.get(position).getName());
            startActivity(intent);
        });

        SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                companyAdapter.myFilter(text);
                return false;
            }
        };

        searchCompany.setOnQueryTextListener(onQueryTextListener);

        searchCompany.setOnCloseListener(() -> {
            companyAdapter.myFilter("");
            return false;
        });
    }

    public void onCreateClick(View view){
        startActivity(new Intent(getApplicationContext(), CreateCompanyActivity.class));
    }
}