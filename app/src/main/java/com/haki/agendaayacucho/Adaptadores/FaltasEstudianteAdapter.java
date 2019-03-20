package com.haki.agendaayacucho.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haki.agendaayacucho.Modelos.Estudiante;
import com.haki.agendaayacucho.Modelos.FaltaHijo;
import com.haki.agendaayacucho.R;

import java.util.List;

public class FaltasEstudianteAdapter extends RecyclerView.Adapter<FaltasEstudianteAdapter.FaltasEstudianteHolder> implements View.OnClickListener {

    List<FaltaHijo> listFalta;
    Context context;
    private View.OnClickListener listener;

    public FaltasEstudianteAdapter(List<FaltaHijo> listFalta, Context context) {
        this.listFalta = listFalta;
        this.context = context;
    }

    @NonNull
    @Override
    public FaltasEstudianteAdapter.FaltasEstudianteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_falta_estudiante,viewGroup,false);
        layout.setOnClickListener(this);
        return new FaltasEstudianteHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FaltasEstudianteAdapter.FaltasEstudianteHolder faltasEstudianteHolder, int i) {
        faltasEstudianteHolder._tvMateria.setText("Materia : "+listFalta.get(i).getMateria());
        faltasEstudianteHolder._tvFecha.setText("Registrado en : "+listFalta.get(i).getFecha());
        faltasEstudianteHolder._tvDescripcion.setText("Falta Cometida : "+listFalta.get(i).getDescripcion()+"\nObservacion : "+listFalta.get(i).getObservacion());
    }

    @Override
    public int getItemCount() {
        return listFalta.size();
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



    public class FaltasEstudianteHolder extends RecyclerView.ViewHolder {

        TextView _tvMateria,_tvFecha,_tvDescripcion;

        public FaltasEstudianteHolder(@NonNull View itemView) {
            super(itemView);
             _tvMateria = itemView.findViewById(R.id.tv_MateriaFalta);
             _tvFecha = itemView.findViewById(R.id.tv_FechaFalta);
             _tvDescripcion = itemView.findViewById(R.id.tv_descripcionFalta);
        }
    }
}
