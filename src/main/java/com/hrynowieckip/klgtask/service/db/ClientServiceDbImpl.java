package com.hrynowieckip.klgtask.service.db;

import com.hrynowieckip.klgtask.domain.model.Client;
import com.hrynowieckip.klgtask.domain.repository.ClientRepository;
import com.hrynowieckip.klgtask.exception.ClientNotFoundException;
import com.hrynowieckip.klgtask.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceDbImpl implements ClientService {
    private final ClientRepository repository;

    @Override
    public Client getClientById(Long id) {
        log.debug("Getting client by id: {}", id);
        return repository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(String.format("Client %d not found", id)));
    }
}
