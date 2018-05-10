package com.example.osanseviero.proyectofinalmoviles.waiterFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.osanseviero.proyectofinalmoviles.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class WaiterTablesFragment extends Fragment {
    Integer[] available;
    String tables;
    Integer counter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_waiter_tables, container, false);

        final JSONObject js = new JSONObject();
        try {
            js.put("token", ((WaiterHomeScreenActivity) getContext()).token );
            Log.d("DBG", js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://docker-azure.cloudapp.net/tables/";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String url = "http://docker-azure.cloudapp.net/tables/available";
                        Log.d("DBG", url);
                        try {
                            tables = response.getString("tables");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest req = new JsonObjectRequest(
                                Request.Method.POST, url, js,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Log.d("DBG", "Getting available tables");
                                            available = getIntegerArray("tables", response);


                                            LinearLayout linearLayout = getActivity().findViewById(R.id.tableList);
                                            Log.d("DBG", "# of available tables: " + available);
                                            for(counter=1; counter<=Integer.parseInt(tables); counter++) {
                                                Button b = new Button(getActivity());
                                                b.setTag(counter);
                                                b.setText("Mesa " + Integer.toString(counter));

                                                if(Arrays.asList(available).contains(counter)) {
                                                    b.setBackgroundColor(Color.GREEN);

                                                    b.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            WaiterAsssignUserFragment waiterAsssignUserFragment = new WaiterAsssignUserFragment();
                                                            FragmentManager waufm = getFragmentManager();
                                                            FragmentTransaction reportFragmentTransaction = waufm.beginTransaction();
                                                            reportFragmentTransaction.replace(R.id.contentWaiterFragment, waiterAsssignUserFragment);

                                                            // Specify category name
                                                            Bundle args = new Bundle();
                                                            args.putInt("table", Integer.parseInt(v.getTag().toString()));
                                                            waiterAsssignUserFragment.setArguments(args);
                                                            reportFragmentTransaction.commit();
                                                        }
                                                    });
                                                } else {
                                                    b.setBackgroundColor(Color.RED);

                                                    b.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            WaiterTableOrders waiterTableOrders = new WaiterTableOrders();
                                                            FragmentManager wto = getFragmentManager();
                                                            FragmentTransaction reportFragmentTransaction = wto.beginTransaction();
                                                            reportFragmentTransaction.replace(R.id.contentWaiterFragment, waiterTableOrders);

                                                            // Specify category name
                                                            Bundle args = new Bundle();
                                                            args.putInt("table", Integer.parseInt(v.getTag().toString()));
                                                            waiterTableOrders.setArguments(args);
                                                            reportFragmentTransaction.commit();
                                                        }
                                                    });
                                                }
                                                linearLayout.addView(b);
                                            }



                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //TODO: Handle error
                                Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                                Log.e("err", "Message:" + new String(error.networkResponse.data));
                            }
                        });

                        Volley.newRequestQueue(rootView.getContext()).add(req);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Handle error
                Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                Log.e("err", "Message:" + new String(error.networkResponse.data));
            }
        });

        Volley.newRequestQueue(rootView.getContext()).add(jsonObjReq);

        // Inflate the layout for this fragment
        return rootView;
    }

    Integer[] getIntegerArray(String name, JSONObject object) throws JSONException {
        JSONArray jsonArray = object.getJSONArray(name);
        Integer[] array = new Integer[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++){
            array[i] = Integer.parseInt(jsonArray.getString(i));
        }
        return array;
    }

}
