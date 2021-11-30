package tp.j14;

import java.time.Duration;
import jdk.jfr.consumer.RecordingStream;//amélioré en v14
//https://openjdk.java.net/jeps/349

public class LiveMonitoring {

	public static void main(String[] args) {
		try (var rs = new RecordingStream()) {
		       rs.enable("jdk.CPULoad").withPeriod(Duration.ofMillis(500));
		       rs.onEvent("jdk.CPULoad", event -> {
		         System.out.println(event.getFloat("machineTotal"));
		       });
		       rs.start();
		    }

	}

}
