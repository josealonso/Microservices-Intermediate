package info.josealonso.organizationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.josealonso.organizationservice.dto.OrganizationDto;
import info.josealonso.organizationservice.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTests {

    public static final String BASE_URL = "http://localhost:8083/api/v1/organizations";
    public static final String MESSAGE = "organization-service is working!!";
    public static final String TEST_NAME = "test-name";
    public static final String CODE = "A23ED";
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrganizationServiceImpl organizationService;

    private OrganizationDto organizationDto;


    @BeforeEach
    public void setup() {
        organizationDto = OrganizationDto.builder()
                .id(2L)
                .organizationName(TEST_NAME)
                .organizationDescription("test-description")
                .organizationCode(CODE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getMessageTest() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(MESSAGE))
                .andDo(print());
    }

    @Test
    void getOrganizationByCodeTest() throws Exception {
        given(organizationService.getOrganizationByCode(CODE)).willReturn(organizationDto);

        this.mockMvc.perform(get(BASE_URL + "/" + CODE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(organizationDto.getId()))
                .andExpect(jsonPath("$.organizationCode").isString())
                .andExpect(jsonPath("$.organizationCode").value(organizationDto.getOrganizationCode()))
                .andExpect(jsonPath("$.organizationName").value(organizationDto.getOrganizationName()))
                .andExpect(jsonPath("$.organizationDescription").value(organizationDto.getOrganizationDescription()))
                // toString() is required
                .andExpect(jsonPath("$.createdAt").value(organizationDto.getCreatedAt().toString()))
                .andDo(print());

        verify(organizationService).getOrganizationByCode(CODE);
    }

    @Test
    void saveOrganizationTest() throws Exception {

        OrganizationDto savedOrganizationDto = OrganizationDto.builder()
                .id(2L)
                .organizationName(TEST_NAME)
                .organizationDescription("test-description")
                .organizationCode(CODE)
                .createdAt(LocalDateTime.now())
                .build();

        given(organizationService.saveOrganization(organizationDto)).willReturn(savedOrganizationDto);

        this.mockMvc.perform(post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(organizationDto)))
                .andExpect(status().isCreated())
                .andDo(print());

//        verify(organizationService).saveOrganization(organizationDto);
    }

    @Test
    void saveOrganizationFailsBadRequestTest() throws Exception {

        given(organizationService.saveOrganization(any()))
                .willThrow(HttpClientErrorException.BadRequest.class);

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())   // This expectation is not working !!
                .andDo(print());
    }

    @Test
    void saveOrganizationFailsMethodNotAllowedTest() throws Exception {
        given(organizationService.saveOrganization(organizationDto)).willReturn(organizationDto);

        this.mockMvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }
}










