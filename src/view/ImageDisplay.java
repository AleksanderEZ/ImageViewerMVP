package view;

public interface ImageDisplay {
    void display(String name);
    String getCurrentImage();
    void on(Shift shift);
    
    interface Shift {
        String left();
        String right();
    }
}
