/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Base;

import order.base.IAddress;
import order.base.IPerson;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
 */
public class Person implements IPerson {

    private IAddress address;
    private String name;

    public Person(IAddress tempAddress, String tempName) {
        this.address = tempAddress;
        this.name = tempName;
    }

    @Override
    public IAddress getAddress() {
        return address;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setAddress(IAddress ia) {
        this.address = ia;
    }

    @Override
    public void setName(String string) {
        this.name = string;
    }

}
