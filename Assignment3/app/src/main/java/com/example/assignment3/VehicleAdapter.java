package com.example.assignment3;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleAdapter extends ArrayAdapter<Vehicle> implements Filterable {
    List<Vehicle> filteredVehicles = new ArrayList<>();
    List<Vehicle> allVehicle;
    List<Vehicle> originalVehicleList;

    public VehicleAdapter(Context applicationContext, List<Vehicle> vehicleData) {
        super(applicationContext, R.layout.vehicle_list_view_item, vehicleData);

        allVehicle = vehicleData;
        originalVehicleList = new ArrayList<>(allVehicle);
    }

    @Override
    public View getView(int i, View view, ViewGroup parent){

        Vehicle vehicle = getItem(i);



        if(view == null){
            view = LinearLayout.inflate(getContext(), R.layout.vehicle_list_view_item, null);
        }


        ImageView image = view.findViewById(R.id.vehicle_list_view_item_image);
        TextView make = view.findViewById(R.id.vehicle_list_view_item_make);
        TextView model = view.findViewById(R.id.vehicle_list_view_item_model);
        TextView condition = view.findViewById(R.id.vehicle_list_view_item_condition);
//        TextView price = view.findViewById(R.id.vehicle_list_view_item_price);
        TextView dateSold = view.findViewById(R.id.vehicle_list_view_item_date_sold);


        make.setText(vehicle.getMake());
        model.setText(vehicle.getModel());
        condition.setText(vehicle.getCondition());
//        price.setText((int) vehicle.getPrice());
        dateSold.setText(vehicle.getDateSold());

        File file = new File(vehicle.getImage());


        if(file.exists()){
            image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }

        else{
            image.setImageResource(R.drawable.activity_main);
        }

        return view;
    }

    public void myFilter(String text){
        if(text.length() == 0){
            allVehicle.clear();
            allVehicle.addAll(originalVehicleList);
            notifyDataSetChanged();
            return;
        }

        filteredVehicles.clear();
        filteredVehicles.addAll(originalVehicleList.stream().filter(v -> v.getMatch(text)).collect(Collectors.toList()));

        allVehicle.clear();
        allVehicle.addAll(filteredVehicles);
        notifyDataSetChanged();
    }
}
