/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.shortlyst.vendingmachine.service;

import ai.shortlyst.vendingmachine.model.Coins;
import ai.shortlyst.vendingmachine.enums.CoinsTypeEnum;
import ai.shortlyst.vendingmachine.exception.BusinessException;
import ai.shortlyst.vendingmachine.model.VendingState;
import ai.shortlyst.vendingmachine.model.Items;
import java.util.Optional;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class OperationService {

    public VendingState insertCoins(VendingState vendingState, int totalAmount)
            throws BusinessException {
        if (totalAmount > 10 && vendingState.getChangeCoins10().getQuantity() < 9) {
            throw new BusinessException(" Coins 10 is running low, "
                    + "please use only Coins 10 to insert ");
        }

        if (totalAmount > 100 && vendingState.getChangeCoins100().getQuantity() < 4) {
            throw new BusinessException(" Coins 100 is running low, "
                    + "please use only Coins 100/50/10 to insert ");
        }
        vendingState.setInputAmount(getEnumCoinsByInputAmount(totalAmount));
        vendingState.getListItemForSale().stream().forEach(data -> {
            if (data.getQuantity() <= 0) {
                data.setDesc("Sold out");
            } else if (data.getPrice() <= vendingState.getInputAmount().getValue()) {
                data.setDesc("Available for purchase");
            }

        });
        return vendingState;
    }

    public VendingState chooseItemToPurchase(VendingState vendingState,
            int itemType) {
        if(vendingState.getInputAmount() == null){
            throw new BusinessException(" Please insert the coins first ");
        }
        Items selectedItems = convertItemTypeToItems(vendingState, itemType);
        if (selectedItems.getQuantity() <= 0) {
            throw new BusinessException(" Selected Item is Sold out ");
        }

        if (vendingState.getInputAmount().getValue() < selectedItems.getPrice()) {
            throw new BusinessException(" Unsufficient amount ");
        }
        vendingState.setPurchasedItem(selectedItems);
        return vendingState;
    }

    public VendingState getItem(VendingState vendingState) {
        //update quantity of purchased item at vending machine

        if (vendingState.getPurchasedItem() == null) {
            throw new BusinessException("Please first select item to purchase ");
        }
        vendingState.getListItemForSale().stream().forEach(data -> {
            if (data.getName().equals(vendingState.getPurchasedItem().getName())) {
                int currentQty = data.getQuantity() - 1;
                data.setQuantity(currentQty);
            }
        });

        //count change
        int change = vendingState.getInputAmount().getValue()
                - vendingState.getPurchasedItem().getPrice();
        vendingState.setChange(change);
        return vendingState;
    }

    public VendingState returnCoins(VendingState vendingState) {
        int totalCoin100 = vendingState.getChange() / 100;
        int totalCoin10 = (vendingState.getChange() % 100) / 10;

        vendingState.setTotalCoins100ForChange(totalCoin100);
        vendingState.setTotalCoins10ForChange(totalCoin10);
        return vendingState;
    }

    public VendingState getReturnedCoins(VendingState vendingState) {

        //Update Coins 100 inventory
        Coins coins100 = vendingState.getChangeCoins100();
        coins100.setQuantity(coins100.getQuantity() - vendingState.getTotalCoins100ForChange());
        if (vendingState.getInputAmount().getValue() == 100) {
            coins100.setQuantity(coins100.getQuantity() + 1);
        }
        vendingState.setChangeCoins100(coins100);

        //Update Coins 10 inventory
        Coins coins10 = vendingState.getChangeCoins10();
        coins10.setQuantity(coins10.getQuantity() - vendingState.getTotalCoins10ForChange());
        if (vendingState.getInputAmount().getValue() == 10) {
            coins10.setQuantity(coins10.getQuantity() + 1);
        }
        vendingState.setChangeCoins10(coins10);

        //reset Vending State
        vendingState = resetVendingState(vendingState);
        return vendingState;
    }

    private VendingState resetVendingState(VendingState vendingState) {
        vendingState.setInputAmount(null);
        vendingState.setChange(0);
        vendingState.setPurchasedItem(null);
        vendingState.setTotalCoins100ForChange(0);
        vendingState.setTotalCoins10ForChange(0);
        return vendingState;
    }

    private Items convertItemTypeToItems(VendingState vendingState, int itemType) {
        Optional<Items> optionalItems = vendingState.getListItemForSale()
                .stream()
                .filter(data -> itemType == data.getItemNo())
                .findFirst();
        if (optionalItems.isPresent()) {
            return optionalItems.get();
        } else {
            throw new BusinessException("Item no doesn't matched with "
                    + "available items");
        }
    }

    private CoinsTypeEnum getEnumCoinsByInputAmount(int totalAmount) {
        if (CoinsTypeEnum.COINS_10.getValue() == totalAmount) {
            return CoinsTypeEnum.COINS_10;
        }

        if (CoinsTypeEnum.COINS_50.getValue() == totalAmount) {
            return CoinsTypeEnum.COINS_50;
        }

        if (CoinsTypeEnum.COINS_100.getValue() == totalAmount) {
            return CoinsTypeEnum.COINS_100;
        }

        if (CoinsTypeEnum.COINS_500.getValue() == totalAmount) {
            return CoinsTypeEnum.COINS_500;
        }

        throw new BusinessException("amount doesn't matched"
                + " with allowed amount. Allowed amount is : "
                + " 10/50/100/500");

    }
}
