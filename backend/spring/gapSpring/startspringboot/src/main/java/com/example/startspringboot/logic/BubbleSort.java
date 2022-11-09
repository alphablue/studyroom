package com.example.startspringboot.logic;

import java.util.ArrayList;
import java.util.List;

public class BubbleSort<T extends Comparable<T>> {
    public List<T> sort(List<T> list) {
        List<T> outPut = new ArrayList<>(list);

        for (int i = outPut.size() - 1; i>0; i--) {
            for (int j = 0; j < i; j++){
                if(outPut.get(j).compareTo(outPut.get(j + 1)) > 0){
                    T temp = outPut.get(j);
                    outPut.set(j, outPut.get(j + 1));
                    outPut.set(j + 1, temp);
                }
            }
        }

        return outPut;
    }
}
