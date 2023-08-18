package com.example.assignment3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class DeleteCompanyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_company);

        Intent intent = getIntent();
        String companyName = intent.getStringExtra("Name");

        Button confirmDeleteButton = findViewById(R.id.company_form_activity_delete_button);
        Button cancelButton = findViewById(R.id.company_form_activity_take_photo_button); // Just using an existing button for demonstration

        confirmDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog(companyName);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showDeleteConfirmationDialog(final String companyName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this company?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteCompany(companyName);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing, simply dismiss the dialog
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteCompany(String companyName) {
        int index = getIndexOfCompanyByCompanyName(companyName);
        if (index != -1) {
            companyData.remove(index);
            try {
                String newContents = generateCompanyOverWriteContents();
                overWriteCompanyFileContent(newContents);
                startActivity(new Intent(DeleteCompanyActivity.this, CompanyMainActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }
}
