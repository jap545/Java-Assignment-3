package com.example.assignment3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DeleteVehicleActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

        // Retrieve the Make from the Intent
        String make = getIntent().getStringExtra("Make");

        // Handle the delete button click
        Button deleteButton = findViewById(R.id.vehicle_form_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find the index of the vehicle by Make
                int index = getIndexOfVehicleByVehicleMake(make);

                if (index != -1) {
                    // Remove the vehicle from the list
                    vehicleData.remove(index);

                    try {
                        // Generate the updated content
                        String content = generateOverWriteContents();

                        // Write the updated content to the file
                        writeFileContents(content);

                        // Display success message
                        Toast.makeText(DeleteVehicleActivity.this, "Vehicle deleted successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(DeleteVehicleActivity.this, "Error deleting vehicle", Toast.LENGTH_SHORT).show();
                    }

                    // Finish the activity
                    finish();
                } else {
                    Toast.makeText(DeleteVehicleActivity.this, "Vehicle not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
