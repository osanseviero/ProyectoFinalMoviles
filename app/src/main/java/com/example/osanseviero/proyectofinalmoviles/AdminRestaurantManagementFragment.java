package com.example.osanseviero.proyectofinalmoviles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminRestaurantManagementFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_admin_restaurant_management, container, false);
        Button createDishButton = rootView.findViewById(R.id.createDish);

        createDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminDishCreationFragment fragment = new AdminDishCreationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentAdminFragment, fragment);
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
