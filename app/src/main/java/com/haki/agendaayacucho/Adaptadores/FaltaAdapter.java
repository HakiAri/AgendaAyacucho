package com.haki.agendaayacucho.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haki.agendaayacucho.Modelos.Falta;
import com.haki.agendaayacucho.R;

import java.util.List;

public class FaltaAdapter extends RecyclerView.Adapter<FaltaAdapter.FaltaHolder> implements View.OnClickListener{
    List<Falta> listFalta;
    Context context;
    private View.OnClickListener listener;

    public FaltaAdapter(List<Falta> listFalta, Context context) {
        this.listFalta = listFalta;
        this.context = context;
    }

    @NonNull
    @Override
    public FaltaAdapter.FaltaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_falta,viewGroup,false);
        layout.setOnClickListener(this);
        return new FaltaAdapter.FaltaHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FaltaAdapter.FaltaHolder faltaHolder, int i) {
        faltaHolder._tvNombreFalta.setText(String.valueOf(listFalta.get(i).getDescripcion()));
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

    public class FaltaHolder extends RecyclerView.ViewHolder {

        TextView _tvNombreFalta;

        public FaltaHolder(@NonNull View itemView) {
            super(itemView);
            _tvNombreFalta = itemView.findViewById(R.id.if_tvFalta);

        }
    }
}
