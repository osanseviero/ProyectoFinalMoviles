package com.example.osanseviero.proyectofinalmoviles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminUserCreationFragment extends Fragment {
    int kindval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_admin_user_creation, container, false);
        final EditText username = rootView.findViewById(R.id.adminCreateUsername);
        final EditText name = rootView.findViewById(R.id.adminCreateName);
        final EditText email = rootView.findViewById(R.id.adminCreateEmail);
        final EditText password = rootView.findViewById(R.id.adminCreatePassword);

        Button b = rootView.findViewById(R.id.adminCreateUserButton);

        String [] values = {"Administrador","Dueño","Cocinero","Mesero","Cliente",};
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerKind);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id)
            {

                kindval = pos + 1;
                Log.d("DBG", "Kind: " + kindval);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // vacío
            }
        });

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
                    js.put("kind", kindval);
                    js.put("token", ((AdminHomeScreenActivity) v.getContext()).token );
                    Log.d("DBG", js.toString());
                    Toast.makeText(getActivity().getApplicationContext(), "Usuario '" + username.getText().toString() + "' creado exitosamente", Toast.LENGTH_SHORT).show();

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

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();

    }


}
