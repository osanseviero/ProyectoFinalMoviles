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

public class AdminUserCreationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_admin_user_creation, container, false);
        final EditText username = rootView.findViewById(R.id.adminCreateUsername);
        final EditText name = rootView.findViewById(R.id.adminCreateName);
        final EditText email = rootView.findViewById(R.id.adminCreateEmail);
        final EditText password = rootView.findViewById(R.id.adminCreatePassword);
        final EditText kind = rootView.findViewById(R.id.adminCreateKind);
        Button b = rootView.findViewById(R.id.adminCreateUserButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject js = new JSONObject();
                try {
                    // Build JSON from text fields
                    js.put("username", username.getText().toString());
                    js.put("password", password.getText().toString());

                    js.put("name", name.getText().toString());
                    js.put("email", email.getText().toString());
                    js.put("password", password.getText().toString());

                    //TODO: Validar que sea de 1 a 5
                    js.put("kind", Integer.parseInt(kind.getText().toString()));
                    js.put("token", ((AdminHomeScreenActivity) v.getContext()).token );
                    Log.d("DBG", js.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO: Have a loading bar or something like that

                // Create JSON request
                String url = "http://docker-azure.cloudapp.net/user/create";
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, url, js,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String message = response.getString("message");
                                    String id = response.getString("id");
                                    Log.d("DBG", "Message: " + message);
                                    Log.d("DBG", "User id: " + id);

                                    //TODO: Show a success message
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

                Volley.newRequestQueue(rootView.getContext()).add(jsonObjReq);
            }
        });

        return rootView;
    }


}
