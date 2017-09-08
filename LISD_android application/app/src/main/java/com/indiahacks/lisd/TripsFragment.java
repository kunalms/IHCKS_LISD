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
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TripsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Trip> tripList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TripAdapter mAdapter;
    private ProgressDialog pDialog;

    private FetchTrips fetchTripsTask=null;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "user_details" ;
    public TripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trips, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter=new TripAdapter(tripList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        mSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);


        PrepareTripList();

        return view;
    }

    private void PrepareTripList() {

        fetchTripsTask=new FetchTrips();
        fetchTripsTask.execute();
    }

    @Override
    public void onRefresh() {
        tripList.clear();
        PrepareTripList();
    }


    public class FetchTrips extends AsyncTask<Void, Void, String> {


        String response = null;
        FetchTrips() {
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

            // TODO: attempt authentication against a network service.
            Log.d("connection_done","inside back");
            sharedPreferences=getActivity().getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
            String user_id=sharedPreferences.getString("user_id",null);
            try {

                Log.d("connection_done","inside try");
                String api_call= (String) getText(R.string.api_url);
                Log.d("connection_done","add:"+api_call);
                URL url = new URL(api_call+"fetch_gps_user_id?user_id="+user_id);
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
            ArrayList<String> Latitude, Longitude,Timestamp;

            if(mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(getActivity(),response,Toast.LENGTH_LONG);

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray trips = jsonObj.getJSONArray("trips");
                for(int i=0;i<trips.length();i++){
                    Latitude=new ArrayList<String>();
                    Longitude=new ArrayList<String>();
                    Timestamp=new ArrayList<String>();

                    JSONObject c = trips.getJSONObject(i);

                    int trip_id = c.getInt("trip_id");
                    int user_id = c.getInt("user_id");
                    int vehicle_id = c.getInt("vehicle_id");
                    JSONArray longitude = c.getJSONArray("longitude");
                    JSONArray latitude = c.getJSONArray("latitude");
                    JSONArray timestamp = c.getJSONArray("timestamp");
                    boolean islive=c.getBoolean("islive");

                    for(int j=0;j<longitude.length();j++){
                        Latitude.add(latitude.get(j).toString());
                        Longitude.add(longitude.get(j).toString());
                        Timestamp.add(timestamp.get(j).toString());
                    }

                    Trip item= new Trip(Latitude,Longitude,Timestamp,trip_id,vehicle_id,islive);
                    tripList.add(item);
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
