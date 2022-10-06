/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mng;
import data.ProductList;
import java.text.ParseException;
import tools.MyTool;
/**
 *
 * @author Administrator
 */
public class ProductMng {
    public static void main(String[] args) throws ParseException {
        String[] options = {"Print all product", "Create new product", "Check exist product", 
                            "Search product","Update Product","Save product to file", "Print list of product from file"};
        Menu mnu = new Menu(options);
        ProductList pList = new ProductList();
        pList.initWithFile();
        int choice = 0;
        int subchoice = 0;
        do {
            choice = mnu.getChoice("--------Product Manager--------");
            switch (choice) {
                case 1:
                    String [] subop1 = {"Printing Default","Printing Ordered by Name","Printing Ordered by ID","Printing Ordered by Status","Quit print"};
                    Menu sm1 = new Menu(subop1);
                    do{
                        subchoice = sm1.getChoice("--------Search Product--------");
                        switch (subchoice){
                        case 1:
                            pList.printAllProductsDefault();
                            break;
                        case 2:
                            pList.printAllProductsSortingName();
                            break;
                        case 3:
                            pList.printAllProductsSortingID();
                            break;
                        case 4:
                            pList.printAllProductsSortingStatus();
                            break;
                        case 5:
                            System.out.println("Quit Successfully !");
                            break;
                        default:
                            System.out.println("Invalid input! Try again.");
                        }
                    }while(subchoice<0 || subchoice>sm1.size());
                    break;
                case 2:
                    pList.addProduct();
                    break;
                case 3:
                    pList.checkExistProduct();
                    break;
                case 4:
                    String [] subop2 = {"Search Name","Search ID","Search Available products","Quit search"};
                    Menu sm2 = new Menu(subop2);
                    do{
                        subchoice = sm2.getChoice("--------Search Product--------");
                        switch (subchoice){
                        case 1:
                            pList.searchProduct();
                            break;
                        case 2:
                            pList.searchID();
                            break;
                        case 3:
                            pList.searchAvailable();
                            break;
                        case 4:
                            System.out.println("Quit Successfully !");
                            break;
                        default:
                            System.out.println("Invalid input! Try again.");
                        }
                    }while(subchoice<0 || subchoice>sm2.size());
                    break;
                case 5:
                    String [] subop3 = {"Update product","Delete product","Quit update"};
                    Menu sm3 = new Menu(subop3);
                    do{
                        subchoice = sm3.getChoice("--------Update Product--------");
                        switch (subchoice){
                        case 1:
                            pList.updateProduct();
                            break;
                        case 2:
                            pList.deleteProduct();
                            break;
                        case 3:
                            System.out.println("Updated failed");
                            System.out.println("Quit Successfully !");
                            break;
                        default:
                            System.out.println("Invalid input! Try again.");
                        }
                    }while(subchoice<0 || subchoice>sm3.size());
                    break;
                case 6:
                    pList.writeProductToFile();
                    break;
                case 7:
                    pList.printAllProductInFile();
                    break;
                default:
                    if (pList.isChanged()) {
                        boolean res = MyTool.readBool("Data changed. Write to file? ");
                        if (res == true) {
                            pList.writeProductToFile();
                        }
                    }
            }
        } while (choice > 0 && choice <= mnu.size());
        System.out.println("Good Bye!");
    }
}
