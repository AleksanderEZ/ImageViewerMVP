package view;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Image;

public class FileImageLoader implements ImageLoader{

    private final File[] files;

    public FileImageLoader(File folder) {
        this.files = folder.listFiles(imageTypes());
    }
    
    private FileFilter imageTypes(){
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jpg")) return true;
                return false;
            }
        };
    }
    
    private Image imageAt(int i) {
        return new Image(){
            @Override
            public String name() {
                return files[i].getName();
            }
            
            @Override
            public InputStream stream() {
                try {
                    return new BufferedInputStream(new FileInputStream(files[i]));
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                return null;
            }
            
            @Override
            public Image next() {
                return i == files.length - 1 ? imageAt(0) : imageAt(i+1);
            }

            @Override
            public Image prev() {
                return i == 0 ? imageAt(files.length - 1): imageAt(i-1);
            }
        };
    }    
    
    @Override
    public List<Image> load() {
        List<Image> images = new ArrayList<>();
        for(int i = 0; i < files.length; i++){
            images.add(imageAt(i));
        }
        return images;
    }
}
