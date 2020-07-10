/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp_er_8170308;

import Order.Management.*;
import Order.Packing.*;
import Order.Base.*;
import java.time.LocalDate;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.management.ShipmentStatus;
import order.packing.Color;

/**
 *
 * @author tomaspendao
 */
public class PP_ER_8170308 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws OrderException, ContainerException {
        
        Position position1 = new Position(0, 0, 0);
        Position position2 = new Position(5, 5, 0);
        Position position3 = new Position(10, 10, 0);
        
        Address adress1 = new Address("Porto", "Portugal", 12, "Porto", "Rua");
        
        Address adress2 = new Address("Porto", "Portugal", 13, "Porto", "rua2");
        
        Person person1 = new Person(adress1, "Victor");
        
        Customer customer1 = new Customer(01, "vat?", adress2, adress2, "customer_name");
        
        Item item1 = new Item("referencia", "descrição", 1, 1, 1, 1);
        
        Item item2 = new Item("referencia2", "outra_descição", 10, 10, 10, 1000);
        
        Item item3 = new Item("referencia3", "outra__descição", 5, 5, 5, 125);
        
        ItemPacked packed1 = new ItemPacked(item1, position1, Color.silver, Color.red);
        ItemPacked packed2 = new ItemPacked(item2, position2, Color.aqua, Color.red);
        ItemPacked packed3 = new ItemPacked(item3, position3, Color.gray, Color.red);
        
        ItemPacked[] packes = {packed1,packed2};
        ItemPacked[] packe2 = {packed2};
        
        Item[] items = {item1,item2};
        //items[0] = item1;
        //items[1] = item2;
        
        //Order order1 = new Order(person1, customer1, items, 0, LocalDate.MAX, 1.0);
        
                
        //System.out.println(order1.getNumberOfItems());
        
        Container container1 = new Container(packes, "referencia", 12500, 50, 102, 50, Color.silver, Color.red, true);
        Container container2 = new Container(packe2, "outra_referencia", 125000, 50, 50, 50, Color.silver, Color.red, true);
        Container container3 = new Container(packes, "outra-_referencia", 50, 50, 50, 125000, Color.silver, Color.red, true);
        Container[] containers = {container1,container2};
       
        //Shipping shipping1 = new Shipping(containers, ShipmentStatus.SHIPPED, 12.0);
        //shipping1.addContainer(container3);
        
        //System.out.println(shipping1.summary());
        //System.out.println(shipping1.removeContainer(container2));
        container1.removeItem(item2);
        container1.removeItem(item1);
        //System.out.println(shipping1.summary());
        System.out.println(item1.getVolume());
        
    }
    
}
