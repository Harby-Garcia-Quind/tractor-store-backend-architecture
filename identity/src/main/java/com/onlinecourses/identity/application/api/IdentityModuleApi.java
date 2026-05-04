package com.onlinecourses.identity.application.api;

import java.util.UUID;

public interface IdentityModuleApi {

    boolean existsActiveUser(UUID userId);

}
