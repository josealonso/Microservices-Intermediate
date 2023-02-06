package info.josealonso.organizationservice.service;

import info.josealonso.organizationservice.dto.OrganizationDto;
import info.josealonso.organizationservice.entity.Organization;
import info.josealonso.organizationservice.exception.ResourceNotFoundException;
import info.josealonso.organizationservice.repository.OrganizationRepository;
import info.josealonso.organizationservice.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

// @ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrganizationServiceTests {

    public static final String TEST_CODE = "test-code";
    public static final String NON_EXISTING_CODE = "non-existing-code";
    public static final String TEST_NAME = "test-name";

    @Mock
    private OrganizationRepository organizationRepository;
    @InjectMocks
    private OrganizationServiceImpl organizationService;

    private Organization organization;
    private OrganizationDto organizationDto;

    @BeforeEach
    public void setup() {
        organization = Organization.builder()
                .id(2L)
                .organizationName(TEST_NAME)
                .organizationDescription("test-description")
                .organizationCode(TEST_CODE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    // Testing "saveOrganization()" causes a Mockito error
    @Test
    void saveOrganizationNoDtoTest() throws ResourceNotFoundException {
        given(organizationRepository.findByOrganizationCode(organization.getOrganizationCode()))
                .willReturn(Optional.empty());
        given(organizationRepository.save(organization))
                .willReturn(organization);

        Organization savedOrganization = organizationService.saveOrganizationNoDto(organization);
        assertThat(savedOrganization).isNotNull();
        assertThat(savedOrganization.getOrganizationName()).isEqualTo(TEST_NAME);
    }

    @Test
    void saveOrganizationNoDto_ThrowsExceptionTest() throws ResourceNotFoundException {
        given(organizationRepository.findByOrganizationCode(organization.getOrganizationCode()))
                .willReturn(Optional.of(organization));

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            organizationService.saveOrganizationNoDto(organization);
        });
    }

    @Test
    void getOrganizationByCodeTest() {
        given(organizationRepository.findByOrganizationCode(TEST_CODE))
                .willReturn(Optional.ofNullable(organization));
        OrganizationDto retrievedOrganization = organizationService.getOrganizationByCode(TEST_CODE);
        assertThat(retrievedOrganization).isNotNull();
        assertThat(retrievedOrganization.getOrganizationName()).isEqualTo(TEST_NAME);
    }

    @Test
    void getOrganizationByCode_ThrowsException_Test() {
        given(organizationRepository.findByOrganizationCode(organization.getOrganizationCode()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> {
            organizationService.getOrganizationByCode(NON_EXISTING_CODE);
        });
    }
}
