package com.max.proyecto_parcial2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Cardview_cartitem extends RecyclerView.Adapter<Cardview_cartitem.ViewHolder> {
    ArrayList<UserVariables.CartItem> lista = null;
    Context context = null;

    // Éste adaptador sigue el patrón de diseño view holder, que significa que define una
    // clase interna que extienda de RecyclerView.ViewHolder. Éste patrón minimiza el número
    // de llamadas al costoso método findViewById.
    public Cardview_cartitem(Context context, ArrayList<UserVariables.CartItem> lista) {
        this.context = context;
        this.lista = lista;
    }

    // Debemos de crear estos dos métodos: onCreateViewHolder() y onBindViewHolder()
    // Hay sobreescribir estos dos métodos para inflar la vista y vincular datos a la vista
    // Encargado de crear los nuevos objetos ViewHolder necesarios para los elementos de la lista.
    @Override
    public com.max.proyecto_parcial2.Cardview_cartitem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Aquí creamos una nueva vista (invocada por el layout manager)
        View childView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview_cartitem, parent, false);
        return new com.max.proyecto_parcial2.Cardview_cartitem.ViewHolder(childView);
    }

    // Encargado de actualizar los datos de un ViewHolder ya existente.
    // onBindViewHolder nos sirve para especificar el contenido de cada elemento del RecyclerView.
    // Éste método es muy similar al método getView de un adaptador de ListView.
    // Aquí es donde tenemos que establecer los valores de los campos nombre y letra del CardView.
    @Override
    public void onBindViewHolder(final com.max.proyecto_parcial2.Cardview_cartitem.ViewHolder holder, final int position) {
        String nombre = lista.get(position).product.getName();
        holder.letra.setText(String.valueOf(lista.get(position).quantity));
        holder.nombre.setText(nombre.substring(0,1)+nombre.substring(1));
        holder.quantity.setText(String.valueOf(lista.get(position).quantity));
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linear.setVisibility(View.VISIBLE);
                holder.update.setVisibility(View.INVISIBLE);
            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linear.setVisibility(View.INVISIBLE);
                holder.update.setVisibility(View.VISIBLE);
                holder.letra.setText(String.valueOf(lista.get(position).quantity));
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.get(position).increment();
                holder.quantity.setText(String.valueOf(lista.get(position).quantity));
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.get(position).decrement();
                holder.quantity.setText(String.valueOf(lista.get(position).quantity));
            }
        });
    }

    // Indica el número de elementos del set de datos.
    // y nos la regresa
    @Override
    public int getItemCount() {
        return lista.size();
    }

    // Ésta clase es llamada cuando el ViewHolder necesita ser inicializado.
    // Especificamos el layout que cada elemento de RecyclerView debería usar.
    // Ésto se hace al inflar el layout usando LayoutInflater, pasando el resultado al
    // constructor del ViewHolder.
    public class ViewHolder extends RecyclerView.ViewHolder{
        // definimos los componentes de nuestra vista
        LinearLayout linear;
        TextView letra, nombre, quantity;
        CardView carta;
        Button minus, plus, update, check;

        public ViewHolder(final View verElemento) {
            super(verElemento);
            carta = (CardView)  verElemento.findViewById(R.id.Card);
            letra = (TextView)  verElemento.findViewById(R.id.Letter);
            quantity = (TextView)  verElemento.findViewById(R.id.Quantity);
            linear = (LinearLayout) verElemento.findViewById(R.id.ChangeQuantity);
            nombre = (TextView) verElemento.findViewById(R.id.Name);
            minus = (Button) verElemento.findViewById(R.id.Minus);
            plus = (Button) verElemento.findViewById(R.id.Plus);
            update = (Button) verElemento.findViewById(R.id.Update);
            check = (Button) verElemento.findViewById(R.id.Check);
        }

    }
}
