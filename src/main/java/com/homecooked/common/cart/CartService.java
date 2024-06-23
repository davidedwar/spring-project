package com.homecooked.common.cart;

import com.homecooked.common.cart.dto.CartItemDto;
import com.homecooked.common.cart.dto.CartResponseDto;
import com.homecooked.common.cart.dto.CartUpdateDto;
import com.homecooked.common.client.Client;
import com.homecooked.common.client.ClientService;
import com.homecooked.common.exception.CartChefClashException;
import com.homecooked.common.orderitem.OrderItem;
import com.homecooked.common.orderitem.OrderItemMapper;
import com.homecooked.common.orderitem.repository.OrderItemsRepository;
import com.homecooked.common.product.Product;
import com.homecooked.common.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final ClientService clientService;
    private final ProductRepository productRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderItemMapper orderItemMapper;

    public CartResponseDto addToCart(CartUpdateDto dto) {
        Client client = clientService.currentClient();
        Cart cart = cartRepository.findByClientId(client.getId())
                .orElseThrow(() -> new EntityNotFoundException("No active cart for current user"));

        Product product = productRepository.findById(dto.productId()).orElseThrow(() -> new EntityNotFoundException("product not found"));

        BigDecimal unitPrice = product.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(dto.quantity());
        BigDecimal totalPrice = unitPrice.multiply(quantity);

        if (product.getChef() != cart.getChef()) {
            throw new CartChefClashException("cart can contain items from only one chef");
        }

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .productName(product.getName())
                .quantity(dto.quantity())
                .totalPrice(unitPrice)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        cart.getItems().add(orderItem);
        cart.setChef(product.getChef());
        orderItem.setCart(cart);
        orderItemsRepository.save(orderItem);

        cart.setTotalPrice(cart.getTotalPrice().add(totalPrice));
        cartRepository.save(cart);

        List<CartItemDto> cartItems = cart.getItems().stream()
                .map(orderItemMapper::toCartItemDto)
                .toList();

        return CartResponseDto.builder()
                .cartItems(cartItems)
                .totalPrice(totalPrice)
                .build();

    }

    public CartResponseDto removeFromCart(CartUpdateDto dto) {
        Client client = clientService.currentClient();
        Cart cart = cartRepository.findByClientId(client.getId())
                .orElseThrow(() -> new EntityNotFoundException("No active cart for current user"));

        Optional<OrderItem> itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(dto.productId()))
                .findFirst();

        if (itemToRemove.isEmpty()) {
            throw new EntityNotFoundException("Item not found in cart");
        }

        OrderItem orderItem = itemToRemove.get();

        BigDecimal totalPriceReduction = orderItem.getTotalPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        cart.getItems().remove(orderItem);
        orderItemsRepository.delete(orderItem);

        cart.setTotalPrice(cart.getTotalPrice().subtract(totalPriceReduction));

        cartRepository.save(cart);

        List<CartItemDto> cartItems = cart.getItems().stream()
                .map(orderItemMapper::toCartItemDto)
                .toList();

        return CartResponseDto.builder()
                .cartItems(cartItems)
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    public CartResponseDto clearCart() {
        Client client = clientService.currentClient();
        Cart cart = cartRepository.findByClientId(client.getId())
                .orElseThrow(() -> new EntityNotFoundException("No active cart for current user"));

        if (cart.getItems().isEmpty()) {
            return CartResponseDto.builder()
                    .cartItems(null)
                    .totalPrice(BigDecimal.ZERO)
                    .build();
        }

        cart.getItems().clear();

        cart.setTotalPrice(BigDecimal.ZERO);

        cartRepository.save(cart);

        orderItemsRepository.deleteAll(cart.getItems());

        return CartResponseDto.builder()
                .cartItems(null)
                .totalPrice(BigDecimal.ZERO)
                .build();
    }

}
