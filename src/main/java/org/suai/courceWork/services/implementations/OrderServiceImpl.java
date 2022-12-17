package org.suai.courceWork.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suai.courceWork.dto.BucketDTO;
import org.suai.courceWork.dto.BucketItemDTO;
import org.suai.courceWork.dto.OrderDTO;
import org.suai.courceWork.models.entities.Order;
import org.suai.courceWork.models.entities.OrderDetails;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.repositories.OrderDetailsRepository;
import org.suai.courceWork.repositories.OrderRepository;
import org.suai.courceWork.services.interfaces.OrderService;

import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BucketServiceImpl bucketServiceImpl;
    private final OrderDetailsRepository orderDetailsRepository;

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BucketServiceImpl bucketServiceImpl, OrderDetailsRepository orderDetailsRepository, UserServiceImpl userServiceImpl) {
        this.orderRepository = orderRepository;
        this.bucketServiceImpl = bucketServiceImpl;
        this.orderDetailsRepository = orderDetailsRepository;
        this.userServiceImpl = userServiceImpl;
    }

    public Order saveOrder(String name){
        User user = this.userServiceImpl.findFirstByName(name);
        BucketDTO bucketDTO = this.bucketServiceImpl.getBucketByUserName(name);

        Order orderToSave = new Order();
        orderToSave.setUser(user);
        orderToSave.setCreationTime(new Date());

        Order savedOrder = this.orderRepository.save(orderToSave);
        savedOrder.setOrderNum(savedOrder.getId());

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        savedOrder.setOrderDetails(new ArrayList<>());

        for(BucketItemDTO bucketItemDTO : bucketDTO.getBucketItems()){
            OrderDetails orderDetails = new OrderDetails(bucketItemDTO);
            orderDetails.setOrder(savedOrder);
            orderDetailsList.add(orderDetails);
            savedOrder.getOrderDetails().add(orderDetails);
        }
        user.getOrderList().add(savedOrder);
        savedOrder.setTotalSum(bucketDTO.getSum());
        this.orderDetailsRepository.saveAll(orderDetailsList);

        this.bucketServiceImpl.deleteBucketAfterOrder(name);

        return savedOrder;
    }

    public List<OrderDTO> getAllOrderDTO(){
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for(Order order : this.orderRepository.findByOrderByCreationTime()){
            OrderDTO orderDTO = new OrderDTO(order, 0);
            orderDTO.calculateAmount();
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }

    public OrderDTO findOrderById(int id){

        Optional<Order> order = this.orderRepository.findById(id);

        if(order.isPresent()){
            OrderDTO orderDTO = new OrderDTO(order.get(), 0);
            orderDTO.calculateAmount();
            return orderDTO;
        }

        return null;
    }


}
