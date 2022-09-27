package com.hrynowieckip.klgtask.service.db;

import com.hrynowieckip.klgtask.domain.model.ToRent;
import com.hrynowieckip.klgtask.domain.repository.ToRentRepository;
import com.hrynowieckip.klgtask.exception.ToRentNotFoundException;
import com.hrynowieckip.klgtask.service.ToRentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToRentServiceDbImpl implements ToRentService {
    private final ToRentRepository repository;

    @Override
    public ToRent getToRentById(Long id) {
        log.debug("Getting toRent by id: {}", id);
        return repository.findById(id).orElseThrow(
                () -> new ToRentNotFoundException(String.format("toRent %d not found", id)));
    }
}
