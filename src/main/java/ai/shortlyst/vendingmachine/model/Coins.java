/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.shortlyst.vendingmachine.model;

import ai.shortlyst.vendingmachine.enums.CoinsTypeEnum;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class Coins {
    private CoinsTypeEnum coinsTypeEnum;
    private int quantity = 0;

    public Coins(CoinsTypeEnum coinsTypeEnum, int quantity) {
        this.coinsTypeEnum = coinsTypeEnum;
        this.quantity = quantity;
    }

    public CoinsTypeEnum getCoinsTypeEnum() {
        return coinsTypeEnum;
    }

    public void setCoinsTypeEnum(CoinsTypeEnum coinsTypeEnum) {
        this.coinsTypeEnum = coinsTypeEnum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public boolean isAvailableForChange(){
        if(!coinsTypeEnum.isIsUsedForChange()){
            return Boolean.FALSE;
        }
        
        if(CoinsTypeEnum.COINS_100.getValue() == coinsTypeEnum.getValue()){
           return quantity >= 4;
        }
        
        if(CoinsTypeEnum.COINS_10.getValue() == coinsTypeEnum.getValue()){
            return quantity >= 9;
        }
        
        return Boolean.FALSE;
    }
}
