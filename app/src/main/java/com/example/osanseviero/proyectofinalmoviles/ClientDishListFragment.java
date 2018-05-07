package com.example.osanseviero.proyectofinalmoviles;

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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class ClientDishListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_client_dish_list, container, false);

        JSONObject js = new JSONObject();
        try {
            js.put("token", ((ClientHomeScreenActivity) rootView.getContext()).token );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Extract category
        String category = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            category = bundle.getString("category");
        }

        Log.d("DBG", "Opening category " + category);
        String url = "http://docker-azure.cloudapp.net/recipe/by-category/" + category;

        MyJsonArrayRequest request = new MyJsonArrayRequest(
                Request.Method.POST,
                url,
                js,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("DBG", response.toString());

                        LinearLayout linearLayout = getActivity().findViewById(R.id.clientDishList);
                        for(int i=0; i<response.length(); i++) {
                            try {
                                String name = response.getJSONObject(i).getString("name");
                                String cost = response.getJSONObject(i).getString("cost");

                                Button b = new Button(getActivity());
                                b.setText("Receta");

                                TextView nameView = new TextView(getActivity());
                                nameView.setText(name);

                                TextView costView = new TextView(getActivity());
                                costView.setText(cost);

                                final String dishId = response.getJSONObject(i).getString("id");

                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ClientDishFragment clientDishFragment = new ClientDishFragment();
                                        FragmentManager cdfm = getFragmentManager();
                                        FragmentTransaction reportFragmentTransaction = cdfm.beginTransaction();
                                        reportFragmentTransaction.replace(R.id.contentFragment, clientDishFragment);

                                        // Specify category name
                                        Bundle args = new Bundle();
                                        args.putString("dishId", dishId);
                                        clientDishFragment.setArguments(args);

                                        reportFragmentTransaction.commit();
                                    }
                                });

                                linearLayout.addView(nameView);
                                linearLayout.addView(costView);
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
        // Inflate the layout for this fragment
        return rootView;
    }

}
