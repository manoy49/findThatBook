package com.testvagrant.booknamechallenge.findthatbook.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class UnionAndIntersectionFinder {

    public static ArrayList findUnion(ArrayList firstList, ArrayList secondList) {
        Set set = new HashSet();

        set.addAll(firstList);
        set.addAll(secondList);

        return new ArrayList(set);
    }

    public static ArrayList findIntersection(ArrayList firstList, ArrayList secondList) {
        ArrayList result = new ArrayList();

        for(Object item : firstList) {
            if(secondList.contains(item))
                result.add(item);
        }

        return result;
    }
}
