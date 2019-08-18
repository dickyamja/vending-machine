/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.shortlyst.vendingmachine.enums;

/**
 *
 * @author ahmadmudzakir
 */
public enum ItemsNameEnum {
    CANNED_COFFEE("Canned coffee"),
    WATER_PET_BOTTLE("Water PET bottle"),
    SPORT_DRINKS("Sport drinks");
    
    private String itemsName;

    private ItemsNameEnum(String itemsName) {
        this.itemsName = itemsName;
    }
    
    
}
