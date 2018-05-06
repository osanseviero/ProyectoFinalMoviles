package com.example.osanseviero.proyectofinalmoviles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DishCreationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_dish_creation, container, false);
        final EditText name = rootView.findViewById(R.id.dishCreationName);
        final EditText description = rootView.findViewById(R.id.dishCreationDescription);
        final EditText detail = rootView.findViewById(R.id.dishCreationDetail);
        final EditText image = rootView.findViewById(R.id.dishCreationImage);
        final EditText cost = rootView.findViewById(R.id.dishCreationCost);
        Button addIngredient = rootView.findViewById(R.id.addIngredient);
        final Button submitForm = rootView.findViewById(R.id.submitDishRequest);

        final List<EditText> ingredients = new ArrayList<EditText>();
        final List<EditText> quantities = new ArrayList<EditText>();

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change ingredient to be an Autocomplete that shows names with /material/query/name
                EditText ingredient =  new EditText(getActivity());
                ingredient.setHint("Ingrediente");

                EditText quantity =  new EditText(getActivity());
                quantity.setHint("Cantidad");
                quantity.setInputType(InputType.TYPE_CLASS_NUMBER);

                LinearLayout linearLayout = getActivity().findViewById(R.id.dishCreation);
                linearLayout.addView(ingredient, linearLayout.indexOfChild(submitForm)-1);
                linearLayout.addView(quantity, linearLayout.indexOfChild(submitForm)-1);

                ingredients.add(ingredient);
                quantities.add(quantity);
            }
        });

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject js = new JSONObject();
                try {
                    // Build JSON from text fields
                    js.put("token", ((AdminHomeScreenActivity) v.getContext()).token );
                    js.put("name", name.getText().toString());
                    js.put("desc", description.getText().toString());
                    js.put("detail", detail.getText().toString());
                    js.put("img_url", image.getText().toString());
                    js.put("cost",  Integer.parseInt(cost  .getText().toString()));

                    JSONArray ingredientJsons = new JSONArray();
                    for(int i=0; i<ingredients.size(); i++) {
                        JSONObject ing = new JSONObject();
                        ing.put("material-id", ingredients.get(i).getText().toString());
                        ing.put("quantity", quantities.get(i).getText().toString());
                        ingredientJsons.put(ing);
                    }

                    js.put("ingredients", ingredientJsons);

                    Log.d("DBG", js.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "http://docker-azure.cloudapp.net/recipe/create";
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, url, js,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String message = response.getString("message");
                                    String id = response.getString("id");
                                    Log.d("DBG", "Message: " + message);
                                    Log.d("DBG", "Dish id: " + id);

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
}
