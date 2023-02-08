package info.josealonso.organizationservice.controller;

import info.josealonso.organizationservice.dto.OrganizationDto;
import info.josealonso.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String message = "organization-service is working!!";
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @GetMapping("{organizationCode}")
    public ResponseEntity<OrganizationDto> getOrganizationByCode(@PathVariable String organizationCode) {
        OrganizationDto organizationDto = organizationService.getOrganizationByCode(organizationCode);
        return new ResponseEntity(organizationDto, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization(
            @RequestBody @NotNull OrganizationDto organizationDto) {

        OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);
        return new ResponseEntity(savedOrganization, HttpStatus.CREATED);
    }
}
