package com.example.osanseviero.proyectofinalmoviles.chefFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.osanseviero.proyectofinalmoviles.R;
import org.json.JSONException;
import org.json.JSONObject;

public class ChefInventoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_chef_inventory, container, false);
        final EditText id = rootView.findViewById(R.id.inventoryMaterialId);
        final EditText date = rootView.findViewById(R.id.inventoryMaterialDate);
        final EditText size = rootView.findViewById(R.id.inventoryMaterialSize);
        final EditText cost = rootView.findViewById(R.id.inventoryMaterialCost);
        final EditText amount = rootView.findViewById(R.id.inventoryMaterialAmount);
        final EditText loc = rootView.findViewById(R.id.inventoryMaterialLocation);
        final Button submitForm = rootView.findViewById(R.id.submitInventoryReception);

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject js = new JSONObject();
                try {
                    js.put("token", ((ChefHomeScreenActivity) v.getContext()).token );
                    js.put("material-id", id.getText().toString());
                    js.put("expiration-date", date.getText().toString());
                    js.put("size", Integer.parseInt(size.getText().toString()));
                    js.put("cost", Integer.parseInt(cost.getText().toString()));
                    js.put("amount", Integer.parseInt(amount.getText().toString()));
                    js.put("location", loc.getText().toString());

                    Log.d("DBG", js.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create JSON request
                String url = "http://docker-azure.cloudapp.net/inventory/create";
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

                                    Toast.makeText(rootView.getContext(), "Material recibido en inventario", Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(rootView.getContext(),  new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();

                            Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                            Log.e("err", "Message:" + new String(error.networkResponse.data));
                        }
                });

                Volley.newRequestQueue(rootView.getContext()).add(jsonObjReq);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
