package cz.uhk.data;

import cz.uhk.datamanager.CsvDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CashList {
    private List<Cash> cashList = new ArrayList<>();
    public Cash getItem(int index) {
        return cashList.get(index);
    }
    public int getItemsCount() {
        return cashList.size();
    }

    public void add(Cash cash) {
        cashList.add(cash);
    }

    public void save(CsvDataManager dataManager, String file) throws IOException {
        dataManager.save(file, cashList);
    }
}
