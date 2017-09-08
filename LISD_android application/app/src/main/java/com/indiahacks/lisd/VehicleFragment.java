package com.indiahacks.lisd;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<Vehicle> VehicleList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VehicleAdapter mAdapter;
    private ProgressDialog pDialog;

    SwipeRefreshLayout swipeRefreshLayout;

    private  FetchVehicle fetchVehicleTask;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "user_details" ;

    public VehicleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_vehicle, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_vehicle);

        mAdapter=new VehicleAdapter(VehicleList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_vehicle);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        PrepareVehicleList();

        return view;


    }

    private void PrepareVehicleList() {
        fetchVehicleTask=new FetchVehicle();
        fetchVehicleTask.execute();
    }

    @Override
    public void onRefresh() {
        VehicleList.clear();
        PrepareVehicleList();
    }


    public class FetchVehicle extends AsyncTask<Void, Void, String> {


        String response = null;
        FetchVehicle(){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching your trips");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            StringBuffer sb = new StringBuffer();

            sharedPreferences=getActivity().getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
            String user_id=sharedPreferences.getString("user_id",null);
            // TODO: attempt authentication against a network service.
            Log.d("connection_done","inside back");

            try {
                Log.d("connection_done","inside try");
                String api_call= (String) getText(R.string.api_url);
                Log.d("connection_done","add:"+api_call);
                URL url = new URL(api_call+"fetch_user_vehicle_id?user_id="+user_id);
                Log.d("connection_done","URl:"+url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("connection_done", "response: "+response);
            return response;
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(final String response) {

            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(getActivity(),response,Toast.LENGTH_LONG);
            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray vehicles = jsonObj.getJSONArray("vehicles");
                for(int i=0;i<vehicles.length();i++) {
                    JSONObject vehicle = vehicles.getJSONObject(i);
                    int user_id = vehicle.getInt("user_id");
                    int vehicle_id = vehicle.getInt("vehicle_id");
                    String vehicle_type = vehicle.getString("vehicle_type");
                    int vehicle_count = vehicle.getInt("vehicle_count");
                    String immobilize = vehicle.getString("immobilize");

                    boolean immo=(immobilize.equals("t"))?true:false;
                    Log.d("connection_done", "onPostExecute: immo"+immo);
                    Vehicle item= new Vehicle(user_id,vehicle_id,vehicle_count,vehicle_type,immo);
                    VehicleList.add(item);
                    mAdapter.notifyDataSetChanged();
                }

                } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity(), "cancelled by user",
                    Toast.LENGTH_LONG).show();
        }
    }

}
