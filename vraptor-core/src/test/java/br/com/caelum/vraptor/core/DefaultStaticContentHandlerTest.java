/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.caelum.vraptor.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.io.File;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DefaultStaticContentHandlerTest {

	@Rule
	public TemporaryFolder tmpdir = new TemporaryFolder();

	@Mock private HttpServletRequest request;
	@Mock private ServletContext context;
	private File file;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		file = tmpdir.newFile();
	}

	@Test
	public void returnsTrueForRealStaticResources() throws Exception {
		String key = file.getAbsolutePath();
		when(request.getRequestURI()).thenReturn("/contextName/" +key);
		when(request.getContextPath()).thenReturn("/contextName/");
		when(context.getResource(key)).thenReturn(file.toURI().toURL());

		boolean result = new DefaultStaticContentHandler(context).requestingStaticFile(request);
		assertThat(result, is(equalTo(true)));
	}
	
	@Test
	public void returnsTrueForRealStaticResourcesWithQueryString() throws Exception {
		String key = file.getAbsolutePath();
		when(request.getRequestURI()).thenReturn("/contextName/" + key + "?jsesssionid=12lkjahfsd12414");
		when(request.getContextPath()).thenReturn("/contextName/");
		when(context.getResource(key)).thenReturn(file.toURI().toURL());

		boolean result = new DefaultStaticContentHandler(context).requestingStaticFile(request);
		assertThat(result, is(equalTo(true)));
	}
	
	@Test
	public void returnsTrueForRealStaticResourcesWithJSessionId() throws Exception {
		String key = file.getAbsolutePath();
		when(request.getRequestURI()).thenReturn("/contextName/" + key + ";jsesssionid=12lkjahfsd12414");
		when(request.getContextPath()).thenReturn("/contextName/");
		when(context.getResource(key)).thenReturn(file.toURI().toURL());

		boolean result = new DefaultStaticContentHandler(context).requestingStaticFile(request);
		assertThat(result, is(equalTo(true)));
	}

	@Test
	public void returnsFalseForNonStaticResources() throws Exception {
		String key = "thefile.xml";
		when(request.getRequestURI()).thenReturn("/contextName/" + key);
		when(request.getContextPath()).thenReturn("/contextName/");
		when(context.getResource(key)).thenReturn(null);

		boolean result = new DefaultStaticContentHandler(context).requestingStaticFile(request);
		assertThat(result, is(equalTo(false)));
	}
}
