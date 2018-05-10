package com.example.osanseviero.proyectofinalmoviles.Graphs;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osanseviero.proyectofinalmoviles.R;
import com.example.osanseviero.proyectofinalmoviles.Requests.RequestHelper;
import com.example.osanseviero.proyectofinalmoviles.Resources;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static void loadPieData(final PieChart pieChart, String report, String label, Context context){
        String path = Resources.rootEndpoint + "/reports/" + report;
        JSONObject jsonObject = RequestHelper.makeJsonToken();
        Log.d("DBG", "loadPieData: json: " + jsonObject.toString());
        JsonObjectRequest req = RequestHelper.makeJsonPOST(path, jsonObject,
            (response)->{
                try{
                    List<PieEntry> entries = new ArrayList<>();
                    JSONArray array = response.getJSONArray("data");
                    for(int i = 0; i < array.length(); i++){
                        entries.add(new PieEntry(
                                (float)array.getJSONObject(i).getDouble("value"),
                                array.getJSONObject(i).getString("name")
                        ));
                        Log.v("PIECHART", "Adding: " + array.getJSONObject(i).getString("name"));
                    }
                    PieDataSet set = new PieDataSet(entries, label);
                    set.setColors(new int[]{R.color.color1, R.color.color2, R.color.color3, R.color.color4}, context);
                    PieData data = new PieData(set);
                    pieChart.setData(data);
                    pieChart.invalidate();
                    //Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
                }
                catch (JSONException ex){
                    Toast.makeText(context, "Error :(", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            },
            (error)->{
                Toast.makeText(context, "Error :(", Toast.LENGTH_SHORT).show();
                Log.e("PIECHART", new String(error.networkResponse.data));
            }
        );
        Volley.newRequestQueue(context).add(req);
    }
}
