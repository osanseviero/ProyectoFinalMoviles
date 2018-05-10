package com.example.osanseviero.proyectofinalmoviles.adminFragments;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminUsersManagementFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_admin_users_management, container, false);

        String urlFirstQuery = "http://docker-azure.cloudapp.net/user/query/3";
        String urlSecondQuery = "http://docker-azure.cloudapp.net/user/query/4";
        String urlThirdQuery = "http://docker-azure.cloudapp.net/user/query";
        final String urlDelete = "http://docker-azure.cloudapp.net/user/delete";

        JSONObject js = new JSONObject();
        try {
            js.put("token", ((AdminHomeScreenActivity) rootView.getContext()).token );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("DBG", js.toString());


        // Put this in a listView
        // TODO: Add filters to do according to user type
        // TODO: Have a loading bar or something like that
        MyJsonArrayRequest request = new MyJsonArrayRequest(
                Request.Method.POST,
                urlThirdQuery,
                js,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("DBG", response.toString());
                        LinearLayout linearLayout = getActivity().findViewById(R.id.CustomerList);
                        for(int i=0; i<response.length(); i++) {
                            try {
                                String name = response.getJSONObject(i).getString("username");

                                Button b = new Button(getActivity());
                                b.setText("Eliminar Usuario");
                                b.setTag(response.getJSONObject(i).getString("id"));

                                TextView nameView = new TextView(getActivity());
                                nameView.setText(name);

                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Toast.makeText(getActivity().getApplicationContext(), "Oops", Toast.LENGTH_SHORT).show();
                                        JSONObject jsDel = new JSONObject();
                                        try {
                                            jsDel.put("token", ((AdminHomeScreenActivity) rootView.getContext()).token );
                                            jsDel.put("user-id", v.getTag());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        Log.d("DBG", jsDel.toString());

                                        JsonObjectRequest delete = new JsonObjectRequest(
                                                Request.Method.POST,
                                                urlDelete,
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

        // Inflate the layout for this fragment
        return rootView;
    }
}
