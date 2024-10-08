/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.caelum.vraptor.config;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Basic implementation of an application configuration.<br>
 *
 * @author guilherme silveira
 * @author lucas cavalcanti
 * @since 3.0.3
 */
@RequestScoped
public class ApplicationConfiguration implements Configuration {

	private final HttpServletRequest request;
	
	/** 
	 * @deprecated CDI eyes only
	 */
	protected ApplicationConfiguration() {
		this(null);
	}

	@Inject
	public ApplicationConfiguration(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Returns the application path, including the http protocol.<br>
	 * One can implement this method to return a fixed http/ip prefix.
	 */
	@Override
	public String getApplicationPath() {
		return request.getScheme() + "://" + request.getServerName()
			+ (request.getServerPort() != 80? ":" + request.getServerPort() : "")
			+ request.getContextPath();
	}

}
