package org.mycontrib.tp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class ProcessApiApp {
	
	public static void pause(long nbMs) {
		try {
			Thread.currentThread().sleep(nbMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//startNodePad();
		//displayingProcess();
		//destroyProcessByAskedId();
		startAndDestroyProcess();
		//startAndWaitingProcess();
	}
	
	public static String processHandleAsString(ProcessHandle ph) {
		return "pid="+ph.pid()+" - "+ph.info().toString();
				//+ "\n \t children:"  
				//+ ph.children().map((ProcessHandle dph) -> String.valueOf(dph.pid())).collect(Collectors.joining(" "));
	}
	
	public static void displayingProcess() {
		ProcessHandle.allProcesses().forEach(ph -> System.out.println(processHandleAsString(ph)));
	}
	
	public static void startNodePad() {
		ProcessBuilder builder = new ProcessBuilder("notepad.exe");//de java.lang depuis java 1.5
		try {
			Process process = builder.start();
			System.out.println("process info=" + process.info());
			System.out.println("pid of process=" + process.pid());
			process.descendants().forEach(processHandle -> System.out.println("descendant pid="+processHandle.pid()));
			//NB: the launched process may be a shell starting another subprocess (descendant)
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public static void destroyProcessByAskedId() {
		long pid=Long.parseLong(JOptionPane.showInputDialog(null, "pid"));
		destroyProcessById(pid);
	}
	
	public static void destroyProcessById(long pid) {
		Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(pid);
		optionalProcessHandle.ifPresentOrElse(processHandle -> { 
			                                      processHandle.destroy();
			                                      if(processHandle.isAlive()) {
			                                    	  processHandle.destroyForcibly();
			                                      }
			                                      if(!processHandle.isAlive()) {
			                                    	  System.out.println("process of pid="  +pid + "was stopped");
			                                      }
			                                   }, 
		
				                              ()-> System.out.println("no process with pid="+pid));
	}
	
	public static void startAndDestroyProcess() {
		//ProcessBuilder builder = new ProcessBuilder("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		ProcessBuilder builder = new ProcessBuilder("notepad.exe");
		try {
			Process process = builder.start();
			System.out.println("info of direct sub process=" + process.info().toString());
			//pause(50);//50ms
			//NB: appeler .info() ou bien faire une petite pause permet de voir le sous processus descendant
			Long descendantPid = process.descendants().findFirst().map(dph -> dph.pid()).orElse(null);
			System.out.println("pid of direct sub process=" + process.pid());
			System.out.println("descendantPid=" + descendantPid);
			pause(3000);//attendre 3s avant arrêter processus
			if(descendantPid!=null)
				destroyProcessById(descendantPid);
			
			destroyProcessById(process.pid());
			
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	
	public static void startAndWaitingProcess() {
		ProcessBuilder builder = new ProcessBuilder();
		/*
		final String javaHome = "C:\\Program Files\\Java\\jdk-11.0.4" ; // " " need around directory name with space 
		final String javaCmdeWithQuote = "\"" + javaHome+"\\bin\\java.exe" +"\"";
		//final String cmde = javaCmdeWithQuote + " -version";
		final String cmde = javaCmdeWithQuote+" -cp .\\target\\myProcessApp.jar org.mycontrib.tp.MySubProcess";
		System.out.println("cmde="+cmde);
		//builder.command("cmd.exe", "/c", cmde); // ok , "/c" = terminate after this run
		*/
		//NB: .\\target\\myProcessApp.jar will be ready after mvn install or ...
		builder.command("java","-cp",".\\target\\myProcessApp.jar", "org.mycontrib.tp.MySubProcess");//ok with java in PATH
		
		boolean isStopped = false;
		try {
			Process process = builder.start();
			System.out.println("pid of process=" + process.pid());
			BufferedReader outputReaderOfSubProcess = new BufferedReader(new InputStreamReader(process.getInputStream()));
			System.out.println("output of process (first line) =" + outputReaderOfSubProcess.readLine());
			isStopped=process.waitFor(3, TimeUnit.SECONDS);
			if(isStopped) {
				System.out.println("process is terminated");
			}else {
			   System.out.println("process not terminated after 3s");
			   isStopped=process.waitFor(3, TimeUnit.SECONDS);//re-wait
			}
			if (isStopped) {
				System.out.println("process is stopped with exit value="+process.exitValue());
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

}
