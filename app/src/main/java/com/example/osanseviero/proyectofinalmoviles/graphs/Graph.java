package com.example.osanseviero.proyectofinalmoviles.graphs;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osanseviero.proyectofinalmoviles.R;
import com.example.osanseviero.proyectofinalmoviles.requests.RequestHelper;
import com.example.osanseviero.proyectofinalmoviles.Resources;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
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

    public static void loadBarData(final BarChart barChart, String report, String label, Context context){
        String path = Resources.rootEndpoint + "/reports/" + report;
        JSONObject jsonObject = RequestHelper.makeJsonToken();
        Log.d("DBG", "loadPieData: json: " + jsonObject.toString());
        JsonObjectRequest req = RequestHelper.makeJsonPOST(path, jsonObject,
                (response)->{
                    try{
                        List<BarEntry> entries = new ArrayList<>();
                        JSONArray array = response.getJSONArray("data");
                        for(int i = 0; i < array.length(); i++){
                            entries.add( new BarEntry(
                                    (float)array.getJSONObject(i).getDouble("X"),
                                    (float)array.getJSONObject(i).getDouble("Y")
                            ));
                            Log.v("!-!-!-!-!-!-!-!-!", array.getJSONObject(i).toString());
                            Log.v("!-!-!-!-!-!-!-!-!", "Adding: " + array.getJSONObject(i).getDouble("X") + " -> " + array.getJSONObject(i).getDouble("Y"));
                        }
                        BarDataSet set = new BarDataSet(entries, label);
                        BarData data = new BarData(set);
                        barChart.setData(data);
                        barChart.setFitBars(true);
                        barChart.invalidate();
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
