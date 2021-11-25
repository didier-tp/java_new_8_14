package tp.j15_16_17;

public class MainClassForJPackageTestApp {

	public static void main(String[] args) {
		//JOptionPane.showMessageDialog(null, "welcome to my hello world tiny java app");
        System.out.println("welcome to my hello world tiny java app"); // with --win-console option of jpackage
        java.util.Scanner scanner =   new java.util.Scanner(System.in);
        System.out.println("Ecrire votre phrase:");
    	String phrase = scanner.nextLine();
    	System.out.println("phrase="+phrase);
    	System.out.println("PAUSE");
    	phrase = scanner.nextLine(); //pour finir
	}
}
