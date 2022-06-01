package st43189.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import st43189.dto.CartProductResultDto;
import st43189.dto.OrderDto;
import st43189.dto.ProductDto;
import st43189.dto.ProductInCartDto;
import st43189.entity.Product;
import st43189.entity.ProductAtOrder;
import st43189.entity.ProductInCart;
import st43189.service.OrderService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public List<OrderDto> createOrderFromUserCart(Authentication authentication) {
        List<OrderDto> dtoList = new LinkedList<>();
        orderService.doOrder(authentication.getName()).forEach(order -> dtoList.add(toDto(order)));

        return dtoList;
    }

    @GetMapping
    public List<OrderDto> ReadAllOfLoggedUser(Authentication authentication) {
        List<OrderDto> dtoList = new LinkedList<>();
        orderService.getAllOrderOf(authentication.getName()).forEach(order -> dtoList.add(toDto(order)));

        return dtoList;
    }

    @GetMapping("/{id}")
    public List<CartProductResultDto> ReadAllProductAtOrder(Authentication authentication, @PathVariable long id) {
        List<CartProductResultDto> dtoList = new LinkedList<>();
        orderService.getAllProductAtOrder(authentication.getName(), id).forEach(order -> dtoList.add(toDto(order)));

        return dtoList;
    }

    private OrderDto toDto(ProductAtOrder productAtOrder) {
        OrderDto dto = new OrderDto();

        dto.setAmount(productAtOrder.getAmount());
        dto.setUserId(productAtOrder.getUser().getId());
        dto.setProductId(productAtOrder.getProduct().getId());
        dto.setOrderId(productAtOrder.getOrder().getId());

        return dto;
    }

    private CartProductResultDto toDto(ProductInCart productInCart) {
        CartProductResultDto dto = new CartProductResultDto();

        dto.setAmount(productInCart.getAmount());
        dto.setId(productInCart.getId().getProductId());

        Product product = productInCart.getProduct();
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());

        return dto;
    }
}
