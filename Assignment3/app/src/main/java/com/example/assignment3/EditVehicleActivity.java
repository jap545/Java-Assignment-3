package com.example.assignment3;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class EditVehicleActivity extends BaseActivity {

    private EditText makeEditText;
    private EditText modelEditText;
    private EditText conditionEditText;
    private EditText engineCylindersEditText;
    private EditText yearEditText;
    private EditText numberOfDoorsEditText;
    private EditText priceEditText;
    private EditText colorEditText;
    private EditText dateSoldEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        makeEditText = findViewById(R.id.edit_vehicle_make);
        modelEditText = findViewById(R.id.edit_vehicle_model);
        conditionEditText = findViewById(R.id.edit_vehicle_condition);
        engineCylindersEditText = findViewById(R.id.edit_vehicle_engine_cylinders);
        yearEditText = findViewById(R.id.edit_vehicle_year);
        numberOfDoorsEditText = findViewById(R.id.edit_vehicle_number_of_doors);
        priceEditText = findViewById(R.id.edit_vehicle_price);
        colorEditText = findViewById(R.id.edit_vehicle_color);
        dateSoldEditText = findViewById(R.id.edit_vehicle_date_sold);

        Button saveButton = findViewById(R.id.edit_vehicle_save_button);
        Button cancelButton = findViewById(R.id.edit_vehicle_cancel_button);
        ImageView vehicleImage = findViewById(R.id.edit_vehicle_image);

        String make = getIntent().getStringExtra("Make");
        Vehicle vehicle = getVehicleByMake(make);

        if (vehicle != null) {
            makeEditText.setText(vehicle.getMake());
            modelEditText.setText(vehicle.getModel());
            conditionEditText.setText(vehicle.getCondition());
            engineCylindersEditText.setText(vehicle.getEngineCylinders());
            yearEditText.setText(vehicle.getYear());
            numberOfDoorsEditText.setText(vehicle.getNumberOfDoors());
            priceEditText.setText(String.valueOf(vehicle.getPrice()));
            colorEditText.setText(vehicle.getColor());
            dateSoldEditText.setText(vehicle.getDateSold());

            File imageFile = new File(vehicle.getImage());
            if (imageFile.exists()) {
                vehicleImage.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
            } else {
                vehicleImage.setImageResource(R.drawable.activity_main);
            }
        } else {
            Toast.makeText(this, "Vehicle not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVehicleDetails(make);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateVehicleDetails(String make) {
        String updatedMake = makeEditText.getText().toString();
        String updatedModel = modelEditText.getText().toString();
        String updatedCondition = conditionEditText.getText().toString();
        String updatedEngineCylinders = engineCylindersEditText.getText().toString();
        String updatedYear = yearEditText.getText().toString();
        String updatedNumberOfDoors = numberOfDoorsEditText.getText().toString();
        double updatedPrice = Double.parseDouble(priceEditText.getText().toString());
        String updatedColor = colorEditText.getText().toString();
        String updatedDateSold = dateSoldEditText.getText().toString();

        /*Vehicle updatedVehicle = new Vehicle(updatedMake, updatedModel, updatedCondition,
                updatedEngineCylinders, updatedYear, updatedNumberOfDoors, updatedPrice,
                updatedColor, updatedDateSold,);
        */
        // Update the vehicle in your data source using the updatedVehicle object
       // vehicleData.updateVehicle(updatedVehicle);
    }

    private Vehicle getVehicleByMake(String make) {
        // Implement the logic to retrieve a vehicle by make from your data source
        // Return the retrieved vehicle object or null if not found
        return null;
    }
}
