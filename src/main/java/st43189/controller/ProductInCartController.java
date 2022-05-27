package st43189.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import st43189.dto.ProductInCartDto;
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

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ProductInCartDto createOrUpdate(@Valid @RequestBody ProductInCartDto dto, Authentication authentication) {
        return toDto(productInCartService.createOrUpdate(fromDto(dto, authentication)));
    }

    @GetMapping
    public List<ProductInCartDto> readAll(Authentication authentication) {
        List<ProductInCartDto> dtoList = new LinkedList<>();
        productInCartService.getAllOfUser(authentication).forEach(productInCart -> dtoList.add(toDto(productInCart)));

        return dtoList;
    }

    @GetMapping("/{userId}/{productId}")
    public ProductInCartDto read(@PathVariable long userId, @PathVariable long productId) {
        return toDto(productInCartService.find(userId, productId));
    }

    @DeleteMapping("/{userId}/{productId}}")
    public ProductInCartDto delete(@PathVariable long userId, @PathVariable long productId) {
        return toDto(productInCartService.delete(userId, productId));
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

    private ProductInCartDto toDto(ProductInCart productInCart) {
        ProductInCartDto dto = new ProductInCartDto();

        dto.setAmount(productInCart.getAmount());

        dto.setUserId(productInCart.getUser().getId());
        dto.setProductId(productInCart.getProduct().getId());

        return dto;
    }
}
