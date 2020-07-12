
import Order.Management.ExporterB;
import Order.Management.Order;
import Order.Management.OrderImporter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.management.IOrder;
import order.management.IShipping;
import order.packing.IContainer;
import order.packing.IItem;
import order.packing.IItemPacked;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import pp_er_8170308.PP_ER_8170308;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tomaspendao
 */
public class Test {

    public static void main(String[] args) throws ContainerException, OrderException, IOException {

       
        
        int arrayData[] = {0, 1, 2, 3, 4, 5};
        String[] arrayStr = {"AWAITS_TREATMENT", "CANCELLED", "CLOSED", "IN_TREATMENT", "RECEIVED", "SHIPPED"};
        JSONArray dataArray = new JSONArray();
        dataArray.add(arrayData[0]);
        dataArray.add(arrayData[1]);
        dataArray.add(arrayData[2]);
        dataArray.add(arrayData[3]);
        dataArray.add(arrayData[4]);
        JSONArray labelArray = new JSONArray();
        labelArray.add(arrayStr[0]);
        labelArray.add(arrayStr[1]);
        labelArray.add(arrayStr[2]);
        labelArray.add(arrayStr[3]);
        labelArray.add(arrayStr[4]);
        
        JSONObject obj = new JSONObject();
        JSONObject dataDetailsObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject dataObj = new JSONObject();
        dataObj.put("data", dataArray);
        dataObj.put("label", "percentage of each item status");
        jsonArray.add(dataObj);        
        
        
        dataDetailsObj.put("datasets", jsonArray);
        dataDetailsObj.put("labels", labelArray);
        obj.put("data", dataDetailsObj);
        obj.put("type", "bar");
        obj.put("title", "percentage of order in each status");
        

        try (FileWriter file = new FileWriter("PercentageOfOrdersInEachStatus.json")) {

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(packing_gui.PackingGUI.validate("PercentageOfOrdersInEachStatus.json") == true){
            try {
                packing_gui.PackingGUI.render("PercentageOfOrdersInEachStatus.json");
            } catch (FileNotFoundException | ParseException ex) {
                Logger.getLogger(ExporterB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(obj);
    }
}
