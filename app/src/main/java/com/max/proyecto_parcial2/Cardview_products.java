package com.max.proyecto_parcial2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import android.support.v7.widget.CardView;

public class Cardview_products extends RecyclerView.Adapter<Cardview_products.ViewHolder> {

    ArrayList<Product> lista = null;
    Context context = null;

    // Éste adaptador sigue el patrón de diseño view holder, que significa que define una
    // clase interna que extienda de RecyclerView.ViewHolder. Éste patrón minimiza el número
    // de llamadas al costoso método findViewById.
    public Cardview_products(Context context, ArrayList<Product> lista) {
        this.context = context;
        this.lista = lista;
    }

    // Debemos de crear estos dos métodos: onCreateViewHolder() y onBindViewHolder()
    // Hay sobreescribir estos dos métodos para inflar la vista y vincular datos a la vista
    // Encargado de crear los nuevos objetos ViewHolder necesarios para los elementos de la lista.
    @Override
    public Cardview_products.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Aquí creamos una nueva vista (invocada por el layout manager)
        View childView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview_products, parent, false);
        return new ViewHolder(childView);
    }

    // Encargado de actualizar los datos de un ViewHolder ya existente.
    // onBindViewHolder nos sirve para especificar el contenido de cada elemento del RecyclerView.
    // Éste método es muy similar al método getView de un adaptador de ListView.
    // Aquí es donde tenemos que establecer los valores de los campos nombre y letra del CardView.
    @Override
    public void onBindViewHolder(Cardview_products.ViewHolder holder, int position) {
        String nombre = lista.get(position).getName();
        holder.letra.setText(nombre.substring(0,1).toUpperCase());
        holder.nombre.setText(nombre.substring(0,1)+nombre.substring(1));
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
        TextView letra, nombre;
        CardView carta;

        public ViewHolder(final View verElemento) {
            super(verElemento);
            carta = (CardView)  verElemento.findViewById(R.id.Card);
            letra = (TextView)  verElemento.findViewById(R.id.Letter);
            nombre = (TextView) verElemento.findViewById(R.id.Name);

            //A work around solution to implement item click in recycler view
            // tomado de StackOverflow
            carta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(UserVariables.getInstance().getStatus() == "1"){
                    final Intent mainIntent = new Intent(verElemento.getContext(), ItemProductAdministrator.class);
                    Bundle id = new Bundle();
                    id.putString("idProduct", lista.get(getAdapterPosition()).getid());
                    mainIntent.putExtras(id);
                    UserVariables.getInstance().tempProduct = lista.get(getAdapterPosition());
                    verElemento.getContext().startActivity(mainIntent);
                }
                else {
                    final Intent mainIntent = new Intent(verElemento.getContext(), item_Product.class);
                    Bundle id = new Bundle();
                    id.putString("idProduct", lista.get(getAdapterPosition()).getid());
                    mainIntent.putExtras(id);
                    UserVariables.getInstance().tempProduct = lista.get(getAdapterPosition());
                    verElemento.getContext().startActivity(mainIntent);
                }
                }
            });

        }

    }
}

