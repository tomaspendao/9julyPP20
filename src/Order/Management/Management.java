/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import order.base.ICustomer;
import order.exceptions.OrderException;
import order.management.IManagement;
import order.management.IOrder;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
*/

public class Management implements IManagement{
    
    private IOrder[] orders;

    public Management(IOrder[] orders) {
        this.orders = orders;
    }

    
    @Override
    public boolean add(IOrder iorder) throws OrderException {
        for (int i = 0; i < orders.length; i++) {
            if(orders[i] == iorder){
                return false;
            }
        }
        for (int i = 0; i < orders.length; i++) {
            if(orders[i] == null){
                orders[i] = iorder;
                return true;
            }
        }
        //adicionar posição
        int size = orders.length;
        size++;
        IOrder[] temp;
        temp = new IOrder[size];
        orders = temp;
        
        add(iorder);
        
        return false;
    }

    @Override
    public boolean remove(IOrder iorder) throws OrderException {
        for (int i = 0; i < orders.length; i++) {
            if(orders[i] == iorder){
                orders[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public IOrder[] getOrders(ICustomer ic) {
        for (int i = 0; i < orders.length; i++) {
            if(orders[i].getCustomer() == ic){
                return orders;
            }
        }
        return null;
    }

    @Override
    public IOrder[] getOrders() {
        return orders;
    }
    
}
