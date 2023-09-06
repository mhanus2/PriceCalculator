package cz.uhk.datamanager;

import cz.uhk.data.Cash;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvDataManager {
    private static final String SEPARATOR = ";";

    public void save(String file, List<Cash> cashList) throws IOException {
        try (PrintWriter output = new PrintWriter(file)) {
            // Iterating backwards so the output matches the table
            for (int i=cashList.size()-1;i>=0;i--) {
                Cash cash = cashList.get(i);

                output.print(cash.getType());
                output.print(SEPARATOR);
                output.print(cash.getValue());
                output.print(SEPARATOR);
                output.println(cash.getQty());
            }
        }
    }
}
