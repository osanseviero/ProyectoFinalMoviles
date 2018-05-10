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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.example.osanseviero.proyectofinalmoviles.MyJsonArrayRequest;
import com.example.osanseviero.proyectofinalmoviles.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClientCategoryListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_client_category_list, container, false);

        JSONObject js = new JSONObject();
        try {
            js.put("token", ((ClientHomeScreenActivity) rootView.getContext()).token );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("DBG", js.toString());

        String url = "http://docker-azure.cloudapp.net/recipe/categories";
        MyJsonArrayRequest request = new MyJsonArrayRequest(
                Request.Method.POST,
                url,
                js,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("DBG", response.toString());
                        LinearLayout linearLayout = getActivity().findViewById(R.id.clientCategoryList);

                        for(int i=0; i<response.length(); i++) {
                            Button b = new Button(getActivity());
                            final String category;
                            try {
                                category = response.get(i).toString();
                                b.setText(category);

                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ClientDishListFragment clientDishListFragment = new ClientDishListFragment();
                                        FragmentManager cdfm = getFragmentManager();
                                        FragmentTransaction reportFragmentTransaction = cdfm.beginTransaction();
                                        reportFragmentTransaction.replace(R.id.contentFragment, clientDishListFragment);

                                        // Specify category name
                                        Bundle args = new Bundle();
                                        args.putString("category", category);
                                        clientDishListFragment.setArguments(args);

                                        reportFragmentTransaction.commit();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            linearLayout.addView(b);
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
