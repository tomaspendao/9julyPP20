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
import order.management.IExporter;
import order.management.IOrder;
import order.management.IShipping;
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
public class ExporterB implements IExporter {

    private IOrder[] orders;

    public ExporterB(IOrder[] orders) {
        this.orders = orders;
    }

    @Override
    public void export() throws IOException {
        int totalOrders = orders.length;
        String[] pathToFiles = null;
        int resA_T = 0, resCan = 0, resClo = 0, resI_T = 0, resRec = 0, resShi = 0, total = 0;
        for (int i = 0; i < orders.length; i++) {
            IShipping[] tempShip = orders[i].getShippings();
            for (int j = 0; j < tempShip.length; j++) {
                switch (tempShip[j].getShipmentStatus()) {
                    case AWAITS_TREATMENT:
                        resA_T++;
                        total++;
                        break;
                    case CANCELLED:
                        resCan++;
                        total++;
                        break;
                    case CLOSED:
                        resClo++;
                        total++;
                        break;
                    case IN_TREATMENT:
                        resI_T++;
                        total++;
                        break;
                    case RECEIVED:
                        resRec++;
                        total++;
                        break;
                    case SHIPPED:
                        resShi++;
                        total++;
                        break;
                }
            }
        }
        resA_T = (resA_T * 100) / total;
        resCan = (resCan * 100) / total;
        resClo = (resClo * 100) / total;
        resI_T = (resI_T * 100) / total;
        resRec = (resRec * 100) / total;
        resShi = (resShi * 100) / total;
        
        int arrayData[] = {resA_T, resCan, resClo, resI_T, resRec, resShi};
        String[] arrayStr = {"AWAITS_TREATMENT", "CANCELLED", "CLOSED", "IN_TREATMENT", "RECEIVED", "SHIPPED"};

        JSONObject obj = new JSONObject();
        JSONObject dataDetailsObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject dataObj = new JSONObject();
        dataObj.put("data", arrayData);
        JSONObject labelObj = new JSONObject();
        labelObj.put("label", "percentage of each item status");
        jsonArray.add(dataObj);
        jsonArray.add(labelObj);
        dataDetailsObj.put("datasets", jsonArray);
        dataDetailsObj.put("labels", arrayStr);
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
        

    }

}
