package pesistance;

import domain.model.ElectricBill;
import java.util.List;
import java.util.Map;

public interface ElectricBillGateway {
    void addElectricBill(ElectricBill electricBill);
    void updateElectricBill(ElectricBill electricBill);
    void deleteElectricBill(String idClient);
    List<ElectricBill> getElectricBillsByMonth(String month);
    List<ElectricBill> getAllElectricBills();
    List<ElectricBill> findElectricBillsById(String searchIdClient);
    Map<String, Integer> calcTotalQuantityCustomer();
    Double avgTotalForeigner();
}