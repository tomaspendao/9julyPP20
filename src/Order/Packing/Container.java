/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

import order.exceptions.ContainerException;
import order.exceptions.PositionException;
import order.packing.Color;
import order.packing.IColored;
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

public class Container extends Box implements IContainer, IColored {

    private static final int MAX_ITENS_P = 10;
    private IItemPacked[] packedItems;
    private final String reference;
    private int volume;
    private int depth;  //--> x
    private int height; //--> y
    private int lenght; //--> z
    private Color color;
    private Color colorEdge;
    private int currentVolume;

    private boolean isClosed;
    private int currentNumberOfItems;

    public Container(String reference, int volume, int lenght, int height, int depth, Color color, Color colorEdge, boolean isClosed) {
        super(depth,height,lenght,volume);
        packedItems = new IItemPacked[MAX_ITENS_P];
        this.reference = reference;
        this.color = color;
        this.colorEdge = colorEdge;
        this.isClosed = isClosed;
        for (int i = 0; i < packedItems.length; i++) {
            this.currentVolume = this.currentVolume + packedItems[i].getItem().getVolume();
        }
    }

    

    public Container(String reference, int volume, int lenght, int height, int depth, Color color, Color colorEdge, boolean isClosed, int occupiedVolume) {
        super(depth,height,lenght,volume);
        packedItems = new IItemPacked[MAX_ITENS_P];
        this.reference = reference;
        this.color = color;
        this.colorEdge = colorEdge;
        this.isClosed = isClosed;
        this.currentVolume = occupiedVolume;
    }

    @Override
    public boolean addItem(IItem iitem, IPosition ip, Color color) throws ContainerException {

        for (int j = 0; j < currentNumberOfItems; j++) {//se o item ja existe
            if (packedItems[j].getItem().getReference().equals(iitem.getReference()) == true) {
                return false;
            }
        }

        if (currentNumberOfItems >= packedItems.length) {
            int size = packedItems.length;
            size = size * 2;
            IItemPacked[] temp = new IItemPacked[size];
            for (int k = 0; k < packedItems.length; k++) {
                temp[k] = packedItems[k];
            }
            packedItems = temp;
        }
        if (iitem == null || isClosed() == true) {
            throw new ContainerException() {
            };
        } else {
            IItemPacked NItem = new ItemPacked(iitem, ip, color);
            packedItems[currentNumberOfItems] = NItem;
            int plusVolume = iitem.getVolume();
            this.currentVolume = this.currentVolume + plusVolume;
            currentNumberOfItems++;
            return true;
        }
    }

    @Override
    public boolean removeItem(IItem iitem) throws ContainerException {
        if (iitem == null || isClosed() == true) {
            throw new ContainerException() {
            };
        }
        int k;
        for (int i = 0; i < currentNumberOfItems; i++) {
            if (packedItems[i].getItem().getReference().equals(iitem.getReference()) == true) {
                k = i;
                for (int j = k; j < currentNumberOfItems; j++) {
                    k++;
                    if (k >= currentNumberOfItems) {
                        int lessVolume = packedItems[i].getItem().getVolume();
                        packedItems[j] = null;
                        this.currentVolume = this.currentVolume - lessVolume;
                        currentNumberOfItems--;
                        return true;
                    }
                    packedItems[j] = packedItems[k];
                }
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
                throw new PositionException("Item out of container, overflowing") {
                };
            }
            for (int j = 0; j < packedItems.length; j++) { //varificar sobreposiçaõ de intems
                if (packedItems[i].getPosition().getX() == packedItems[j].getPosition().getX()
                        || packedItems[i].getPosition().getY() == packedItems[j].getPosition().getY()
                        || packedItems[i].getPosition().getZ() == packedItems[j].getPosition().getZ()) {
                    throw new PositionException("Items overlapping") {
                    };

                }
                if (j == i) {
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
