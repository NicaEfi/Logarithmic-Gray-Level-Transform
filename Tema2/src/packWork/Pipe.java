package packWork;

import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pipe {
    private BlockingQueue<BufferedImage> queue;  // Coada blocanta pentru transferul imaginilor dintre firul producator si cel consumator
    private int capacity;  // Capacitatea maxima a cozii

    public Pipe(int capacity) {
        this.queue = new LinkedBlockingQueue<>();  // Initializarea cozii cu o coada blocanta sincronizata
        
        this.capacity = capacity;  // Setarea capacitatii maxime a cozii
        System.out.println("A inceput un pipe");  // Mesaj de afisare la crearea obiectului Pipe
    }

    public void write(BufferedImage image) {
        queue.add(image);  // Adaugarea imaginii in coada
        
        System.out.println("S-a scris in Pipe");  // Mesaj de afisare la scrierea in coada
    }

    public BufferedImage read() throws InterruptedException {
        System.out.println("S-a citit din Pipe");  // Mesaj de afisare la citirea din coada
        
        return queue.take();  // Returnarea si eliminarea imaginii din coada (metoda este blocanta daca coada este goala)
    }
}
