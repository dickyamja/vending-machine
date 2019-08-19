package ai.shortlyst.vendingmachine.enums;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public enum ItemsNameEnum {
    CANNED_COFFEE("Canned coffee"),
    WATER_PET_BOTTLE("Water PET bottle"),
    SPORT_DRINKS("Sport drinks");
    
    private String itemsName;

    private ItemsNameEnum(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getItemsName() {
        return itemsName;
    }
    
    
    
    
}
