package restaurant.service.bill;

import org.springframework.stereotype.Service;
import restaurant.model.Orderr;

@Service
public interface BillService {
    void generateBill(Orderr orderr);

}
