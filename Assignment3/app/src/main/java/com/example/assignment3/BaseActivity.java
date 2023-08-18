package com.example.assignment3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BaseActivity extends AppCompatActivity {
    ListView vehicleList;
    ArrayList<Vehicle> vehicleData = new ArrayList<>();
    List<String> vehicleMakes = new ArrayList<>();
    final String db_vehicle_file = "myvehicledata";

    List<Company> companyData = new ArrayList<>();
    List<String> companyNames = new ArrayList<>();
    final String db_company_file = "mycompanydata";

    final String FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    public static final int to_locate = 100;
    public static final int retrieve_images = 900;
    Random random = new Random();

    public String randomStringGenerator(int limit){
        int leftLimit = 48;
        int rightLimit = 122;

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(limit)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


    }

    // Use the passed Context object to open the file for reading
    protected void readFileContents() {
        try {
            FileInputStream fileInputStream = openFileInput(db_vehicle_file);
            Scanner scanner = new Scanner(fileInputStream);

            while (scanner.hasNext()) {
                String content = scanner.nextLine();
                Vehicle vehicle = new Vehicle();
                String[] data = content.split(",");
                vehicle.setImage(data[0]);
                vehicle.setMake(data[1]);
                vehicle.setModel(data[2]);
                vehicle.setCondition(data[3]);
                vehicle.setEngineCylinders(Integer.parseInt(data[4]));
                vehicle.setYear(Integer.parseInt(data[5]));
                vehicle.setNumberOfDoors(Integer.parseInt(data[6]));
                vehicle.setPrice(Double.parseDouble(data[7]));
                vehicle.setColor(data[8]);
                vehicle.setDateSold(data[9]);
                vehicleData.add(vehicle);
                vehicleMakes.add(vehicle.getMake());

                fileInputStream.close();


                Log.d("MyTag", "vehicleData: " + vehicleData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void overWriteFileContent(String content) throws Exception{
        FileOutputStream fileOutputStream = openFileOutput(db_vehicle_file, Context.MODE_PRIVATE);
        fileOutputStream.write(content.getBytes());
    }

    protected void writeFileContents(String content) throws Exception{

        FileOutputStream fileOutputStream = openFileOutput(db_vehicle_file, Context.MODE_APPEND);

        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();

    }

    protected void clearContents() throws Exception{

        FileOutputStream fileOutputStream = openFileOutput(db_vehicle_file, Context.MODE_PRIVATE);

        fileOutputStream.write("".getBytes());
        fileOutputStream.close();

    }

    public int getIndexOfVehicleByVehicleMake(String vehicleMake){
        for (int i = 0; i < vehicleData.size(); i++){
            if(vehicleData.get(i).getMake().equals(vehicleMake)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId(); // Store the itemId in a variable

        if (itemId == R.id.menu_item_vehicle_list) {
            // Create an Intent
            Intent intent = new Intent(this, MainActivity.class);
            // Start new activity
            startActivity(intent);
            return true; // Return true to indicate that the action was handled
        } else if (itemId == R.id.menu_item_company_list) {
            Intent intent = new Intent(this, CompanyMainActivity.class); // change to company
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item); // Call the parent method for other cases
    }

    public void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public String generateOverWriteContents(){
        StringBuilder sb = new StringBuilder();
        for(Vehicle vehicle : vehicleData){
            sb.append(vehicle.writeToFile());
        }
        return sb.toString();
    }

    // company methods
    protected void readCompanyContent() throws Exception{
        FileInputStream fileInputStream = openFileInput(db_company_file);
        Scanner scanner = new Scanner(fileInputStream);

        while(scanner.hasNext()){
            String content = scanner.nextLine();
            Company company = new Company();
            String[] data = content.split(",");
            company.setImage(data[0]);
            company.setName(data[1]);
            company.setAddress(data[2]);
            company.setCarsSold(Integer.parseInt(data[3]));
            company.setTotalProfit(Double.parseDouble(data[4]));
            companyData.add(company);
            companyNames.add(company.getName());
        }
    }

    protected void overWriteCompanyFileContent(String content) throws Exception{
        FileOutputStream fileOutputStream = openFileOutput(db_company_file, Context.MODE_PRIVATE);
        fileOutputStream.write(content.getBytes());
    }

    protected void writeCompanyFileContents(String content) throws Exception{
        FileOutputStream fileOutputStream = openFileOutput(db_company_file, Context.MODE_APPEND);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
    }

    protected void clearCompanyContents() throws Exception{

        FileOutputStream fileOutputStream = openFileOutput(db_company_file, Context.MODE_PRIVATE);

        fileOutputStream.write("".getBytes());
        fileOutputStream.close();

    }

    public String generateCompanyOverWriteContents(){
        StringBuilder sb = new StringBuilder();
        for(Company company : companyData){
            sb.append(company.writeToFile());
        }
        return sb.toString();
    }

    public int getIndexOfCompanyByCompanyName(String companyName){
        for (int i = 0; i < companyData.size(); i++){
            if(companyData.get(i).getName().equals(companyName)){
                return i;
            }
        }
        return -1;
    }

    public void askForPermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(!Environment.isExternalStorageManager()){
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }
    }
}