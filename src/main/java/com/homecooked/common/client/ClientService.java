package com.homecooked.common.client;

import com.homecooked.common.client.dto.ClientCreateUpdateDto;
import com.homecooked.common.client.dto.ClientResponseDto;

public interface ClientService {

    ClientResponseDto me();

    ClientResponseDto update(Integer id, ClientCreateUpdateDto dto);

    void delete(Integer id);

    Client findById(Integer id);

    Client currentClient();
}
