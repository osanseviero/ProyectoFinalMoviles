package com.example.osanseviero.proyectofinalmoviles;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by osanseviero on 4/30/18.
 */

public class userRequests {
    public void createUserSignUp(String username, String name, String email, String password) {
        createUser(username, name, email, password, "4", "");

    }


    public void createUser(String username, String name, String email, String password, String kind,
                           String token) {

        JSONObject js = new JSONObject();
        try {
            JSONObject userJson = new JSONObject();

            userJson.put("username", username);
            userJson.put("email", email);
            userJson.put("password", password);
            userJson.put("kind", kind);
            userJson.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO: Make volley request

    }

    public void login(String username, String password) {
        JSONObject js = new JSONObject();
        try {
            JSONObject userJson = new JSONObject();

            userJson.put("username", username);
            userJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO: Make volley request
    }
}
