/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Management;

import java.util.logging.Level;
import java.util.logging.Logger;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.management.IShipping;
import order.management.ShipmentStatus;
import static order.management.ShipmentStatus.CLOSED;
import order.packing.IContainer;
import order.packing.IItemPacked;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
 */
public class Shipping implements IShipping {

    private static final int MAX_CONTAINERS = 10;

    private IContainer[] containers;
    private ShipmentStatus shipmentStatus;
    private int numberOfCont = 0;
    //private double cost;
    private final double pricePerCubicVolume; //não sei se e para colocar aqui

    public Shipping(ShipmentStatus shipmentStatus, double pricePerCubicVolume) {
        containers = new IContainer[MAX_CONTAINERS];
        try {
            setShipmentStatus(shipmentStatus);
        } catch (OrderException | ContainerException | PositionException ex) {
            Logger.getLogger(Shipping.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.pricePerCubicVolume = pricePerCubicVolume;
    }
    
    public Shipping(ShipmentStatus shipmentStatus) {
        containers = new IContainer[MAX_CONTAINERS];
        try {
            setShipmentStatus(shipmentStatus);
        } catch (OrderException | ContainerException | PositionException ex) {
            Logger.getLogger(Shipping.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.pricePerCubicVolume = 12.00;
    }

    public double getPricePerCubicVolume() {
        return pricePerCubicVolume;
    }

    @Override
    public boolean addContainer(IContainer ic) throws OrderException, ContainerException {
        if (shipmentStatus != ShipmentStatus.IN_TREATMENT) {
            throw new OrderException("Not IN_TREATMENT") {
            };
        }
        
        for (int j = 0; j < numberOfCont; j++) {
            if (containers[j].getReference().equals(ic.getReference()) == true) {
                return false;
            }
        }
        if(numberOfCont >= containers.length){
            int size = containers.length;
            size++;
            IContainer[] temp = new IContainer[size];
            int k = 0;
            while (k<containers.length) {                
                temp[k] = containers[k];
                k++;
            }
            containers = temp;
        }
        if (ic == null || ic.isClosed() == false) {
            throw new ContainerException() {
            };
        }else{
            containers[numberOfCont] = ic;
            numberOfCont++;
            return true;
        }
    }

    @Override
    public boolean removeContainer(IContainer ic) throws OrderException, ContainerException {
        if(ic == null) {
            throw new ContainerException("value cant be null") {
            };
        }
        if(shipmentStatus != ShipmentStatus.IN_TREATMENT){
            throw new OrderException("Not in treatment") {
            };
        }
        int k;
        for (int i = 0; i < numberOfCont; i++) {
            if(containers[i].getReference().equals(ic.getReference())){
                k=i;
                for (int j = 0; j < numberOfCont; j++) {
                    k++;
                    if (k>=numberOfCont) {
                        containers[j] = null;
                        numberOfCont--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean existsContainer(IContainer ic) {
        for (int i = 0; containers.length < 10; i++) {
            if (containers[i].getReference().equals(ic.getReference())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IContainer findContainer(String reference) {
        for (IContainer container : containers) {
            if (container.getReference().equals(reference)) { //encontrar o contentor atraves da refrencia comparando as strings
                return container;
            }
        }
        return null;
    }

    @Override
    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    @Override
    public void setShipmentStatus(ShipmentStatus ss) throws OrderException, ContainerException, PositionException {
        switch (ss) {
            case IN_TREATMENT:
                if (shipmentStatus == ShipmentStatus.AWAITS_TREATMENT) {
                    shipmentStatus = ss;
                } else {
                    throw new OrderException("Previous Shipment Status Incorrect") {
                    };
                }
                break;
            case CLOSED:
                if (shipmentStatus == ShipmentStatus.IN_TREATMENT) {
                    if (containers.length > 0) { //ter mais que um contentor no shipping
                        validate();
                        shipmentStatus = ss;
                    }
                } else {
                    throw new OrderException("Previous Shipment Status Incorrect") {
                    };
                }
                break;
            case SHIPPED:
                if (shipmentStatus == ShipmentStatus.CLOSED) {
                    shipmentStatus = ss;
                } else {
                    throw new OrderException("Previous Shipment Status Incorrect") {
                    };
                }
                break;
            case RECEIVED:
                if (shipmentStatus == ShipmentStatus.SHIPPED) {
                    shipmentStatus = ss;
                } else {
                    throw new OrderException("Previous Shipment Status Incorrect") {
                    };
                }
                break;
            case CANCELLED:
                if (shipmentStatus == ShipmentStatus.RECEIVED) {
                    shipmentStatus = ss;
                } else {
                    throw new OrderException("Previous Shipment Status Incorrect") {
                    };
                }
                break;
        }

    }

    @Override
    public IContainer[] getContainers() {
        return containers;
    }

    @Override
    public void validate() throws ContainerException, PositionException {
        for (int i = 0; i < containers.length; i++) { //falatam as exceções ??????????????????????????
            containers[i].validate();
        }
    }

    @Override
    public String summary() {
        String str = "";
        for (int i = 0; i < containers.length; i++) {
            if (containers[i] != null) {
                //info do contentor
                str = str + "Number Of items:";
                str = str + String.valueOf(containers[i].getNumberOfItems());
                str = str + "\n";
                str = str + "Volume:";
                str = str + String.valueOf(containers[i].getVolume());
                str = str + "\n";
                str = str + "Remaining Volume:";
                str = str + String.valueOf(containers[i].getRemainingVolume());
                str = str + "\n";
                str = str + "Color:";
                str = str + String.valueOf(containers[i].getColor());
                str = str + "\n";
                str = str + "Depth:";
                str = str + String.valueOf(containers[i].getDepth());
                str = str + "\n";
                str = str + "Height:";
                str = str + String.valueOf(containers[i].getHeight());
                str = str + "\n";
                str = str + "Lenght:";
                str = str + String.valueOf(containers[i].getLenght());
                str = str + "\n";
                str = str + "Reference:";
                str = str + String.valueOf(containers[i].getReference());
                str = str + "\n";
                IItemPacked[] temppacked = containers[i].getPackedItems();
                for (int j = 0; j < temppacked.length; j++) {
                    if (temppacked[j] != null) {
                        //info dos itens
                        str = str + "\tDescription:";
                        str = str + String.valueOf(temppacked[j].getItem().getDescription());
                        str = str + "\n";
                        str = str + "\tVolume:";
                        str = str + String.valueOf(temppacked[j].getItem().getVolume());
                        str = str + "\n";
                        str = str + "\tDepth:";
                        str = str + String.valueOf(temppacked[j].getItem().getDepth());
                        str = str + "\n";
                        str = str + "\tHeight:";
                        str = str + String.valueOf(temppacked[j].getItem().getHeight());
                        str = str + "\n";
                        str = str + "\tLenght:";
                        str = str + String.valueOf(temppacked[j].getItem().getLenght());
                        str = str + "\n";
                        str = str + "\tReference:";
                        str = str + String.valueOf(temppacked[j].getItem().getReference());
                        str = str + "\n";
                    }
                }
            }
        }
        return str;
    }

    @Override
    public double getCost() {
        double res = 0;
        for (int i = 0; i < containers.length; i++) {
            double vol = containers[i].getOccupiedVolume();
            vol = vol * getPricePerCubicVolume();
            res = res + vol;
        }
        return res;
    }

}
