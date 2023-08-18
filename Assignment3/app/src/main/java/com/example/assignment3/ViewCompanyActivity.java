package com.example.assignment3;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ViewCompanyActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_form);

        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");

        try {
            readCompanyContent();
        } catch (Exception e){
            Toast.makeText(this, "Could not read the file Contents", Toast.LENGTH_SHORT).show();
        }
        Company company = companyData.stream().filter(c -> c.getName().equals(Name)).findFirst().get();

        TextView pageTitle = findViewById(R.id.company_form_activity_title);
        Button submitButton = findViewById(R.id.company_form_activity_submit_button);
        Button editButton = findViewById(R.id.company_form_activity_edit_button);
        Button deleteButton = findViewById(R.id.company_form_activity_delete_button);

        submitButton.setVisibility(View.INVISIBLE);
        pageTitle.setText("View Company");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditCompanyActivity.class);
                intent.putExtra("Name", Name);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeleteCompanyActivity.class);
                intent.putExtra("Name", Name);
                startActivity(intent);
            }
        });

        ImageView image = findViewById(R.id.company_form_activity_image);
        EditText name = findViewById(R.id.company_form_activity_company_name_field);
        EditText address = findViewById(R.id.company_form_activity_company_name_field);
        EditText carsSold = findViewById(R.id.company_form_activity_cars_sold_field);

        Button takePhotoButton = findViewById(R.id.company_form_activity_take_photo_button);
        Button choosePhotoButton = findViewById(R.id.company_form_activity_choose_photo_button);

        takePhotoButton.setVisibility(View.INVISIBLE);
        choosePhotoButton.setVisibility(View.INVISIBLE);

        name.setText(company.getName());
        address.setText(company.getAddress());
        carsSold.setText(company.getCarsSold());

        File file = new File(company.getImage());

        if(file.exists()){
            image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        } else {
            image.setImageResource(R.drawable.company_main);
        }
    }
}
