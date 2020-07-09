/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

import order.packing.Color;
import order.packing.IColored;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
*/

public class Colored implements IColored{

    private Color color;
    private Color colorEdge;

    public Colored(Color color, Color colorEdge) {
        this.color = color;
        this.colorEdge = colorEdge;
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
