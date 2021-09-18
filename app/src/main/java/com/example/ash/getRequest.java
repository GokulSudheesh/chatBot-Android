package com.example.ash;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class getRequest {
    private RequestQueue queue;
    private String APIkey = "[API Key]";
    private String brainID = "[Brain id]";
    private String reply;
    private char[] illegalChars = {'#', '<', '>', '$', '+', '%', '!', '`', '&',
            '*', '\'', '\"', '|', '{', '}', '/', '\\', ':', '@'};

    public getRequest(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String reply);
    }

    private String formatMessage(String message){

        message = message.replace(' ', '-');
        for(char illegalChar : illegalChars){
            message = message.replace(illegalChar, '-');
        }
        return message;
    }

    public void getResponse(String message, final VolleyResponseListener volleyResponseListener){
        message = formatMessage(message);
        String url = "http://api.brainshop.ai/get?bid=" + brainID+ "&key=" + APIkey + "&uid=1&msg=" + message;
        Log.d("URL", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            reply = response.getString("cnt");
                            Log.d("RESPONSE", reply);
                            volleyResponseListener.onResponse(reply);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            volleyResponseListener.onError("JSON Exception");
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                        volleyResponseListener.onError("Volley Error");
                    }
                });
        queue.add(request);
    }
}
