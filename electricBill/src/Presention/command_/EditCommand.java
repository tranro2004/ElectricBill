package Presention.command_;

import domain.ElectricBillService;
import domain.model.ElectricBill;

public class EditCommand extends  Command {
    private ElectricBillService electricBillService;
    private ElectricBill electricBill;

    public EditCommand(ElectricBillService electricBillService, ElectricBill electricBill) {
        super(electricBillService);
        this.electricBill = electricBill;
    }

    @Override
    public void execute() {
        electricBillService.update(electricBill);
    }
}