import com.emersun.rashin.Application;
import com.emersun.rashin.collections.Permission;
import com.emersun.rashin.collections.Role;
import com.emersun.rashin.panel.agent.repositories.AgentRepository;
import com.emersun.rashin.security.AdminAuthenticationManager;
import com.emersun.rashin.security.AuthorizedUser;
import com.emersun.rashin.security.JWTUtil;
import com.emersun.rashin.security.JwtTokenProvider;
import com.emersun.rashin.utils.MongodbUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class AbstractMongoDbPurging {

    @Autowired
    private MongodbUtil mongodbUtil;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AgentRepository agentRepository;
    @SpyBean
    private AdminAuthenticationManager authenticationManager;
    @Value("${panel.admin.username}")
    private String adminUsername;
    @Before
    public void before() {
        mongodbUtil.dropCollections();
        mongodbUtil.createCollections();
        Role role = new Role();
        role.setPermissions(new HashSet<>(new ArrayList<>(Arrays.asList(Permission.values()))));
        role.setName("ADMIN");
        role.setId("customId");
        AuthorizedUser authorizedUser = AuthorizedUser.builder()
                .role(role)
                .username(adminUsername)
                .password("aksdjkhadshasd")
                .build();
        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class)))
                .thenReturn(Mono.just(new UsernamePasswordAuthenticationToken(authorizedUser,null,authorizedUser.getAuthorities())));
    }

    protected String getToken() {
        return "Bearer " + jwtUtil.doGenerateToken(adminUsername);
    }

}
