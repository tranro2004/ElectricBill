package Presention.command_;

import domain.ElectricBillService;

public class AvgAmountCommand extends Command {
    public AvgAmountCommand(ElectricBillService electricBillServiceRemote) {
        super(electricBillServiceRemote);
    }

    @Override
    public void execute() {
        electricBillServiceRemote.calculateAverageAmountForForeignerCustomers();
    }
}