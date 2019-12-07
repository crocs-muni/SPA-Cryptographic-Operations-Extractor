/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.util;

/**
 *
 * @author Martin
 */
public class OverlappingPair<T, U> {
    private final T firstValue;
    private final U secondValue;
    
    public OverlappingPair(T firstValue, U secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }
    
    public T getFirstValue() {
        return firstValue;
    }
    
    public U getSecondValue() {
        return secondValue;
    }
}
