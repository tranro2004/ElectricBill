package Presention.command_;

import domain.ElectricBillService;

public class DeleteCommand extends Command {
    private String idClient;

    public DeleteCommand(ElectricBillService electricBillServiceRemote, String idClient) {
        super(electricBillServiceRemote);
        this.idClient = idClient;
    }

    @Override
    public void execute() {
        electricBillServiceRemote.deleteBill(idClient);
    }
}