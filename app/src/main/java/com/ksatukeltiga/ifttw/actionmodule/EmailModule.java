package com.ksatukeltiga.ifttw.actionmodule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EmailModule extends ActionModule {
    private String title;
    private String body;
    private String from;
    private String to;
    // Jangan digunakan untuk macam macam
    private final String API_KEY = "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";
    private final String defaultJson = "{\"personalizations\": [{\"to\": [{\"email\": \"\"}]}],\"from\": {\"email\": \"\"},\"subject\": \"\",\"content\": [{\"type\": \"text/plain\", \"value\": \"\"}]}";
    private final String endpoint = "https://api.sendgrid.com/v3/mail/send";
    public EmailModule()
    {
        super(0,
                "",
                "EmailModule",
                "");
    }
    public EmailModule(String title, String body, String from, String to)
    {
        super(0,
                title + "---" + body + "---" + from + "---" + to,
                "EmailModule",
                "Email from (" + from + "), to (" + to + ")\n"
                + "(" + title + ")\n"
                + body);
        this.setData(this.data);
    }

    @Override
    public void setData(String data)
    {
        this.data = data;
        String[] temp = data.split("---");
        Log.println(Log.INFO, "EmailModule", "SetData : " + data + " " + Arrays.toString(temp));
        if(temp.length == 4) {
            this.title = temp[0];
            this.body = temp[1];
            this.from = temp[2];
            this.to = temp[3];
        }
        updateActionString();
    }

    @Override
    public void updateActionString() {
        this.actionString = "Email from (" + this.from + "), to (" + this.to + ")\n"
                + "(" + this.title + ")\n"
                + this.body;
    }

    @Override
    public Bundle getBundle()
    {
        Bundle bundle = new Bundle();
        bundle.putString("title", this.title);
        bundle.putString("body", this.body);
        bundle.putString("from", this.from);
        bundle.putString("to", this.to);
        return bundle;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String title = workIntent.getStringExtra("title");
        String body = workIntent.getStringExtra("body");
        String from = workIntent.getStringExtra("from");
        String to = workIntent.getStringExtra("to");
        Log.println(Log.INFO, "EmailModule", "onHandleIntent " + title + " " + body + " " + from
                    + " " + to);
        try {
            JSONObject sendToAPI = new JSONObject(defaultJson);

            JSONObject fromObj = sendToAPI.getJSONObject("from");
            fromObj.put("email", from);
            sendToAPI.put("from", fromObj);

            JSONArray toArray = sendToAPI.getJSONArray("personalizations").getJSONObject(0).getJSONArray("to");
            JSONObject toArrayObj = toArray.getJSONObject(0);
            toArrayObj.put("email", to);
            toArray.put(0, toArrayObj);
            sendToAPI.put("to", toArray);

            sendToAPI.put("subject", title);

            JSONArray contentArray = sendToAPI.getJSONArray("content");
            JSONObject contentArrayObj = contentArray.getJSONObject(0);
            contentArrayObj.put("value", body);
            contentArray.put(0, contentArrayObj);
            sendToAPI.put("content", contentArray);

            // Kirim pakai post
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, endpoint, sendToAPI, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.println(Log.INFO, "EmailModule", "Response : " + response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.println(Log.INFO, "EmailModule", "Error : " + error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + API_KEY);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);

            Log.println(Log.INFO, "EmailModule", "Kirim email : " + body);
            Log.println(Log.DEBUG, "EmailModule", "kirim email harusnya");
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
