package com.homecooked.common.cart;

import com.homecooked.common.chef.Chef;
import com.homecooked.common.client.Client;
import com.homecooked.common.orderitem.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "cart")
    private List<OrderItem> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    private BigDecimal totalPrice;
}
