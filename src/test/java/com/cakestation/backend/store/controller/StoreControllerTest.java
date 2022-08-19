package com.cakestation.backend.store.controller;

import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import com.cakestation.backend.store.fixture.StoreFixture;
import com.cakestation.backend.store.service.StoreService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StoreService storeService;

    static final String MYSQL_IMAGE = "mysql:8";
    static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(MYSQL_IMAGE);

    @BeforeAll
    static void beforeAll() {
        MY_SQL_CONTAINER.start();

    }

    @AfterAll
    static void afterAll() {
        MY_SQL_CONTAINER.stop();
    }

    @Test
    public void 가게_조회() throws Exception {
        CreateStoreDto createStoreDto = StoreFixture.getStoreDto();
        storeService.saveStore(createStoreDto);
        Store store = storeService.findStoreById(1L);

        String uri = "/api/stores/1";
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
