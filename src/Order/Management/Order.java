/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import Order.Packing.Item;
import java.time.LocalDate;
import java.time.Month;
import order.base.ICustomer;
import order.base.IPerson;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.management.IOrder;
import order.management.IShipping;
import order.management.ShipmentStatus;
import order.packing.IContainer;
import order.packing.IItem;
import order.packing.IItemPacked;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
 */
public class Order implements IOrder {

    private static final int MAX_SHIPPING = 10;
    private static final int MAX_ITENS = 20;

    private IPerson destination;
    private ICustomer customer;
    private IItem[] items;
    private int id;
    private LocalDate date;
    private IShipping[] shippings;

    private int numberOfItems = 0;
    private int numberOfShippings = 0;
    private boolean isClosed = false;
    private double orderPrice;

    public Order() {
        shippings = new IShipping[MAX_SHIPPING];
        items = new IItem[MAX_ITENS];
    }
    

    public Order(IPerson destination, ICustomer customer, int id, LocalDate date, double orderPrice) {
        setDestination(destination);
        setCustomer(customer);
        setId(id);
        setDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        this.numberOfItems = items.length;
        this.orderPrice = orderPrice;

        shippings = new IShipping[MAX_SHIPPING];
        items = new IItem[MAX_ITENS];
    }

    public Order(IPerson destination, ICustomer customer, int id, LocalDate date) {
        setDestination(destination);
        setCustomer(customer);
        items = new IItem[MAX_ITENS];
        setId(id);
        setDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        shippings = new IShipping[MAX_SHIPPING];
    }

    @Override
    public IPerson getDestination() {
        return destination;
    }

    @Override
    public void setDestination(IPerson person) {
        this.destination = person;
    }

    @Override
    public ICustomer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(ICustomer customer) {
        this.customer = customer;
    }

    @Override
    public IItem[] getItems() {
        return items;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setDate(int day, int month, int year) {
        this.date = LocalDate.of(year, month, day);
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean add(IItem iitem) throws OrderException {
        for (int i = 0; i < numberOfItems; i++) {
            if (items[i].getReference().equals(iitem.getReference()) == true) {
                return false;
            }
        }
        if (numberOfItems >= items.length) {
            int size = items.length;
            size = size * 2;
            IItem[] temp = new IItem[size];
            int k = 0;
            while (k < items.length) {
                temp[k] = items[k];
                k++;
            }
            items = temp;
        }
        if (iitem != null) {
            items[numberOfItems] = iitem;
            numberOfItems++;
            return true;
        } else {
            throw new OrderException("value cant be null") {
            };
        }
    }

    @Override
    public IShipping[] getShippings() {
        return shippings;
    }

    @Override
    public boolean addShipping(IShipping is) throws OrderException {
        if (numberOfShippings >= MAX_SHIPPING) {
            return false;
        }
        if (isClosed() == true || is == null) {
            throw new OrderException() {
            };
        } else {
            shippings[numberOfShippings] = is;
            numberOfShippings++;
            return true;
        }

    }

    @Override
    public boolean removeShipping(IShipping is) throws OrderException {
        if (is == null) {
            throw new OrderException("value cant be null") {
            };
        }
        int k;
        for (int i = 0; i < numberOfShippings; i++) {
            //if (shippings[i].equals(is) == true) {
            if (shippings[i].summary().equals(is.summary()) == true) {
                k = i;
                for (int j = k; j < numberOfShippings; j++) {
                    k++;
                    if (k >= numberOfShippings) {
                        shippings[j] = null;
                        numberOfShippings--;
                        return true;
                    }
                    shippings[j] = shippings[k];
                }
                //numberOfShippings--;
                //return true;
            }
        }
        return false;
    }

    @Override
    public int clean() {
        int res = 0;
        for (int i = 0; i < shippings.length; i++) {
            if (shippings[i].getShipmentStatus() == ShipmentStatus.CANCELLED) {
                shippings[i] = null;
                res++;
            }
        }
        return res;
    }

    @Override
    public void validate() throws OrderException, ContainerException, PositionException {
        for (int i = 0; i < shippings.length; i++) {
            shippings[i].validate();
        }
    }

    @Override
    public void close() throws OrderException, ContainerException, PositionException {
        validate(); //try and catch?????????????
        int count = 0;
        for (int i = 0; i < shippings.length; i++) {
            if (shippings[i].getShipmentStatus() == ShipmentStatus.RECEIVED) {
                count++;
            }
            if (shippings[i].getShipmentStatus() != ShipmentStatus.RECEIVED) {
                shippings[i].setShipmentStatus(ShipmentStatus.CANCELLED);
            }
        }
        if (count == shippings.length) {
            isClosed = true;
            for (int i = 0; i < shippings.length; i++) { // não sei se e necessário fechar cad shipment
                shippings[i].setShipmentStatus(ShipmentStatus.CLOSED);
            }
        }
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public double getCost() {
        for (IShipping shipping : shippings) {
            orderPrice = orderPrice + shipping.getCost();//somar os preços de cada shipping
        }
        return orderPrice;
    }

    @Override
    public String summary() {
        String str = "";
        for (int i = 0; i < shippings.length; i++) {
            str = str + shippings[i].summary();
        }
        return str;
    }

    @Override
    public int getNumberOfItems() {
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getNumberOfRemaingItemsToSend() {
        int count = 0;
        for (int i = 0; i < this.items.length; i++) {
            for (int j = 0; j < shippings.length; j++) {
                int flag = 1;
                IContainer[] tempCont = shippings[j].getContainers();
                for (int k = 0; k < tempCont.length; k++) {
                    if (tempCont[k].getItem(this.items[i].getReference()) != null) {
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public IItem[] getRemainingItemsToSend() {
        int value = getNumberOfRemaingItemsToSend();
        IItem[] itens = new IItem[value];
        int z = 0; //para incrementar o item[]
        for (int i = 0; i < this.items.length; i++) {
            for (int j = 0; j < shippings.length; j++) {
                int k = 0;
                int flag = 1;
                IContainer[] tempCont = shippings[j].getContainers();
                for (k = 0; k < tempCont.length; k++) {
                    if (tempCont[k].getItem(this.items[i].getReference()) != null) { //se o intem nao exister irá retornar null
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    itens[z] = tempCont[k].getItem(this.items[i].getReference());
                    z++;
                }
            }
        }
        return itens;
    }

}
