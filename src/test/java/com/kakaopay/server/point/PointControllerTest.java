package com.kakaopay.server.point;

import com.kakaopay.server.store.StoreCategory;
import com.kakaopay.server.store.StoreController;
import com.kakaopay.server.store.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PointController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointService pointService;

    @Test
    void savePoint() throws Exception {
        mockMvc.perform(post("/point/save").param("storeId", String.valueOf(1)).param("barcode", "1071637887").param("price", String.valueOf(400L))).andDo(print()).andExpect(status().isOk());


    }

    @Test
    void spendPoint() {
    }

    @Test
    void getHistoryByTerm() {
    }
}