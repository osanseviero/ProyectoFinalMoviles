package com.example.osanseviero.proyectofinalmoviles.clientFragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.osanseviero.proyectofinalmoviles.MyJsonArrayRequest;
import com.example.osanseviero.proyectofinalmoviles.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientDishFragment extends Fragment {
    String dishId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_dish, container, false);
        final Button order =  rootView.findViewById(R.id.clientOrderDish);

        // Extract dishId
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            dishId = bundle.getString("dishId");
        }

        JSONObject js = new JSONObject();
        try {
            js.put("token", ((ClientHomeScreenActivity) rootView.getContext()).token );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://docker-azure.cloudapp.net/recipe/by-id/" + dishId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("DBG", response.toString());
                            Log.d("DBG", "Inside recipe");

                            String imgurl = response.getString("img_url");
                            String name = response.getString("name");

                            ImageRequest requestImage = new ImageRequest(
                                    imgurl,
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            Log.v("DBG", "Got image");
                                            ImageView iv =  rootView.findViewById(R.id.dishImage);
                                            iv.setImageBitmap(response);
                                        }
                                    },
                                    0,
                                    0,
                                    ImageView.ScaleType.CENTER_CROP,
                                    Bitmap.Config.RGB_565,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.v("ERROR", "Volley error image:\n" + error.toString());
                                            error.printStackTrace();
                                        }
                                    }

                            );
                            Volley.newRequestQueue(rootView.getContext()).add(requestImage);
                            Log.v("DBG", "what");
                            TextView nameView = rootView.findViewById(R.id.textView3);
                            nameView.setText(name);

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                        Log.e("err", "Message:" + new String(error.networkResponse.data));
                    }
                }
        );
        Volley.newRequestQueue(rootView.getContext()).add(request);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mytaburl = "http://docker-azure.cloudapp.net/tab/mytabs";

                JSONObject js = new JSONObject();
                try {
                    js.put("token", ((ClientHomeScreenActivity) rootView.getContext()).token );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MyJsonArrayRequest tabRequest = new MyJsonArrayRequest(
                        Request.Method.POST,
                        mytaburl,
                        js,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    Log.d("DBG", "TAB DEL USER");
                                    Log.d("DBG", response.toString());
                                    JSONObject js = response.getJSONObject(0);
                                    String tabId = js.getString("id");

                                    String mytaburl = "http://docker-azure.cloudapp.net/tab/" + tabId + "/addorder";

                                    JSONObject jsOrder = new JSONObject();
                                    try {
                                        jsOrder.put("token", ((ClientHomeScreenActivity) rootView.getContext()).token );
                                        jsOrder.put("recipe-id", dishId);

                                        Log.d("DBG", js.toString());

                                        JsonObjectRequest tabRequest = new JsonObjectRequest(
                                            Request.Method.POST,
                                            mytaburl,
                                            jsOrder,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Toast.makeText(getContext(), "Orden realizada", Toast.LENGTH_SHORT).show();
                                                    Log.d("DBG", response.toString());
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                                                    Log.e("err", "Message:" + new String(error.networkResponse.data));
                                                }
                                            }
                                        );
                                        Volley.newRequestQueue(rootView.getContext()).add(tabRequest);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                } catch( JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                                Log.e("err", "Message:" + new String(error.networkResponse.data));
                            }
                        }
                );
                Volley.newRequestQueue(rootView.getContext()).add(tabRequest);
            }
        });





        // Inflate the layout for this fragment
        return rootView;


    }
}
