package com.max.proyecto_parcial2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import android.support.v7.widget.CardView;

public class Cardview_employee extends RecyclerView.Adapter<Cardview_employee.ViewHolder> {

    ArrayList<Employee> list_employee = null;
    Context employee_context = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView capital_n_employee, n_employee;
        CardView card;

        public ViewHolder(final View vElement) {
            super(vElement);
            card = (CardView)  vElement.findViewById(R.id.Card);
            capital_n_employee = (TextView)  vElement.findViewById(R.id.Letter);
            n_employee = (TextView) vElement.findViewById(R.id.Name);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent mainIntent = new Intent(vElement.getContext(), item_Employee.class);
                    Bundle id = new Bundle();
                    id.putString("idEmployee", list_employee.get(getAdapterPosition()).getid());
                    mainIntent.putExtras(id);
                    vElement.getContext().startActivity(mainIntent);
                }
            });
        }
    }

    public Cardview_employee(Context context, ArrayList<Employee> _list_employee) {
        this.employee_context = context;
        this.list_employee = _list_employee;
    }

    @Override
    public Cardview_employee.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview_employee, parent, false);
        return new ViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(Cardview_employee.ViewHolder holder, int position) {

        try {
            holder.capital_n_employee.setText(list_employee.get(position).getName().substring(0, 1).toUpperCase());
            holder.n_employee.setText(list_employee.get(position).getName().substring(0, 1) + list_employee.get(position).getName().substring(1));
        }catch (Exception e)
        {
            Log.i("ERROR", list_employee.get(position).getName());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_employee.size();
    }
}

