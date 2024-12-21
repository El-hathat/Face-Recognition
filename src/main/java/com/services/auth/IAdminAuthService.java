package com.services.auth;

/*
*  Application auth service
* */

public interface IAdminAuthService {

    boolean login(String username, String password);

    boolean isLogged();

}
