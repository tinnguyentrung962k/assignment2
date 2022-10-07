/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import tools.MyTool;
/**
 *
 * @author Administrator
 */
public class ProductList extends ArrayList<Product> {
    Product pobj = null;
    private String datafile="";
    boolean changed = false;

    public ProductList(Product pobj) {
        this.pobj = pobj;
    }
    public ProductList() {
    }
    private void loadProductFromFile() throws ParseException{
        List<String> list = MyTool.readLinesFromFile(datafile);
        for(String s: list){
            Product p = new Product(s);
            this.add(p);
        }
    }
    public void initWithFile() throws ParseException{
        Config cR = new Config();
        datafile = cR.getProductFile();
        loadProductFromFile();   
    }
    public void checkExistProduct() throws ParseException {
        boolean check = false;
        List<String> lines = MyTool.readLinesFromFile(datafile);
        ArrayList<Product> list = new ArrayList<>();
        for (String s : lines) {
            Product p = new Product(s);
            list.add(p);
        }
        String find = MyTool.readPattern("Enter product name", Product.PRODUCT_FORMAT);
        for (Product p : list) {
            if (p.getName().toLowerCase().contains(find.toLowerCase())) {
                check = true;
            }
        }
        if (check == true) {
            System.out.println("Product found!");
        } else {
            System.out.println("Not found!");
        }
    }
    public void searchProduct() {
        String find = MyTool.readPattern("Enter product name", Product.PRODUCT_FORMAT);
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: this){
            list.add(product);
        }
        if(!searchProduct(find)){
            System.out.println("Not Found!");
        }
        else{
            Collections.sort(list,Product.compDefault);
            MyTool.createTable();
            for (Product p : list) {
                if (p.getName().toLowerCase().contains(find.toLowerCase())) {
                    System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",p.productID,p.name,p.price,p.quantity,p.status);
                    System.out.println("+-----+------------------------------+---------------+----------+--------------+");
                }
            }
            
        }
    }
    public void searchAvailable(){
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: this){
            list.add(product);
        }
        Collections.sort(list,Product.compDefault);
        MyTool.createTable();
        for(Product p: list){
            if(p.getStatus().matches("Available")){
                System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",p.productID,p.name,p.price,p.quantity,p.status);
                System.out.println("+-----+------------------------------+---------------+----------+--------------+");
            }
        }
    }
    public boolean searchProduct(String name) {
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: this){
            list.add(product);
        }
        for (Product p : list) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public boolean searchName(String name){
        for(Product p : this) {
            if (p.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public void searchID(){
        String find = MyTool.readPattern("Enter ID to search", Product.ID_FORMAT);
        int pos = checkID(find);
        if (pos < 0){
            System.out.println("Not Found !");
        }
        else{
            MyTool.createTable(); 
            System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",this.get(pos).productID,this.get(pos).name,this.get(pos).price,this.get(pos).quantity,this.get(pos).status);
            System.out.println("+-----+------------------------------+---------------+----------+--------------+");
        }
        
    }
    public int checkID(String productID){
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getProductID().equals(productID)) {
                return i;
            }
        }
        return -1;
    }
    public void addProduct(){
        String productID;
        String name;
        double price;
        int quantity;
        String status;
        int pos;
        do {
            productID = MyTool.readPattern("New product ID", Product.ID_FORMAT);
            productID = productID.toUpperCase();
            pos = checkID(productID);
            if (pos >= 0) {
                System.out.println("Product ID is duplicated!");
            }
        } while (pos >= 0);
        do {
            name = MyTool.readPattern("Name of new product", Product.PRODUCT_FORMAT);
            if (searchName(name)) {
                System.out.println("Product name is duplicated!");
            }
        } while (searchName(name));
        price = MyTool.readRangeDouble("Price of new product", 0, 10000);
        quantity = MyTool.readRangeInt("Quantity of new product", 0, 1000);
        if(quantity == 0){
            status = "Not Available";
        }
        else{
            status = MyTool.readStatus("Status of new product");    
        }
        Product p = new Product(productID, name, price, quantity, status);
        this.add(p);
        System.out.println("Product has been updated");
        MyTool.createTable();
        System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",productID,name,price,quantity,status);
        System.out.println("+-----+------------------------------+---------------+----------+--------------+");
        changed = true;
    }
    public void deleteProduct() {
        int pos;
        do{
            String productID = MyTool.readPattern("Enter ID of a product to delete",Product.ID_FORMAT).toUpperCase();
            pos = checkID(productID);
            if (pos < 0) {    
                System.out.println("This product is not found!");
            }
        }while(pos<0);
        this.remove(pos);
        System.out.println("A product has been deleted!");
        changed = true;
        
    }

    public void updateProduct() {
        String ID = MyTool.readPattern("Enter ID need to update",Product.ID_FORMAT);
        int pos = checkID(ID);
        String name;
        String price;
        String quantity;
        String status = null;
        double toDouble;
        int toInt;
        boolean valid;
        boolean updateName;
        boolean updatePrice;
        boolean updateQuantity;
        boolean updateStatus = false;
        if(pos<0){
            System.out.println("This product is not found !");
        }
        else{
            do{
                valid=false;
                updateName=false;
                System.out.print("Enter new name: ");
                Scanner sc = new Scanner(System.in);
                name = sc.nextLine();
                if(name.isEmpty()){
                    name = this.get(pos).getName();
                    valid = true;
                    updateName=false;
                }
                else{
                    if(!name.matches(Product.PRODUCT_FORMAT)){
                        System.out.println("Invalid input ! Try again.");
                        valid = false;
                    }
                    else{
                        if(searchName(name)){
                            System.out.println("This name is duplicated ! Try again.");
                            valid = false;
                        }
                        else{    
                            this.get(pos).setName(name);
                            valid = true;
                            updateName=true;
                        }
                    }
                }   
            }while(!valid);
            do{
                valid=false;
                updatePrice=false;
                System.out.print("Enter new price: ");
                Scanner sc = new Scanner(System.in);
                price = sc.nextLine();
                if(price.isEmpty()){
                    toDouble = this.get(pos).getPrice();
                    valid = true;
                    updatePrice=false;
                }
                else{
                    toDouble=Double.valueOf(price);
                    if(toDouble < 0 || toDouble > 10000){
                        System.out.println("Invalid input ! Try again.");
                        valid = false;
                    }
                    else{
                        this.get(pos).setPrice(toDouble);
                        valid = true;
                        updatePrice=true;
                    }
                }
            }while(!valid);
            do{
                valid = false;
                updateQuantity=false;
                System.out.print("Enter new quantity: ");
                Scanner sc = new Scanner(System.in);
                quantity = sc.nextLine();
                if(quantity.isEmpty()){
                    toInt = this.get(pos).getQuantity();
                    valid = true;
                    updateQuantity=false;
                    do{
                        valid = false;
                        updateStatus=false;
                        System.out.print("Enter new status: ");
                        status = sc.nextLine();
                        if(status.isEmpty()){
                            status = this.get(pos).getStatus();
                            valid = true;
                            updateStatus=false;
                        }
                        else{
                            if(!(status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Not Available"))){
                                System.out.println("Invalid input ! Try again.");
                            }
                            else{
                                if(status.equalsIgnoreCase("Available")){
                                    status="Available";
                                }
                                if(status.equalsIgnoreCase("Not Available")){
                                    status="Not Available";
                                }
                                this.get(pos).setStatus(status);
                                valid = true;
                                updateStatus=true;
                            }
                        }
                    }while(!valid);    
                }
                else{
                    toInt=Integer.valueOf(quantity);
                    if(toInt < 0 || toInt > 1000){
                        System.out.println("Invalid input ! Try again.");
                        valid = false;
                    }
                    else{
                        if(toInt == 0){
                            this.get(pos).setQuantity(toInt);
                            valid = true;
                            updateQuantity=true;
                            status = "Not Available";
                        }
                        else{
                            this.get(pos).setQuantity(toInt);
                            valid = true;
                            updateQuantity=true;
                            do{
                                valid = false;
                                updateStatus=false;
                                System.out.print("Enter new status: ");
                                status = sc.nextLine();
                                if(status.isEmpty()){
                                    status = this.get(pos).getStatus();
                                    valid = true;
                                    updateStatus=false;
                                }
                                else{
                                    if(!(status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Not Available"))){
                                        System.out.println("Invalid input ! Try again.");
                                    }
                                    else{
                                        if(status.equalsIgnoreCase("Available")){
                                            status="Available";
                                        }
                                        if(status.equalsIgnoreCase("Not Available")){
                                            status="Not Available";
                                        }
                                        this.get(pos).setStatus(status);
                                        valid = true;
                                        updateStatus=true;
                                    }
                                }
                            }while(!valid);
                        }
                    }
                }
            }while(!valid);
            
            if(!updateName && !updatePrice && !updateQuantity && !updateStatus){
                System.out.println("Updated failed !");
            }
            else{
                System.out.println("Update Successfully !");
                MyTool.createTable();
                System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",ID,name,toDouble,toInt,status);
                System.out.println("+-----+------------------------------+---------------+----------+--------------+");
            }
        }
       
        changed = true;
    }
    public void writeProductToFile() {
        if (changed) {
            MyTool.writeFile(datafile, this);
            changed = false;
            System.out.println("Successfully Written!");
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    public void printAllProductsDefault() {
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: this){
            list.add(product);
        }
        if (list.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            Collections.sort(list,Product.compDefault);
            MyTool.createTable();
            for (Product product : list) {
                System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",product.productID,product.name,product.price,product.quantity,product.status);
                System.out.println("+-----+------------------------------+---------------+----------+--------------+");
            }
            
        }
    }
    public void printAllProductsSortingName() {
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: this){
            list.add(product);
        }
        if (list.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            Collections.sort(list);
            MyTool.createTable();
            for (Product product : list) {
                System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",product.productID,product.name,product.price,product.quantity,product.status);
                System.out.println("+-----+------------------------------+---------------+----------+--------------+");
            }
            
        }
    }
    public void printAllProductsSortingID() {
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: this){
            list.add(product);
        }
        if (list.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            Collections.sort(list,Product.compID);
            MyTool.createTable();
            for (Product product : list) {
                System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",product.productID,product.name,product.price,product.quantity,product.status);
                System.out.println("+-----+------------------------------+---------------+----------+--------------+");
            }
            
        }
    }
    public void printAllProductsSortingStatus() {
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: this){
            list.add(product);
        }
        if (list.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            Collections.sort(list,Product.compStatus);
            MyTool.createTable();
            for (Product product : list) {
                System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",product.productID,product.name,product.price,product.quantity,product.status);
                System.out.println("+-----+------------------------------+---------------+----------+--------------+");
            }
            
        }
    }
    public void printAllProductInFile() throws ParseException {
        List<String> lines = MyTool.readLinesFromFile(datafile);
        ArrayList<Product> list = new ArrayList<>();
        for (String s : lines) {
            Product p = new Product(s);
            list.add(p);
        }
        if(list.isEmpty()){
            System.out.println("Empty list!");
        }
        else{
            Collections.sort(list,Product.compDefault);
            MyTool.createTable();
            for (Product product : list) {
                System.out.format("|%-5s|%-30s|%-15.2f|%-10d|%-14s|\n",product.productID,product.name,product.price,product.quantity,product.status);
                System.out.println("+-----+------------------------------+---------------+----------+--------------+");
            } 
        }
    }
}
