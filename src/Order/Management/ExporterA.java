/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import java.io.IOException;
import order.management.IExporter;
import order.management.IOrder;
import order.management.IShipping;


import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
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

public class ExporterA implements IExporter{
    
    private IOrder[] orders;
    //private String[] pathToFiles;
    
     public ExporterA(IOrder[] orders) {
        this.orders = orders;
    }
    
    
    @Override
    public void export() throws IOException {
        String[] pathToFiles = null;
        int res=0;
        for (int i = 0; i < orders.length; i++) {
            if(orders[i].getNumberOfRemaingItemsToSend() > 0){
                int total = orders[i].getNumberOfItems();
                int minus = orders[i].getNumberOfRemaingItemsToSend();
                res = (100 * minus)/total;
                
                
                JSONObject obj = new JSONObject();
                int[] arrayData = {100-res,res};
                obj.put("data", arrayData);
                obj.put("label", "Remaining Items");
                
                JSONObject datasetobject = new JSONObject();
                datasetobject.put("datasets", obj);
                datasetobject.put("label", "Remaining Items");
                
                JSONObject dataObject = new JSONObject();
                dataObject.put("data", datasetobject);
                dataObject.put("type", "pie");
                dataObject.put("title", "Percentage of Remaining Items to send");
                
                JSONArray dataArray = new JSONArray();
                dataArray.add(dataObject);
                
                try (FileWriter file = new FileWriter("/Users/Shared/PercentageOfItemsToSend" + i + ".json")){
                    file.write(dataArray.toJSONString());
                    file.flush();
                } catch (IOException e) {
                }
                
                if(packing_gui.PackingGUI.validate("/Users/Shared/PercentageOfItemsToSend" + i + ".json") == true){
                    for (int j = 0; j < pathToFiles.length; j++) {
                        if(pathToFiles[j] == null){
                            pathToFiles[i] = "/Users/Shared/PercentageOfItemsToSend" + i + ".json";
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
