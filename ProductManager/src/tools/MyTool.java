/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.io.IOException;
/**
 *
 * @author Administrator
 */

public class MyTool {
    public static boolean validStr(String str, String regEx) {
        return str.matches(regEx);
    }

    public static boolean parseBool(String boolStr) {
        char c = boolStr.trim().toUpperCase().charAt(0);
        return (c == '1' || c == 'Y' || c == 'T');
    }
    public static double parseDouble(String doubleStr) throws ParseException {
        String s = doubleStr.trim();
        return Double.parseDouble(s);
    }
    public static int parseInt(String intStr) throws ParseException {
        String s = intStr.trim();
        return Integer.parseInt(s);
    }
    public static int readRangeInt(String message, int min, int max) {
        int input = 0;
        do {
            System.out.print(message + ": ");
            Scanner SC = new Scanner(System.in);
            input = SC.nextInt();
            if(input < min || input > max){
                System.out.println("Invalid input! Please, try again.");
            }
        } while (input < min || input > max);
        return input;
    }
    public static double readRangeDouble(String message, double min, double max) {
        double input = 0;
        do {
            System.out.print(message + ": ");
            Scanner SC = new Scanner(System.in);
            input = SC.nextDouble();
            if(input < min || input > max){
                System.out.println("Invalid input! Please, try again.");
            }
        } while (input < min || input > max);
        return input;
    }
    public static String readNonBlank(String message) {
        String input = "";
        do {
            System.out.print(message + ": ");
            Scanner SC = new Scanner(System.in);
            input = SC.nextLine().trim();
            if(input.isEmpty()){
                System.out.println("Invalid input! Please, try again.");
            }
        } while (input.isEmpty());
        return input;
    }
    public static String readPattern(String message, String pattern) {
        String input = "";
        boolean valid;
        do {
            System.out.print(message + ": ");
            Scanner SC = new Scanner(System.in);
            input = SC.nextLine().trim();
            valid = validStr(input, pattern);
            if(!valid){
                System.out.println("Invalid input! Please, try again.");
            }
        } while (!valid);
        return input;
    } 
    public static boolean readBool(String message) {
        String input;
        System.out.print(message + "[1/0-Y/N-T/F]: ");
        Scanner SC = new Scanner(System.in);
        input = SC.nextLine().trim();
        if (input.isEmpty()) {
            return false;
        }
        char c = Character.toUpperCase(input.charAt(0));
        return (c == '1' || c == 'Y' || c == 'T');
    }
    public static List<String> readLinesFromFile(String filename) {
        ArrayList<String> list = new ArrayList();
        File f = new File(filename);
        if (f.exists()) {
            String line;
            try {
                FileReader fr = new FileReader(f);
                BufferedReader bf = new BufferedReader(fr);
                while ((line = bf.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        list.add(line);
                    }
                }
                bf.close();
                fr.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return list;
    }
    public static void writeFile(String filename, List list) {
        if (list != null && !list.isEmpty()) {
            try {
                FileWriter fw = new FileWriter(filename);
                PrintWriter pw = new PrintWriter(fw);
                for (Object item : list) {
                    pw.println(item);
                }
                fw.close();
                pw.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    public static String readStatus(String message) {
        String input="";
        boolean valid;
        String status="";
        do {
            valid = false;
            System.out.print(message + ": ");
            Scanner SC = new Scanner(System.in);
            input = SC.nextLine().trim();
            if(input.equalsIgnoreCase("available")){
                status = "Available";
                valid = true;
            }
            else{
                if(input.equalsIgnoreCase("not available")){
                    status = "Not Available";
                    valid = true;
                }
                else{
                    System.out.println("Invalid status! Try again!");
                }
            }
    
        }while(!valid);
        return status;
    }
    public static void createTable(){
        System.out.println("+-----+------------------------------+---------------+----------+--------------+");
        System.out.format("|%-5s|%-30s|%-15s|%-10s|%-14s|\n","ID","Name","Price","Quantity","Status");
        System.out.println("+-----+------------------------------+---------------+----------+--------------+");
    }
}
