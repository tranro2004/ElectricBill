package Presention.command_;

import domain.ElectricBillService;
import domain.model.ElectricBill;
import java.util.List;

public class GetByMonthCommand extends Command {
    private String month;

    public GetByMonthCommand(ElectricBillService electricBillServiceRemote, String month) {
        super(electricBillServiceRemote);
        this.month = month;
    }

    @Override
    public void execute() {
        List<ElectricBill> bills = electricBillServiceRemote.getElectricBillByMonth(month);
        // Additional logic to handle the retrieved bills, e.g., notify observers
        // notifyObservers(bills);
    }
}