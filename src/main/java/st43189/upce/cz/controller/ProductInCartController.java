package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.ProductInCartDto;
import st43189.upce.cz.entity.ProductInCart;
import st43189.upce.cz.service.ProductInCartService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
public class ProductInCartController {

    private final ProductInCartService productInCartService;

    public ProductInCartController(ProductInCartService productInCartService) {
        this.productInCartService = productInCartService;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ProductInCartDto createOrUpdate(@RequestBody ProductInCartDto dto) {
        return toDto(productInCartService.createOrUpdate(fromDto(dto)));
    }

    @GetMapping
    public List<ProductInCartDto> readAll() {
        return productInCartService
                .getAll()
                .stream()
                .map(ProductInCartController::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public List<ProductInCartDto> readAllOfUser(@PathVariable long userId) {
        return productInCartService
                .getAllOfUser(userId)
                .stream()
                .map(ProductInCartController::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/{productId}")
    public ProductInCartDto read(@PathVariable long userId, @PathVariable long productId) {
        return toDto(productInCartService.find(userId, productId));
    }

    @DeleteMapping("/{userId}/{productId}}")
    public ProductInCartDto delete(@PathVariable long userId, @PathVariable long productId) {
        return toDto(productInCartService.delete(userId, productId));
    }

    private ProductInCart fromDto(ProductInCartDto dto) {
        ProductInCart productInCart = new ProductInCart();

        productInCart.setAmount(dto.getAmount());
        productInCart.setUser(productInCartService.findUser(dto.getUserId()));
        productInCart.setProduct(productInCartService.findProduct(dto.getProductId()));

        return productInCart;
    }

    private static ProductInCartDto toDto(ProductInCart productInCart) {
        ProductInCartDto dto = new ProductInCartDto();

        dto.setAmount(productInCart.getAmount());

        dto.setUserId(productInCart.getUser().getId());
        dto.setProductId(productInCart.getProduct().getId());

        return dto;
    }
}
