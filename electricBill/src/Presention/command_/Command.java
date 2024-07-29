package Presention.command_;

import domain.ElectricBillService;

public abstract class Command {
    protected ElectricBillService electricBillServiceRemote;

    public Command(ElectricBillService electricBillServiceRemote) {
        this.electricBillServiceRemote = electricBillServiceRemote;
    }

    public abstract void execute();
}
