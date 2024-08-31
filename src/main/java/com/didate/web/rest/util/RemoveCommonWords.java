package com.didate.web.rest.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveCommonWords {

    private RemoveCommonWords() {}

    public static final String SPLITER = " ";

    public static String[] remove(String str1, String str2) {
        String[] result = new String[2];

        System.out.println("--0------===============================");

        System.out.println(Arrays.asList(str1.split(SPLITER)));

        // Split strings into words and store them in ArrayLists
        List<String> list1 = new ArrayList<>(Arrays.asList(str1.split(SPLITER)));
        List<String> list2 = new ArrayList<>(Arrays.asList(str2.split(SPLITER)));

        System.out.println("--0------===============================");

        // Create a temporary copy of list1 to store common items
        List<String> commonItems = new ArrayList<>(list2);

        commonItems.retainAll(list1); // Retain only common items between list1 and list2
        System.out.println(commonItems.size());

        // Remove common items from both lists
        list1.removeAll(commonItems);
        list2.removeAll(commonItems);

        result[0] = String.join(SPLITER, list1);
        result[1] = String.join(SPLITER, list2);

        return result;
    }
}
