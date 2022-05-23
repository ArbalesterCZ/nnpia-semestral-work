package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.ProductInCartDto;
import st43189.upce.cz.entity.ProductInCart;
import st43189.upce.cz.service.ProductInCartService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class ProductInCartController {

    private final ProductInCartService productInCartService;

    public ProductInCartController(ProductInCartService productInCartService) {
        this.productInCartService = productInCartService;
    }

    @PostMapping
    public ProductInCartDto create(@RequestBody ProductInCartDto dto) {
        return toDto(productInCartService.createOrUpdate(fromDto(dto)));
    }

    @GetMapping
    public List<ProductInCartDto> readAll() {
        List<ProductInCartDto> dtoList = new LinkedList<>();
        productInCartService.getAll().forEach(productInCart -> dtoList.add(toDto(productInCart)));

        return dtoList;
    }

    @GetMapping("/{userId}/{productId}")
    public ProductInCartDto read(@PathVariable long userId, @PathVariable long productId) {
        return toDto(productInCartService.find(userId, productId));
    }

    @PutMapping
    public ProductInCartDto update(@RequestBody ProductInCartDto dto) {
        return toDto(productInCartService.createOrUpdate(fromDto(dto)));
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

    private ProductInCartDto toDto(ProductInCart productInCart) {
        ProductInCartDto dto = new ProductInCartDto();

        dto.setAmount(productInCart.getAmount());

        dto.setUserId(productInCart.getUser().getId());
        dto.setProductId(productInCart.getProduct().getId());

        return dto;
    }
}
