/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Base;

import order.base.IAddress;

/*
* Nome: Tomás Prior Pendão
* Número: 8170308
* Turma: LSIRC
*
* Nome: 
* Número: 
*/

public class Address implements IAddress{
    
    private String city;
    private String country;
    private int number;
    private String state;
    private String street;

    public Address(String city, String country, int number, String state, String street) {
        setCity(city);
        setCountry(country);
        setNumber(number);
        setState(state);
        setStreet(street);
    }
    
    
    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public void setNumber(int num) {
        this.number = num;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }
    
}
