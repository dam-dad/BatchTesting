package io.github.fvarrui.reviser.testers;

import static org.apache.commons.io.FilenameUtils.isExtension;

import java.io.File;
import java.util.Arrays;

import io.github.fvarrui.reviser.ui.Reviser;

public class BashScript extends Tester {

	private boolean isScript(File file) {
		return file.isFile() && isExtension(file.getName().toLowerCase(), "sh");
	}

	public boolean matches(File submissionDir) {
		return Arrays.asList(submissionDir.listFiles())
				.stream()
				.anyMatch(this::isScript);
	}

	@Override
	public void test(File submissionDir) throws Exception {
		Reviser.console.println("Abriendo BASH en " + submissionDir);
		new ProcessBuilder()
				.command("cmd", "/c", "start /max bash")
				.directory(submissionDir)
				.start();
		Reviser.console.println("¡Completado!");
	}
	
	@Override
	public String toString() {
		return "BASH script";
	}

}
