package com.team.app.service;

import com.team.app.domain.AdminUser;

public interface LoginService {

	AdminUser getLoginUser(String username, String password) throws Exception;

	AdminUser getUserByEmail(String email) throws Exception;

	String getPasswordResetMessage(AdminUser admin)throws Exception;

	AdminUser setGeneratedPwd(AdminUser adminUser)throws Exception;

	AdminUser getUserById(String id)throws Exception;

}
