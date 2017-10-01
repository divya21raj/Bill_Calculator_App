package com.javalab;

import org.jetbrains.annotations.Contract;

public class Item
{
    String name;

    String categoryName;

    Double costPerUnit;

    @Contract(pure = true)
    public Double getGST()
    {
        Double gst = 0d;

        for(Category category: BillGeneration.categories)
        {
            if(category.name.equals(this.categoryName))
            {
                gst = category.gst;
                break;
            }
        }

        return gst;
    }
}
