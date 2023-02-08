package info.josealonso.organizationservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDto {
    private Long id;
    private String organizationName;
    private String organizationDescription;
    @NotNull
    private String organizationCode;
    private LocalDateTime createdAt;
}
