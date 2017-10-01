package com.javalab;

public class TaxCalculation
{
    public static void calc(Bill bill)
    {
        Double amount = 0d;
        Double basicAmount;

        for(Item item: bill.itemsBought)
        {
            basicAmount = item.costPerUnit*bill.numofItems.get(bill.itemsBought.indexOf(item));

            amount += basicAmount + basicAmount*(item.getGST())/100;
        }

        bill.amount = amount;

    }
}
