package packWork;  

public abstract class Forma {  

    int width;  // Camp pentru latime
    int height;  // Camp pentru inaltime

    public Forma(int width, int height){  // Constructor cu doi parametri pentru initializarea latimii si inaltimii
        this.height = height;  // Initializarea campului pentru inaltime
        this.width = width;  // Initializarea campului pentru latime
    }

    public abstract int calcArea();  // Metoda abstracta pentru calculul ariei

    public int calcArea(int width){  // Metoda pentru calculul ariei cu un parametru de latime
        return width * this.height;  // Returnarea rezultatului calculului
    }

    public int calcArea(int height, int width){  // Metoda pentru calculul ariei cu doi parametri pentru inaltime si latime
        return width * height;  // Returnarea rezultatului calculului
    }
}
