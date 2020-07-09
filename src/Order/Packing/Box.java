/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Packing;

import order.packing.IBox;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
*/

public class Box implements IBox{

    private int depth;
    private int height;
    private int lenght;
    private int volume;

    public Box(int depth, int height, int lenght, int volume) {
        this.depth = depth;
        this.height = height;
        this.lenght = lenght;
        this.volume = volume;
    }
    public Box(int depth, int height, int lenght) {
        this.depth = depth;
        this.height = height;
        this.lenght = lenght;
        this.volume = depth * height * lenght;
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
    
}
