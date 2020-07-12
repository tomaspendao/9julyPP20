/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp_er_8170308;

import Order.Management.*;
import Order.Packing.*;
import Order.Base.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.base.IAddress;
import order.base.ICustomer;
import order.base.IPerson;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.management.IOrder;
import order.management.ShipmentStatus;
import order.packing.Color;
import order.packing.IItem;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tomaspendao
 */
public class PP_ER_8170308 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws OrderException, ContainerException {
        OrderImporter importer = new OrderImporter();
        IAddress tempAddress = new Address("cidade", "pais", 1, "states", "streets");
        IAddress tempAddress2 = new Address("cidade2", "pais2", 2, "states2", "streets2");
        IAddress tempAddress3 = new Address("cidade3", "pais3", 3, "states3", "streets3");
        IPerson destination = new Person(tempAddress, "destination1");
        ICustomer customer = new Customer(0, "vat", tempAddress2, tempAddress3, "name");
        IOrder order = new Order(destination, customer, 0, LocalDate.MAX);

        IOrder order2 = new Order();

        try {
            importer.importData(order2, "/Users/tomaspendao/NetBeansProjects/CppApplication_1/PP_ER_8170308/src/pp_er_8170308/myJSON.json");
        } catch (IOException | ParseException | PositionException ex) {
            Logger.getLogger(PP_ER_8170308.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(order.getId());
        System.out.println(order2.getId());
        System.out.println(order2.getDate().getYear());
        IItem[] items = order2.getItems();
        System.out.println(items[0].getReference());
        System.out.println(items.length);
        System.out.println(items[1].getReference());
        
    }

}
