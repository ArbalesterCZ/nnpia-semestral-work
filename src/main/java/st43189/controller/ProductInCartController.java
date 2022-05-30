package st43189.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import st43189.dto.CartProductResultDto;
import st43189.dto.ProductInCartDto;
import st43189.entity.Product;
import st43189.entity.ProductInCart;
import st43189.entity.User;
import st43189.entity.UserProductKey;
import st43189.service.ProductInCartService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/carts")
public class ProductInCartController {

    private final ProductInCartService productInCartService;

    public ProductInCartController(ProductInCartService productInCartService) {
        this.productInCartService = productInCartService;
    }

    @PostMapping
    public CartProductResultDto create(@Valid @RequestBody ProductInCartDto dto, Authentication authentication) {
        return toDto(productInCartService.addProductToCart(fromDto(dto, authentication)));
    }

    @PutMapping
    public CartProductResultDto update(@Valid @RequestBody ProductInCartDto dto, Authentication authentication) {
        return toDto(productInCartService.updateProductInCart(fromDto(dto, authentication)));
    }

    @GetMapping
    public List<CartProductResultDto> readAll(Authentication authentication) {
        List<CartProductResultDto> dtoList = new LinkedList<>();
        productInCartService.getAllOfUser(authentication).forEach(productInCart -> dtoList.add(toDto(productInCart)));

        return dtoList;
    }

    @GetMapping("/{userId}/{productId}")
    public CartProductResultDto read(@PathVariable long userId, @PathVariable long productId) {
        return toDto(productInCartService.find(userId, productId));
    }

    @DeleteMapping
    public CartProductResultDto delete(@RequestBody ProductInCartDto dto, Authentication authentication) {
        return toDto(productInCartService.delete(fromDto(dto, authentication)));
    }

    private ProductInCart fromDto(ProductInCartDto dto, Authentication authentication) {
        ProductInCart productInCart = new ProductInCart();

        User user = productInCartService.findUser(authentication);

        productInCart.setAmount(dto.getAmount());
        productInCart.setUser(user);
        productInCart.setProduct(productInCartService.findProduct(dto.getProductId()));
        productInCart.setId(new UserProductKey(user.getId(), dto.getProductId()));

        return productInCart;
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
