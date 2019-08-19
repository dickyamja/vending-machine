/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.shortlyst.vendingmachine.service;

import ai.shortlyst.vendingmachine.enums.CoinsTypeEnum;
import ai.shortlyst.vendingmachine.enums.ItemsNameEnum;
import ai.shortlyst.vendingmachine.exception.BusinessException;
import ai.shortlyst.vendingmachine.model.Items;
import ai.shortlyst.vendingmachine.model.VendingState;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ahmadmudzakir
 */
public class OperationServiceGetReturnedCoinsTest {

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    /*
    1. Test Get Returned Coins With Change Coins 10
    2. Test Get Returned Coins With Change Coins 100 And 10
    3. Test Get Returned Coins With Change is zero (amount equals price)
    4. Test Get Returned Coins Without selecting the items
     */
    @Test
    public void testGetReturnedCoinsWithChangeCoins100() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(CoinsTypeEnum.COINS_500, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
            if (data.getPrice() <= 500) {
                assertEquals("Available for purchase", data.getDesc());
            } else {
                assertEquals(StringUtils.EMPTY, data.getDesc());
            }
        });

        vendingState = operationService.chooseItemToPurchase(vendingState, 2);
        assertNotNull(vendingState.getPurchasedItem());
        assertEquals(ItemsNameEnum.WATER_PET_BOTTLE.name(), vendingState.getPurchasedItem().getName());

        vendingState = operationService.getItem(vendingState);
        assertEquals(400, vendingState.getChange());
        Items selectedItems = vendingState.getListItemForSale().stream()
                .filter(data -> StringUtils.equals(data.getName(), ItemsNameEnum.WATER_PET_BOTTLE.name()))
                .findFirst().get();

        assertEquals(99, selectedItems.getQuantity().longValue());

        vendingState = operationService.returnCoins(vendingState);
        assertEquals(4, vendingState.getTotalCoins100ForChange());
        assertEquals(0, vendingState.getTotalCoins10ForChange());

        vendingState = operationService.getReturnedCoins(vendingState);
        assertEquals(96, vendingState.getChangeCoins100().getQuantity());
        assertEquals(100, vendingState.getChangeCoins10().getQuantity());

    }
    
    @Test
    public void testGetReturnedCoinsWithChangeCoins100AndCoins10() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(CoinsTypeEnum.COINS_500, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
            if (data.getPrice() <= 500) {
                assertEquals("Available for purchase", data.getDesc());
            } else {
                assertEquals(StringUtils.EMPTY, data.getDesc());
            }
        });

        vendingState = operationService.chooseItemToPurchase(vendingState, 3);
        assertNotNull(vendingState.getPurchasedItem());
        assertEquals(ItemsNameEnum.SPORT_DRINKS.name(), vendingState.getPurchasedItem().getName());

        vendingState = operationService.getItem(vendingState);
        assertEquals(350, vendingState.getChange());
        Items selectedItems = vendingState.getListItemForSale().stream()
                .filter(data -> StringUtils.equals(data.getName(), ItemsNameEnum.SPORT_DRINKS.name()))
                .findFirst().get();

        assertEquals(99, selectedItems.getQuantity().longValue());

        vendingState = operationService.returnCoins(vendingState);
        assertEquals(3, vendingState.getTotalCoins100ForChange());
        assertEquals(5, vendingState.getTotalCoins10ForChange());

        vendingState = operationService.getReturnedCoins(vendingState);
        assertEquals(97, vendingState.getChangeCoins100().getQuantity());
        assertEquals(95, vendingState.getChangeCoins10().getQuantity());

    }
    
    @Test
    public void testGetReturnedCoinsWithChangeisZero() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 100);
        assertEquals(CoinsTypeEnum.COINS_100, vendingState.getInputAmount());
        vendingState.getListItemForSale().stream().forEach(data -> {
            if (data.getPrice() <= 100) {
                assertEquals("Available for purchase", data.getDesc());
            } else {
                assertEquals(StringUtils.EMPTY, data.getDesc());
            }
        });

        vendingState = operationService.chooseItemToPurchase(vendingState, 2);
        assertNotNull(vendingState.getPurchasedItem());
        assertEquals(ItemsNameEnum.WATER_PET_BOTTLE.name(), vendingState.getPurchasedItem().getName());

        vendingState = operationService.getItem(vendingState);
        assertEquals(0, vendingState.getChange());
        Items selectedItems = vendingState.getListItemForSale().stream()
                .filter(data -> StringUtils.equals(data.getName(), ItemsNameEnum.WATER_PET_BOTTLE.name()))
                .findFirst().get();

        assertEquals(99, selectedItems.getQuantity().longValue());

        vendingState = operationService.returnCoins(vendingState);
        assertEquals(0, vendingState.getTotalCoins100ForChange());
        assertEquals(0, vendingState.getTotalCoins10ForChange());

        vendingState = operationService.getReturnedCoins(vendingState);
        assertEquals(101, vendingState.getChangeCoins100().getQuantity());
        assertEquals(100, vendingState.getChangeCoins10().getQuantity());

    }
    
    @Test(expected = BusinessException.class)
    public void testGetReturnedCoinsWithoutChoosingItem() {
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        
        vendingState = operationService.getReturnedCoins(vendingState);
        assertEquals(100, vendingState.getChangeCoins100().getQuantity());
        assertEquals(100, vendingState.getChangeCoins10().getQuantity());

    }
}
