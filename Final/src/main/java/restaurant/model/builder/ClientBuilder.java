package restaurant.model.builder;

import restaurant.model.*;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setId(long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setMoney(float money) {
        client.setMoney(money);
        return this;
    }


    public Client build() {
        return client;
    }
}
