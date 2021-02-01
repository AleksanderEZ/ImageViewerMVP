package app;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Image;
import view.FileImageLoader;
import view.ImageDisplay;
import view.SwingImageDisplay;
import control.ImagePresenter;
import java.util.List;

public class MainFrame extends JFrame{
    
    public static int MAX_SIZE = 100_000;
    private ImageDisplay imageDisplay;
    private ImagePresenter imagePresenter;
    
    public MainFrame() {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ImageViewer");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        getContentPane().add(imageDisplay());
        this.imagePresenter = createImagePresenter();
        this.setVisible(true);
    }

    private JPanel imageDisplay() {
        SwingImageDisplay imageDisplay = new SwingImageDisplay("images");
        this.imageDisplay = imageDisplay;
        return imageDisplay;
    }

    private ImagePresenter createImagePresenter() {
        return new ImagePresenter(loadImages(), imageDisplay);
    }
    
    private List<Image> loadImages(){
        return new FileImageLoader(new File("images")).load();
    }
}
