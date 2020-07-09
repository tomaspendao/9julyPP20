/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

import order.packing.Color;
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

public class ItemPacked extends Colored implements IItemPacked{

    private final IItem item; //final??
    private IPosition position;

    public ItemPacked(IItem item, IPosition position, Color color, Color colorEdge) {
        super(color, colorEdge);
        this.item = item;
        this.position = position;
    }
    
    public ItemPacked(IItem item, IPosition position, Color color) {
        super(color, color);
        this.item = item;
        this.position = position;
    }

    @Override
    public IItem getItem() {
        return item;
    }

    @Override
    public IPosition getPosition() {
        return position;
    }

    @Override
    public void setPosition(IPosition ip) {
        this.position = ip;
    }

    
}
