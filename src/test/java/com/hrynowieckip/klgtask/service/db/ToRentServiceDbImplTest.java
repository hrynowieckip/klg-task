package com.hrynowieckip.klgtask.service.db;

import com.hrynowieckip.klgtask.domain.model.ToRent;
import com.hrynowieckip.klgtask.domain.repository.ToRentRepository;
import com.hrynowieckip.klgtask.exception.ToRentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToRentServiceDbImplTest {
    @Mock
    ToRentRepository repository;
    @InjectMocks
    ToRentServiceDbImpl service;

    Long id;
    ToRent toRent;

    @BeforeEach
    void setUp() {
        id = 1L;
        toRent = new ToRent();
    }

    @Test
    void getToRentById_returnsToRent() {
        when(repository.findById(id)).thenReturn(Optional.of(toRent));
        ToRent rentById = service.getToRentById(id);

        assertEquals(toRent, rentById);
        verify(repository).findById(id);
    }

    @Test
    void getToRentById_throwException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ToRentNotFoundException.class, () -> service.getToRentById(id));
        verify(repository).findById(id);
    }
}
