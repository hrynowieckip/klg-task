package com.hrynowieckip.klgtask.service.db;

import com.hrynowieckip.klgtask.domain.model.Client;
import com.hrynowieckip.klgtask.domain.repository.ClientRepository;
import com.hrynowieckip.klgtask.exception.ClientNotFoundException;
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
class ClientServiceDbImplTest {
    @Mock
    ClientRepository repository;
    @InjectMocks
    ClientServiceDbImpl service;

    Long id;
    Client client;

    @BeforeEach
    void setUp() {
        id = 1L;
        client = new Client();
    }

    @Test
    void getClientById_returnsClient() {
        when(repository.findById(id)).thenReturn(Optional.of(client));
        Client clientById = service.getClientById(id);

        assertEquals(client, clientById);
        verify(repository).findById(id);
    }

    @Test
    void getClientById_throwException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> service.getClientById(id));
        verify(repository).findById(id);
    }
}
