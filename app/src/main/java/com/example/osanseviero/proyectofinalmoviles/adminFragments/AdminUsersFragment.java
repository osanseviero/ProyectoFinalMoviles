package com.example.osanseviero.proyectofinalmoviles.adminFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.osanseviero.proyectofinalmoviles.R;

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
