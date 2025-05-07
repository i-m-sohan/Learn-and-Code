package service;

import exception.LocationNotFoundException;
import model.Location;
import org.json.*;
import util.ApiUtility;
import wrapper.ApiRequestWrapper;

public class GeocodingService {

    public Location getCoordinates(String place) throws Exception {
        ApiRequestWrapper requestWrapper = prepareRequestWrapper(place);

        try {
            String response = ApiUtility.sendRequest(requestWrapper);
            Location location = extractLocation(response);
            return location;
        }
        catch(LocationNotFoundException locationNotFoundException){
            throw new LocationNotFoundException("For model.Location: "+place+", "+"No result found!");
        }
    }

    private ApiRequestWrapper prepareRequestWrapper(String place){
        ApiRequestWrapper requestWrapper = new ApiRequestWrapper();
        requestWrapper.setMethod("GET");
        requestWrapper.addQueryParam("q", place);

        return requestWrapper;
    }

    private Location extractLocation(String response){
        JSONArray results = new JSONArray(response);
        if (results.length() > 0) {
            JSONObject location = results.getJSONObject(0);
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lon");
            return new Location(lat, lng);
        } else {
            throw new LocationNotFoundException("No model.Location is found for specified place");
        }
    }
}