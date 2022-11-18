package br.com.sicredi.votacao.integration.feign;

import br.com.sicredi.votacao.integration.feign.response.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-user-info", url = "${user-info.feign.url}")
public interface ValidadorCPFFeign {

    @GetMapping(value = "/users/{cpf}")
    ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable String cpf);
}
