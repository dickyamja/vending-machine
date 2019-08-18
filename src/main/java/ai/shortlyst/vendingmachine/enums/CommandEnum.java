/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.shortlyst.vendingmachine.enums;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public enum CommandEnum {
    INSERT_COIN(1, "Insert coin"),
    CHOOSE_ITEM_TO_PURCHASE(2, "Choose item to purchase"),
    GET_ITEMS(3, "Get items"),
    RETURN_COINS(4, "Return coins"),
    GET_RETURNED_COINS(5, "Get returned coins"),
    UNRECOGNIZED_COMMAND(-1, "Unrecognized command");
    
    private int commandNo;
    private String commandName;

    private CommandEnum(int commandNo, String commandName) {
        this.commandNo = commandNo;
        this.commandName = commandName;
    }

    public int getCommandNo() {
        return commandNo;
    }

    public void setCommandNo(int commandNo) {
        this.commandNo = commandNo;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
    
    
    
}
