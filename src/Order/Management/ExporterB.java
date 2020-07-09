/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import java.io.IOException;
import order.management.IExporter;
import order.management.IOrder;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
*/
public class ExporterB implements IExporter{
    
    private IOrder[] orders;
    
     public ExporterB(IOrder[] orders) {
        this.orders = orders;
    }

    @Override
    public void export() throws IOException {
          int totalOrders = orders.length;
        int count1,count2,count3,count4,count5,count6 = 0;
        for (int i = 0; i < orders.length; i++) {
            
        }
    }
    
}
