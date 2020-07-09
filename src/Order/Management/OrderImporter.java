/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import Order.Packing.Item;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.management.IOrder;
import order.management.IOrderImporter;
import order.packing.IItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
 */
public class OrderImporter implements IOrderImporter {

    public OrderImporter() {
    }

    @Override
    public void importData(IOrder iorder, String string) throws IOException, ParseException, ContainerException, OrderException, PositionException {
        //importar o json importado com o caminho string;
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("/Users/tomaspendao/Downloads/dist_students-4/ficheiroJSONExemplo/example.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray orderList = (JSONArray) obj;
            //System.out.println(orderList);

            for (Object o : orderList) {
                JSONObject order = (JSONObject) o;

                //Get Id
                int id = (int) order.get("id");
                iorder.setId(id);

                //Get date object from list
                JSONObject dateObject = (JSONObject) order.get("date");
                //get day from date
                int day = (int) dateObject.get("day");
                //get month from date
                int month = (int) dateObject.get("month");
                //get year from  date
                int year = (int) dateObject.get("year");
                iorder.setDate(day, month, year);

                //get customer object from list
                JSONObject customerObject = (JSONObject) order.get("customer");
                //get name from customer
                String name = (String) customerObject.get("name");
                iorder.getCustomer().setName(name);
                //get vat from customer
                String vat = (String) customerObject.get("vat");
                iorder.getCustomer().setVat(vat);

                //get address object from customer list
                JSONObject addressObject = (JSONObject) customerObject.get("address");
                //get country from address
                String country = (String) addressObject.get("country");
                iorder.getCustomer().getAddress().setCountry(country);
                //get number from address
                int number = (int) addressObject.get("number");
                iorder.getCustomer().getAddress().setNumber(number);
                //get street from address
                String street = (String) addressObject.get("street");
                iorder.getCustomer().getAddress().setStreet(street);
                //get city from address
                String city = (String) addressObject.get("city");
                iorder.getCustomer().getAddress().setCity(city);
                //get state from address
                String state = (String) addressObject.get("state");
                iorder.getCustomer().getAddress().setState(state);

                //get billing address object from customer list
                JSONObject billingAdrObject = (JSONObject) customerObject.get("billingAddress");
                //get country from billing address
                String country2 = (String) billingAdrObject.get("country");
                iorder.getCustomer().getBillingAddress().setCountry(country2);
                //get number from billing address
                int number2 = (int) billingAdrObject.get("number");
                iorder.getCustomer().getBillingAddress().setNumber(number2);
                //get street from billing address
                String street2 = (String) billingAdrObject.get("street");
                iorder.getCustomer().getBillingAddress().setStreet(street2);
                //get city from billing address
                String city2 = (String) billingAdrObject.get("city");
                iorder.getCustomer().getBillingAddress().setCity(city2);
                //get state from billing address
                String state2 = (String) billingAdrObject.get("state");
                iorder.getCustomer().getBillingAddress().setState(state2);

                //get destination object from list
                JSONObject destinationObject = (JSONObject) order.get("destination");
                //get name from destination
                String namedest = (String) destinationObject.get("name");
                iorder.getDestination().setName(namedest);

                //get address object from destination list
                JSONObject addressDestObject = (JSONObject) destinationObject.get("address");
                //get country from address
                String countryDest = (String) addressDestObject.get("country");
                iorder.getDestination().getAddress().setCountry(countryDest);
                //get number from address
                int numberDest = (int) addressDestObject.get("number");
                iorder.getDestination().getAddress().setNumber(numberDest);
                //get street from address
                String streetDest = (String) addressDestObject.get("street");
                iorder.getDestination().getAddress().setStreet(streetDest);
                //get city from address
                String cityDest = (String) addressDestObject.get("city");
                iorder.getDestination().getAddress().setCity(cityDest);
                //get state from address
                String stateDest = (String) addressDestObject.get("state");
                iorder.getDestination().getAddress().setState(stateDest);

                //get items Object from list
                JSONObject itemsObject = (JSONObject) order.get("items");

                //for (int i = 0; i < 10; i++) {
                //get reference from items
                String reference = (String) itemsObject.get("reference");
                //get depth from items
                int depth = (int) itemsObject.get("depth");
                //get lenght from items
                int lenght = (int) itemsObject.get("lenght");
                //get height from items
                int height = (int) itemsObject.get("height");
                //get description from items
                String description = (String) itemsObject.get("description");

                IItem temp = new Item(reference, description, depth, height, lenght);
                iorder.add(temp);
                //}
            }

        } catch (FileNotFoundException e) {
        } catch (IOException | ParseException e) {
        }
    }

}
