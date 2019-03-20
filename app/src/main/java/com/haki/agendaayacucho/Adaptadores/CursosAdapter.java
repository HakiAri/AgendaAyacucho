package com.haki.agendaayacucho.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haki.agendaayacucho.Fragments.EstudiantesCursoFragment;
import com.haki.agendaayacucho.Modelos.Curso;
import com.haki.agendaayacucho.R;

import java.util.List;

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.CursosHolder> implements View.OnClickListener {

    List<Curso> listCurso;
    Context context;
    private View.OnClickListener listener;

    public CursosAdapter(List<Curso> listCurso, Context context) {
        this.listCurso = listCurso;
        this.context = context;
    }

    @NonNull
    @Override
    public CursosAdapter.CursosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_curso_asignado,viewGroup,false);
        layout.setOnClickListener(this);
        return new CursosHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CursosAdapter.CursosHolder cursosHolder, final int i) {
        cursosHolder._tvCurso.setText(listCurso.get(i).getGrado()+" "+listCurso.get(i).getParalelo());
        cursosHolder._tvMateria.setText(listCurso.get(i).getMateria());
    }

    @Override
    public int getItemCount() {
        return listCurso.size();
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

    public class CursosHolder extends RecyclerView.ViewHolder {

        TextView _tvCurso, _tvMateria;

        public CursosHolder(@NonNull View itemView) {
            super(itemView);
            _tvCurso   = itemView.findViewById(R.id.ic_tvCurso);
            _tvMateria = itemView.findViewById(R.id.ic_tvMateria);
        }
    }
}
