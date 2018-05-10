package com.example.osanseviero.proyectofinalmoviles.clientFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.osanseviero.proyectofinalmoviles.R;
import com.example.osanseviero.proyectofinalmoviles.graphs.Graph;
import com.github.mikephil.charting.charts.PieChart;

public class ClientOrderListFragment extends Fragment {
    View rootview;

    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_order_list, container, false);

        pieChart = rootview.findViewById(R.id.pieChart_client);

        Graph.loadPieData(pieChart, "customer/tabs", "Categorias", rootview.getContext());

        return rootview;
    }
}
