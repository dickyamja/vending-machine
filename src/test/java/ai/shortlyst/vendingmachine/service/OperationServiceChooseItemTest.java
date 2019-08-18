/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.shortlyst.vendingmachine.service;

import ai.shortlyst.vendingmachine.enums.CoinsTypeEnum;
import ai.shortlyst.vendingmachine.enums.ItemsNameEnum;
import ai.shortlyst.vendingmachine.exception.BusinessException;
import ai.shortlyst.vendingmachine.model.VendingState;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class OperationServiceChooseItemTest {

    
    @Test
    public void testChooseItemCannedCoffee() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(CoinsTypeEnum.COINS_500, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
            if(data.getPrice() <= 500){
                 assertEquals("Available for purchase", data.getDesc()); 
             }else{
                 assertEquals(StringUtils.EMPTY, data.getDesc()); 
             }  
        });

        vendingState = operationService.chooseItemToPurchase(vendingState, 1);
        assertNotNull(vendingState.getPurchasedItem());
        assertEquals(ItemsNameEnum.CANNED_COFFEE.name(), vendingState.getPurchasedItem().getName());
    }

    @Test
    public void testChooseItemWaterPetBottle() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 100);
        assertEquals(CoinsTypeEnum.COINS_100, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
            if(data.getPrice() <= 100){
                 assertEquals("Available for purchase", data.getDesc()); 
             }else{
                 assertEquals(StringUtils.EMPTY, data.getDesc()); 
             }  
        });

        vendingState = operationService.chooseItemToPurchase(vendingState, 2);
        assertNotNull(vendingState.getPurchasedItem());
        assertEquals(ItemsNameEnum.WATER_PET_BOTTLE.name(), vendingState.getPurchasedItem().getName());
    }

    @Test
    public void testChooseItemSportDrinks() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(CoinsTypeEnum.COINS_500, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
            if(data.getPrice() <= 500){
                 assertEquals("Available for purchase", data.getDesc()); 
             }else{
                 assertEquals(StringUtils.EMPTY, data.getDesc()); 
             }  
        });

        vendingState = operationService.chooseItemToPurchase(vendingState, 3);
        assertNotNull(vendingState.getPurchasedItem());
        assertEquals(ItemsNameEnum.SPORT_DRINKS.name(), vendingState.getPurchasedItem().getName());
    }
    
    @Test(expected = BusinessException.class)
    public void testChooseItemOutOfStock() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initializeOutOfStock();
        
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(CoinsTypeEnum.COINS_500, vendingState.getInputAmount());
        vendingState = operationService.chooseItemToPurchase(vendingState, 1);
        assertNull(vendingState.getPurchasedItem());
        
    }
    
    @Test(expected = BusinessException.class)
    public void testChooseUnknownItem() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(CoinsTypeEnum.COINS_500, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
            if(data.getPrice() <= 500){
                 assertEquals("Available for purchase", data.getDesc()); 
             }else{
                 assertEquals(StringUtils.EMPTY, data.getDesc()); 
             }  
        });

        vendingState = operationService.chooseItemToPurchase(vendingState, 6);
        assertNull(vendingState.getPurchasedItem());        
    }
    
    @Test(expected = BusinessException.class)
    public void testChooseItemWithItemPriceGreaterThanInputAmount() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 50);
        assertEquals(CoinsTypeEnum.COINS_50, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
           assertEquals(StringUtils.EMPTY, data.getDesc()); 
        });
        
        vendingState = operationService.chooseItemToPurchase(vendingState, 1);
        assertNull(vendingState.getPurchasedItem());
    }
    
    @Test(expected = BusinessException.class)
    public void testChooseItemWithoutInsertingTheCoins() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.chooseItemToPurchase(vendingState, 1);
        assertNull(vendingState.getPurchasedItem());
    }
}
