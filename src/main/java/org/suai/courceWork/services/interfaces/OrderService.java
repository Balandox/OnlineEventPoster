package org.suai.courceWork.services.interfaces;

import org.suai.courceWork.dto.OrderDTO;
import org.suai.courceWork.models.entities.Order;

import java.util.List;

public interface OrderService {

    Order saveOrder(String name);

    List<OrderDTO> getAllOrderDTO();

    OrderDTO findOrderById(int id);
}
