package info.josealonso.organizationservice.mapper;

import info.josealonso.organizationservice.dto.OrganizationDto;
import info.josealonso.organizationservice.entity.Organization;

public class OrganizationMapper {

    public static OrganizationDto mapToOrganizationDto(Organization organization) {
        return OrganizationDto.builder()
                .organizationName(organization.getOrganizationName())
                .organizationDescription(organization.getOrganizationDescription())
                .organizationCode(organization.getOrganizationCode())
                .createdAt(organization.getCreatedAt())
                .build();
    }

    public static Organization mapToOrganization(OrganizationDto organizationDto) {
        return Organization.builder()
                .organizationName(organizationDto.getOrganizationName())
                .organizationDescription(organizationDto.getOrganizationDescription())
                .organizationCode(organizationDto.getOrganizationCode())
                .createdAt(organizationDto.getCreatedAt())
                .build();
    }
}
