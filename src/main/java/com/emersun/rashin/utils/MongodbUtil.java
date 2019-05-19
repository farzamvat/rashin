package com.emersun.rashin.utils;

import com.emersun.rashin.collections.*;
import com.emersun.rashin.dto.RoleDto;
import com.emersun.rashin.exceptions.BadRequestException;
import com.emersun.rashin.panel.agent.dtos.AgentAddDto;
import com.emersun.rashin.panel.agent.dtos.AgentDto;
import com.emersun.rashin.panel.agent.repositories.AgentRepository;
import com.emersun.rashin.panel.agent.services.AgentService;
import com.emersun.rashin.panel.services.RoleService;
import com.emersun.rashin.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;


@Component
public class MongodbUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AgentService agentService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Value("${panel.admin.username}")
    private String username;
    @Value("${panel.admin.password}")
    private String password;

    public void insertAdminRole() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ADMIN");
        roleDto.setPermissions(new HashSet<>(Arrays.asList(Permission.values())));
        roleRepository.findByName(roleDto.getName())
                .switchIfEmpty(Mono.just(new Role(roleDto)))
                .flatMap(roleRepository::save)
                .subscribe();
    }

    public void insertAdminUserAsAgent() {
        roleRepository.findByName("ADMIN")
                .switchIfEmpty(Mono.error(new BadRequestException("admin role not found")))
                .map(role -> new AgentAddDto(username,password,role.getId()))
                .flatMap(agentService::create)
                .subscribe();
    }

    public void dropCollections() {
        mongoOperations.dropCollection(Agent.class);
        mongoOperations.dropCollection(AgentApplication.class);
        mongoOperations.dropCollection(Role.class);
        mongoOperations.dropCollection(User.class);
    }
    public void createCollections() {
        mongoOperations.createCollection(Agent.class, CollectionOptions.empty().size(10000000));
        mongoOperations.createCollection(AgentApplication.class, CollectionOptions.empty().size(10000000));
        mongoOperations.createCollection(Role.class, CollectionOptions.empty().size(10000000));
        mongoOperations.createCollection(User.class, CollectionOptions.empty().size(10000000));
    }
}
