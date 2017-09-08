package com.indiahacks.lisd;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by adikundiv on 03-09-2017.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    private List<Trip> Trips;


    public  TripAdapter(List<Trip> tripList){
        this.Trips=tripList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Trip trip = Trips.get(position);

        SimpleDateFormat  formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date objectCreatedDate = null;

        try {
            objectCreatedDate = formater.parse(trip.getInitialTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formater = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");
        String strDate = formater.format(objectCreatedDate);
        Log.d("connection_done", "onBindViewHolder: "+strDate);
        holder.timestamp.setText("Date-Time: "+strDate);
        holder.vehicle_id.setText("Vehicle_id: "+String.valueOf(trip.getVehicle_id()));
        holder.trip_id.setText("Trip_id: "+String.valueOf(trip.getTrip_id()));
        if(trip.is_live) {
            holder.trip_live.setText("Live");
            holder.trip_live.setBackgroundResource(R.drawable.green_bg);
        }
        else{
            holder.trip_live.setText("Terminated");
            holder.trip_live.setBackgroundResource(R.drawable.red_bg);

        }
        holder.btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"clicked item with id"+String.valueOf(trip.getTrip_id()),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(),MapsActivity.class);
                intent.putExtra("trip_details",trip);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return Trips.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView trip_id,vehicle_id,timestamp,trip_live;
        public Button btn_track;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn_track=(Button) itemView.findViewById(R.id.btn_track);
            trip_id=(TextView) itemView.findViewById(R.id.trip_id);
            vehicle_id=(TextView) itemView.findViewById(R.id.vehicle_id);
            timestamp=(TextView) itemView.findViewById(R.id.timestamp);
            trip_live=(TextView)itemView.findViewById(R.id.tv_live);
        }

    }
}
