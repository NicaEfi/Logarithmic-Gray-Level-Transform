package packWork;  

public interface ImageProcessor { 

    void produce(int x, int y, int initialWidth);  // Metoda pentru a produce imaginea

    void consume() throws InterruptedException;  // Metoda pentru a consuma imaginea, poate arunca exceptie de intrerupere
}
