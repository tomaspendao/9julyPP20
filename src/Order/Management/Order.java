/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

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
    
    private IPerson destination;
    private ICustomer customer;
    private IItem[] items;
    private int id;
    private LocalDate date;
    private IShipping[] shippings;
    private int numberOfItems;
    private boolean isClosed = false;
    
    private double orderPrice;
    
    public Order(IPerson destination, ICustomer customer, IItem[] items, int id, LocalDate date, double orderPrice) {
        this.destination = destination;
        this.customer = customer;
        this.items = items;
        this.id = id;
        this.date = date;
        //this.shippings = shippings;
        this.numberOfItems = items.length;
        this.orderPrice = orderPrice;
    }
    
    public Order(IPerson destination, ICustomer customer, IItem[] items, int id, LocalDate date, IShipping[] shippings, int numberOfItems, double orderPrice) {
        this.destination = destination;
        this.customer = customer;
        this.items = items;
        this.id = id;
        this.date = date;
        this.shippings = shippings;
        this.numberOfItems = numberOfItems;
        this.orderPrice = orderPrice;
    }
    
    @Override
    public IPerson getDestination() {
        return destination;
    }
    
    @Override
    public void setDestination(IPerson ip) {
        this.destination = ip;
    }
    
    @Override
    public ICustomer getCustomer() {
        return customer;
    }
    
    @Override
    public void setCustomer(ICustomer ic) {
        this.customer = ic;
    }
    
    @Override
    public IItem[] getItems() {
        return items;
    }
    
    @Override
    public void setId(int i) {
        this.id = i;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public void setDate(int i, int i1, int i2) {
        this.date = LocalDate.of(i, i1, i2);
    }
    
    @Override
    public LocalDate getDate() {
        return date;
    }
    
    @Override
    public boolean add(IItem iitem) throws OrderException {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = iitem;
                return true;
            }
            
        }
        //adicionar posição
        numberOfItems++;
        IItem[] temp;
        temp = new IItem[numberOfItems];
        items = temp;
        add(iitem);
        
        return false;
    }
    
    @Override
    public IShipping[] getShippings() {
        return shippings;
    }
    
    @Override
    public boolean addShipping(IShipping is) throws OrderException {
        /*for (IShipping shipping : shippings) { //shipping repetida
            if (shipping == is) {
                return false;
            }
        }*/
        for (int i = 0; i < shippings.length; i++) { //encontrar espaço no array
            if (shippings[i] == null) {
                shippings[i] = is;
                return true;
            }
        }
        int size = shippings.length;
        size++;
        IShipping[] temp;
        temp = new IShipping[size];
        int j;
        for (j = 0; j < shippings.length; j++) {
            temp[j] = shippings[j];
        }
        shippings = temp;
        if (shippings[j] == null) {
            shippings[j] = is;
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean removeShipping(IShipping is) throws OrderException {
        for (int i = 0; i < shippings.length; i++) { //encontrar no array
            if (shippings[i] == is) {
                shippings[i] = null;
                return true;
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
            /*for (int i = 0; i < shippings.length; i++) { // não sei se e necessário fechar cad shipment
                shippings[i].setShipmentStatus(ShipmentStatus.CLOSED);
            }*/
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
