package com.kakaopay.server.store;

import com.kakaopay.server.member.MemberController;
import com.kakaopay.server.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StoreController.class)
@MockBean(JpaMetamodelMappingContext.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/store").param("name", "abc-mart").param("category", String.valueOf(StoreCategory.A))).andDo(print()).andExpect(status().isOk());
        mockMvc.perform(post("/store").param("name", "abc-mart").param("category", "D")).andDo(print()).andExpect(status().is4xxClientError());


    }
}