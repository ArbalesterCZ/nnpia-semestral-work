package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.ProductInCartDto;
import st43189.upce.cz.entity.ProductInCart;
import st43189.upce.cz.service.ProductInCartService;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class ProductInCartController {

    private final ProductInCartService productInCartService;

    public ProductInCartController(ProductInCartService productInCartService) {
        this.productInCartService = productInCartService;
    }

    @PostMapping
    public ProductInCart create(ProductInCartDto dto) {
        return productInCartService.createOrUpdate(fromDto(dto));
    }

    @GetMapping
    public List<ProductInCart> readAll() {
        return productInCartService.getAll();
    }

    @GetMapping("/{userId}/{productId}")
    public ProductInCart read(@PathVariable long userId, @PathVariable long productId) {
        return productInCartService.find(userId, productId);
    }

    @PutMapping
    public ProductInCart update(@RequestBody ProductInCartDto dto) {
        return productInCartService.createOrUpdate(fromDto(dto));
    }

    @DeleteMapping("/{userId}/{productId}}")
    public ProductInCart delete(@PathVariable long userId, @PathVariable long productId) {
        return productInCartService.delete(userId, productId);
    }

    private ProductInCart fromDto(@RequestBody ProductInCartDto dto) {
        ProductInCart productInCart = new ProductInCart();

        productInCart.setAmount(dto.getAmount());
        productInCart.setUser(productInCartService.findUser(dto.getUserId()));
        productInCart.setProduct(productInCartService.findProduct(dto.getProductId()));

        return productInCart;
    }
}
