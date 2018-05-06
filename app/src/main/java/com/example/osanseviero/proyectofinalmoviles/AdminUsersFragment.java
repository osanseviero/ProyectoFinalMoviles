package com.example.osanseviero.proyectofinalmoviles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminUsersFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        Button createUserButton = rootView.findViewById(R.id.createUser);
        Button manageEmployeesButton = rootView.findViewById(R.id.manageEmployees);

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminUserCreationFragment fragment = new AdminUserCreationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentAdminFragment, fragment);
                transaction.commit();
            }
        });

        manageEmployeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminUsersManagementFragment fragment = new AdminUsersManagementFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentAdminFragment, fragment);
                transaction.commit();
            }
        });

        return rootView;
    }

}
