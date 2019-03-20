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

public class EstudianteHijoAdapter extends RecyclerView.Adapter<EstudianteHijoAdapter.EstudianteHijoHolder> implements View.OnClickListener {

    List<Estudiante> listEstudiante;
    Context context;
    private View.OnClickListener listener;

    public EstudianteHijoAdapter(List<Estudiante> listEstudiante, Context context) {
        this.listEstudiante = listEstudiante;
        this.context = context;
    }

    @NonNull
    @Override
    public EstudianteHijoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mis_hijos,viewGroup,false);
        layout.setOnClickListener(this);
        return new EstudianteHijoHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull EstudianteHijoHolder estudianteHolder, int i) {
        estudianteHolder._tvNombre.setText(listEstudiante.get(i).getCurso());
        //estudianteHolder._tvDatos.setText(String.valueOf(listEstudiante.get(i).getId_rude()));
        estudianteHolder._tvDatos.setText(listEstudiante.get(i).getNombre()+" "+listEstudiante.get(i).getPaterno()+" "+listEstudiante.get(i).getMaterno());
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

    public class EstudianteHijoHolder extends RecyclerView.ViewHolder {

        TextView _tvNombre, _tvDatos;

        public EstudianteHijoHolder(@NonNull View itemView) {
            super(itemView);
            _tvNombre = itemView.findViewById(R.id.imh_tvMiHijo);
            _tvDatos  = itemView.findViewById(R.id.imh_tvCurso);
        }
    }
}
