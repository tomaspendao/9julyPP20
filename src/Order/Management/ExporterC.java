/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.base.ICustomer;
import order.management.IExporter;
import order.management.IOrder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
 */
public class ExporterC implements IExporter {

    private IOrder[] orders;

    public ExporterC(IOrder[] orders) {
        this.orders = orders;
    }

    @Override
    public void export() throws IOException {
        ICustomer customersFound[] = new ICustomer[orders.length];
        int numFound = 0;
        int countArray[] = new int[orders.length];
        for (int i = 0; i < orders.length; i++) {
            int count = 1;
            int flag = 0;
            ICustomer tempCust = orders[i].getCustomer();
            for (int c = 0; c < numFound; c++) {
                if (customersFound[c].getCustomerId() == tempCust.getCustomerId()) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                customersFound[numFound] = tempCust;
                //numFound++;
                for (int j = i + 1; j < orders.length; j++) {
                    if (orders[j].getCustomer().getCustomerId() == tempCust.getCustomerId()) {
                        count++;
                    }
                }
            }
            countArray[numFound] = count;
            numFound++;
        }
        int arrayNum[] = new int[numFound];
        String customerArray[] = new String[numFound];
        for (int i = 0; i < numFound; i++) {
            arrayNum[i] = countArray[i];
            customerArray[i] = customersFound[i].getName();
        }
        
        JSONArray dataArray = new JSONArray();
        for (int i = 0; i < arrayNum.length; i++) {
            dataArray.add(arrayNum[i]);
        }
        JSONArray labelArray = new JSONArray();
        for (int j = 0; j < customerArray.length; j++) {
            labelArray.add(customerArray[j]);
        }
        JSONObject obj = new JSONObject();
        JSONObject dataDetailsObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject dataObj = new JSONObject();
        dataObj.put("data", dataArray);
        dataObj.put("label", "customers");
        jsonArray.add(dataObj);
        
        
        dataDetailsObj.put("datasets", jsonArray);
        dataDetailsObj.put("labels", labelArray);
        obj.put("data", dataDetailsObj);
        obj.put("type", "bar");
        obj.put("title", "Number Of Orders Per Customer");

        try (FileWriter file = new FileWriter("ClientsAndOrders.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(packing_gui.PackingGUI.validate("ClientsAndOrders.json") == true){
            try {
                packing_gui.PackingGUI.render("ClientsAndOrders.json");
            } catch (FileNotFoundException | ParseException ex) {
                Logger.getLogger(ExporterB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}