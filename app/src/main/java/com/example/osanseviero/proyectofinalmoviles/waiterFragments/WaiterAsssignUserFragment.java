package com.example.osanseviero.proyectofinalmoviles.waiterFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.osanseviero.proyectofinalmoviles.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WaiterAsssignUserFragment extends Fragment {
    Integer table;
    TextView tableTextview;
    EditText tableCustomer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_waiter_assign_user, container, false);
        tableCustomer =  rootView.findViewById(R.id.tableAssignUser);

        // Extract table
        table = 0;
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            table = bundle.getInt("table");
            tableTextview = rootView.findViewById(R.id.tableTitle);
            tableTextview.setText("MESA " + table);
            Button b = rootView.findViewById(R.id.assignTable);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject js = new JSONObject();
                    try {
                        js.put("token", ((WaiterHomeScreenActivity) getContext()).token );
                        js.put("table", table);

                        JSONArray customers = new JSONArray();
                        customers.put(tableCustomer.getText().toString());

                        js.put("customers", customers);
                        Log.d("DBG", js.toString());

                        String url = "http://docker-azure.cloudapp.net/tab/create";
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                Request.Method.POST, url, js,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String message = response.getString("message");
                                            String id = response.getString("id");

                                            Log.d("DBG", "Message: " + message);
                                            Log.d("DBG", "Id: " + id);

                                            Toast.makeText(getContext(), "Mesa asignada correctamente", Toast.LENGTH_SHORT).show();

                                            WaiterTablesFragment waiterTablesFragment = new WaiterTablesFragment();
                                            FragmentManager wtfm = getFragmentManager();
                                            FragmentTransaction reportFragmentTransaction = wtfm.beginTransaction();
                                            reportFragmentTransaction.replace(R.id.contentWaiterFragment, waiterTablesFragment);
                                            reportFragmentTransaction.commit();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(),  new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();

                                        Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                                        Log.e("err", "Message:" + new String(error.networkResponse.data));
                                    }
                                });

                        Volley.newRequestQueue(getContext()).add(jsonObjReq);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }



        return rootView;
    }
}
