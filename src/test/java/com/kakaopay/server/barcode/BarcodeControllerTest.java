package com.kakaopay.server.barcode;

import com.kakaopay.server.api.ApiResponseDto;
import com.kakaopay.server.barcode.dto.BarcodeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BarcodeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
class BarcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarcodeService barcodeService;



    @Test
    void 바코드발급() throws Exception {
        //정상적인 회원번호로 발급할 때
        mockMvc.perform(post("/barcode").param("memberId", "000000001")).andDo(print()).andExpect(status().isOk());

        //비정상적인 회원번호로 발급할 때 -> 에러코드 ERROR-E002 발생
        mockMvc.perform(post("/barcode").param("memberId", "00000000")).andDo(print()).andExpect(jsonPath("code").value("ERROR-E002")).andExpect(status().is4xxClientError());

    }
}