package domain;

import domain.model.ElectricBill;
import java.util.List;


public interface Subscriber {
    void update(List<ElectricBill> electricBills);
   
}