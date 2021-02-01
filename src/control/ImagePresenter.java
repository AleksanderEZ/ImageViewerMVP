package control;

import java.util.List;
import model.Image;
import view.ImageDisplay;

public class ImagePresenter {

    private final List<Image> images;
    private final ImageDisplay imageDisplay;

    
    public ImagePresenter(List<Image> images, ImageDisplay imageDisplay) {
        this.images = images;
        this.imageDisplay = imageDisplay;
        this.imageDisplay.display(images.get(0).name());
        this.imageDisplay.on(shift());
    }

    private ImageDisplay.Shift shift() {
        return new ImageDisplay.Shift() {
            @Override
            public String left() {
                return images.get(bounds(index()+1)).name();
            }

            @Override
            public String right() {
                return images.get(bounds(index()-1)).name();
            }
            
            private int bounds(int index) {
                return (index + images.size()) % images.size();
            }
        };
    }
    
    private int index(){
        for (int i = 0; i < images.size(); i++) {
            if (imageDisplay.getCurrentImage().equals(images.get(i).name())) return i;
        }
        return -1;
    }

}
