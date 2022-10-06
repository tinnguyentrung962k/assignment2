/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import java.util.List;
import tools.MyTool;
/**
 *
 * @author Administrator
 */
public class Config {
    private static final String CONFIG_FILE = "Product/config.txt";
    private String productFile;
    public Config() {
        readData();
    }
    private void readData(){
        List<String> lines = MyTool.readLinesFromFile(CONFIG_FILE);
        for(String line: lines){
            line = line.toUpperCase();
            String[] parts = line.split(":");
            if(line.indexOf("PRODUC")>=0){
                productFile = "Product/" + parts[1].trim();
            }
        }
    }
    public String getProductFile(){
        return productFile;
    }
}
