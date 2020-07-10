/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

import java.util.logging.Level;
import java.util.logging.Logger;
import order.exceptions.PositionException;
import order.packing.IPosition;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
*/

public class Position implements IPosition{

    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        try {
            setX(x);
            setY(y);
            setZ(z);
        } catch (PositionException ex) {
            Logger.getLogger(Position.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public void setX(int x) throws PositionException {
        this.x = x;
    }

    @Override
    public void setY(int y) throws PositionException {
        this.y = y;
    }

    @Override
    public void setZ(int z) throws PositionException {
        this.z = z;
    }
    
}
