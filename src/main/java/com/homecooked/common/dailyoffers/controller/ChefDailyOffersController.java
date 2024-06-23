package com.homecooked.common.dailyoffers.controller;

import com.homecooked.common.dailyoffers.dto.DailyOfferCreateUpdateDto;
import com.homecooked.common.dailyoffers.dto.DailyOffersResponseDto;
import com.homecooked.common.dailyoffers.service.DailyOffersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ch/api/daily-offers")
@RequiredArgsConstructor
public class ChefDailyOffersController {

    private final DailyOffersService dailyOffersService;

    @PostMapping
    public void addOffer(@RequestBody DailyOfferCreateUpdateDto dto) {
        dailyOffersService.addOffer(dto);
    }

    @PutMapping("/{offerId}")
    public DailyOffersResponseDto updateOffer(@PathVariable Integer offerId, @RequestBody DailyOfferCreateUpdateDto dto) {
        return dailyOffersService.updateOffer(offerId, dto);

    }

    @DeleteMapping("/{offerId}")
    public void deleteOffer(@PathVariable Integer offerId) {
        dailyOffersService.deleteOffer(offerId);
    }

    @GetMapping
    public List<DailyOffersResponseDto> myOffers() {
        return dailyOffersService.getMyOffers();
    }
}
