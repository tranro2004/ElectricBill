package domain;

import domain.model.ElectricBill;
import java.util.List;
import java.util.Map;

public interface ElectricBillService {
    void add(ElectricBill electricBill);
    void update(ElectricBill electricBill);
    void deleteBill(String idClient);
    List<ElectricBill> getElectricBillByMonth(String month);
    List<ElectricBill> getAllElectricBills();
    List<ElectricBill> findElectricBill(String searchIdClient);
    Map<String, Integer> calcTotalQuantityCustomer();
    Double calculateAverageAmountForForeignerCustomers();
    void subscribe(Subscriber subscriber);
    
  
}