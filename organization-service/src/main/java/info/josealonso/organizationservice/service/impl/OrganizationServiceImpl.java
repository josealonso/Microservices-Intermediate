package info.josealonso.organizationservice.service.impl;

import info.josealonso.organizationservice.dto.OrganizationDto;
import info.josealonso.organizationservice.entity.Organization;
import info.josealonso.organizationservice.exception.ResourceNotFoundException;
import info.josealonso.organizationservice.mapper.OrganizationMapper;
import info.josealonso.organizationservice.repository.OrganizationRepository;
import info.josealonso.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization savedOrganization = OrganizationMapper.mapToOrganization(organizationDto);
        Organization savedOrganization2 = organizationRepository.save(savedOrganization);
        return OrganizationMapper.mapToOrganizationDto(savedOrganization2);
    }

    public Organization saveOrganizationNoDto(Organization organization) throws ResourceNotFoundException {
        Optional<Organization> savedOrganization = organizationRepository.
                findByOrganizationCode(organization.getOrganizationCode());
        if (savedOrganization.isPresent()) {
            throw new ResourceNotFoundException("Organization already exists");
        }
        return organizationRepository.save(organization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization2 = organizationRepository
                .findByOrganizationCode(organizationCode).orElseThrow();
        return OrganizationMapper.mapToOrganizationDto(organization2);
    }
}
