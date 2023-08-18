package com.example.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateVehicleActivity extends BaseActivity{
    ImageView image_selected;
    Vehicle vehicle = new Vehicle();
    private final String default_profile_image = "activity_main.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

        TextView pageTitle = findViewById(R.id.vehicle_form_title);
        Button pageButton = findViewById(R.id.vehicle_form_submit_button);
        pageButton.setText(pageButton.getText().toString().replace("Action", "Submit"));
        pageTitle.setText(R.string.vehicle_form_title);


        image_selected = findViewById(R.id.vehicle_form_image);
        vehicle.setImage(default_profile_image);

        Button createButton = findViewById(R.id.vehicle_form_edit_button);
        createButton.setVisibility(View.INVISIBLE);

        Button deleteButton = findViewById(R.id.vehicle_form_delete_button);
        deleteButton.setVisibility(View.INVISIBLE);

        Button takePhoto = findViewById(R.id.vehicle_form_take_photo_button);
        Button choosePhoto = findViewById(R.id.vehicle_form_choose_photo_button);

        takePhoto.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, to_locate);
        });

        choosePhoto.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Please choose image"), retrieve_images);
        });

        askForPermissions();
    }

    public void onUploadPhotoClick(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, to_locate);
    }

    public void onChoosePhotoClick(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult( Intent.createChooser( intent, "Please choose image"), retrieve_images );
    }

    public void onSubmitClick(View view){
        TextView make = findViewById(R.id.vehicle_form_make_field);
        TextView model = findViewById(R.id.vehicle_form_model_field);
        TextView condition = findViewById(R.id.vehicle_form_condition_field);
        TextView engineCylinders = findViewById(R.id.vehicle_form_engine_cylinders_field);
        TextView year = findViewById(R.id.vehicle_form_year_field);
        TextView numberOfDoors = findViewById(R.id.vehicle_form_number_of_doors_field);
        TextView price = findViewById(R.id.vehicle_form_price_field);
        TextView color = findViewById(R.id.vehicle_form_color_field);
        TextView dateSold = findViewById(R.id.vehicle_form_date_sold_field);

        try {
            vehicle.setMake(make.getText().toString());
            vehicle.setModel(condition.getText().toString());
            vehicle.setCondition(model.getText().toString());
            vehicle.setEngineCylinders(Integer.parseInt(engineCylinders.getText().toString()));
            vehicle.setYear(Integer.parseInt(year.getText().toString()));
            vehicle.setNumberOfDoors(Integer.parseInt(numberOfDoors.getText().toString()));
            vehicle.setPrice(Double.parseDouble(price.getText().toString()));
            vehicle.setColor(color.getText().toString());
            vehicle.setDateSold(dateSold.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        try{
            writeFileContents(vehicle.writeToFile());
            startActivity(new Intent(this, MainActivity.class));
        }
        catch (Exception e){
//            Toast.makeText(this, "Could not write to file", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int response, int result, Intent info){

        super.onActivityResult(response, result, info);

        String filename = randomStringGenerator(20) + ".png";

        String file_path = FILE_DIR + filename;
        File file = new File(file_path);
        Bitmap photo;
        InputStream stream;
        vehicle.setImage(file_path);

        if (response == to_locate) {

            photo = (Bitmap) info.getExtras().get("data");
            image_selected.setImageBitmap(photo);

            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (response == retrieve_images) {

            Uri locationOfImage = info.getData();

            try {
                stream = getContentResolver().openInputStream(locationOfImage);
                photo = BitmapFactory.decodeStream(stream);
                image_selected.setImageBitmap(photo);


                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    }

    public void askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);

            }

        }
    }
}
