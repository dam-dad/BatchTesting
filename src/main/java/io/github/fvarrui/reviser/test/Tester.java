package io.github.fvarrui.reviser.test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.maven.shared.invoker.MavenInvocationException;

import io.github.fvarrui.reviser.ui.Reviser;
import io.github.fvarrui.reviser.utils.FileUtils;
import io.github.fvarrui.reviser.utils.GitUtils;
import io.github.fvarrui.reviser.utils.MavenUtils;

public class Tester {
	
	public static void testAll(String input, File submissionsDir) throws Exception {
		
		Reviser.console.println("--- Ejecutando entrega: " + submissionsDir.getName());
		try {
			
			for (File submissionDir : Arrays.asList(submissionsDir.listFiles())) {
				test(input, submissionDir);
			}
			
		} catch (MavenInvocationException e) {
			
			if (e.getMessage().equals("compile failed")) throw new Exception("Fallo al compilar el proyecto (mvn compile). Revisa la versión de Java en las properties del pom.xml.");
			if (e.getMessage().equals("exec:java failed")) throw new Exception("Fallo al ejecutar el proyecto (mvn exec:java). Revisa la propiedad 'exec.mainClass' del fichero pom.xml.");
			throw e;
			
		}
		
	}

	private static void test(String input, File submittedFile) throws Exception {

		Reviser.console.println("--- Ejecutando: " + submittedFile.getName());

		// busca el directorio .git de forma recursiva 
		File gitFolder = FileUtils.find(submittedFile, ".git");
		if (gitFolder != null) {
			
			// hace un "git pull" si es un repo Git
			File repoFolder = gitFolder.getParentFile();
			Reviser.console.println("--- Haciendo pull al repositorio GIT en " + repoFolder.getName());
			GitUtils.pull(repoFolder);
			
		}
		
		// busca el proyecto maven de forma recursiva
		File pomFile = FileUtils.find(submittedFile, "pom.xml");
		if (pomFile != null) {
		
			// hace "mvn compile exec:java" si es un proyecto Maven
			Reviser.console.println("--- Encontrado fichero pom.xml en el proyecto. Se trata de un repositorio Maven: " + pomFile.getParentFile().getName());			
			testMaven(input, submittedFile, pomFile);
			
		} else {

			open(submittedFile);
			
		}
		
		Reviser.console.println("--- Test completado");

	}

	private static void testMaven(String input, File submittedFile, File pomFile) throws Exception {
		File mavenProject = pomFile.getParentFile();		
		Reviser.console.println("--- Compilando y ejecutando con Maven: " + mavenProject.getName());
		MavenUtils.compileAndExec(mavenProject, input);
	}

	private static void open(File file) throws IOException {
		Reviser.console.println("--- Abriendo " + file.getName() + " ...");
		Desktop.getDesktop().open(file);
	}

}