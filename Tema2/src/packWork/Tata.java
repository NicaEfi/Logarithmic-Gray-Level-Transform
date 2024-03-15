package packWork;

public class Tata extends Thread {
    int counter;  // Contor pentru pasii executati
    public boolean step1, step2, step3;  // Indicatori pentru fiecare pas

    public Tata(int counter) {
        this.step1 = false;
        this.step2 = false;
        this.step3 = false;
        this.counter = counter;
    }

    @Override
    public void run() {
        while (!step1 && !step2 && !step3) {
            this.counter = counter + 1;  // Incrementarea contorului in fiecare iteratie
            try {
                this.sleep(1000);  // Se asteapta 1 secunda intre iteratii
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Afisarea rezultatelor bazate pe pasii executati
        if (step1) {
            System.out.println("Pasul 1 + " + counter);
        }
        if (step2) {
            System.out.println("Pasul 2 + " + counter);
        }
        if (step3) {
            System.out.println("Pasul 3 + " + counter);
        }
    }
}
