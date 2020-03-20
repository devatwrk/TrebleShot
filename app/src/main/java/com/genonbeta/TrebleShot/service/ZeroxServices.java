package com.genonbeta.TrebleShot.service;


import com.android.volley.RequestQueue;

import org.json.JSONObject;
import android.content.Context;

public class ZeroxServices {

    Context contxt;

   /* public JSONObject backEndSignupAPICall(JSONObject json) {
        final JSONObject responseJson ;
// Instantiate the RequestQueue.
        SingletonRequestQueue queue = SingletonRequestQueue.getInstance(contxt);
        RequestQueue requestQueue = queue.getRequestQueue();


// public CustomeJsonObjectRequest(int method, String url, JSONObject jsonRequest,
//                                    Listener<JSONObject> listener, ErrorListener errorListener) {
//
        //
        CustomeJsonObjectRequest customRequest = null;

        try{

            customRequest = new CustomeJsonObjectRequest(Request.Method.POST, url, json,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(" Successful ***************" + response.getClass()+"\n\n\n"+response.toString());
                    try {
                        response.put("mobileNo", mobileNumber.getText().toString());
                        Intent intent = new Intent(getContext().getApplicationContext(), OTPVerificationActivity.class);

                        intent.putExtra("otpResponse", response.toString());
                        System.out.println("======== Put Extras =========== "+ response.toString());
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("============   Ganesh Ram 3 ===== \n\n\n" + error.toString());
                    System.out.println("============   Ganesh Ram 4===== \n\n\n" + error.toString() +
                            "\n\n\n" + error.getStackTrace() + "============= " + error.getCause().toString());
                }
            });
            System.out.println("============  completed=====12 \n\n\n");
            JSONObject result = customRequest.getJSONResponse();
            System.out.println("======*** ====== "+ result.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        //


        try{


            //requestQueue.add(jsonObjectRequest);
            requestQueue.add(customRequest);

            return null;
        }catch(Exception e) {
            e.printStackTrace();
        }
        //	requestQueue.start();
        return null;
    }*/


}
