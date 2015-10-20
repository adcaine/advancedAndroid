package com.caine.allan.networkarchitecture.web;

import com.caine.allan.networkarchitecture.models.VenueSearchResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by allancaine on 2015-10-19.
 */
public class VenueListDeserializer implements JsonDeserializer<VenueSearchResponse> {

    @Override
    public VenueSearchResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement responseJson = json.getAsJsonObject().get("response");
        return new Gson().fromJson(responseJson, VenueSearchResponse.class);
    }
}
