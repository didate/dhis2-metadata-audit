package com.didate.web.script;

import com.didate.domain.DHISUser;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.DHISUserService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DHISUserScript {

    private static final Logger log = LoggerFactory.getLogger(DHISUserScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<DHISUser> dhisUserApiService;
    private final DHISUserService dhisUserService;

    public DHISUserScript(DhisApiService<DHISUser> dhisUserApiService, DHISUserService dhisUserService) {
        this.dhisUserApiService = dhisUserApiService;
        this.dhisUserService = dhisUserService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling users API...");

        boolean hasExistingUsers = dhisUserService.count() > 0;
        String lastUpdated = hasExistingUsers ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<DHISUser> dhisUsers = dhisUserApiService.getData(
            project,
            "users",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<DHISUser>>() {}
        );

        for (DHISUser user : dhisUsers) {
            TypeTrack typeTrack = determineTypeTrack(user.getId(), hasExistingUsers);
            user = user.track(typeTrack).project(project).lastLogin(user.getLastLogin() == null ? user.getCreated() : user.getLastLogin());

            if (typeTrack == TypeTrack.UPDATE) {
                dhisUserService.partialUpdate(user);
            } else {
                dhisUserService.save(user);
            }
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
