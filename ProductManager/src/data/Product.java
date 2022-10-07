/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import tools.MyTool;
import java.text.ParseException;
import java.util.Comparator;
/**
 *
 * @author Administrator
 */
public class Product implements Comparable<Product> {
    public static final String PRODUCT_FORMAT = "[a-zA-Z0-9\" \"]{5,100}";
    public static final String ID_FORMAT = "P\\d{3}";
    String productID;
    String name;
    double price;
    int quantity;
    String status;
    public static final char SEPARATOR = ',';

    public Product(String productID, String name, double price, int quantity, String status) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }
    public Product(String line) throws ParseException{
        String[] parts = line.split(""+this.SEPARATOR);
        productID = parts[0].trim();
        name = parts[1].trim();
        price = MyTool.parseDouble(parts[2]);
        quantity = MyTool.parseInt(parts[3]);
        status = parts[4].trim();     
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString(){
        return productID + SEPARATOR + name + SEPARATOR + price + SEPARATOR + quantity + SEPARATOR + status;
    }
    @Override
    public int compareTo(Product p){
        return this.name.compareTo(((Product)p).name);
    }
    public static Comparator compID = new Comparator(){
        @Override
        public int compare(Object o1, Object o2){
            Product p1 = (Product)o1;
            Product p2 = (Product)o2;
            return p1.productID.compareTo(((Product)p2).productID);
        }    
    };
    public static Comparator compStatus = new Comparator(){
        @Override
        public int compare(Object o1, Object o2){
            Product p1 = (Product)o1;
            Product p2 = (Product)o2;
            return p1.getStatus().compareTo(((Product)p2).getStatus());
        }    
    };
    public static Comparator compDefault = new Comparator(){
        @Override
        public int compare(Object o1, Object o2){
            Product p1 = (Product)o1;
            Product p2 = (Product)o2;
            if(p1.getQuantity()<p2.getQuantity()){
                return 1;
            }
            else if(p1.getQuantity() == p2.getQuantity()){
                if (p1.getPrice() < p2.getPrice())
                    return -1;
                else if (p1.getPrice() == p2.getPrice()){
                    return 0;
                }
                else{
                    return 1;
                }    
            }
            else{
                return -1;
            }
        }
    };
}
