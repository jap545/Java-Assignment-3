package com.example.assignment3;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ViewVehicleActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

        Intent intent = getIntent();
        String Make = intent.getStringExtra("Make");

        try{
            readFileContents();
        }
        catch (Exception e){
            Toast.makeText(this, "Could not read file contents", Toast.LENGTH_SHORT).show();
        }
        Vehicle vehicle = vehicleData.stream().filter(v -> v.getMake().equals(Make)).findFirst().get();

        TextView pageTitle = findViewById(R.id.vehicle_form_title);
        Button pageButton = findViewById(R.id.vehicle_form_submit_button);
        Button editButton = findViewById(R.id.vehicle_form_edit_button);
        Button deleteButton = findViewById(R.id.vehicle_form_delete_button);

        pageButton.setVisibility(View.INVISIBLE);
        pageTitle.setText("View Vehicle");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditVehicleActivity.class);
                intent.putExtra("Make", Make);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeleteVehicleActivity.class);
                intent.putExtra("Make", Make);
                startActivity(intent);
            }
        });

        ImageView photo = findViewById(R.id.vehicle_form_image);
        EditText make = findViewById(R.id.vehicle_form_make_field);
        EditText model = findViewById(R.id.vehicle_form_model_field);
        EditText condition = findViewById(R.id.vehicle_form_condition_field);
        EditText engineCylinders = findViewById(R.id.vehicle_form_engine_cylinders_field);
        EditText year = findViewById(R.id.vehicle_form_year_field);
        EditText numberOfDoors = findViewById(R.id.vehicle_form_number_of_doors_field);
        EditText price = findViewById(R.id.vehicle_form_price_field);
        EditText color = findViewById(R.id.vehicle_form_color_field);
        EditText dateSold = findViewById(R.id.vehicle_form_date_sold_field);

        Button takePhoto = findViewById(R.id.vehicle_form_take_photo_button);
        Button choosePhoto = findViewById(R.id.vehicle_form_choose_photo_button);

        takePhoto.setVisibility(View.INVISIBLE);
        choosePhoto.setVisibility(View.INVISIBLE);


        make.setText(vehicle.getMake());
        model.setText(vehicle.getModel());
        condition.setText(vehicle.getCondition());
        engineCylinders.setText(vehicle.getEngineCylinders());
        year.setText(vehicle.getYear());
        numberOfDoors.setText(vehicle.getNumberOfDoors());
        price.setText((int) vehicle.getPrice());
        color.setText(vehicle.getColor());
        dateSold.setText(vehicle.getDateSold());

        disableEditText(make);
        disableEditText(model);
        disableEditText(condition);
        disableEditText(engineCylinders);
        disableEditText(year);
        disableEditText(numberOfDoors);
        disableEditText(price);
        disableEditText(color);
        disableEditText(dateSold);

        File file = new File(vehicle.getImage());

        if(file.exists()){
            photo.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }
        else{
            photo.setImageResource(R.drawable.activity_main);
        }
    }
}
