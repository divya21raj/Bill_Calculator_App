package com.javalab;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BillGeneration
{
    static ArrayList<Category> categories = new ArrayList<>();

    static ArrayList<Customer> customers = new ArrayList<>();

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {

        int cho = 1;

        setupShop(); //Sets up the database by reading from files...
        System.out.println("Shop is set!");
        bufferedReader.read();

        while(cho ==1)
        {
            LabourMethods.clrscr(); //clears console

            System.out.printf("1. Login as customer\n2. Close shop\n");
            cho = Integer.parseInt(bufferedReader.readLine());

            if(cho == 1)
                Menu();

        }

    }

    public static void setupShop() throws IOException
    {
        generateCategories();

        generateItems();

    }

    public static void Menu() throws IOException
    {
        int catCho, itemCho;

        Scanner scanner = new Scanner(System.in);

        String moreshopping;

        System.out.println("Welcome!");

        Customer tempCust = customerCreation();  //customer for this session
        Bill tempBill = new Bill();  //This session's bill for this session's customer

        do
        {
            int i =1;
            System.out.println("The categories are: ");

            for(Category ctemp: categories)
            {
                System.out.format("%d.%-20s%10s: %s\n", i++, ctemp.name, "GST", ctemp.gst);
            }

            System.out.printf("...or enter %d to exit and see your bills(if you have any)...\n", i);
            System.out.println("Enter category number: ");
            catCho = Integer.parseInt(bufferedReader.readLine());

            if(catCho == i)
                break;

            LabourMethods.clrscr();

            String moreitems = "y";

            while(moreitems.equals("Y")||moreitems.equals("y"))
            {
                int j=1;
                System.out.printf("The %s category has the following items:\n", categories.get(catCho-1).name);

                for(Item itemp: categories.get(catCho-1).items)
                {
                    System.out.printf("%d.%s\n", j++, itemp.name);
                }

                System.out.println("Enter the item that you want: ");
                itemCho = Integer.parseInt(bufferedReader.readLine());

                System.out.println("How many units?: ");
                Integer numofItems = Integer.parseInt(bufferedReader.readLine());

                int itemIndex = LabourMethods.checkIteminBill(tempBill, categories.get(catCho - 1).items.get(itemCho - 1));

                if(itemIndex == -1)
                {
                    tempBill.itemsBought.add(categories.get(catCho - 1).items.get(itemCho - 1));
                    tempBill.numofItems.add(numofItems);
                }

                else
                {
                    int prevnumofItems = tempBill.numofItems.get(itemIndex);

                    tempBill.numofItems.remove(itemIndex);
                    tempBill.numofItems.add(itemIndex, prevnumofItems+ numofItems);
                }

                System.out.println("More items from this category?(Y/N):");
                moreitems = bufferedReader.readLine();
            }

            System.out.println("More shopping?(Y/N): ");
            moreshopping = bufferedReader.readLine();

        }while(moreshopping.equals("Y")||moreshopping.equals("y"));

        TaxCalculation.calc(tempBill);

        tempBill.time = LabourMethods.getTime();

        int index = LabourMethods.checkCustomer(tempCust.name);

        if(index != -1)
        {
            customers.get(index).bills.add(tempBill);
            tempCust.bills = customers.get(index).bills;
            customers.remove(index);
            customers.add(index, tempCust);
        }

        else
        {
            tempCust.bills.add(tempBill);
            customers.add(tempCust);
        }

        System.out.printf("\nYour bill(s) are....\n");

        for(Bill bill: tempCust.bills)  //printing each bill of this customer
            bill.printBill(tempCust);

        scanner.nextLine();
    }

    public static Customer customerCreation() throws IOException
    {
        String name = new String();

        System.out.println("Please enter your name: ");
        name = bufferedReader.readLine();

        Customer tempCust = new Customer();

        tempCust.name = name;

        int index = LabourMethods.checkCustomer(name);

        if(index != -1)
            tempCust.bills = customers.get(index).bills;

        return tempCust;
    }

    public static void generateCategories() throws IOException
    {
        String csourceFile = "files/Categories.txt";
        String gsourceFile = "files/GST.txt";

        FileReader cfileReader = new FileReader(csourceFile);
        FileReader gfileReader = new FileReader(gsourceFile);

        BufferedReader cbufferedReader = new BufferedReader(cfileReader);
        BufferedReader gbufferedReader = new BufferedReader(gfileReader);

        while(true)
        {
            String cat = cbufferedReader.readLine();
            String gst = gbufferedReader.readLine();

            if(cat == null)
                break;

            else
            {
                Category temp = new Category();
                temp.name = cat;
                temp.gst = Double.parseDouble(gst.trim());

                categories.add(temp);
            }

        }

        cbufferedReader.close();
        gbufferedReader.close();
        cfileReader.close();
        gfileReader.close();
    }

    public static void generateItems() throws IOException
    {
        int i =0;
        String name = null, cost = null;

        String csourceFile = "files/Categories.txt";

        FileReader cfileReader = new FileReader(csourceFile);

        BufferedReader cbufferedReader = new BufferedReader(cfileReader);

        while(true)
        {
            String cat = cbufferedReader.readLine();

            if(cat == null)
                break;

            else
            {
                String isourceFile = "files/Categories/" + cat + ".txt";

                FileReader ifileReader = new FileReader(isourceFile);

                BufferedReader ibufferedReader = new BufferedReader(ifileReader);

                while(true)
                {
                    String item = ibufferedReader.readLine();

                    if(item == null)
                        break;

                    else
                    {
                        StringTokenizer tokenizer = new StringTokenizer(item, ":");

                        while(tokenizer.hasMoreTokens())
                        {
                            if(i%2 == 0)
                            {
                                name = tokenizer.nextToken().trim();
                                i++;
                            }

                            else
                            {
                                cost = tokenizer.nextToken().trim();
                                i++;
                            }

                        }

                        Item temp = new Item();

                        temp.name = name;
                        temp.costPerUnit = Double.parseDouble(cost);
                        temp.categoryName = cat;

                        categories.get(LabourMethods.indexofcategory(cat)).items.add(temp);

                    }
                }

                ibufferedReader.close();
                ifileReader.close();
            }
        }
    }
}


