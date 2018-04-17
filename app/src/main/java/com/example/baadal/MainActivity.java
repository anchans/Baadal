package com.example.baadal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;

    private Button mButtonDo;
    private TextView mTextView;
    private String url = "https://api.particle.io/v1/devices/37001c001147353136383631/bpm?access_token=2928487f14bbd22707b07250a11df02714d8ca73";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        // Get the widget reference from XML layout
        mButtonDo = (Button) findViewById(R.id.bn);
        mTextView = (TextView) findViewById(R.id.value);

        // Set a click listener for button widget
        mButtonDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new RequestQueue instance
                final RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new StringRequest
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Do something with response string
                                try {

                                    String bpmvalues = response.getString("result");
                                    if(Integer.parseInt(bpmvalues) < 55 || Integer.parseInt(bpmvalues) > 110)
                                    {
                                        Toast.makeText(mActivity, "Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        mTextView.setText(bpmvalues);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("Response",response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do something when get error
                                Toast.makeText(mActivity, error.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("Error.Response","Error");
                            }
                        }
                );

                // Add StringRequest to the RequestQueue
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
