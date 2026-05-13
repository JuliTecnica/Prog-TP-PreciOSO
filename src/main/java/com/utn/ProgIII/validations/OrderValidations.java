package com.utn.ProgIII.validations;

import com.utn.ProgIII.model.Order.OrderDetails;
import com.utn.ProgIII.model.User.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderValidations {

    public boolean UserOwnsTheOrder(User user, OrderDetails details)
    {
        return Objects.equals(details.getUser().getIdUser(), user.getIdUser());
    }
}
