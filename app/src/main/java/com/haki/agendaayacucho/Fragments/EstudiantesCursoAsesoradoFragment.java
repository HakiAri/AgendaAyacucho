package com.haki.agendaayacucho.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haki.agendaayacucho.Adaptadores.EstudianteAdapter;
import com.haki.agendaayacucho.FaltasEstudianteActivity;
import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Curso;
import com.haki.agendaayacucho.Modelos.Estudiante;
import com.haki.agendaayacucho.R;
import com.haki.agendaayacucho.RegistroKardexActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstudiantesCursoAsesoradoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EstudiantesCursoAsesoradoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstudiantesCursoAsesoradoFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView _rvEstudiantes;
    private EditText _etEstudiantes;
    private ArrayList<Estudiante> listEstudiantes;
    private ArrayList<Estudiante> listEstudiantesAux;
    private EstudianteAdapter adaptador;
    private LinearLayoutManager linearLayoutManager;
    private String estado = "n";

    private ProgressDialog progress;
    private Maya maya;
    Curso curso = null;

    private OnFragmentInteractionListener mListener;

    public EstudiantesCursoAsesoradoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstudiantesCursoAsesoradoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstudiantesCursoAsesoradoFragment newInstance(String param1, String param2) {
        EstudiantesCursoAsesoradoFragment fragment = new EstudiantesCursoAsesoradoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estudiantes_curso_asesorado, container, false);

        Bundle itemCurso = getArguments();
        //Curso curso = null;

        if (itemCurso != null){
            curso = (Curso) itemCurso.getSerializable("itemCurso");
        }

        maya = new Maya(getContext());
        _etEstudiantes = view.findViewById(R.id.feca_etbuscar);
        _rvEstudiantes = view.findViewById(R.id.feca_rvEstudiantes);

        _etEstudiantes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscardor(""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        consultarProductos(String.valueOf(curso.getId_curso()));
        _rvEstudiantes.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        _rvEstudiantes.setLayoutManager(linearLayoutManager);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void buscardor(String filtro) {
        listEstudiantes.clear();

        for (int i = 0; i < listEstudiantesAux.size(); i++){
            if (listEstudiantesAux.get(i).getNombre().toLowerCase().contains(filtro.toLowerCase())){
                listEstudiantes.add(listEstudiantesAux.get(i));
            }
        }
        adaptador.notifyDataSetChanged();
    }

    private void consultarProductos(final String id_curso) {

        progress=new ProgressDialog(getContext());
        progress.setMessage("Espere un momento...");
        progress.show();
        progress.setCancelable(false);

        //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //String url="http://prueba.hakiari.com/wsLoginJson.php";
        //String url="http://172.16.11.30/frigopaz/?/sitio/app-listar";
        String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_estudiantes_curso.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //maya.Toast(response);
                Log.e("json",response);
                try {
                    //JSONObject jsonUsuario = new JSONObject(response+"}]}");
                    JSONObject jsonUsuario = new JSONObject(response);
                    //maya.Toast("tam "+jsonUsuario.length());

                    if(jsonUsuario.length()==2){
                        JSONArray json = jsonUsuario.optJSONArray("estudiantes");
                        listEstudiantes    = new ArrayList<>();
                        listEstudiantesAux = new ArrayList<>();

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            listEstudiantes.add(new Estudiante(jsonObject.optString("nombre"),jsonObject.optString("paterno"),jsonObject.optString("materno"),jsonObject.optString("sexo"),jsonObject.optString("estado"),jsonObject.optInt("id_rude"),jsonObject.optInt("id_kardex"),""));
                            listEstudiantesAux.add(new Estudiante(jsonObject.optString("nombre"),jsonObject.optString("paterno"),jsonObject.optString("materno"),jsonObject.optString("sexo"),jsonObject.optString("estado"),jsonObject.optInt("id_rude"),jsonObject.optInt("id_kardex"),""));
                        }
                        progress.hide();
                        progress.dismiss();
                        adaptador = new EstudianteAdapter(listEstudiantes, getContext());
                        _rvEstudiantes.setAdapter(adaptador);
                        adaptador.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //maya.toastInfo(listEstudiantes.get(_rvEstudiantes.getChildAdapterPosition(v)).getNombre());
                                Intent aa = new Intent(getContext(), FaltasEstudianteActivity.class);
                                aa.putExtra("itemEstudiante",listEstudiantes.get(_rvEstudiantes.getChildAdapterPosition(v)));
                                aa.putExtra("itemCurso",curso);
                                startActivity(aa);
                            }
                        });
                    }else{
                        progress.hide();
                        maya.Toast("No existen Estudiantes a mostrar...");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("URLClientes ", error+"");
                //maya.toastAdvertencia("Servidor no encontrado");
                maya.toastInfo("No existe respuesta para continuar");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                /*params.put("usuario",maya.buscarUsuarioLogeado());
                params.put("contrasenia",maya.buscarSecretLogeado());*/
                params.put("id_curso",id_curso);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
