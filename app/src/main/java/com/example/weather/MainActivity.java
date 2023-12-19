package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private TextView text,address,updated_at,status,feels,cloudness,wind,pressure,humidity;
    private EditText edVille;
    private Button btn;
    RequestQueue queue;
    DecimalFormat df = new DecimalFormat("#.##");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updated_at=findViewById(R.id.updated_at);
        status=findViewById(R.id.status);
        feels=findViewById(R.id.feels);
        humidity=findViewById(R.id.humidity);
        pressure=findViewById(R.id.pressure);
        cloudness=findViewById(R.id.nnn);
        wind=findViewById(R.id.wind);
        text=findViewById(R.id.temp);
        edVille=findViewById(R.id.etCity);
        queue = Volley.newRequestQueue(this);
        btn=findViewById(R.id.search);
        address =findViewById(R.id.address);

        Calendar calendar;
        SimpleDateFormat simpleDateFormat;
        calendar = Calendar.getInstance();
        String dateTime;
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+"gafsa"+"&appid=fe8a70c62f07172592b0a98e9ae624b9";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject r = new JSONObject(response);
                        //text.setText(response.toString());

                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String description = jsonObjectWeather.getString("description");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            double temp = jsonObjectMain.getDouble("temp") - 273.15;
                            double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                            float pressuree = jsonObjectMain.getInt("pressure");
                            int humidityy = jsonObjectMain.getInt("humidity");
                            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                            String windd = jsonObjectWind.getString("speed");
                            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                            String clouds = jsonObjectClouds.getString("all");
                            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                            String countryName = jsonObjectSys.getString("country");
                            String cityName = jsonResponse.getString("name");
                            address.setText(cityName + " (" + countryName + ")");
                            text.setText(df.format(temp) + " °C");
                            feels.setText(df.format(feelsLike) + " °C");
                            humidity.setText(humidityy + "%");
                            status.setText(description);
                            wind.setText(windd + "m/s ");
                            cloudness.setText(clouds + "%");
                            pressure.setText(pressuree + " hPa");
                            updated_at.setText(dateTime);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                text.setText(e.toString());
            }
        });
        queue.add(stringRequest);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather();
            }
        });

    }

    private void getWeather() {
        String ville=edVille.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+ville+"&appid=fe8a70c62f07172592b0a98e9ae624b9";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject r = new JSONObject(response);
                        //text.setText(response.toString());

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String description = jsonObjectWeather.getString("description");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            double temp = jsonObjectMain.getDouble("temp") - 273.15;
                            double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                            float pressuree = jsonObjectMain.getInt("pressure");
                            int humidityy = jsonObjectMain.getInt("humidity");
                            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                            String windd = jsonObjectWind.getString("speed");
                            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                            String clouds = jsonObjectClouds.getString("all");
                            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                            String countryName = jsonObjectSys.getString("country");
                            String cityName = jsonResponse.getString("name");
                            address.setText(cityName + " (" + countryName + ")");
                            text.setText(df.format(temp) + " °C");
                            feels.setText(df.format(feelsLike) + " °C");
                            humidity.setText(humidityy + "%");
                            status.setText(description);
                            wind.setText(windd + "m/s ");
                            cloudness.setText(clouds + "%");
                            pressure.setText(pressuree + " hPa");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                text.setText("Error");
            }
        });
        queue.add(stringRequest);
    }
}