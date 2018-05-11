package restaurant.service.client;

import org.springframework.stereotype.Service;
import restaurant.model.Client;

import java.util.List;

@Service
public interface ClientService {

    Client findById(long id);

    List<Client> findAll();

//    Client findByOrderrId(long id);

    void addClient(String name,float money,long orderID);
}
