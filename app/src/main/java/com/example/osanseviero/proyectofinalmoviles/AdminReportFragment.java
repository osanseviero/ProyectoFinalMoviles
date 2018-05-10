package com.example.osanseviero.proyectofinalmoviles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.osanseviero.proyectofinalmoviles.Graphs.Graph;
import com.github.mikephil.charting.charts.PieChart;


public class AdminReportFragment extends Fragment {
    View rootview;
    PieChart pieChart1;
    PieChart pieChart2;
    SeekBar seekBar1;
    TextView daysView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_report, container, false);

        pieChart1 = rootview.findViewById(R.id.pieChart1);
        pieChart2 = rootview.findViewById(R.id.pieChart2);
        seekBar1 = rootview.findViewById(R.id.seekBar);
        daysView = rootview.findViewById(R.id.daysView);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                daysView.setText("Días: " + (progress+1));
                update(progress+1);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        update(1);
        return rootview;
    }



    private void update(int days){
        Graph.loadPieData(pieChart1, "inventory/usage/" + String.valueOf(days), "Uso de las últimas 24 horas.", rootview.getContext());
        Graph.loadPieData(pieChart2, "inventory/expense/" + String.valueOf(days), "Gasto de las últimas 24 horas.", rootview.getContext());
    }
}
