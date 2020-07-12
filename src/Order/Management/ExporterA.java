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
public class ExporterA implements IExporter {

    private IOrder[] orders;
    private String[] pathToFiles;

    public ExporterA(IOrder[] orders) {
        this.orders = orders;
    }

    @Override
    public void export() throws IOException {
        int size = orders.length;
        pathToFiles = new String[size];
        int j = 0;
        for (int i = 0; i < orders.length; i++) {
            if (orders[i].getNumberOfRemaingItemsToSend() > 0) {
                int total = orders[i].getNumberOfItems();
                int part = orders[i].getNumberOfRemaingItemsToSend();
                int res = (part * 100) / total;

                int arrayData[] = {res, 100 - res};
                String[] arrayStr = {"To Send", "Sent"};
                JSONArray labelArray = new JSONArray();
                labelArray.add(arrayStr[0]);
                labelArray.add(arrayStr[1]);
                
                JSONArray dataArray = new JSONArray();
                for (int d = 0; d < arrayData.length; d++) {
                    dataArray.add(arrayData[d]);
                }

                JSONObject obj = new JSONObject();
                JSONObject dataDetailsObj = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject dataObj = new JSONObject();
                dataObj.put("data", dataArray);
                dataObj.put("label", "Percentage of Items to send");
                jsonArray.add(dataObj);
                
                
                dataDetailsObj.put("datasets", jsonArray);
                dataDetailsObj.put("labels", labelArray);
                obj.put("data", dataDetailsObj);
                obj.put("type", "pie");
                obj.put("title", "Percentage of Items to send");

                try (FileWriter file = new FileWriter("PercentageOfItemsToSend" + i + ".json")) {

                    file.write(obj.toJSONString());
                    file.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (packing_gui.PackingGUI.validate("PercentageOfItemsToSend" + i + ".json") == true) {
                    pathToFiles[j] = "PercentageOfItemsToSend" + i + ".json";
                    j++;
                }

            }
            try {
                packing_gui.PackingGUI.render(pathToFiles);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ExporterA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
