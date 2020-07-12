/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import java.io.FileWriter;
import java.io.IOException;
import order.management.IExporter;
import order.management.IOrder;
import order.management.IShipping;
import order.packing.IContainer;
import order.packing.IItem;
import order.packing.IItemPacked;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author tomaspendao
 */
public class Exporter_3 implements IExporter {

    private IOrder order;

    public Exporter_3(IOrder order) {
        this.order = order;
    }

    @Override
    public void export() throws IOException {
        JSONObject jsonObj = new JSONObject();
        JSONObject dateObj = new JSONObject();
        dateObj.put("month", order.getDate().getMonth());
        dateObj.put("year", order.getDate().getYear());
        dateObj.put("day", order.getDate().getDayOfMonth());
        jsonObj.put("date", dateObj);
        JSONArray shippingsArray = new JSONArray();
        IShipping[] tempShipp = order.getShippings();
        for (int i = 0; i < order.getShippings().length; i++) {
            JSONObject shippingsObj = new JSONObject();
            JSONObject shippingObj = new JSONObject();
            shippingObj.put("cost", tempShipp[i].getCost());
            JSONArray containersArray = new JSONArray();
            IContainer[] tempCont = tempShipp[i].getContainers();
            for (int j = 0; j < tempCont.length; j++) {
                JSONObject containerObj = new JSONObject();
                containerObj.put("volume", tempCont[j].getVolume());
                containerObj.put("reference", tempCont[j].getReference());
                containerObj.put("depth", tempCont[j].getDepth());
                containerObj.put("color", tempCont[j].getColor());
                containerObj.put("lenght", tempCont[j].getLenght());
                containerObj.put("closed",tempCont[j].isClosed());
                containerObj.put("colorEdge" , tempCont[j].getColorEdge());
                JSONArray itemsArray = new JSONArray();
                IItemPacked[] tempPackeIt = tempCont[j].getPackedItems();
                for (int k = 0; k < 10; k++) {
                    IItem tempItem = tempPackeIt[k].getItem();
                    JSONObject itemObj = new JSONObject();
                    itemObj.put("reference", tempItem.getReference());
                    itemObj.put("depth", tempItem.getDepth());
                    itemObj.put("color", tempPackeIt[k].getColor());
                    itemObj.put("x", tempPackeIt[k].getPosition().getX());
                    itemObj.put("length", tempItem.getLenght());
                    itemObj.put("y", tempPackeIt[k].getPosition().getY());
                    itemObj.put("description", tempItem.getDescription());
                    itemObj.put("z", tempPackeIt[k].getPosition().getZ());
                    itemObj.put("colorEdge", tempPackeIt[k].getColorEdge());
                    itemObj.put("height", tempItem.getHeight());
                    
                    itemsArray.add(itemObj);
                    
                }
                containerObj.put("items", itemsArray);
                containerObj.put("height", tempCont[j].getHeight());
                containerObj.put("occupiedVolume", tempCont[j].getOccupiedVolume());
                
                containersArray.add(containerObj);
                
            }
            shippingObj.put("containers", containersArray);
            shippingsObj.put("shipping", shippingObj);
            shippingsObj.put("status", tempShipp[i].getShipmentStatus());
            shippingsArray.add(shippingsObj);
        }
        jsonObj.put("shippings", shippingsArray);
        
        JSONObject destinationObj = new JSONObject();
        JSONObject addressDestObj = new JSONObject();
        addressDestObj.put("country", order.getDestination().getAddress().getCountry());
        addressDestObj.put("number", order.getDestination().getAddress().getNumber());
        addressDestObj.put("city", order.getDestination().getAddress().getCity());
        addressDestObj.put("street", order.getDestination().getAddress().getStreet());
        addressDestObj.put("state", order.getDestination().getAddress().getState());
        destinationObj.put("address", addressDestObj);
        destinationObj.put("name", order.getDestination().getName());
        jsonObj.put("destination", destinationObj);
        
        jsonObj.put("id", order.getId());
        
        JSONObject customerObj = new JSONObject();
        JSONObject addressCustObj = new JSONObject();
        addressCustObj.put("country", order.getCustomer().getAddress().getCountry());
        addressCustObj.put("number", order.getCustomer().getAddress().getNumber());
        addressCustObj.put("city", order.getCustomer().getAddress().getCity());
        addressCustObj.put("street", order.getCustomer().getAddress().getStreet());
        addressCustObj.put("state", order.getCustomer().getAddress().getState());
        customerObj.put("address", addressCustObj);
        customerObj.put("name", order.getCustomer().getName());
        customerObj.put("id", order.getCustomer().getCustomerId());
        JSONObject billingAddressObj = new JSONObject();
        billingAddressObj.put("country", order.getCustomer().getBillingAddress().getCountry());
        billingAddressObj.put("number", order.getCustomer().getBillingAddress().getNumber());
        billingAddressObj.put("city", order.getCustomer().getBillingAddress().getCity());
        billingAddressObj.put("street", order.getCustomer().getBillingAddress().getStreet());
        billingAddressObj.put("state", order.getCustomer().getBillingAddress().getState());
        customerObj.put("billingAddress", billingAddressObj);
        jsonObj.put("customer", customerObj);
        
        
        try (FileWriter file = new FileWriter("OrderDetails.json")) {
            file.write(jsonObj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
