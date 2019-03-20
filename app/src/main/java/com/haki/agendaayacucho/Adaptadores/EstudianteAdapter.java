package com.haki.agendaayacucho.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haki.agendaayacucho.Modelos.Estudiante;
import com.haki.agendaayacucho.R;

import java.util.List;

public class EstudianteAdapter extends RecyclerView.Adapter<EstudianteAdapter.EstudianteHolder> implements View.OnClickListener {

    List<Estudiante> listEstudiante;
    Context context;
    private View.OnClickListener listener;

    public EstudianteAdapter(List<Estudiante> listEstudiante, Context context) {
        this.listEstudiante = listEstudiante;
        this.context = context;
    }

    @NonNull
    @Override
    public EstudianteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_estudiante,viewGroup,false);
        layout.setOnClickListener(this);
        return new EstudianteHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull EstudianteHolder estudianteHolder, int i) {
        estudianteHolder._tvNombre.setText(listEstudiante.get(i).getNombre()+" "+listEstudiante.get(i).getPaterno()+" "+listEstudiante.get(i).getMaterno());
        //estudianteHolder._tvDatos.setText(String.valueOf(listEstudiante.get(i).getId_rude()));
        estudianteHolder._tvDatos.setText("");
    }

    @Override
    public int getItemCount() {
        return listEstudiante.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class EstudianteHolder extends RecyclerView.ViewHolder {

        TextView _tvNombre, _tvDatos;

        public EstudianteHolder(@NonNull View itemView) {
            super(itemView);
            _tvNombre = itemView.findViewById(R.id.it_tvNombreEstudiante);
            _tvDatos  = itemView.findViewById(R.id.it_tvDatos);
        }
    }
}
