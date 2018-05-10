package com.example.osanseviero.proyectofinalmoviles.waiterFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osanseviero.proyectofinalmoviles.MyJsonArrayRequest;
import com.example.osanseviero.proyectofinalmoviles.R;
import com.example.osanseviero.proyectofinalmoviles.chefFragments.ChefHomeScreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WaiterTableOrders extends Fragment {
    Integer table;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_waiter_table_orders, container, false);

        String urlQuery = "http://docker-azure.cloudapp.net/tab/orders";

        table = 0;
        Bundle bundle = this.getArguments();
        table = bundle.getInt("table");
        Log.d("DBG", "table: " + table);

        JSONObject js = new JSONObject();
        try {
            js.put("token", ((WaiterHomeScreenActivity) rootView.getContext()).token );
            js.put("table", table);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("DBG", js.toString());



        // Put this in a listView
        MyJsonArrayRequest request = new MyJsonArrayRequest(
                Request.Method.POST,
                urlQuery,
                js,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("DBG", response.toString());
                        LinearLayout linearLayout = getActivity().findViewById(R.id.CustomerList);
                        for(int i=0; i<response.length(); i++) {
                            try {
                                String name = response.getJSONObject(i).getString("name");

                                Button b = new Button(getActivity());
                                b.setText("Despachar Orden");
                                b.setTag(R.id.id1, response.getJSONObject(i).getString("tab-id"));
                                b.setTag(R.id.id2, response.getJSONObject(i).getString("id"));

                                TextView nameView = new TextView(getActivity());
                                nameView.setText(name);

                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Toast.makeText(getActivity().getApplicationContext(), "Orden despachada", Toast.LENGTH_SHORT).show();
                                        JSONObject jsDel = new JSONObject();
                                        try {
                                            jsDel.put("token", ((ChefHomeScreenActivity) rootView.getContext()).token );
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        Log.d("DBG", jsDel.toString());

                                        JsonObjectRequest delete = new JsonObjectRequest(
                                                Request.Method.POST,
                                                "http://docker-azure.cloudapp.net/tab/" + v.getTag(R.id.id1) + "/dispatch/" + v.getTag(R.id.id2),
                                                jsDel,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            String message = response.getString("message");
                                                            Log.d("DBG", "Message: " + message);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                                                        Log.e("err", "Message:" + new String(error.networkResponse.data));
                                                    }
                                                }
                                        );

                                        Volley.newRequestQueue(rootView.getContext()).add(delete);
                                    }
                                });

                                linearLayout.addView(nameView);
                                linearLayout.addView(b);
                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                        Log.e("err", "Message:" + new String(error.networkResponse.data));
                    }
                }
        );

        Volley.newRequestQueue(rootView.getContext()).add(request);

        return rootView;
    }
}
