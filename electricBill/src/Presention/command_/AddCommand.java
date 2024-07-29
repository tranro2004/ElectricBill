package Presention.command_;

import domain.ElectricBillService;
import domain.model.ElectricBill;

public class AddCommand extends  Command {

    private ElectricBill electricBill;
    private ElectricBillService electricBillService;
   

    public AddCommand(ElectricBillService electricBillService, ElectricBill electricBill) {
        super(electricBillService);
        this.electricBill = electricBill;
    }

    @Override
    public void execute() {
        electricBillServiceRemote.add(electricBill);
    }
}