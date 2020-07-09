/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

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
        this.x = x;
        this.y = y;
        this.z = z;
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
    public void setX(int i) throws PositionException {
        this.x = i;
    }

    @Override
    public void setY(int i) throws PositionException {
        this.y = i;
    }

    @Override
    public void setZ(int i) throws PositionException {
        this.z = i;
    }
    
}
