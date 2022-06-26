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

//@WebMvcTest(BarcodeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BarcodeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarcodeService barcodeService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void createBarcode() throws Exception {

       // mockMvc.perform(post("/barcode").param("memberId", "000000001")).andDo(print()).andExpect(status().isOk());
        //mockMvc.perform(post("/barcode").param("memberId", "00000000")).andDo(print()).andExpect(jsonPath("code").value("ERROR-E002")).andExpect(status().is4xxClientError());

        String url = "http://localhost:" + port + "/barcode";

        HttpEntity<BarcodeDto> request = new HttpEntity<>(new BarcodeDto("000000001"));
        ApiResponseDto responseEntity = testRestTemplate.postForObject(url, request, ApiResponseDto.class);
        ResponseEntity<ApiResponseDto> responseEntity2 = testRestTemplate.postForEntity(url, new BarcodeDto("000000001"), ApiResponseDto.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("memberId", "000000001");
        ResponseEntity<ApiResponseDto> responseEntity3 = testRestTemplate.postForEntity(url, hashMap, ApiResponseDto.class);


        System.out.println(responseEntity.toString());
        System.out.println(responseEntity2.toString());
        System.out.println(responseEntity3.toString());
        //assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //assertEquals(responseEntity.getBody()., HttpStatus.OK);
        //Barcode barcode = barcodeService.createBarcode(barcodeDto.getMemberId());
        //return new ResponseEntity<>(new ApiResponseDto<>("Success", "성공", barcode), HttpStatus.OK);

    }
}