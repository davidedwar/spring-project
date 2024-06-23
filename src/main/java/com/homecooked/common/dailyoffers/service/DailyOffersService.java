package com.homecooked.common.dailyoffers.service;

import com.homecooked.common.chef.Chef;
import com.homecooked.common.chef.ChefService;
import com.homecooked.common.dailyoffers.dto.DailyOfferCreateUpdateDto;
import com.homecooked.common.dailyoffers.dto.DailyOffersResponseDto;
import com.homecooked.common.dailyoffers.entity.DailyOffers;
import com.homecooked.common.dailyoffers.mapper.DailyOffersMapper;
import com.homecooked.common.dailyoffers.repository.DailyOffersRepository;
import com.homecooked.common.product.Product;
import com.homecooked.common.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyOffersService {

    private final ProductRepository productRepository;
    private final DailyOffersRepository dailyOffersRepository;
    private final DailyOffersMapper dailyOffersMapper;
    private final ChefService chefService;

    @Transactional
    public void addOffer(DailyOfferCreateUpdateDto dto) {

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with Id %d not found", dto.productId())));

        Chef chef = chefService.currentChef();

        Integer offersCountForChef = dailyOffersRepository.countByChefId(chef.getId());
        if (offersCountForChef >= 10) {
            throw new IllegalStateException("Cannot create more than 10 offers for each chef.");
        }

        boolean offerExistsForProduct = dailyOffersRepository.existsByProductId(dto.productId());
        if (offerExistsForProduct) {
            throw new IllegalStateException("Each product can have only one offer.");
        }

        DailyOffers offer = DailyOffers.builder()
                .status(true)
                .product(product)
                .newPrice(dto.newPrice())
                .build();
        dailyOffersRepository.save(offer);
    }

    @Transactional
    public DailyOffersResponseDto updateOffer(Integer offerId, DailyOfferCreateUpdateDto dto) {
        DailyOffers offer = dailyOffersRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Offer with Id %d not found", offerId)));

        productRepository.findById(dto.productId()).ifPresent(offer::setProduct);

        dailyOffersMapper.updateEntityFromDto(dto, offer);
        return dailyOffersMapper.toResponseDto(offer);
    }

    @Transactional
    public void deleteOffer(Integer offerId) {
        DailyOffers offer = dailyOffersRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Offer with Id %d not found", offerId)));

        dailyOffersRepository.delete(offer);
    }

    public List<DailyOffersResponseDto> getMyOffers() {
        Chef chef = chefService.currentChef();
        List<DailyOffers> offers = dailyOffersRepository.findByChefId(chef.getId());
        return dailyOffersMapper.ListEntityToResponse(offers);
    }


}
