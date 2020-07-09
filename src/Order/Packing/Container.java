/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

import order.exceptions.ContainerException;
import order.exceptions.PositionException;
import order.packing.Color;
import order.packing.IContainer;
import order.packing.IItem;
import order.packing.IItemPacked;
import order.packing.IPosition;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
 */
public class Container implements IContainer{

    //private IItem item;
    private IItemPacked[] packedItems;
    private String reference;
    //private int numberOfItems;
    private final int volume;
    private final int depth;  //--> x
    private final int height; //--> y
    private final int lenght; //--> z
    private Color color;
    private Color colorEdge;
    private int currentVolume;

    private boolean isClosed;

    public Container(IItemPacked[] packedItems, String reference, int volume, int lenght, int height, int depth, Color color, Color colorEdge, boolean isClosed) {
        this.packedItems = packedItems;
        this.reference = reference;
        //this.numberOfItems = numberOfItems; 
        //this.numberOfItems = packedItems.length; //^|^ ??
        this.volume = volume;
        this.lenght = lenght;
        this.height = height;
        this.depth = depth;
        this.color = color;
        this.colorEdge = colorEdge;
        this.isClosed = isClosed;
        for (int i = 0; i < packedItems.length; i++) {
            this.currentVolume = this.currentVolume + packedItems[i].getItem().getVolume();
        }
    }

    @Override
    public boolean addItem(IItem iitem, IPosition ip, Color color) throws ContainerException {

        for (int j = 0; j < packedItems.length; j++) {
            if (packedItems[j].getItem() == iitem) {
                return false;
            }
        }
        for (int i = 0; i < packedItems.length; i++) {
            if (packedItems[i] == null) {
                IItemPacked tempPacked = new ItemPacked(iitem, ip, color);
                packedItems[i] = tempPacked;
                int plusVolume = iitem.getVolume();
                this.currentVolume = this.currentVolume + plusVolume;
                return true;
            }
            IItemPacked[] temp;
            int newInt = packedItems.length;
            newInt = newInt * 2;
            temp = new IItemPacked[newInt];
            packedItems = temp;
            addItem(iitem, ip, color);
        }
        return false;
    }

    @Override
    public boolean removeItem(IItem iitem) throws ContainerException {
        for (int i = 0; i < packedItems.length; i++) {
            if (packedItems[i].getItem() == iitem) {
                int lessVolume = packedItems[i].getItem().getVolume();
                packedItems[i] = null;
                this.currentVolume = this.currentVolume - lessVolume;
                return true;
            }
        }
        return false;
    }

    @Override
    public void validate() throws ContainerException, PositionException {
        if (currentVolume > volume) {
            throw new ContainerException("Volume reached maximum capacity") {
            };
        }
        for (int i = 0; i < packedItems.length; i++) {
            if (packedItems[i].getPosition().getX() > depth 
                    || packedItems[i].getPosition().getY() > height
                    || packedItems[i].getPosition().getZ() > lenght) {//verificar se todos os itens se encontram dentro do container
                throw new PositionException("Item out of container, overflowing") {};
            }
            for (int j = 0; j < packedItems.length; j++) { //varificar sobreposiçaõ de intems
                if(packedItems[i].getPosition().getX() == packedItems[j].getPosition().getX()
                        || packedItems[i].getPosition().getY() == packedItems[j].getPosition().getY()
                        || packedItems[i].getPosition().getZ() == packedItems[j].getPosition().getZ()){
                    throw new PositionException("Items overlapping"){};
                    
                }
                if(j==i){
                    j++;
                }
            }
        }
    }

    @Override
    public void close() throws ContainerException, PositionException {
        
        if (isClosed != true) { //como confirmar...
            validate(); // saber se se pode fechar //?????????
            isClosed = true;
        }
    }

    @Override
    public IItem getItem(String string) {
        for (int i = 0; i < packedItems.length; i++) {
            if (packedItems[i].getItem().getReference().equals(string)) {
                return packedItems[i].getItem();
            }
        }
        return null;
    }
    
    @Override
    public int getOccupiedVolume() {
        int totalVolume = 0;
        for (int i = 0; i < packedItems.length; i++) {
            if (packedItems[i] != null) {
                totalVolume = totalVolume + packedItems[i].getItem().getVolume();
            }
        }
        return totalVolume;
        //return currentVolume;
    }

    @Override
    public IItemPacked[] getPackedItems() {
        return packedItems;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public int getNumberOfItems() {
        int count = 0;
        for (int i = 0; i < packedItems.length; i++) {
            if (packedItems[i] != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getRemainingVolume() {
        int temp = volume;
        temp = volume - getOccupiedVolume();

        return temp;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLenght() {
        return lenght;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Color getColorEdge() {
        return colorEdge;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setColorEdge(Color color) {
        this.colorEdge = color;
    }

    

}
