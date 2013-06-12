package com.hartveld.commons.web.jetty;

import javax.servlet.ServletContext;

import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;

import com.google.inject.name.Names;

class ShiroModule extends ShiroWebModule {

	public ShiroModule(final ServletContext servletContext) {
		super(servletContext);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configureShiroWeb() {
		try {
			bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
		} catch (final NoSuchMethodException e) {
			addError(e);
		}

		bindRealm().to(UserValidator.class);
		bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/login");

		addFilterChain("/web/**", ANON);

		addFilterChain("/version", ANON);

		addFilterChain("/logout", LOGOUT);
		addFilterChain("/login", AUTHC);

		addFilterChain("/demo/**", ANON);
		addFilterChain("/secured/**", AUTHC_BASIC);

		addFilterChain("/**", AUTHC);
	}

}
