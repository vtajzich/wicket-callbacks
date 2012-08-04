package org.agileworks.wicket.callbacks.ui;

import org.apache.wicket.protocol.http.WebApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

public final class ApplicationVersion {

	private static final ApplicationVersion SINGLETON = new ApplicationVersion();
	private String version;
	private String revision;

	private ApplicationVersion() {
		init();
	}

	private void init() {
		final Manifest manifest = getManifest();
		if (null != manifest) {
			version = manifest.getMainAttributes().getValue("Implementation-Version");
			revision = manifest.getMainAttributes().getValue("SCM-Revision");
		}
	}

	/**
	 * @return
	 */
	private Manifest getManifest() {
		try {
			final InputStream resourceAsStream = WebApplication.get().getServletContext()
					.getResourceAsStream("/META-INF/MANIFEST.MF");
			if (resourceAsStream == null) {
				return null;
			}
			return new Manifest(resourceAsStream);
		} catch (final IOException e) {
			return null;
		}
	}

	public static final String getVersion() {
		return SINGLETON.version;
	}

	public static final String getRevision() {
		return SINGLETON.revision;
	}
}
