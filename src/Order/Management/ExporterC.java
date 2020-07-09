/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ExporterC implements IExporter{

    private IOrder[] orders;

    public ExporterC(IOrder[] orders) {
        this.orders = orders;
    }
    
    @Override
    public void export() throws IOException {
        String[] pathToFiles = null;
        int[] foundIds = null;
        for (int i = 0; i < orders.length; i++) {
            for (int j = 0; j < foundIds.length; j++) {
                int countCustomer = 0;
                if(orders[i].getCustomer().getCustomerId() == foundIds[j]){
                    break;
                }
                int k = i + 1;
                for (k = 0; k < orders.length; k++) {
                    if(orders[k].getCustomer().getCustomerId() == foundIds[j]){
                        countCustomer++;
                    }
                }
                JSONObject obj = new JSONObject();
                int[] arrayData = {orders.length,countCustomer};
                obj.put("data", arrayData);
                obj.put("label", "Customer and number of orders");
                
                JSONObject datasetobject = new JSONObject();
                datasetobject.put("datasets", obj);
                datasetobject.put("label", "Customer and number of orders");
                
                JSONObject dataObject = new JSONObject();
                dataObject.put("data", datasetobject);
                dataObject.put("type", "pie");
                dataObject.put("title", "Customer and number of orders");
                
                JSONArray dataArray = new JSONArray();
                dataArray.add(dataObject);
                
                try (FileWriter file = new FileWriter("/Users/Shared/ClientsAndOrders" + i + ".json")){
                    file.write(dataArray.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                if(packing_gui.PackingGUI.validate("/Users/Shared/ClientsAndOrders" + i + ".json") == true){
                    for (String pathToFile : pathToFiles) {
                        if (pathToFile == null) {
                            pathToFiles[i] = "/Users/Shared/ClientsAndOrders" + i + ".json";
                        }
                    }
                }
            }
            
            
        }
        try {
            packing_gui.PackingGUI.render(pathToFiles);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(ExporterA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
