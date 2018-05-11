package restaurant.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Client;
import restaurant.model.Orderr;
import restaurant.model.builder.ClientBuilder;
import restaurant.repository.ClientRepository;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

//    @Override
//    public Client findByOrderrId(long id) {
//        return clientRepository.findByOrderrId(id);
//    }

    @Override
    public void addClient(String name,float money,long orderID){
        Client client=new ClientBuilder()
                .setName(name)
                .setMoney(money)
//                .setOrder(new Orderr())
                .build();
        clientRepository.save(client);
    }
}
