package com.example.osanseviero.proyectofinalmoviles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class AdminMaterialCreationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_admin_material_creation, container, false);

        final EditText name = rootView.findViewById(R.id.adminMaterialName);
        final EditText image = rootView.findViewById(R.id.adminMaterialImage);
        final EditText units = rootView.findViewById(R.id.adminMaterialUnits);
        Button b = rootView.findViewById(R.id.submitCreateMaterialRequest);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject js = new JSONObject();
                try {
                    // Build JSON from text fields
                    js.put("name", name.getText().toString());
                    js.put("img_url", image.getText().toString());
                    js.put("units", units.getText().toString());
                    js.put("token", ((AdminHomeScreenActivity) v.getContext()).token );
                    Log.d("DBG", js.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO: Have a loading bar or something like that

                // Create JSON request
                String url = "http://docker-azure.cloudapp.net/material/create";
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, url, js,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String message = response.getString("message");
                                    String id = response.getString("id");
                                    Log.d("DBG", "Message: " + message);
                                    Log.d("DBG", "Material id: " + id);

                                    //TODO: Show a success message
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: Handle error
                        Log.e("ERR", "Error code: " + error.networkResponse.statusCode);
                        Log.e("ERR", "Message:" + new String(error.networkResponse.data));
                    }
                });

                Volley.newRequestQueue(rootView.getContext()).add(jsonObjReq);
            }
        });

        return rootView;
    }

}
