package restaurant.model.validation;

import restaurant.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private final Client client;
    private List<String> errors;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateMoney(client.getMoney());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateMoney(float money){
        if(money<0.0)
            errors.add("Not enough money!");
    }

}
