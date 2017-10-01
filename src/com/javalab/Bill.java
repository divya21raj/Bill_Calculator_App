package com.javalab;

import java.util.ArrayList;

public class Bill
{
    Integer billNum;
    ArrayList<Item> itemsBought;
    ArrayList<Integer> numofItems;

    String time;

    Double amount;

    public void printBill(Customer customer)
    {
        System.out.printf("\n");

        Integer j=1;

        if(itemsBought.size() != 0)
        {
            for (int i = 0; i < 100; i++)
                System.out.printf("/");

            System.out.printf("\n%-15s: %15s", "Time", time);
            System.out.printf("\n%-15s: %15s\n", "Bill no.:", billNum);
            System.out.printf("%-15s: %15s\n", "Customer name", customer.name);

            System.out.printf("\nPurchases:\n");
            System.out.printf("%-4s|%25s|%15s|%12s|%12s|%14s\n", "S.no", "Item", "Num. of units", "Cost", "GST", "Total cost");
            for (Item item : itemsBought)
            {
                Double basicAmount = item.costPerUnit * numofItems.get(itemsBought.indexOf(item));
                Double amountPerItem = basicAmount + basicAmount * (item.getGST()) / 100;

                System.out.printf("%-4d|%25s|%15d|%12.4f|%12.4f|%14.4f\n", j++, item.name, numofItems.get(itemsBought.indexOf(item)), basicAmount, basicAmount * (item.getGST()) / 100, amountPerItem);
            }

            for (int i = 0; i < 100; i++)
                System.out.printf("-");

            System.out.printf("\nGRAND TOTAL %61s%14f\n", "|", amount);

            for (int i = 0; i < 100; i++)
                System.out.printf("-");

            System.out.printf("\n\t\t\t\t\t\t\t\tTHANKS FOR SHOPPING!\n");

            for (int i = 0; i < 100; i++)
                System.out.printf("/");

            System.out.printf("\n");
        }

    }

    Bill()
    {
        itemsBought = new ArrayList<>();
        numofItems = new ArrayList<>();
        billNum = LabourMethods.uniqueRandom(5000);
    }
}
