package Presention;

import Presention.command_.AddCommand;
import Presention.command_.Command;
import Presention.command_.DeleteCommand;
import domain.ElectricBillService;
import domain.model.ElectricBill;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ElectricBillController implements ActionListener, ItemListener, ListSelectionListener {
    private static ElectricBillController instance;
    private ElectricBillView electricBillView;
    private ElectricBillService electricBillService;

    public ElectricBillController(ElectricBillView electricBillView, ElectricBillService electricBillService) {
        this.electricBillView = electricBillView;
        this.electricBillService = electricBillService;

        this.electricBillView.addAddButtonListener(this);
        this.electricBillView.addRemoveButtonListener(this);
        this.electricBillView.addEditButtonListener(this);
        this.electricBillView.addSearchBillButtonListener(this);
        this.electricBillView.addAvgAmountOfMoneyFromForeignerCustomersButtonListener(this);
        this.electricBillView.addIssueInvoiceButtonListener(this);
        this.electricBillView.addExitButtonListener(this);
        this.electricBillView.addVietnamTypeButtonListener(this);
        this.electricBillView.addForeignerTypeButtonListener(this);

    }

    public static ElectricBillController getInstance(ElectricBillView electricBillView,
            ElectricBillService electricBillService) {
        if (instance == null) {
            instance = new ElectricBillController(electricBillView, electricBillService);
        }
        return instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == electricBillView.getAddButton()) {
            ElectricBill electricBill = electricBillView.getDataFromTextField();
            if (electricBill != null) {
                Command addCommand = new AddCommand(electricBillService, electricBill);
                execute(addCommand);
            }
            electricBillView.loadElectricBills();

        } else if (source == electricBillView.getRemoveButton()) {
            String idClient = JOptionPane.showInputDialog("Enter ID Client to Remove:");
            Command addCommand = new DeleteCommand(electricBillService, idClient);
            execute(addCommand);

            electricBillView.loadElectricBills();

        } else if (source == electricBillView.getEditButton()) {
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
                    electricBillView.loadElectricBills();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Electric Bill not found.");
            }
        } else if (source == electricBillView.getSearchBillButton()) {
            String idClient = JOptionPane.showInputDialog("Enter ID Client to find:");
            List<ElectricBill> data = electricBillService.findElectricBill(idClient);
            electricBillView.updateTable(data);

        } else if (source == electricBillView.getAvgAmountOfMoneyFromForeignerCustomersButton()) {

            Double data = electricBillService.calculateAverageAmountForForeignerCustomers();
            JOptionPane.showMessageDialog(null, data);

        } else if (source == electricBillView.getIssueInvoiceButton()) {
            // Code to handle "Issue Invoice" action
            String idClient = JOptionPane.showInputDialog("Enter ID Client to Issue Invoice:");
            List<ElectricBill> bills = electricBillService.findElectricBill(idClient);
            if (bills != null && !bills.isEmpty()) {
                ElectricBill bill = bills.get(0);
                
                String invoiceDetails = String.format(
                        "Invoice Details:\n" +
                                "-----------------------------\n" +
                                "Client ID: \n" + bill.getIdClient() + "\n" +
                                "Invoice Number: \n" + bill.getFullName() + "\n" +
                                "Date: \n" + bill.getTime() + "\n" +
                                "Amount: \n" + bill.getTotal() + "\n" +
                                "-----------------------------\n" +
                                "Thank you for your business!");

              
                JOptionPane.showMessageDialog(null, invoiceDetails);
            } else {
                JOptionPane.showMessageDialog(null, "Electric Bill not found.");
            }

        } else if (source == electricBillView.getExitButton()) {
            
        } else if (source == electricBillView.getVietnamTypeButton()) {
            electricBillView.showVietnamFields();
        } else if (source == electricBillView.getForeignerTypeButton()) {
            electricBillView.showForeignerFields();
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        
    }

    private void execute(Command command) {
        command.execute();
    }
}