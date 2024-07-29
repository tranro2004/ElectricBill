package Presention;

import domain.ElectricBillService;
import domain.ElectricBillServiceImpl;
import domain.Subscriber;
import domain.model.ElectricBill;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ElectricBillView extends JFrame implements Subscriber {
    private ElectricBillService electricBillService;
    private JTextField idField, nameField, qtyField, unitPriceField, monthlyElectriclyField, nationalField;
    private JLabel countCustomersVietNam, countCustomersForeigner;
    private JComboBox<String> comboBoxCustomersType, comboBoxElectricityRates;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel inputContainerPanel, outputContainerPanel, buttonPanel;
    private JButton addButton, removeButton, editButton, searchBillButton, avgAmountOfMoneyFromForeignerCustomersButton;
    private JButton issueInvoiceButton, exitButton;
    private JRadioButton vietnamTypeButton, foreignerTypeButton;
    private JMenuBar menuBar;
    private ElectricBillController electricBillController;

    public ElectricBillView(ElectricBillServiceImpl electricBillService) {
        this.electricBillService = electricBillService;
        this.electricBillService.subscribe(this);

        System.out.println("views");

        initialize();
        loadElectricBills();
    }

    private void initialize() {
        setTitle("Electric Bill Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildMenu();
        buildPanel();

        loadElectricBills();
        // updateCustomerCounts();
    }

    private void buildPanel() {
        // Input Panel
        inputContainerPanel = new JPanel(new GridLayout(8, 2));

        inputContainerPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputContainerPanel.add(idField);

        inputContainerPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputContainerPanel.add(nameField);

        inputContainerPanel.add(new JLabel("Qty:"));
        qtyField = new JTextField();
        inputContainerPanel.add(qtyField);

        inputContainerPanel.add(new JLabel("Unit Price:"));
        unitPriceField = new JTextField();
        inputContainerPanel.add(unitPriceField);

        inputContainerPanel.add(new JLabel("MonthlyElectricly:"));
        monthlyElectriclyField = new JTextField();
        inputContainerPanel.add(monthlyElectriclyField);

        //inputContainerPanel.add(new JLabel("Date:"));
       // comboBoxMonth = new JComboBox<>(new String[] { "January", "February", "March", "April", "May", "June", "July",
        //        "August", "September", "October", "November", "December" });
       // inputContainerPanel.add(comboBoxMonth);

        inputContainerPanel.add(new JLabel("Customer Type:"));
        comboBoxCustomersType = new JComboBox<>(new String[] { "Vietnam", "Foreigner" });
        inputContainerPanel.add(comboBoxCustomersType);

        inputContainerPanel.add(new JLabel("Electricity Rates:"));
        comboBoxElectricityRates = new JComboBox<>(new String[] { "Rate1", "Rate2", "Rate3" });
        inputContainerPanel.add(comboBoxElectricityRates);

        // Output Panel
        outputContainerPanel = new JPanel(new GridLayout(1, 2));

        countCustomersVietNam = new JLabel("Vietnam Customers: 0");
        outputContainerPanel.add(countCustomersVietNam);

        countCustomersForeigner = new JLabel("Foreigner Customers: 0");
        outputContainerPanel.add(countCustomersForeigner);

        // Button Panel
        buttonPanel = new JPanel(new GridLayout(2, 4));

        addButton = new JButton("Add");
        buttonPanel.add(addButton);

        removeButton = new JButton("Remove");
        buttonPanel.add(removeButton);

        editButton = new JButton("Edit");
        buttonPanel.add(editButton);

        searchBillButton = new JButton("Search");
        buttonPanel.add(searchBillButton);

        avgAmountOfMoneyFromForeignerCustomersButton = new JButton("Average Amount (Foreigner)");
        buttonPanel.add(avgAmountOfMoneyFromForeignerCustomersButton);

        issueInvoiceButton = new JButton("Issue Invoice");
        buttonPanel.add(issueInvoiceButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });
        buttonPanel.add(exitButton);

        vietnamTypeButton = new JRadioButton("Vietnam Type");
        buttonPanel.add(vietnamTypeButton);

        foreignerTypeButton = new JRadioButton("Foreigner Type");
        buttonPanel.add(foreignerTypeButton);

        // Table
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Name", "Person", "MonthlyElectricly", "Date", "Qty", "Unit Price", "Quota", "Total" }, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add panels to the frame
        getContentPane().add(inputContainerPanel, BorderLayout.NORTH);
        getContentPane().add(outputContainerPanel, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void buildMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        JMenuItem addMenuItem = new JMenuItem("Add");
        JMenuItem editMenuItem = new JMenuItem("Edit");
        JMenuItem removeMenuItem = new JMenuItem("Remove");
        JMenuItem issueInvoiceMenuItem = new JMenuItem("Issue Invoice");

        addMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("check addd");
                ElectricBill electricBill = getDataFromTextField();
                if (electricBill != null) {
                    electricBillService.add(electricBill);
                    loadElectricBills();
                    clearFields();
                }
            }
        });

        editMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to handle "Edit" action
                String idClient = JOptionPane.showInputDialog("Enter ID Client to Edit:");
                List<ElectricBill> bills = electricBillService.findElectricBill(idClient);
                if (bills != null && !bills.isEmpty()) {
                    ElectricBill bill = bills.get(0);
                    String fullName = JOptionPane.showInputDialog("Enter Full Name:", bill.getFullName());
                    String person = JOptionPane.showInputDialog("Enter Person Type:", bill.getPerson());
                    String monthlyElectricly = JOptionPane.showInputDialog("Enter Monthly Electricly:",
                            bill.getMonthlyElectricly());
                    String timeStr = JOptionPane.showInputDialog("Enter Time (YYYY-MM-DD):",
                            new SimpleDateFormat("yyyy-MM-dd").format(bill.getTime()));
                    int qty = Integer.parseInt(JOptionPane.showInputDialog("Enter Quantity:", bill.getQty()));
                    double unitPrice = Double
                            .parseDouble(JOptionPane.showInputDialog("Enter Unit Price:", bill.getUnitPrice()));
                    double quota = Double.parseDouble(JOptionPane.showInputDialog("Enter Quota:", bill.getQuota()));

                    try {
                        Date time = new SimpleDateFormat("yyyy-MM-dd").parse(timeStr);
                        bill.setFullName(fullName);
                        bill.setPerson(person);
                        bill.setMonthlyElectricly(monthlyElectricly);
                        bill.setTime(time);
                        bill.setQty(qty);
                        bill.setUnitPrice(unitPrice);
                        bill.setQuota(quota);
                        electricBillService.update(bill);
                        loadElectricBills();
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Electric Bill not found.");
                }
            }
        });

        removeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idClient = JOptionPane.showInputDialog("Enter ID Client to Remove:");
                electricBillService.deleteBill(idClient);
                loadElectricBills();
            }
        });

        issueInvoiceMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idClient = JOptionPane.showInputDialog("Enter ID Client to Issue Invoice:");
                List<ElectricBill> bills = electricBillService.findElectricBill(idClient);
                if (bills != null && !bills.isEmpty()) {
                    ElectricBill bill = bills.get(0);
                    // Code to issue invoice (e.g., display or print invoice)
                    JOptionPane.showMessageDialog(null, "Invoice Issued:\n" + bill.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Electric Bill not found.");
                }
            }
        });

        menu.add(addMenuItem);
        menu.add(editMenuItem);
        menu.add(removeMenuItem);
        menu.add(issueInvoiceMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void update(List<ElectricBill> electricBills) {
        updateTable(electricBills);
        // updateCustomerCounts();
    }

    public void loadElectricBills() {
        List<ElectricBill> electricBills = electricBillService.getAllElectricBills();

        updateTable(electricBills);
    }


    private void reset() {
        // Logic to reset view
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
        unitPriceField.setText("");
        nationalField.setText("");
    }

    
        private double extractQuota(String rateString) {
            if (rateString.equals("Rate1")) {
                return 1;
    
            } else if (rateString.equals("Rate2")) {
                return 2;
    
            } else if (rateString.equals("Rate3")) {
                return 3;
    
            } else {
                return 0;
            }
            
        }
    

        public ElectricBill getDataFromTextField() {
            String id = idField.getText();
            String name = nameField.getText();
            Integer qty = null;
            Double unitPrice = null;
            Double quota = extractQuota(comboBoxElectricityRates.getSelectedItem().toString());
            String customertype = (String) comboBoxCustomersType.getSelectedItem();
            String monthly = monthlyElectriclyField.getText();
            try {
                qty = Integer.parseInt(qtyField.getText());
                unitPrice = Double.parseDouble(unitPriceField.getText());
                quota = extractQuota(comboBoxElectricityRates.getSelectedItem().toString());
            } catch (NumberFormatException e) {
                // Handle exception: show error message to user, return null or a default
                // ElectricBill, etc.
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity and unit price.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return null;
            }
    
            return new ElectricBill(id, name, customertype, monthly, null, qty, unitPrice, quota);
        }

    void showVietnamFields() {
        
    }

    void showForeignerFields() {
       
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);

    }

    public void addRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addSearchBillButtonListener(ActionListener listener) {
        searchBillButton.addActionListener(listener);
    }

    public void addAvgAmountOfMoneyFromForeignerCustomersButtonListener(ActionListener listener) {
        avgAmountOfMoneyFromForeignerCustomersButton.addActionListener(listener);
    }

    public void addIssueInvoiceButtonListener(ActionListener listener) {
        issueInvoiceButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void addVietnamTypeButtonListener(ActionListener listener) {
        vietnamTypeButton.addActionListener(listener);
    }

    public void addForeignerTypeButtonListener(ActionListener listener) {
        foreignerTypeButton.addActionListener(listener);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getSearchBillButton() {
        return searchBillButton;
    }

    public JButton getAvgAmountOfMoneyFromForeignerCustomersButton() {
        return avgAmountOfMoneyFromForeignerCustomersButton;
    }

    public JButton getIssueInvoiceButton() {
        return issueInvoiceButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JRadioButton getVietnamTypeButton() {
        return vietnamTypeButton;
    }

    public JRadioButton getForeignerTypeButton() {
        return foreignerTypeButton;
    }

    private void exitApplication() {
        System.exit(0);
    }

    public void updateTable(List<ElectricBill> electricBills) {
        tableModel.setRowCount(0);
        for (ElectricBill electricBill : electricBills) {
            tableModel.addRow(
                    new Object[] { electricBill.getIdClient(), electricBill.getFullName(), electricBill.getPerson(),
                            electricBill.getMonthlyElectricly(), electricBill.getTime(), electricBill.getQty(),
                            electricBill.getUnitPrice(), electricBill.getQuota(), electricBill.getTotal() });
        }

    }
}