package info.josealonso.organizationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.josealonso.organizationservice.dto.OrganizationDto;
import info.josealonso.organizationservice.entity.Organization;
import info.josealonso.organizationservice.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @ExtendWith(MockitoExtension.class)
//@TestPropertySource("/application-test.properties")
//@AutoConfigureMockMvc
//@SpringBootTest
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTests {

    public static final String BASE_URL = "http://localhost:8083/api/v1/organizations";
    public static final String MESSAGE = "organization-service is working!!";
    public static final String TEST_NAME = "test-name";
    private static MockHttpServletRequest request;
    public final String CODE = "A23ED";
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrganizationServiceImpl organizationService;

    private OrganizationDto organizationDto;

//    @BeforeAll
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new OrganizationController(organizationService)).build();
//    }

    @BeforeEach
    public void setup() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new OrganizationController(organizationService)).build();
        organizationDto = OrganizationDto.builder()
                .id(2L)
                .organizationName(TEST_NAME)
                .organizationDescription("test-description")
                .organizationCode(CODE)
                .createdAt(LocalDateTime.now())
                .build();

        request = new MockHttpServletRequest();
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
                .andDo(print());

        verify(organizationService).getOrganizationByCode(CODE);
    }

    @Test
    void saveOrganizationTest() throws Exception {

        given(organizationService.saveOrganization(organizationDto)).willReturn(organizationDto);

        this.mockMvc.perform(post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(organizationDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andDo(print());

        verify(organizationService).saveOrganization(organizationDto);
    }

}










