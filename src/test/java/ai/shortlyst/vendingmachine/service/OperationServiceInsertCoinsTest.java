package ai.shortlyst.vendingmachine.service;

import ai.shortlyst.vendingmachine.enums.CoinsTypeEnum;
import ai.shortlyst.vendingmachine.exception.BusinessException;
import ai.shortlyst.vendingmachine.model.Coins;
import ai.shortlyst.vendingmachine.model.VendingState;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class OperationServiceInsertCoinsTest {
    
    public OperationServiceInsertCoinsTest() {
    }
    
    @Test
    public void testInsertCoins10(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 10);
        assertEquals(CoinsTypeEnum.COINS_10, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertNotEquals("Available for purchase", data.getDesc());            
        });
       
    }
    
    @Test
    public void testInsertCoins50(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 50);
        assertEquals(CoinsTypeEnum.COINS_50, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertNotEquals("Available for purchase", data.getDesc());            
        });
       
    }
    
    @Test
    public void testInsertCoins100(){
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
    }
    
    @Test
    public void testInsertCoins500(){
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
    }
    
    @Test(expected = BusinessException.class)
    public void testInsertUnknownCoins(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState = operationService.insertCoins(vendingState, 200);
        assertEquals(null, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertEquals(StringUtils.EMPTY, data.getDesc());                      
        });
    }
    
    @Test
    public void testInsertCoins10WhenCoins10LessThan9(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.setChangeCoins10(new Coins(CoinsTypeEnum.COINS_10, 8));
        vendingState = operationService.insertCoins(vendingState, 10);
        assertEquals(CoinsTypeEnum.COINS_10, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertEquals(StringUtils.EMPTY, data.getDesc());                      
        });
    }
    
    @Test(expected = BusinessException.class)
    public void testInsertCoins50WhenCoins10LessThan9(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.setChangeCoins10(new Coins(CoinsTypeEnum.COINS_10, 8));
        vendingState = operationService.insertCoins(vendingState, 50);
        assertEquals(null, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertEquals(StringUtils.EMPTY, data.getDesc());                      
        });
    }
    
    @Test(expected = BusinessException.class)
    public void testInsertCoins100WhenCoins10LessThan9(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.setChangeCoins10(new Coins(CoinsTypeEnum.COINS_10, 8));
        vendingState = operationService.insertCoins(vendingState, 100);
        assertEquals(null, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertEquals(StringUtils.EMPTY, data.getDesc());                      
        });
    }
    
    @Test(expected = BusinessException.class)
    public void testInsertCoins500WhenCoins10LessThan9(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.setChangeCoins10(new Coins(CoinsTypeEnum.COINS_10, 8));
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(null, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertEquals(StringUtils.EMPTY, data.getDesc());                      
        });
    }
    
    @Test
    public void testInsertCoinsBelow100WhenCoins100LessThan4(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.setChangeCoins100(new Coins(CoinsTypeEnum.COINS_100, 3));
        vendingState = operationService.insertCoins(vendingState, 10);
        assertEquals(CoinsTypeEnum.COINS_10, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertEquals(StringUtils.EMPTY, data.getDesc());                              
        });
    }
    
    @Test
    public void testInsertCoins100WhenCoins100LessThan4(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.setChangeCoins100(new Coins(CoinsTypeEnum.COINS_100, 3));
        vendingState = operationService.insertCoins(vendingState, 100);
        assertEquals(CoinsTypeEnum.COINS_100, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             if(data.getPrice() <= 100){
                 assertEquals("Available for purchase", data.getDesc()); 
             }else{
                 assertEquals(StringUtils.EMPTY, data.getDesc()); 
             }                              
        });
    }
    
    @Test(expected = BusinessException.class)
    public void testInsertCoins500WhenCoins100LessThan4(){
        OperationService operationService = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.setChangeCoins100(new Coins(CoinsTypeEnum.COINS_100, 3));
        vendingState = operationService.insertCoins(vendingState, 500);
        assertEquals(null, vendingState.getInputAmount());
         vendingState.getListItemForSale().stream().forEach(data -> {
             assertEquals(StringUtils.EMPTY, data.getDesc());                              
        });
    }
}
