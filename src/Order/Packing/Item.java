/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

import order.management.ShipmentStatus;
import order.packing.IItem;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
 */
public class Item extends Box implements IItem {

    private String reference;
    private String description;


    public Item(String reference, String description, int depth, int height, int lenght, int volume) {
        super(depth, height, lenght, volume);
        this.reference = reference;
        this.description = description;
    }

    public Item(String reference, String description, int depth, int height, int lenght) {
        super(depth, height, lenght);
        this.reference = reference;
        this.description = description;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String string) {
        this.description = string;
    }


}
