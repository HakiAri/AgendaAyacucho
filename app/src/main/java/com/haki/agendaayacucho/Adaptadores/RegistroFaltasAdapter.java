package com.haki.agendaayacucho.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haki.agendaayacucho.Modelos.RegistroFalta;
import com.haki.agendaayacucho.R;

import java.util.List;

public class RegistroFaltasAdapter extends RecyclerView.Adapter<RegistroFaltasAdapter.RegistroFaltasHolder> implements View.OnClickListener {

    List<RegistroFalta> listfalta;
    Context context;
    private View.OnClickListener listener;

    public RegistroFaltasAdapter(List<RegistroFalta> listfalta, Context context) {
        this.listfalta = listfalta;
        this.context = context;
    }

    @NonNull
    @Override
    public RegistroFaltasHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_registro_falta,viewGroup,false);
        layout.setOnClickListener(this);
        return new RegistroFaltasHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroFaltasHolder registroFaltasHolder, int i) {
        registroFaltasHolder._tvCurso.setText(listfalta.get(i).getGrado()+" "+listfalta.get(i).getParalelo()+" - "+listfalta.get(i).getNombre_asignatura());
        registroFaltasHolder._tvNombre.setText(listfalta.get(i).getNombrecompleto());
        registroFaltasHolder._tvFecha.setText("En Fecha : "+listfalta.get(i).getFecha());
        registroFaltasHolder._tvDescripcion.setText("Causa : "+listfalta.get(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listfalta.size();
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

    public class RegistroFaltasHolder extends RecyclerView.ViewHolder {

        TextView _tvCurso, _tvNombre, _tvFecha, _tvDescripcion;

        public RegistroFaltasHolder(@NonNull View itemView) {
            super(itemView);
            _tvCurso   = itemView.findViewById(R.id.irf_tvCurso);
            _tvNombre = itemView.findViewById(R.id.irf_tvNombre);
            _tvFecha   = itemView.findViewById(R.id.irf_tvFecha);
            _tvDescripcion = itemView.findViewById(R.id.irf_tvDescripcion);
        }
    }
}
