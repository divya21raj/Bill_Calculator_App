package com.javalab;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class LabourMethods
{
    static ArrayList<Integer> array = new ArrayList<>();
    static Integer key =0;

    static void clrscr()
    {
        for(int i = 0; i<70; i++)     //works as clrscr()
            System.out.println();
    }

    public static int checkCustomer(String name)
    {
        int index = -1;

        for(Customer temp : BillGeneration.customers)
        {
            if(temp.name.equals(name))
                index = BillGeneration.customers.indexOf(temp);
        }

        return index;
    }

    public static int checkIteminBill(Bill bills, Item item)
    {
        if(bills.itemsBought.contains(item))
            return bills.itemsBought.indexOf(item);

        else
            return -1;
    }

    public static int indexofcategory(String cat)
    {
        int index = 0;

        for(Category temp : BillGeneration.categories)
        {
            if(temp.name.equals(cat))
            {
                index = BillGeneration.categories.indexOf(temp);
                break;
            }
        }

        return index;
    }

    @NotNull
    public static String getTime()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(cal.getTime());
    }

    public static Integer uniqueRandom(Integer size)
    {
        array.clear();

        for(int i=1; i<=size; i++)
            array.add(i);

        Collections.shuffle(array);

        return array.get(key++);
    }
}
