package packWork;  
import java.awt.*;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
import javax.imageio.ImageIO;  

public class ConsumerThread extends ImageThread {  // Defineste clasa ConsumerThread care extinde clasa ImageThread

    ProducerThread producer;  // Referinta catre obiectul ProducerThread
    String destination;  // Calea destinatiei fisierului de iesire

    public ConsumerThread(ProducerThread producer, String destination) { 
        // Constructor care instantiaza obiectul ConsumerThread
        super(producer.pipe);  // Apelarea constructorului parinte cu obiectul Pipe al producatorului
        this.rgbImage = producer.rgbImage;  // Initializarea imaginii color cu cea a producatorului
        this.grayImage = producer.grayImage;  // Initializarea imaginii la nivel de gri cu cea a producatorului
        this.producer = producer;  // Initializarea referintei catre producator
        this.destination = destination;  // Initializarea caii destinatie

        System.out.println("Pornire ConsumerThread");  // Afisarea unui mesaj la pornirea firului de executie
    }

    public int sum(int... args) {  
        // Functie folosita la conversie 
        // Utilizare varargs
        int sum = 0;  // Initializarea variabilei pentru suma
        for (int i : args)
            sum += i;  // Adunarea argumentelor folosind varargs
        return sum;  // Returnarea sumei
    }

    // Suprascrierea metodei pentru conversia imaginii la nivel de gri
    @Override
    public synchronized void convertToGrayscale(BufferedImage image) {
        long start1 = System.nanoTime();  // Memorarea momentului de start al procesarii

        int width = grayImage.getWidth();  // Obtinerea latimii imaginii la nivel de gri
        int height = grayImage.getHeight();  // Obtinerea inaltimii imaginii la nivel de gri

        System.out.println("Dimensiuni imagine: " + width + " x " + height);  // Afisarea dimensiunilor imaginii

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = grayImage.getRGB(x, y);  // Obtine culoarea pixelului

                int r = (color >> 16) & 0xff;  // Componenta rosie a culorii
                int g = (color >> 8) & 0xff;   // Componenta verde a culorii
                int b = color & 0xff;           // Componenta albastra a culorii

                // Aplicarea transformarii logaritmice a nivelurilor de gri
                int gray = (int) (255 * Math.log(1 + r) / Math.log(256));

                color = (gray << 16) | (gray << 8) | gray;  // Construirea noii culori la nivel de gri
                grayImage.setRGB(x, y, color);  // Setarea noii culori pentru pixelul curent
            }
        }

        long end1 = System.nanoTime();  // Memorarea momentului de final al procesarii
        System.out.println("Timp prelucrare un sfert de imagine: " + (end1 - start1) / 1_000_000_000);  // Afisarea timpului de prelucrare
        notify();  // Notificarea producatorului - procesul este finalizat
    }

    // Suprascrierea metodei pentru scrierea imaginii în fișier
    @Override
    public void writeToFile(BufferedImage image, int i) {
        // Creare fisier in locatia de destinatie, oferita de catre utilizator
        long start1 = System.nanoTime();  // Memorarea momentului de start al procesarii
        if (i == 473) {
            try {
                ImageIO.write(grayImage, "BMP", new File(this.destination));  // Scrierea imaginii procesate in fisier
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedImage img1 = ImageIO.read(new File(this.destination));
                if (img1 == null) {
                    // Creare un nou BufferedImage in cazul unei imagini NULL
                    img1 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                }

                int width = img1.getWidth() + grayImage.getWidth();
                int height = img1.getHeight();
                BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                Graphics g = combined.getGraphics();
                g.drawImage(img1, 0, 0, null);
                g.drawImage(grayImage, img1.getWidth(), 0, null);
                ImageIO.write(combined, "BMP", new File(this.destination));

                g.dispose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        long end1 = System.nanoTime();  // Memorarea momentului de final al procesarii
        System.out.println("Timp scriere un sfert de imagine in fisier: " + (end1 - start1) / 1_000_000_000);  // Afisarea timpului de scriere
    }

    @Override
    public synchronized void run() {
        long start1 = System.nanoTime();  // Memorarea momentului de start al procesarii

        while (super.getFinish() < 4) {
            super.setPipe(producer.getPipe());
            super.setFinish(producer.getFinish());
            super.setRgbImage(producer.getRgbImage());

            try {
                consume();
                wait();  // Asteptarea producerii urmatorului segment
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("ConsumerThread a fost reluat");  // Afisarea unui mesaj la reluarea firului de executie
        }

        long end1 = System.nanoTime();  // Memorarea momentului de final al procesarii
        System.out.println("Timp necesar pentru prelucrarea si scrierea intregii imagini: " + (end1 - start1) / 1_000_000_000);  // Afisarea timpului total
    }
}
