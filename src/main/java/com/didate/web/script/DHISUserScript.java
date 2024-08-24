package com.didate.web.script;

import com.didate.domain.DHISUser;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.DHISUserService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DHISUserScript {

    private static final Logger log = LoggerFactory.getLogger(DHISUserScript.class);

    private final DhisApiService<DHISUser> dhisUserApiService;
    private final DHISUserService dhisUserService;

    public DHISUserScript(DhisApiService<DHISUser> dhisUserApiService, DHISUserService dhisUserService) {
        this.dhisUserApiService = dhisUserApiService;
        this.dhisUserService = dhisUserService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling users API...");

        List<DHISUser> dhisUsers = dhisUserApiService.getData(project, "users", new TypeReference<Dhis2ApiResponse<DHISUser>>() {});

        boolean hasExistingUsers = dhisUserService.count() > 0;

        for (DHISUser user : dhisUsers) {
            TypeTrack typeTrack = determineTypeTrack(user.getId(), hasExistingUsers);
            dhisUserService.save(user.track(typeTrack));
        }

        log.info("Fetched users: {}", dhisUsers.size());
    }

    private TypeTrack determineTypeTrack(String userId, boolean hasExistingUsers) {
        if (!hasExistingUsers) {
            return TypeTrack.NONE;
        }

        return dhisUserService.exist(userId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
