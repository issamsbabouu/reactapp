package ma.food.easy.ReactNativeMIY.service;
import ma.food.easy.ReactNativeMIY.model.commande;
import ma.food.easy.ReactNativeMIY.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public void confirmDelivery(Long orderId) {
        commande order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setDelivered(true);
        orderRepository.save(order);
    }
}
