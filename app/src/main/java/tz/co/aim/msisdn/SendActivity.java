package tz.co.aim.msisdn;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {
    TextInputLayout textInputLayoutMSISDN;
    EditText customerMSISDN, agentMSISDN;
    Button buttonSend;
    String url;
    private static final String TAG = SendActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);



        textInputLayoutMSISDN =(TextInputLayout) findViewById(R.id.textInputCustomerMSISDN);
        customerMSISDN = (EditText)findViewById(R.id.customerMSISDN);
        final String custmrMSISDN = customerMSISDN.getText().toString();
        agentMSISDN = (EditText)findViewById(R.id.agentMSISDN);
        final String agntMSISDN = agentMSISDN.getText().toString();
        buttonSend = (Button)findViewById(R.id.buttonSend);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                url = "https://tigodemo.registersim.com/api/registration?msisdn=" + customerMSISDN.getText().toString() + "&agentMsisdn=" + agentMSISDN.getText().toString();

                makeJsonArrayRequest();
            }
        });
    }

    private void makeJsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());
                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        JSONObject registrationId = jsonObject.getJSONObject("registrationId");
                        String firstName = registrationId.getString("firstName");
                        String middleName = registrationId.getString("middleName");
                        String lastName = registrationId.getString("lastName");
                        String gender = registrationId.getString("gender");
                        String address = registrationId.getString("address");
                        String wards = registrationId.getString("wards");
                        String identification = registrationId.getString("identification");
                        String dob = registrationId.getString("dob");


                        JSONObject registrationTime = registrationId.getJSONObject("registrationTime");
                        String date = registrationTime.getString("date");
                        String timezone = registrationTime.getString("timezone");

                        HashMap<String,String>contactDetails = new HashMap<>();

                        contactDetails.put("firstName", firstName);
                        contactDetails.put("middleName", middleName);
                        contactDetails.put("lastName" , lastName);
                        contactDetails.put("gender", gender);
                        contactDetails.put("address", address);
                        contactDetails.put("wards",wards);
                        contactDetails.put("identification", identification);
                        contactDetails.put("dob", dob);
                        contactDetails.put("date",date);
                        contactDetails.put("timezone",timezone);

                        Intent intent = new Intent(SendActivity.this, List_Activity.class);
                        intent.putExtra("map",contactDetails);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    Log.e(TAG,"Unable to get Data from the Server");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.e(TAG,"Error: " +error.getMessage());
                Toast.makeText(getApplicationContext(),"Error: " +error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

}