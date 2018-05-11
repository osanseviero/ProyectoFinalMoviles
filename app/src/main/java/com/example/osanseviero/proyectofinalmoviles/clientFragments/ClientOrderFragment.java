package com.example.osanseviero.proyectofinalmoviles.clientFragments;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osanseviero.proyectofinalmoviles.MyJsonArrayRequest;
import com.example.osanseviero.proyectofinalmoviles.R;
import com.example.osanseviero.proyectofinalmoviles.chefFragments.ChefHomeScreenActivity;
import com.example.osanseviero.proyectofinalmoviles.chefFragments.ChefOrdersFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClientOrderFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        String urlQuery = "http://docker-azure.cloudapp.net/tab/orders";

        JSONObject js = new JSONObject();
        try {
            js.put("token", ((ClientHomeScreenActivity) rootView.getContext()).token );
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

                                TextView nameView = new TextView(getActivity());
                                nameView.setText(name);

                                linearLayout.addView(nameView);

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
