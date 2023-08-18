package com.example.assignment3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.stream.Stream;

public class MainActivity extends BaseActivity {

    VehicleAdapter vehicleAdapter;
    ListView vehicleList;
    SearchView searchVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView msg = findViewById(R.id.main_activity_message);
        searchVehicle = findViewById(R.id.main_activity_search);

        AlertDialog.Builder confirmClearContentsDialog = new AlertDialog.Builder(this);
        confirmClearContentsDialog.setTitle("Are you sure?");
        confirmClearContentsDialog.setMessage("Clear all contents? This will erase all data");
        confirmClearContentsDialog.setCancelable(false);

        confirmClearContentsDialog.setPositiveButton("Yes", (arg0, arg1) -> {
            try {
                clearContents();
                readFileContents();
                vehicleAdapter.notifyDataSetChanged();
                msg.setText("Welcome to the vehicle Listing Program");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        confirmClearContentsDialog.setNegativeButton("No", (dialog, which) -> {
            Toast.makeText(MainActivity.this, "Good decision. (No clicked)", Toast.LENGTH_SHORT).show();
        });

        confirmClearContentsDialog.setNeutralButton("Cancel", (dialog, which) -> {
            Toast.makeText(getApplicationContext(), "Close call (Cancel clicked)", Toast.LENGTH_SHORT).show();
        });

        AlertDialog alertDialog = confirmClearContentsDialog.create();

        View.OnClickListener onClickListener = view -> alertDialog.show();

        findViewById(R.id.main_activity_clear_button).setOnClickListener(onClickListener);

        try {
            readFileContents();
        } catch (Exception e) {
            msg.setText("Vehicle file not yet created");
        }

        Button button = findViewById(R.id.main_activity_reload_button);
        button.setOnClickListener(view -> {
            try {
                vehicleData.clear();
                readFileContents();
                vehicleAdapter.notifyDataSetChanged(); // Notify the adapter of the changes
                msg.setText("Welcome to the vehicle Listing Program");
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Unable to complete function", Toast.LENGTH_SHORT).show();
            }
        });

        if (vehicleData.size() == 0) {
            msg.setText("No vehicle entries");
        } else {
            msg.setText("Welcome to the vehicle Listing Program");
        }

        Stream<Vehicle> filtered = vehicleData.stream().filter(v -> v.getMake().length() <= 3);

        vehicleList = findViewById(R.id.main_activity_list_view);
        vehicleAdapter = new VehicleAdapter(getApplicationContext(), vehicleData);
        vehicleList.setAdapter(vehicleAdapter);

        vehicleList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), ViewVehicleActivity.class);
            intent.putExtra("Make", vehicleData.get(position).getMake());
            startActivity(intent);
        });

        SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                vehicleAdapter.myFilter(text);
                return false;
            }
        };

        searchVehicle.setOnQueryTextListener(onQueryTextListener);

        searchVehicle.setOnCloseListener(() -> {
            vehicleAdapter.myFilter("");
            return false;
        });
    }

    public void onCreateClick(View view) {
        startActivity(new Intent(getApplicationContext(), CreateVehicleActivity.class));
    }
}
