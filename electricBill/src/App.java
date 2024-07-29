import Presention.ElectricBillController;
import Presention.ElectricBillView;
import domain.ElectricBillServiceImpl;
import domain.model.ElectricBill;

import java.sql.SQLException;

import pesistance.ElectricBillJdbcGateway;

public class App {

        public static void main(String[] args) throws ClassNotFoundException, SQLException {

                ElectricBillJdbcGateway electricBillGateway = new ElectricBillJdbcGateway();
                ElectricBillServiceImpl electricBillService = new ElectricBillServiceImpl(electricBillGateway);
                ElectricBillView electricBillView = new ElectricBillView(electricBillService);
                ElectricBillController electricBillController = new ElectricBillController(electricBillView,
                                electricBillService);

                electricBillService.subscribe(electricBillView);

                electricBillView.setVisible(true);
        }
}