package packWork;

public class Mama implements Runnable {
    Thread t ; // Declararea variabilei de tip Thread care va gestiona firul de executie asociat obiectului Mama

    public Mama (){
        this.t = new Thread(this); // Constructorul clasei Mama, creeaza un nou obiect Thread asociat cu instanta curenta a clasei (this)
    }

    @Override
    public void run() {
        for(int i = 0; i< 4; i++){
            System.out.println("Clasa mama e in proces"); // Implementarea metodei run care va fi executata in cadrul firului de executie
        }
    }

    public void start(){
        t.start(); // Metoda care porneste firul de executie asociat obiectului Mama
    }
}
