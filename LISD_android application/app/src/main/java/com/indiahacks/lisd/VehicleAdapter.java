package com.indiahacks.lisd;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adikundiv on 05-09-2017.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    List<Vehicle> VehicleList;

    VehicleAdapter(List<Vehicle> input){
        VehicleList=input;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //VALUE INITILIZATION
        final Vehicle item= VehicleList.get(position);
        holder.vehicle_type.setText(item.getVehicle_type().toString());
        holder.vehicle_id.setText(String.valueOf(item.getVehicle_id()));
        if(item.isImmobilize()) {
            holder.immobilze.setChecked(true);
        }
        else {
            holder.immobilze.setChecked(false);
        }
        holder.btn_carbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CarbonActivity.class);
                intent.putExtra("vehicle_id",item.getVehicle_id());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return VehicleList.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView vehicle_id,vehicle_type;
        Switch immobilze;
        Button btn_carbon;

        public MyViewHolder(View itemView) {

            super(itemView);
            vehicle_id=(TextView)itemView.findViewById(R.id.Vehicle_id);
            vehicle_type=(TextView)itemView.findViewById(R.id.vehicle_type);
            immobilze=(Switch)itemView.findViewById(R.id.immobilze);
            btn_carbon=(Button)itemView.findViewById(R.id.btn_carbon);
        }
    }
}
