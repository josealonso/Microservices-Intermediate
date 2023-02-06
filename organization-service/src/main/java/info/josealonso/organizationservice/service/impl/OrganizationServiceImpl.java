package info.josealonso.organizationservice.service.impl;

import info.josealonso.organizationservice.dto.OrganizationDto;
import info.josealonso.organizationservice.entity.Organization;
import info.josealonso.organizationservice.mapper.OrganizationMapper;
import info.josealonso.organizationservice.repository.OrganizationRepository;
import info.josealonso.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization savedOrganization = organizationRepository.save(OrganizationMapper.mapToOrganization(organizationDto));
        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}
