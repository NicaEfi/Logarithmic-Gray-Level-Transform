package packWork;  

import java.awt.image.BufferedImage;  

public abstract class ImageThread extends Thread implements ImageProcessor {
    protected BufferedImage rgbImage;  // Imaginea RGB
    protected BufferedImage grayImage;  // Imaginea in tonuri de gri
    protected Pipe pipe;  // Conducta pentru comunicare intre firele de executie
    protected int finish;  // Indicator pentru terminarea prelucrarii imaginii
    protected int initialWidth;  // Latimea initiala a imaginii
    // Bloc static
    static {
        System.out.println("S-a rulat static block");
    }
    // Bloc de initializare
    {
        System.out.println("S-a rulat block-ul de initializare!");
    }

    public ImageThread(Pipe pipe) {  
    	
    	// Constructorul 
        System.out.println("A pornit ImageThread ");
        this.pipe = pipe;
        this.finish = 0;
    }
    
    @Override
    public void produce(int x, int y, int initialWidth) {
        int height = rgbImage.getHeight();
        BufferedImage portion = rgbImage.getSubimage(0, 0, initialWidth / 4, height);
        this.grayImage = portion;

        int subImageWidth = initialWidth - (initialWidth / 4) * (finish + 1);
        System.out.println("Latimea subimaginii: " + subImageWidth);

        if (finish < 4 && subImageWidth > 0) {
            this.rgbImage = rgbImage.getSubimage(initialWidth / 4, 0, subImageWidth, height);
        } else {
            // Tratarea cazului in care subImageWidth nu este mai mare decat 0
        	//throw new IllegalArgumentException("SubImageWidth nu este mai mare decat 0 sau finish-ul este mai mare sau egal cu 4.");
        }

        this.pipe.write(grayImage);
        this.finish = this.finish + 1;

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void consume() throws InterruptedException {
        BufferedImage image = this.pipe.read(); // Citeste din conducta portiunea de imagine
        this.grayImage = image;
        convertToGrayscale(this.grayImage); // Realizeaza conversia la tonuri de gri si scrie in fisier
        writeToFile(this.grayImage, this.rgbImage.getWidth());
        try {
            sleep(1000); //sleep timp de 1000 de milisecunde
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda abstracta pentru conversia imaginii la tonuri de gri
    public abstract void convertToGrayscale(BufferedImage image);

    // Metoda abstracta pentru scrierea imaginii in fisier
    public abstract void writeToFile(BufferedImage image, int i);

    //Portiune de diverse Settere si Gettere
    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public BufferedImage getRgbImage() {
        return rgbImage;
    }

    public void setRgbImage(BufferedImage rgbImage) {
        this.rgbImage = rgbImage;
    }

    public BufferedImage getGrayImage() {
        return grayImage;
    }

    public void setGrayImage(BufferedImage grayImage) {
        this.grayImage = grayImage;
    }

    public int getInitialWidth() {
        return initialWidth;
    }

    public void setInitialWidth(int initialWidth) {
        this.initialWidth = initialWidth;
    }
}
