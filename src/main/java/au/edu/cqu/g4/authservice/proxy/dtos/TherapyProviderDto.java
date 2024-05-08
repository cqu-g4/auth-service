package au.edu.cqu.g4.authservice.proxy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class TherapyProviderDto {
    private String id;
    private String userId;
    private List<TPService> services = new ArrayList<>();
}
