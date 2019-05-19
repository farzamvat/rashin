import com.emersun.rashin.Application;
import com.emersun.rashin.collections.Permission;
import com.emersun.rashin.dto.RoleDto;
import com.emersun.rashin.panel.agent.dtos.AgentDto;
import com.emersun.rashin.utils.TestDataUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class AgentAdminIntegTest extends AbstractMongoDbPurging {
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private TestDataUtil testDataUtil;

    @Test
    public void success_create_agent() {
        RoleDto roleDto = testDataUtil.createRole("OPERATOR",new HashSet<>(new ArrayList<>(Arrays.asList(Permission.CURRENT_VAS_SUB))));
        AgentDto agentDto = new AgentDto();
        agentDto.setUsername("farzamvat");
        agentDto.setRoleId(roleDto.getId());
        agentDto.setPassword("12345678");
        webClient.post()
                .uri("/api/v1/admin/agents")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION,getToken())
                .body(BodyInserters.fromObject(agentDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.apiKey").isNotEmpty()
                .jsonPath("$.role.name").isEqualTo(roleDto.getName())
                .jsonPath("$.username").isNotEmpty();
    }
}
