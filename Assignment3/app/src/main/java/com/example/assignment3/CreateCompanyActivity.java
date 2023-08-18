package com.example.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateCompanyActivity extends BaseActivity{
    ImageView image_selected;
    Company company = new Company();
    private final String default_profile_image = "company_main.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_form);

        TextView pageTitle = findViewById(R.id.company_form_activity_title);
        Button submitButton = findViewById(R.id.company_form_activity_submit_button);
        submitButton.setText(submitButton.getText().toString().replace("Action", "Submit"));
        pageTitle.setText(R.string.vehicle_form_title);


        image_selected = findViewById(R.id.company_form_activity_image);
        company.setImage(default_profile_image);

        Button createButton = findViewById(R.id.company_form_activity_submit_button);
        createButton.setVisibility(View.INVISIBLE);

        Button deleteButton = findViewById(R.id.company_form_activity_delete_button);
        deleteButton.setVisibility(View.INVISIBLE);

        Button takePhoto = findViewById(R.id.company_form_activity_take_photo_button);
        Button choosePhoto = findViewById(R.id.company_form_activity_choose_photo_button);

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView name = findViewById(R.id.company_form_activity_company_name_field);
                TextView address = findViewById(R.id.company_form_activity_address_field);
                TextView carsSold = findViewById(R.id.company_form_activity_cars_sold_field);

                try {
                    name.setText(name.getText().toString());
                    address.setText(address.getText().toString());
                    carsSold.setText(carsSold.getText().toString());

                    if(companyNames.contains(name.getText())){
                        throw new Exception("The company already exists");
                    }
                }catch (Exception e){
                    Toast.makeText(CreateCompanyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    writeFileContents(company.writeToFile());
                    startActivity(new Intent(CreateCompanyActivity.this, CompanyMainActivity.class));
                }catch (Exception e){
                    Toast.makeText(CreateCompanyActivity.this, "Could not write to file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        askForPermissions();

        try {
            readCompanyContent();
        }catch (Exception e){
            Toast.makeText(this, "Can not read file", Toast.LENGTH_SHORT).show();
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
        company.setImage(file_path);

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

}
