package cz.uhk.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CashCalculator {
    private final List<Integer> cashValues = new ArrayList<>(Arrays.asList(5000, 2000,1000, 500, 200 ,100,50,20,10,5,2,1));
    private CashList preparedMoney = new CashList();
    private int inputValue;

    public CashCalculator(int inputValue) {
        this.inputValue = inputValue;
    }

    public CashList calculateMoney() {
        int tmpValue = this.inputValue;
        String type;

        while (tmpValue > 0) {

            for (int cashValue: cashValues) {
                if ((tmpValue - cashValue) >= 0) {
                    boolean existing = isInList(cashValue);

                    if (!existing) {
                        if (cashValue >= 100) {
                            type = "Bankovka";
                        } else {
                            type = "Mince";
                        }

                        Cash cash = new Cash(type, cashValue, 1);
                        preparedMoney.add(cash);
                    }
                    tmpValue -= cashValue;
                    break;
                }
            }
        }
        return preparedMoney;
    }

    private boolean isInList(int value) {
        for (int i = 0; i<preparedMoney.getItemsCount(); i++) {
            Cash iteratedItem = preparedMoney.getItem(i);
            if (iteratedItem.getValue() == value) {
                iteratedItem.setQty(iteratedItem.getQty() + 1);
                return true;
            }
        }
        return false;
    }
}
