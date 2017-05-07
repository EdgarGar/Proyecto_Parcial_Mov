package com.max.proyecto_parcial2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jacke310 on 7/05/17.
 */

public class ClientAdapter extends ArrayAdapter<Client> {

    public ClientAdapter(Context context, ArrayList<Client> clients){
        super(context, 0, clients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_clients, parent, false);
        }

        Client currentClient = getItem(position);

        TextView nameClient = (TextView) listItemView.findViewById(R.id.nombre_client);
        nameClient.setText(currentClient.getNombreClient() + " " + currentClient.getApellidoClient());

        TextView mailCLient = (TextView) listItemView.findViewById(R.id.mail_cliente);
        mailCLient.setText(currentClient.getMailClient());

        TextView idClient = (TextView) listItemView.findViewById(R.id.id_cliente);
        idClient.setText(currentClient.getIdClient());

        TextView addressClient = (TextView) listItemView.findViewById(R.id.address_cliente);
        addressClient.setText(currentClient.getDireccionClient());

        return listItemView;
    }
}
