package com.manos.prototype.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.manos.prototype.security.UserDetailsImpl;

public class SecurityUtil {

	public static UserDetailsImpl getCurrentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return (UserDetailsImpl) authentication.getPrincipal();
		}
		return null;
	}
}
