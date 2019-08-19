package ai.shortlyst.vendingmachine;

import ai.shortlyst.vendingmachine.enums.CoinsTypeEnum;
import ai.shortlyst.vendingmachine.enums.CommandEnum;
import ai.shortlyst.vendingmachine.enums.ItemsNameEnum;
import ai.shortlyst.vendingmachine.exception.BusinessException;
import ai.shortlyst.vendingmachine.model.Coins;
import ai.shortlyst.vendingmachine.model.Items;
import ai.shortlyst.vendingmachine.model.VendingState;
import ai.shortlyst.vendingmachine.service.OperationService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class Main {

    public static void main(String[] args) throws IOException {

        //Initialization
        OperationService service = new OperationService();
        VendingState vendingState = new VendingState();
        vendingState.initialize();
        vendingState.printCurrentState();

        //prepare console
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        readConsoleAndReadCommand(consoleReader, service, vendingState);
    }

    private static void readConsoleAndReadCommand(BufferedReader consoleReader,
            OperationService service, VendingState vendingState) throws IOException {
        System.out.print("Input your command : ");
        String inputLine = consoleReader.readLine();
        while (!"0".equals(inputLine)) {
            routeCommand(inputLine, service, vendingState);
            System.out.print("Input your command : ");
            inputLine = consoleReader.readLine();
        }
    }

    private static void routeCommand(String inputLine, OperationService service,
            VendingState vendingState) {
        try {
            String[] commands = inputLine.split("\\s+");
            if (commands.length == 0) {
                System.out.println("Please input correct command !");
            } else {

                //validate first argument
                String firstArgValidation = validateFirstArgs(commands);
                if (StringUtils.isNotBlank(firstArgValidation)) {
                    System.out.println(firstArgValidation);
                    return;
                }

                Integer firstArg = Integer.valueOf(commands[0]);
                CommandEnum selectedCommand = convertArgToCommand(firstArg);
                switch (selectedCommand) {
                    case INSERT_COIN:
                        String stepOneSecondArgValidation = validateSecondArgs(commands);
                        if (StringUtils.isNotBlank(stepOneSecondArgValidation)) {
                            System.out.println(stepOneSecondArgValidation);
                            break;
                        }
                        vendingState = service.insertCoins(vendingState, Integer.valueOf(commands[1]));
                        vendingState.printCurrentState();
                        break;
                    case CHOOSE_ITEM_TO_PURCHASE:
                        String stepTwosecondArgValidation = validateSecondArgs(commands);
                        if (StringUtils.isNotBlank(stepTwosecondArgValidation)) {
                            System.out.println(stepTwosecondArgValidation);
                            break;
                        }
                        vendingState = service.chooseItemToPurchase(vendingState, Integer.valueOf(commands[1]));
                        vendingState.printCurrentState();
                        break;
                    case GET_ITEMS:
                        vendingState = service.getItem(vendingState);
                        vendingState.printCurrentState();
                        break;
                    case RETURN_COINS:
                        vendingState = service.returnCoins(vendingState);
                        vendingState.printCurrentState();
                        break;
                    case GET_RETURNED_COINS:
                        vendingState = service.getReturnedCoins(vendingState);
                        vendingState.printCurrentState();
                    default:
                        System.out.println(selectedCommand.getCommandName());
                        break;
                }
            }
        } catch (BusinessException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static String validateFirstArgs(String[] args) {
        if (args.length <= 0) {
            return "First arguments must be provided";
        }

        String firstArgs = args[0];
        if (!StringUtils.isNumeric(firstArgs)) {
            return "First arguments must be numeric";
        }
        return StringUtils.EMPTY;
    }

    private static String validateSecondArgs(String[] args) {
        if (args.length <= 1) {
            return "Second arguments must be provided";
        }

        String secondArg = args[1];
        if (!StringUtils.isNumeric(secondArg)) {
            return "Second arguments must be numeric";
        }
        return StringUtils.EMPTY;
    }

    private static CommandEnum convertArgToCommand(int arg) {
        if (1 == arg) {
            return CommandEnum.INSERT_COIN;
        }

        if (2 == arg) {
            return CommandEnum.CHOOSE_ITEM_TO_PURCHASE;
        }

        if (3 == arg) {
            return CommandEnum.GET_ITEMS;
        }

        if (4 == arg) {
            return CommandEnum.RETURN_COINS;
        }

        if (5 == arg) {
            return CommandEnum.GET_RETURNED_COINS;
        }

        return CommandEnum.UNRECOGNIZED_COMMAND;
    }

    private static VendingState initializeVendingState() {
        VendingState vendingState = new VendingState();

        //Initialize List Item For Sale, each item's quantity is set to 100 pieces
        List<Items> listItemForSale = new ArrayList<>();
        listItemForSale.add(new Items(1, ItemsNameEnum.CANNED_COFFEE.name(), 120, 100, ""));
        listItemForSale.add(new Items(2, ItemsNameEnum.WATER_PET_BOTTLE.name(), 100, 100, ""));
        listItemForSale.add(new Items(3, ItemsNameEnum.SPORT_DRINKS.name(), 150, 100, ""));
        vendingState.setListItemForSale(listItemForSale);

        //Initialize Coins (100 & 10) for change, each type's quantity set to 100 coins
        vendingState.setChangeCoins100(new Coins(CoinsTypeEnum.COINS_100, 100));
        vendingState.setChangeCoins10(new Coins(CoinsTypeEnum.COINS_10, 100));
        return vendingState;
    }
}
