/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.shortlyst.vendingmachine.model;

import ai.shortlyst.vendingmachine.enums.CoinsTypeEnum;
import ai.shortlyst.vendingmachine.enums.ItemsNameEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class VendingState {
    
    private Items purchasedItem;
    private CoinsTypeEnum inputAmount;
    private Coins changeCoins100;
    private Coins changeCoins10;
    private int change = 0;
    private int totalCoins100ForChange = 0;
    private int totalCoins10ForChange = 0;
    private List<Items> listItemForSale = new ArrayList<>();
   
    public Coins getChangeCoins100() {
        return changeCoins100;
    }

    public void setChangeCoins100(Coins changeCoins100) {
        this.changeCoins100 = changeCoins100;
    }

    public Coins getChangeCoins10() {
        return changeCoins10;
    }

    public void setChangeCoins10(Coins changeCoins10) {
        this.changeCoins10 = changeCoins10;
    }

    public int getTotalCoins100ForChange() {
        return totalCoins100ForChange;
    }

    public void setTotalCoins100ForChange(int totalCoins100ForChange) {
        this.totalCoins100ForChange = totalCoins100ForChange;
    }

    public int getTotalCoins10ForChange() {
        return totalCoins10ForChange;
    }

    public void setTotalCoins10ForChange(int totalCoins10ForChange) {
        this.totalCoins10ForChange = totalCoins10ForChange;
    }
    
    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }
    
    public Items getPurchasedItem() {
        return purchasedItem;
    }

    public void setPurchasedItem(Items purchasedItem) {
        this.purchasedItem = purchasedItem;
    }

    public CoinsTypeEnum getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(CoinsTypeEnum inputAmount) {
        this.inputAmount = inputAmount;
    }

    public List<Items> getListItemForSale() {
        return listItemForSale;
    }

    public void setListItemForSale(List<Items> listItemForSale) {
        this.listItemForSale = listItemForSale;
    }
    
    public void initialize(){
        //Initialize List Item For Sale, each item's quantity is set to 100 pieces
        List<Items> list = new ArrayList<>();
        list.add(new Items(1, ItemsNameEnum.CANNED_COFFEE.name(), 120, 100, ""));
        list.add(new Items(2, ItemsNameEnum.WATER_PET_BOTTLE.name(), 100, 100, ""));
        list.add(new Items(3, ItemsNameEnum.SPORT_DRINKS.name(), 150, 100, ""));
        setListItemForSale(list);

        //Initialize Coins (100 & 10) for change, each type's quantity set to 100 coins
        setChangeCoins100(new Coins(CoinsTypeEnum.COINS_100, 100));
        setChangeCoins10(new Coins(CoinsTypeEnum.COINS_10, 100));
    }
    
    public void initializeOutOfStock(){
        //Initialize List Item For Sale, each item's quantity is set to 100 pieces
        List<Items> list = new ArrayList<>();
        list.add(new Items(1, ItemsNameEnum.CANNED_COFFEE.name(), 120, 0, ""));
        list.add(new Items(2, ItemsNameEnum.WATER_PET_BOTTLE.name(), 100, 0, ""));
        list.add(new Items(3, ItemsNameEnum.SPORT_DRINKS.name(), 150, 0, ""));
        setListItemForSale(list);

        //Initialize Coins (100 & 10) for change, each type's quantity set to 100 coins
        setChangeCoins100(new Coins(CoinsTypeEnum.COINS_100, 100));
        setChangeCoins10(new Coins(CoinsTypeEnum.COINS_10, 100));
    }
    
    public void printCurrentState(){
        System.out.println("\n----------------------------------------------------");
        
        System.out.printf("%-25s %-5d JPY %n", "[Input amount]",
                (inputAmount == null ? 0 : inputAmount.getValue()));
        
        // Changes
        System.out.printf("%-25s %-5d JPY %-5s %n","[Change]", changeCoins100.getCoinsTypeEnum().getValue(),
                (changeCoins100.isAvailableForChange() ? "Change" : "No change"));
        System.out.printf(" %-25s %-5d JPY %-5s %n", "",changeCoins10.getCoinsTypeEnum().getValue(),
                (changeCoins10.isAvailableForChange() ? "Change" : "No change"));
        
        // Return Gate
        if(totalCoins100ForChange == 0 && totalCoins10ForChange == 0){
            System.out.printf("%-25s %s %n","[Return Gate]",
                "Empty");
        }else{
             
             
             if(totalCoins100ForChange > 1){
                 System.out.printf("%-25s %-5s %n","[Return Gate]",
                "100 JPY");
                 for(int i = 1; i <= totalCoins100ForChange - 1; i++){
                     System.out.printf("%-25s %-5s  %n","","100 JPY");
                 }
             }
             
             
             
             if(totalCoins10ForChange > 1){
                 
                 if(totalCoins100ForChange > 1){
                     System.out.printf("%-25s %-5s %n","",
                "10 JPY");
                 }else{
                     System.out.printf("%-25s %-5s %n","[Return Gate]",
                "10 JPY");
                 }
                
                 for(int i = 1; i <= totalCoins10ForChange - 1; i++){
                     System.out.printf("%-25s %-5s  %n", "","10 JPY");
                 }
             }
        }
        
        //Items for sale
        Items cannedCoffee = listItemForSale.stream().filter(data -> 
                ItemsNameEnum.CANNED_COFFEE.name().equals(data.getName()))
                .findFirst().get();
        
        Items waterPetBottle = listItemForSale.stream().filter(data -> 
                ItemsNameEnum.WATER_PET_BOTTLE.name().equals(data.getName())
        ).findFirst().get();
        
        Items sportDrinks = listItemForSale.stream().filter(data -> 
                ItemsNameEnum.SPORT_DRINKS.name().equals(data.getName())
        ).findFirst().get();
        
        System.out.printf("%-25s %-5s %s %-5d %-5s %-5s  %n", "[Items for sale]",
                cannedCoffee.getItemNo() + ".",
                cannedCoffee.getName(), cannedCoffee.getPrice(), "JPY", 
                cannedCoffee.getDesc());
        
        System.out.printf("%-25s %-5s %-5s %-5d %-5s %-5s  %n", "", 
                waterPetBottle.getItemNo() + ".",
                waterPetBottle.getName(), waterPetBottle.getPrice(), "JPY",
                waterPetBottle.getDesc());
        
        System.out.printf("%-25s %-5s %-5s %-5d %-5s %-5s %n","", 
                sportDrinks.getItemNo() + ".",
                sportDrinks.getName(), sportDrinks.getPrice(), "JPY",
                sportDrinks.getDesc());
        
        // Outlet
        System.out.printf("%-25s %s %n","[Outlet]",
                (purchasedItem == null ? "Empty" : purchasedItem.getName()));
        
        System.out.println("----------------------------------------------------\n");
        
    }
    
}
