package cz.uhk.gui;

import cz.uhk.datamanager.CsvDataManager;
import cz.uhk.data.Cash;
import cz.uhk.data.CashCalculator;
import cz.uhk.data.CashList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private CashList cashList = new CashList();
    private ListTableModel model = new ListTableModel();
    private JTable table = new JTable(model);
    private JMenuBar mbMenuBar = new JMenuBar();
    private JTextField tfPrice;

    public MainWindow() {
        super("PriceCalculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();
        createLeftPanel();

        add(new JScrollPane(table), BorderLayout.CENTER);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int columnIndexToSort = 1;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();

        setSize(640, 680);
        setVisible(true);
    }

    private void createMenuBar() {
        JMenuItem miSaveCsv = new JMenuItem("Uložit");
        miSaveCsv.addActionListener(e -> saveAsCsv());

        JMenu mnFile = new JMenu("Výčetka");
        mnFile.add(miSaveCsv);
        mbMenuBar.add(mnFile);
        setJMenuBar(mbMenuBar);
    }

    private void saveAsCsv() {
        try {
            JFileChooser dialog = new JFileChooser(".");
            int action = dialog.showSaveDialog(this);

            if (action == JFileChooser.APPROVE_OPTION) {
                cashList.save(new CsvDataManager(), dialog.getSelectedFile().getPath());
            }
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vyskytl se problém během ukládání souboru: " + exp.getLocalizedMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setBorder(BorderFactory.createTitledBorder("Cena"));

        panel.add(new JLabel("Zadejte cenu:"));
        tfPrice = new JTextField(15);
        panel.add(tfPrice);

        JButton btCalculate = new JButton("Spočítat");
        panel.add(btCalculate);
        btCalculate.addActionListener( (e -> calculateToCash()));

        add(panel, BorderLayout.WEST);
    }

    private void calculateToCash() {
        if (tfPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Musíte zadat cenu!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        try {
            int qty = Integer.parseInt(tfPrice.getText());
            System.out.println(qty);
            CashCalculator calculator = new CashCalculator(qty);
            cashList = calculator.calculateMoney();
            model.fireTableDataChanged();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Musíte zadat číslo (pouze celá čísla)!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    private class ListTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return cashList.getItemsCount();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Cash cash = cashList.getItem(rowIndex);
            return switch (columnIndex) {
                case 0 -> cash.getType();
                case 1 -> cash.getValue();
                case 2 -> cash.getQty();
                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return switch (column) {
                case 0 -> "Typ";
                case 1 -> "Hodnota";
                case 2 -> "Počet";
                default -> super.getColumnName(column);
            };
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0 -> String.class;
                case 1, 2 -> Integer.class;
                default -> super.getColumnClass(columnIndex);
            };
        }

    }
}
