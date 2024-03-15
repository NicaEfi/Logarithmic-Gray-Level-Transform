package packWork;
import java.awt.image.BufferedImage;

public class ProducerThread extends ImageThread {
    ConsumerThread consumer;  // Referinta catre obiectul ConsumerThread
    String destination;  // Calea catre destinatia imaginii procesate

    public ProducerThread(BufferedImage image, Pipe pipe, int initialWidth, String destination) {
        // Initializarea clasei mostenite (ImageThread) si popularea variabilelor clasei curente
        super(pipe);
        this.rgbImage = image;
        System.out.println("A inceput ProducerThread");
        this.initialWidth = initialWidth;
        this.destination = destination;
    }

    public synchronized void run() {
        long start1 = System.nanoTime();

        while (super.getFinish() < 4) {
            if (super.getFinish() != 0) {
                produce(0, 0, initialWidth);  // Producerea unei portiuni de imagine
                synchronized (consumer) {
                    consumer.notify();  // Semnalarea consumatorului că o porțiune este gata pentru procesare
                }
            }

            if (super.getFinish() == 0) {
                produce(0, 0, initialWidth);  // Producerea primei portiuni de imagine
                this.consumer = new ConsumerThread(this, this.destination);  // Initializarea consumatorului
                consumer.start();  // Pornirea consumatorului
            }

            synchronized (consumer) {
                try {
                    consumer.wait();  // Asteptarea finalizarii consumatorului
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("ProducerThread a fost reluat");
        }

        synchronized (consumer) {
            consumer.notify();  // Semnalarea consumatorului
        }

        long end1 = System.nanoTime();
        System.out.println("Timpul necesar pentru citirea intregii imagini: " + (end1 - start1) / 1_000_000_000);
    }

    @Override
    public void convertToGrayscale(BufferedImage image) {
        // Metoda abstracta suprascrisa
    }

    @Override
    public void writeToFile(BufferedImage image, int i) {
        // Metoda abstracta suprascrisa
    }
}
