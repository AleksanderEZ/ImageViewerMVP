package view;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SwingImageDisplay extends JPanel implements ImageDisplay{

    private BufferedImage bitmap;
    private BufferedImage futureBitmap;
    private String currentImage;
    private String futureImage;
    private int offset;
    private Shift shift;
    private String directory = "";

    public SwingImageDisplay(String directory) {
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        if(!directory.isEmpty()){
            this.directory = directory + "/";
        }
    }
    
    @Override
    public void display(String image) {
        this.currentImage = image;
        this.bitmap = imageOf(image);
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if (bitmap == null) return;
        g.drawImage(bitmap, offset, 0, null);
        if (offset == 0) return;
        g.drawImage(futureBitmap, offset < 0 ? bitmap.getWidth()+offset : -(futureBitmap.getWidth()-offset), 0, null);
    }

    @Override
    public String getCurrentImage() {
        return currentImage;
    }
    
    private BufferedImage imageOf(String image) {
        try{
            return ImageIO.read(new File(directory+image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void setOffset(int offset){
        this.offset = offset;
        if(offset < 0) setFuture(shift.right());
        if(offset > 0) setFuture(shift.left());
        repaint();
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift;
    }

    private void setFuture(String left) {
        if (left.equals(futureImage)) return;
        this.futureImage = left;
        this.futureBitmap = imageOf(left);
    }
    
    private void toggle() {
        this.currentImage = this.futureImage;
        this.bitmap = this.futureBitmap;
        setOffset(0);
    }
    
    private class MouseHandler implements MouseListener, MouseMotionListener {

        private int initial;

        @Override
        public void mouseClicked(MouseEvent e) {
            return;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            initial = e.getX();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (Math.abs(offset) > getWidth()/2) toggle();
            setOffset(0);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            return;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            return;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            setOffset(e.getX()-initial);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            return;
        }
    }
}


