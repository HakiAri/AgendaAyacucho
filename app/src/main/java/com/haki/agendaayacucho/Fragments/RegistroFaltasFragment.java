package com.haki.agendaayacucho.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haki.agendaayacucho.Adaptadores.CursosAdapter;
import com.haki.agendaayacucho.Adaptadores.RegistroFaltasAdapter;
import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Curso;
import com.haki.agendaayacucho.Modelos.RegistroFalta;
import com.haki.agendaayacucho.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroFaltasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroFaltasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFaltasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView rvCursosAsignados;
    ArrayList<RegistroFalta> listaFalta;

    private ProgressDialog progress;
    private Maya maya;
    RegistroFaltasAdapter adaptador;

    String url="";

    public RegistroFaltasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFaltasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFaltasFragment newInstance(String param1, String param2) {
        RegistroFaltasFragment fragment = new RegistroFaltasFragment();
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
        View view = inflater.inflate(R.layout.fragment_registro_faltas, container, false);

        listaFalta = new ArrayList<>();
        maya = new Maya(getContext());

        rvCursosAsignados = view.findViewById(R.id.frf_rvCursosAsignados);
        consultarFaltas();
        rvCursosAsignados.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvCursosAsignados.setHasFixedSize(true);

        return view;
    }

    private void consultarFaltas() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Espere un momento...");
        progress.show();
        progress.setCancelable(false);

        //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //String url="http://prueba.hakiari.com/wsLoginJson.php";
        //String url="http://172.16.11.30/frigopaz/?/sitio/app-listar";
        url=maya.buscarUrlServidor()+"/app/servicesREST/ws_faltas_registradas_docente.php";
        Log.e("URL",url);
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
                        JSONArray json = jsonUsuario.optJSONArray("faltasdocente");
                        listaFalta = new ArrayList<>();


                        for (int i = 0; i < json.length(); i++) {
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            listaFalta.add(new RegistroFalta(jsonObject.optString("id_user"),jsonObject.optString("grado"),jsonObject.optString("paralelo"),jsonObject.optString("nombre_asignatura"),jsonObject.optString("nombrecompleto"),jsonObject.optString("fecha"),jsonObject.optString("descripcion"),jsonObject.optString("id_fal_com")));

                        }
                        progress.hide();
                        progress.dismiss();
                        adaptador = new RegistroFaltasAdapter(listaFalta,getContext());
                        rvCursosAsignados.setAdapter(adaptador);
                        adaptador.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //maya.toastInfo(listaCurso.get(rvCursosAsignados.getChildAdapterPosition(v)).getMateria());
                                //iFragmentCursoAsignado.enviarCurso(listaCurso.get(rvCursosAsignados.getChildAdapterPosition(v)));
                            }
                        });

                    }else{
                        progress.hide();
                        maya.Toast("No existen Cursos Asignados para mostrar...");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    maya.toastError("Respuesta no valida");
                    progress.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PeticionCursos ", error+"");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("usuario",maya.buscarUsuarioLogeado());
                //params.put("contrasenia",maya.buscarSecretLogeado());
                params.put("id_docente",maya.buscarId_Objeto());
                params.put("fecha","");

                Log.e("PARAMS", String.valueOf(params));
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
}
