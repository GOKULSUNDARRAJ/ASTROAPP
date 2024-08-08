package com.PK.astro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    EditText name,email,phone,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        name=findViewById(R.id.email);
        email=findViewById(R.id.password3);
        phone=findViewById(R.id.password);
        message=findViewById(R.id.password2);


    }

    public void gotologin1(View view) {


        if (name.getText().toString().isEmpty() ){

            name.setError("Name is Required");
        }else if (phone.getText().toString().isEmpty() ){

            phone.setError("Phone Number  is Required");
        }else {
            ApiService apiService = ApiClient4.getClient().create(ApiService.class);

            Enquiry enquiry = new Enquiry(name.getText().toString(), email.getText().toString(), phone.getText().toString(), message.getText().toString());

            Call<ApiResponse4> call = apiService.postEnquiry(enquiry);
            call.enqueue(new Callback<ApiResponse4>() {
                @Override
                public void onResponse(Call<ApiResponse4> call, Response<ApiResponse4> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(MessageActivity.this, response.body().getSuccess(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MessageActivity.this, "Submission failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse4> call, Throwable t) {
                    Toast.makeText(MessageActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    public void goback(View view) {
        onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit the Page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the app
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit the Page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the app
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }
}