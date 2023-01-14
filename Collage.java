package art;

import java.awt.Color;

public class Collage {
    public static void scale (Picture source, Picture target) {
        int ht = target.height();
        int tw = target.width();
        for (int targetCol = 0; targetCol < tw; targetCol++) {
            for (int targetRow = 0; targetRow < ht; targetRow++) {
                    int sourceCol = targetCol * source.width()  / tw;
                    int sourceRow = targetRow * source.height() / ht;
                    Color color = source.get(sourceCol, sourceRow);
                    target.set(targetCol, targetRow, color);
            }
        }
        source.show();
        target.show();
    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }
 
    public void makeCollage () {
        Picture tile = new Picture(tileDimension, tileDimension);  
        scale(originalPicture, tile);
        for(int i=0;i<tileDimension;i++){
            for(int j=0; j<tileDimension;j++){
                Color color = tile.get(i,j);
                for(int a = 0; a<collageDimension; a++){
                    for(int b= 0; b<collageDimension; b++){
                        collagePicture.set(i+(a*tileDimension), j+(b*tileDimension), color);
                    }
                }
            }
        }

    }
    
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        int RGB = 0;
        Color a;
        for(int i=0;i<tileDimension;i++){
            for(int j=0;j<tileDimension;j++){
                Color color = collagePicture.get(collageCol*tileDimension+i, collageRow*tileDimension+j);
                if(component.equals("red")){
                    RGB = color.getRed();
                    a = new Color(RGB,0,0);
                    collagePicture.set(collageCol*tileDimension+i, collageRow*tileDimension+j, a);
                }
                else if(component.equals("green")){
                    RGB = color.getGreen();
                    a = new Color(0,RGB,0);
                    collagePicture.set(collageCol*tileDimension+i, collageRow*tileDimension+j, a);
                }
                else if(component.equals("blue")){
                    /* RGB = color.getBlue();
                    a = new Color (0,0,RGB); */
                    collagePicture.set(collageCol*tileDimension+i, collageRow*tileDimension+j, new Color(0,0,color.getBlue())); 
                }
                else if(component.equals("gray")){
                    collagePicture.set(collageCol*tileDimension+i, collageRow*tileDimension+j,toGray(color));
                }

            }
        }
    }
    
    public void replaceTile (String filename,  int collageCol, int collageRow) {
        Picture original = new Picture(filename);
        Picture tile = new Picture(tileDimension, tileDimension);
        scale(original,tile);
        for (int i=0;i<tileDimension;i++){
            for(int j=0;j<tileDimension;j++){
                Color color = tile.get(i,j);
                for (int a=0;a<tileDimension;a++){
                    for (int b=0;b<tileDimension;b++){
                        collagePicture.set((i)+collageCol*tileDimension, (j)+tileDimension*collageRow, color);
                    }
                }
            }
        }
        //for(int i=0;i<tile)
    }

    public void grayscaleTile (int collageCol, int collageRow) {
        colorizeTile("gray", collageCol, collageRow);
    }
    
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
