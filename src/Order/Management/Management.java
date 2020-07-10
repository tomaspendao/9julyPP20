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
public class Management implements IManagement {

    private static final int MAX_ORDERS = 10;
    private IOrder[] orders;
    private int currentNOrders = 0;

    public Management() {
        orders = new IOrder[MAX_ORDERS];
    }

    @Override
    public boolean add(IOrder iorder) throws OrderException {
        for (int i = 0; i < currentNOrders; i++) {
            if (orders[i].getId() == iorder.getId()) {
                return false;
            }
        }
        if (currentNOrders >= orders.length) {
            int size = orders.length;
            size++;
            IOrder[] temp = new IOrder[size];
            int k = 0;
            while (k < orders.length) {
                temp[k] = orders[k];
                k++;
            }
            orders = temp;
        }
        if (iorder != null) {
            orders[currentNOrders] = iorder;
            currentNOrders++;
            return true;
        } else {
            throw new OrderException("value cant be null") {};
        }
    }

    @Override
    public boolean remove(IOrder iorder) throws OrderException {
        int i = 0;
        int k;
        if(iorder==null){
            throw new OrderException("value cant be null") {};//ver se o iorder e null
        }
        while (i < currentNOrders) {
            if (orders[i].getId() == iorder.getId()) { //ver se a order existe
                k = i;
                for (int j = k; j < currentNOrders; j++) {//fazer rollback
                    k++;
                    if(k>=currentNOrders){
                        orders[j] = null; //"apagar o ultimo valor"
                        currentNOrders--;
                        return true; //?????????????????????????
                    }
                    orders[j] = orders[k];
                }
                //currentNOrders--;
                //return true;
            } else {
                i++;
            }
        }
        return false;
    }

    @Override
    public IOrder[] getOrders(ICustomer ic) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i].getCustomer() == ic) {
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
