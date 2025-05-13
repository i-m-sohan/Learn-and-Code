import exception.LocationNotFoundException;
import exception.RequestConnectionException;
import model.Location;
import service.GeocodingService;
import util.InputHelper;

public class GeoLocatorApp {
    public static void main(String[] args) {
        try {
            String place = InputHelper.getUserInput("Enter Place : ");
            GeocodingService service = new GeocodingService();
            Location location = service.getCoordinates(place);
            System.out.println(location);
        }
        catch(RequestConnectionException requestConnectionException){
            System.out.println(requestConnectionException.getMessage());
        }
        catch(LocationNotFoundException excep){
            System.out.println(excep.getMessage());
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
