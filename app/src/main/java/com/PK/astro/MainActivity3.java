package com.PK.astro;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<HomeItem> homeItemList;

    static  final  float Endscale=0.7f;
    ImageView menu,whatsapp;
    ProgressBar progressBar;
    LinearLayout sim;
    DrawerLayout drawer_layout;
    ConstraintLayout contentView,id10,id11,id12,id13,id14,id15;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);






        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pkgastrologer.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Make the API call
        Call<List<ImageResponse>> call = apiService.getImages();
        call.enqueue(new Callback<List<ImageResponse>>() {
            @Override
            public void onResponse(Call<List<ImageResponse>> call, Response<List<ImageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ImageResponse> images = response.body();
                    if (!images.isEmpty()) {
                        String imgPath = images.get(0).getImg_path();
                        String image = images.get(0).getImage();
                        String imageUrl = imgPath + image;

                        showCustomAlertDialog(imageUrl);


                    }
                } else {
                    Log.e("MainActivity", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<ImageResponse>> call, Throwable t) {
                Log.e("MainActivity", "API call failed", t);
            }
        });








        id10=findViewById(R.id.id10);
        id11=findViewById(R.id.id11);
        id12=findViewById(R.id.id12);
        id13=findViewById(R.id.id13);
        id14=findViewById(R.id.id14);
        id15=findViewById(R.id.id15);

        id15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),DetailActivity15.class);
                startActivity(intent1);
            }
        });

        id14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),DetailActivity14.class);
                startActivity(intent1);
            }
        });

        id13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),DetailActivity13.class);
                startActivity(intent1);
            }
        });

        id12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),DetailActivity12.class);
                startActivity(intent1);
            }
        });

        id11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),DetailActivity11.class);
                startActivity(intent1);
            }
        });
        id10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),DetailActivity10.class);
                startActivity(intent1);
            }
        });

        boolean useCustomTransition = getIntent().getBooleanExtra("TRANSITION", false);
        if (useCustomTransition) {
            overridePendingTransition(R.anim.slid_from_right, R.anim.slid_to_left);
        }

        drawer_layout=findViewById(R.id.drawer_layout);

        sim=findViewById(R.id.sim);
        contentView=findViewById(R.id.context);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    return true;
                } else if (item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(MainActivity3.this, SearchActivity.class);
                    startActivity(intent);

                    return true;
                } else if (item.getItemId() == R.id.nav_settings) {
                    Intent intent = new Intent(MainActivity3.this, ContactActivity.class);
                    startActivity(intent);

                    return true;
                }
                else if (item.getItemId() == R.id.nav_about) {
                    Intent intent = new Intent(MainActivity3.this, ProfileActivity.class);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });

        animateNavigationdrawer();
        progressBar = findViewById(R.id.progressBar);

        menu=findViewById(R.id.imageView);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    return true;
                }if (id==R.id.nav_profile){
                    Intent intent = new Intent(MainActivity3.this, SearchActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slid_from_right,R.anim.slid_to_left);

                    return true;
                }if (id==R.id.nav_settings){
                    Intent intent = new Intent(MainActivity3.this, MapActivity.class);
                    startActivity(intent);

                }
                if (id==R.id.nav_about){
                    Intent intent = new Intent(MainActivity3.this, ContactActivity.class);
                    startActivity(intent);

                }
                if (id==R.id.nav_contact){
                    Intent intent = new Intent(MainActivity3.this, ProfileActivity.class);
                    startActivity(intent);

                }
                if (id==R.id.nav_Enquiry){
                    Intent intent = new Intent(MainActivity3.this, MessageActivity.class);
                    startActivity(intent);

                }
                if (id==R.id.blog){
                    Intent intent = new Intent(MainActivity3.this, BlogActivity.class);
                    startActivity(intent);

                } if (id==R.id.TraningCentre){
                    Intent intent = new Intent(MainActivity3.this, TraningCentreActivity.class);
                    startActivity(intent);

                }


                // Add other menu item click handling here if needed
                return false;
            }
        });



        TextView textViewDate = findViewById(R.id.id);
        String currentDate = getCurrentDate();
        textViewDate.setText(currentDate);

        TextView textViewDate1 = findViewById(R.id.name);
        String currentDate1 = TamilDateUtil.getCurrentTamilDate()+"\t"+","+getCurrentDayNameInTamil();
        textViewDate1.setText(currentDate1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Set 2 columns in the grid

        fetchHomeItems();
    }

    private String getCurrentTamilDate() {
        // Tamil months in Tamil script
        String[] tamilMonths = {
                "சித்திரை", "வைகாசி", "ஆனி", "ஆடி", "ஆவணி", "புரட்டாசி", "ஐப்பசி", "கார்த்திகை", "மார்கழி", "தை", "மாசி", "பங்குனி"
        };

        // Corresponding start dates for Tamil months in Gregorian calendar
        int[][] monthStartDates = {
                {14, 4},  // சித்திரை starts on 14th April
                {15, 5},  // வைகாசி starts on 15th May
                {15, 6},  // ஆனி starts on 15th June
                {16, 7},  // ஆடி starts on 16th July
                {17, 8},  // ஆவணி starts on 17th August
                {17, 9},  // புரட்டாசி starts on 17th September
                {17, 10}, // ஐப்பசி starts on 17th October
                {16, 11}, // கார்த்திகை starts on 16th November
                {16, 12}, // மார்கழி starts on 16th December
                {14, 1},  // தை starts on 14th January
                {13, 2},  // மாசி starts on 13th February
                {14, 3}   // பங்குனி starts on 14th March
        };

        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;  // Months are indexed from 0
        int currentYear = calendar.get(Calendar.YEAR);

        String tamilMonth = "";
        int tamilDay = currentDay;

        for (int i = 0; i < monthStartDates.length; i++) {
            int startDay = monthStartDates[i][0];
            int startMonth = monthStartDates[i][1];
            int nextIndex = (i + 1) % monthStartDates.length;
            int nextStartDay = monthStartDates[nextIndex][0];
            int nextStartMonth = monthStartDates[nextIndex][1];

            if ((currentMonth == startMonth && currentDay >= startDay) ||
                    (currentMonth == nextStartMonth && currentDay < nextStartDay)) {
                tamilMonth = tamilMonths[i];
                if (currentMonth == startMonth && currentDay >= startDay) {
                    tamilDay = currentDay - startDay + 1;
                } else if (currentMonth == nextStartMonth && currentDay < nextStartDay) {
                    Calendar prevMonthCal = Calendar.getInstance();
                    prevMonthCal.set(Calendar.DAY_OF_MONTH, startDay);
                    prevMonthCal.set(Calendar.MONTH, startMonth - 1);
                    prevMonthCal.set(Calendar.YEAR, currentYear);
                    int maxDayInPrevMonth = prevMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    tamilDay = maxDayInPrevMonth - startDay + currentDay + 1;
                }
                break;
            }
        }

        return tamilDay + " " + tamilMonth;
    }

    private String getCurrentTamilMonth() {
        // Tamil months
        String[] tamilMonths = {
                "சித்திரை", "வைகாசி", "ஆனி", "ஆடி", "ஆவணி", "புரட்டாசி", "ஐப்பசி", "கார்த்திகை", "மார்கழி", "தை", "மாசி", "பங்குனி"
        };


        // Corresponding start dates for Tamil months in Gregorian calendar
        int[][] monthStartDates = {
                {14, 4},  // Chithirai starts on 14th April
                {15, 5},  // Vaikasi starts on 15th May
                {15, 6},  // Aani starts on 15th June
                {16, 7},  // Aadi starts on 16th July
                {17, 8},  // Avani starts on 17th August
                {17, 9},  // Purattasi starts on 17th September
                {17, 10}, // Aippasi starts on 17th October
                {16, 11}, // Karthigai starts on 16th November
                {16, 12}, // Margazhi starts on 16th December
                {14, 1},  // Thai starts on 14th January
                {13, 2},  // Maasi starts on 13th February
                {14, 3}   // Panguni starts on 14th March
        };

        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;  // Months are indexed from 0

        for (int i = 0; i < monthStartDates.length; i++) {
            int startDay = monthStartDates[i][0];
            int startMonth = monthStartDates[i][1];
            int nextIndex = (i + 1) % monthStartDates.length;
            int nextStartDay = monthStartDates[nextIndex][0];
            int nextStartMonth = monthStartDates[nextIndex][1];

            if ((currentMonth == startMonth && currentDay >= startDay) ||
                    (currentMonth == nextStartMonth && currentDay < nextStartDay)) {
                return tamilMonths[i];
            }
        }

        return "Unknown";
    }


    private static int getCurrentTamilMonthNumber() {
        // Get the current date
        Date date = new Date();

        // Get the Gregorian month (0-11) and day (1-31)
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int month = calendar.get(Calendar.MONTH); // 0 for January, 11 for December
        int day = calendar.get(Calendar.DAY_OF_MONTH); // 1-31

        // Mapping Gregorian month and day to Tamil month number
        int tamilMonthNumber;
        switch (month) {
            case Calendar.JANUARY:
                tamilMonthNumber = (day < 14) ? 9 : 10; // ஐப்பசி (Aippasi) or கார்த்திகை (Karthigai)
                break;
            case Calendar.FEBRUARY:
                tamilMonthNumber = (day < 13) ? 10 : 11; // கார்த்திகை (Karthigai) or மார்கழி (Margazhi)
                break;
            case Calendar.MARCH:
                tamilMonthNumber = (day < 14) ? 11 : 12; // மார்கழி (Margazhi) or தை (Thai)
                break;
            case Calendar.APRIL:
                tamilMonthNumber = (day < 14) ? 12 : 1;  // தை (Thai) or மாசி (Maasi)
                break;
            case Calendar.MAY:
                tamilMonthNumber = (day < 15) ? 1 : 2;   // மாசி (Maasi) or பங்குனி (Panguni)
                break;
            case Calendar.JUNE:
                tamilMonthNumber = (day < 15) ? 2 : 3;   // பங்குனி (Panguni) or சித்திரை (Chithirai)
                break;
            case Calendar.JULY:
                tamilMonthNumber = (day < 16) ? 3 : 4;   // சித்திரை (Chithirai) or வைகாசி (Vaigasi)
                break;
            case Calendar.AUGUST:
                tamilMonthNumber = (day < 16) ? 4 : 5;   // வைகாசி (Vaigasi) or ஆனி (Aani)
                break;
            case Calendar.SEPTEMBER:
                tamilMonthNumber = (day < 17) ? 5 : 6;   // ஆனி (Aani) or ஆடி (Aadi)
                break;
            case Calendar.OCTOBER:
                tamilMonthNumber = (day < 17) ? 6 : 7;   // ஆடி (Aadi) or ஆவணி (Avani)
                break;
            case Calendar.NOVEMBER:
                tamilMonthNumber = (day < 16) ? 7 : 8;   // ஆவணி (Avani) or புரட்டாசி (Purattasi)
                break;
            case Calendar.DECEMBER:
                tamilMonthNumber = (day < 15) ? 8 : 9;   // புரட்டாசி (Purattasi) or ஐப்பசி (Aippasi)
                break;
            default:
                tamilMonthNumber = 0; // Return 0 if month is out of bounds
        }

        return tamilMonthNumber;
    }

    private static String getCurrentDayNameInTamil() {
        // Define the Tamil day names
        String[] tamilDays = {
                "ஞாயிறு",   // ஞாயிற்றுக்கிழமை (Sunday)
                "திங்கள்",   // திங்கட்கிழமை (Monday)
                "செவ்வாய்",  // செவ்வாய்க்கிழமை (Tuesday)
                "புதன்",     // புதன்கிழமை (Wednesday)
                "வியாழன்",   // வியாழக்கிழமை (Thursday)
                "வெள்ளி",    // வெள்ளிக்கிழமை (Friday)
                "சனி"         // சனிக்கிழமை (Saturday)
        };

        // Get the current day of the week (0-6) where 0 is Sunday
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 1 for Sunday, 7 for Saturday

        // Return the Tamil day name corresponding to the current day of the week
        return tamilDays[dayOfWeek - 1];
    }

    private void fetchHomeItems() {
        progressBar.setVisibility(View.VISIBLE);
        sim.setVisibility(View.VISIBLE);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<HomeItem>> call = apiService.getHomeItems();


        call.enqueue(new Callback<List<HomeItem>>() {
            @Override
            public void onResponse(Call<List<HomeItem>> call, Response<List<HomeItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeItemList = response.body();
                    homeAdapter = new HomeAdapter(homeItemList, MainActivity3.this);
                    recyclerView.setAdapter(homeAdapter);
                    progressBar.setVisibility(View.GONE);
                    sim.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<HomeItem>> call, Throwable t) {
                Toast.makeText(MainActivity3.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                sim.setVisibility(View.GONE);
            }
        });
    }




    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }




    public void gotosearchactivity(View view) {
        startActivity(new Intent(view.getContext(),SearchActivity.class));
        overridePendingTransition(R.anim.slid_from_right,R.anim.slid_to_left);
    }

    public void openWhatsApp() {

        String url = "https://api.whatsapp.com/send?phone=tel:+91%2098425%2091763&text=%20&%20no%20is%20:";
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            // WhatsApp not installed. Handle this scenario.
            // For example, you might want to inform the user to install WhatsApp.
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
        }
    }
    private void animateNavigationdrawer() {


        drawer_layout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // This method is called when the drawer is sliding

                // Scale the View based on current slide offset
                // Here, you are scaling the contentView based on the slide offset

                final float diffScaledOffset = slideOffset * (1 - Endscale);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);


                // You are translating the contentView based on the slide offset
                final float xoffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xoffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    public void gotomapactivity(View view) {
        startActivity(new Intent(view.getContext(),MapActivity.class));
        overridePendingTransition(R.anim.slid_from_right,R.anim.slid_to_left);
    }

    private void showCustomAlertDialog(String imageUrl) {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup, null);

        // Initialize the views in the custom layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageViewAlert = alertLayout.findViewById(R.id.imagegift);


        imageViewAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),MessageActivity.class));

            }
        });

        Glide.with(this).load(imageUrl).into(imageViewAlert);

        // Create and show the AlertDialog
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        AlertDialog dialog = alert.create();
        dialog.show();



        imageViewAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),MessageActivity.class));
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit the App?")
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
