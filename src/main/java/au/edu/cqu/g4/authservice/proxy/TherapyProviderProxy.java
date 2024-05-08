package au.edu.cqu.g4.authservice.proxy;

import au.edu.cqu.g4.authservice.proxy.dtos.TherapyProviderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "THERAPY-PROVIDER-SERVICE", path = "therapy-provider")
public interface TherapyProviderProxy {
    @PostMapping("/")
    TherapyProviderDto create(@RequestBody TherapyProviderDto dto);
}
