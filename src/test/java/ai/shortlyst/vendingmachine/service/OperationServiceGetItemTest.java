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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class OperationServiceGetItemTest {
    
    
    @Test
    public void testGetItemWithAmountEqualsItemPrice(){
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
        
        vendingState = operationService.getItem(vendingState);
        assertEquals(0, vendingState.getChange());
        Items selectedItems = vendingState.getListItemForSale().stream()
                .filter(data -> StringUtils.equals(data.getName(), ItemsNameEnum.WATER_PET_BOTTLE.name()))
                .findFirst().get();
       
        assertEquals(99 , selectedItems.getQuantity().longValue());
    }
    
    @Test
    public void testGetItemWithAmountGreaterThanItemPrice(){
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

        vendingState = operationService.chooseItemToPurchase(vendingState, 2);
        assertNotNull(vendingState.getPurchasedItem());
        assertEquals(ItemsNameEnum.WATER_PET_BOTTLE.name(), vendingState.getPurchasedItem().getName());
        
        vendingState = operationService.getItem(vendingState);
        assertEquals(400, vendingState.getChange());
        Items selectedItems = vendingState.getListItemForSale().stream()
                .filter(data -> StringUtils.equals(data.getName(), ItemsNameEnum.WATER_PET_BOTTLE.name()))
                .findFirst().get();
       
        assertEquals(99 , selectedItems.getQuantity().longValue());
    }
    
    @Test(expected = BusinessException.class)
    public void testGetItemWithoutChoosingTheItem(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        
        vendingState = operationService.getItem(vendingState);
        assertEquals(0, vendingState.getChange());
        Items selectedItems = vendingState.getListItemForSale().stream()
                .filter(data -> StringUtils.equals(data.getName(), ItemsNameEnum.WATER_PET_BOTTLE.name()))
                .findFirst().get();
       
        assertEquals(100 , selectedItems.getQuantity().longValue());
    }
}
