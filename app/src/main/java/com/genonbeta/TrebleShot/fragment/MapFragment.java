package com.genonbeta.TrebleShot.fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.genonbeta.TrebleShot.R;
import com.genonbeta.TrebleShot.callback.HttpRequestUtilsZerox;
import com.genonbeta.TrebleShot.config.ZeroxVMConfig;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements GoogleMap.OnMapLoadedCallback, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    MapView mMapView;
    private GoogleMap googleMap;
    private LocationManager locationManager;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;


    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    double latitude;//= 17.385044;
    double longitude;//= 78.486671;
    //Context context;
    HttpRequestUtilsZerox httpRequestUtilsZerox = null;

    public MapFragment() {
        // Required empty public constructor
        //    this.context =    context;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        ;
        mMapView.getContext();
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            httpRequestUtilsZerox = new HttpRequestUtilsZerox(getActivity().getApplicationContext());


        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);

        // latitude and longitude

        String baseUrl = ZeroxVMConfig.baseURLOfZerox.concat(ZeroxVMConfig.GET_PROFILE_DETAILS).concat("?emailOrMobile=rathor.rajeev@gmail.com");
        //?lat=27.280256&longt=77.940646
       String nearByMachineQuery = ZeroxVMConfig.baseURLOfZerox.concat(ZeroxVMConfig.NEAR_BY_PRINTER).concat("?lat=").concat("27.280256").concat("&longt=").concat("77.940646");
        RequestQueue requestQueue = httpRequestUtilsZerox.getRequestQueue();

        JSONObject requestBody = createRequetBodyForNearByPrinter(27.280256, 77.940646);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                nearByMachineQuery,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        System.out.println("================ Success "+ response.toString());
                        // Process the JSON

                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject mapjson = response.getJSONObject(i);
                               //  [{"machineId":"82879","machineAddress":"string","geoLocation":{"type":"Point","coordinates":[27.240756,77.840676]},"registerOn":0,"modifiedOn":0},{"machineId":"8282","machineAddress":"string","geoLocation":{"type":"Point","coordinates":[27.240256,77.840646]},"registerOn":0,"modifiedOn":0}]
                                System.out.println("------"+ mapjson.getJSONObject("geoLocation").getJSONArray("coordinates").get(0).toString());
                                MarkerOptions marker = new MarkerOptions().position(
                                        new LatLng(Double.parseDouble(  mapjson.getJSONObject("geoLocation").getJSONArray("coordinates").get(0).toString()),
                                                Double.parseDouble(mapjson.getJSONObject("geoLocation").getJSONArray("coordinates").get(0).toString())))
                                        .title(mapjson.getString("machineAddress"));

                                marker.icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                                googleMap.addMarker(marker);
                            }


                            }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(12.0234, 78.54678)).title("Agra");
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                        googleMap.addMarker(marker);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        System.out.println(" Request failed to execute "+ error.getStackTrace().toString());
                        System.out.println(" -===========================********** "+ error.getCause());
                        error.printStackTrace();
                    }
                }
        );

       /* JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, nearByMachineQuery, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(" This is ==========================" + response.toString());
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(12.0234, 78.54678)).title("Agra");
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                        googleMap.addMarker(marker);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(" Request failed to execute "+ error.getStackTrace().toString());
                        System.out.println(" -===========================********** "+ error.getCause());
                        error.printStackTrace();
                    }
                });*/

        requestQueue.add(jsonArrayRequest);
        //jsonObjectRequest.
        // create marker
     /*   MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
*/
        // adding marker
  //      googleMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


        // Perform any camera updates here
        return v;

    }

    private JSONObject createRequetBodyForNearByPrinter(double latitude, double longitude) {

        try {
            JSONArray array = new JSONArray();
            array.put(latitude);
            array.put(longitude);

            JSONObject json = new JSONObject();

            json.put("coordinates", array);
            json.put("type", "POINT");

            System.out.println(" Created JSON " + json.toString());
            return json;
        } catch (Exception e) {
e.printStackTrace();
        }
        return new JSONObject();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapLoaded() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
