package packWork;  

public class FormaMostenire extends Forma { 

    public FormaMostenire(int width, int height) {  // Constructor care apeleaza constructorul clasei parinte cu doi parametri
        super(width, height);  // Apelare constructor din clasa parinte
    }

    @Override
    public int calcArea(){  // Suprascrierea metodei pentru calculul ariei
        return super.width * super.height;  // Returnarea rezultatului calculului ariei
    }
}
