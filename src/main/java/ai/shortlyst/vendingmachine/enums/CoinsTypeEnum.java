package ai.shortlyst.vendingmachine.enums;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public enum CoinsTypeEnum {
    COINS_500(500, "JPY",false),
    COINS_100(100, "JPY",true),
    COINS_50(50, "JPY",false),
    COINS_10(10, "JPY",true);
    
    private final int value;
    private final String currency;
    private final boolean isUsedForChange;

    private CoinsTypeEnum(int value, String currency, boolean isUsedForChange) {
        this.value = value;
        this.currency = currency;
        this.isUsedForChange = isUsedForChange;
    }

    public int getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isIsUsedForChange() {
        return isUsedForChange;
    }
    
    
}
