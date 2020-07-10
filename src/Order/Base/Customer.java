/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Base;

import order.base.IAddress;
import order.base.ICustomer;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
*/


public class Customer extends Person implements ICustomer{
    
    private final int id;
    private String vat;
    private IAddress billingAddress;

    public Customer(int id, String vat, IAddress billingAddress, IAddress tempAddress, String tempName) {
        super(tempAddress, tempName);
        this.id = id;
        setVat(vat);
        setBillingAddress(billingAddress);
    }
 

    @Override
    public int getCustomerId() {
        return id;
    }

    @Override
    public String getVat() {
        return vat;
    }

    @Override
    public void setVat(String vat) {
        this.vat = vat;
    }

    @Override
    public IAddress getBillingAddress() {
        return billingAddress;
    }

    @Override
    public void setBillingAddress(IAddress address) {
        this.billingAddress = address;
    }
    
}
