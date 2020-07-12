/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import Order.Base.Address;
import Order.Base.Customer;
import Order.Base.Person;
import Order.Packing.Container;
import Order.Packing.Item;
import Order.Packing.ItemPacked;
import Order.Packing.Position;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.base.IAddress;
import order.base.ICustomer;
import order.base.IPerson;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.management.IOrder;
import order.management.IOrderImporter;
import order.management.IShipping;
import order.management.ShipmentStatus;
import order.packing.Color;
import order.packing.IContainer;
import order.packing.IItem;
import order.packing.IItemPacked;
import order.packing.IPosition;
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
    @SuppressWarnings("null")
    public void importData(IOrder iorder, String string) throws IOException, ParseException, ContainerException, OrderException, PositionException {
        //iorder = new Order();
        //importar o json importado com o caminho string;
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(string)) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;

            //get Id from list
            int id = Integer.parseInt(jsonObj.get("id").toString());
            iorder.setId(id);
            //get date object from list
            JSONObject dateObj = (JSONObject) jsonObj.get("date");
            //get date values from dateObj
            int day = Integer.parseInt(dateObj.get("day").toString());
            int month = Integer.parseInt(dateObj.get("month").toString());
            int year = Integer.parseInt(dateObj.get("year").toString());
            iorder.setDate(day, month, year);

            //get customer object from list
            JSONObject customerObj = (JSONObject) jsonObj.get("customer");
            //get values from customer Object
            String nameCust = (String) customerObj.get("name");
            String vat = (String) customerObj.get("vat");
            //get address object from customer object
            JSONObject addressCustObj = (JSONObject) customerObj.get("address");
            //get values from address Object
            String countryCustAddr = (String) addressCustObj.get("country");
            int numberCustAddr =  Integer.parseInt(addressCustObj.get("number").toString());
            String streetCustAddr = (String) addressCustObj.get("street");
            String cityCustAddr = (String) addressCustObj.get("city");
            String stateCustAddr = (String) addressCustObj.get("state");
            
            //get billing address object from customer object
            JSONObject billingAddressCustObj = (JSONObject) customerObj.get("billingAddress");
            //get values from billing Address object
            String countryCustBillAddr = (String) billingAddressCustObj.get("country");
            int numberCustBillAddr = Integer.parseInt(billingAddressCustObj.get("number").toString());
            String streetCustBillAddr = (String) billingAddressCustObj.get("street");
            String cityCustBillAddr = (String) billingAddressCustObj.get("city");
            String stateCustBillAddr = (String) billingAddressCustObj.get("state");
            
            //create address's
            IAddress addressCust = new Address(cityCustAddr, countryCustAddr, numberCustAddr, stateCustAddr, streetCustAddr);
            IAddress billingAddressCust = new Address(cityCustBillAddr, countryCustBillAddr, numberCustBillAddr, stateCustBillAddr, streetCustBillAddr);
            //create costumer
            ICustomer customer = new Customer(id, vat, billingAddressCust, addressCust, nameCust); //id???
            
            iorder.setCustomer(customer);
            
            //get destination from list
            JSONObject destinationObj = (JSONObject) jsonObj.get("destination");
            //get values from destination object
            String nameDest = (String) destinationObj.get("name");
            //get address object from destination object
            JSONObject addressObj = (JSONObject) destinationObj.get("address");
            //get values from address object
            String countryDestAddr = (String) addressObj.get("country");
            int numberDestAddr = Integer.parseInt(addressObj.get("number").toString());
            String streetDestAddr = (String) addressObj.get("street");
            String cityDestAddr = (String) addressObj.get("city");
            String stateDestAddr = (String) addressObj.get("state");
            //create address
            IAddress addressDest = new Address(cityDestAddr, countryDestAddr, numberDestAddr, stateDestAddr, streetDestAddr);
            //create destination
            IPerson destination = new Person(addressDest, nameDest);
            
            iorder.setDestination(destination);
            
            //get items array from list
            
            JSONArray itemsArray = (JSONArray) jsonObj.get("items");
            for (int i = 0; i < itemsArray.size(); i++) {                
                JSONObject itemsObj = (JSONObject) itemsArray.get(i);
                String reference = (String) itemsObj.get("reference");
                int depth = Integer.parseInt(itemsObj.get("depth").toString());
                int length = Integer.parseInt(itemsObj.get("length").toString());
                int height = Integer.parseInt(itemsObj.get("height").toString());
                String description = (String) itemsObj.get("description");
                IItem item = new Item(reference, description, depth, height, length);
                iorder.add(item);
            }

        } catch (FileNotFoundException e) {
        }
    }

}
