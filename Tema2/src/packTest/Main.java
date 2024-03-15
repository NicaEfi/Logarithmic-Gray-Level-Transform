package packTest;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Scanner;

import packWork.*;

public class Main {
    public static void main(String... args) throws Exception {
    	
        // Se citeste de la tastatura calea pentru imaginea sursa si pentru imaginea de destinatie
    	//Imagini pentru verificare aplicatie
    	//E:\dog2.bmp
    	//E:\dog1.bmp
    	//Incepe masurarea timpului pentru citire si instantiere
        long start1 = System.nanoTime();
        
        //Se foloseste scanner pentru ca utilizatorul sa poata introduce datele de la tastatura
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce-ti path-ul imaginii de procesat: ");
        String path= scan.nextLine(); //Citeste calea de la utilizator
        System.out.println("Introduce-ti acelasi path pentru(destinatie): ");
        
        // Se citeste imaginea
        // Se foloseste imaginea drept parametru in ProducerThread

        BufferedImage image = ImageIO.read(new File(path));
        String path2= scan.nextLine();
        
        //se formeaza legatura dintre Producer si Consumer
        Pipe pipe = new Pipe(1);
        
        //creeaza un ProduceThread cu ajutorul imaginii sursa si a altor date precum: pipe, latimea imaginii si calea de destinatie
        ProducerThread producer = new ProducerThread(image, pipe, image.getWidth(), path2);
        
        // Se porneste Producer Thread
        
        producer.start();
        
        //Se incheie masurarea timpului pentru citire si instantiere
        long end1 = System.nanoTime();
        
        //Se afiseaza date utile pentru monitorizarea performantei
        System.out.println("Timpul necesar pentru citirea informatiilor de identificare a fisierului si pentru instantierea producatorului: "+ (end1-start1)/ 1_000_000_000);
        
        // Deschide automat imaginea rezultata
       //openImage(path2);

    }
    
	    // Functie pentru a deschide imaginea cu ajutorul clasei Desktop
//	    private static void openImage(String path) {
//	        try {
//	            File file = new File(path);
//	            Desktop desktop = Desktop.getDesktop();
//	            desktop.open(file);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//
//	    }
}