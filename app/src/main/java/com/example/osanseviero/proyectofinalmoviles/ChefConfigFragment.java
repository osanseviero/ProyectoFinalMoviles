package com.example.osanseviero.proyectofinalmoviles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ChefConfigFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: Add a button to return to chefOrdersFragment

        final View rootView = inflater.inflate(R.layout.fragment_chef_config, container, false);
        Button createDishButton = rootView.findViewById(R.id.chefCreateDish);
        Button createMaterialButton = rootView.findViewById(R.id.chefCreateMaterial);
        Button inventoryButton = rootView.findViewById(R.id.chefInventory);

        createDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DishCreationFragment fragment = new DishCreationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentChefFragment, fragment);
                transaction.commit();
            }
        });

        createMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCreationFragment fragment = new MaterialCreationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentChefFragment, fragment);
                transaction.commit();
            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChefInventoryFragment fragment = new ChefInventoryFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentChefFragment, fragment);
                transaction.commit();
            }
        });

        return rootView;
    }
}
