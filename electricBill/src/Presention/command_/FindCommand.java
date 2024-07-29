package Presention.command_;
import java.util.List;

import domain.ElectricBillService;
import domain.model.ElectricBill;

public class FindCommand extends Command {
    private String searchIdClient;

    public FindCommand(ElectricBillService electricBillServiceRemote, String searchIdClient) {
        super(electricBillServiceRemote);
        this.searchIdClient = searchIdClient;
    }

    @Override
    public void execute() {
        List<ElectricBill> bills = electricBillServiceRemote.findElectricBill(searchIdClient);
        // Logic to handle the found bills
    }
}