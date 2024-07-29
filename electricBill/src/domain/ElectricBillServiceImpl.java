package domain;


import domain.model.ElectricBill;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pesistance.ElectricBillGateway;

public class ElectricBillServiceImpl extends Publisher implements ElectricBillService {
    private ElectricBillGateway electricBillGateway;
    private List<Subscriber> subscribers = new ArrayList<>();
    private List<ElectricBill> electricBills = new ArrayList<>();

    public ElectricBillServiceImpl(ElectricBillGateway electricBillGateway) {
        this.electricBillGateway = electricBillGateway;
    }

    @Override
    public void add(ElectricBill electricBill) {
        electricBillGateway.addElectricBill(electricBill);
        
    }

    @Override
    public void update(ElectricBill electricBill) {
        electricBillGateway.updateElectricBill(electricBill);
        
    }

    @Override
    public void deleteBill(String idClient) {
        electricBillGateway.deleteElectricBill(idClient);
        
    }

    @Override
    public List<ElectricBill> getElectricBillByMonth(String month) {
        return electricBillGateway.getElectricBillsByMonth(month);
    }

    @Override
    public List<ElectricBill> getAllElectricBills() {
        
        return electricBillGateway.getAllElectricBills();
    }

    @Override
    public List<ElectricBill> findElectricBill(String searchIdClient) {
        return electricBillGateway.findElectricBillsById(searchIdClient);
    }

    @Override
    public Map<String, Integer> calcTotalQuantityCustomer() {
        return electricBillGateway.calcTotalQuantityCustomer();
    }

    @Override
    public Double calculateAverageAmountForForeignerCustomers() {
        return electricBillGateway.avgTotalForeigner();
    }

}