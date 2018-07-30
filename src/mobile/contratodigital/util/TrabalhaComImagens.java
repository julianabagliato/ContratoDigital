package mobile.contratodigital.util;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
/**
 * Classe criada para imagens dentro da aplicação temporariamente desativada
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
 * 
 * @version 1.0
 */
public class TrabalhaComImagens {    
    	
//    public static byte[] pegaByteBaseadoEmDrawable(Context context, int drawableImagem) {
//    	
//        						 Drawable drawable = ContextCompat.getDrawable(context, drawableImagem);
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//        
//        							   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//               bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
//        
//        return byteArrayOutputStream.toByteArray();
//    }
   
    public static byte[] pegaBytesDoDrawable(Context context, Drawable drawable) {
    	
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        
          						  		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        
        return byteArrayOutputStream.toByteArray();
    }
    
    public Image transformaDrawableEmImagem(Context context, int drawableImagem){
    	
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) context.getResources().getDrawable(drawableImagem));
        Bitmap bitmap = bitmapDrawable.getBitmap();
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        
        Image image = null;
        try {
        	image = Image.getInstance(byteArrayOutputStream.toByteArray());
        	image.setCompressionLevel(PdfStream.BEST_COMPRESSION);
        } 
        catch (Exception e) {
        	e.printStackTrace();
        }    

        return image;
    }
    
    public Image transformaDrawableEmImagem(Context context, Drawable drawable){
    	
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        
			  							 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
			  
			  
		Image image = null;
			try {
				image = Image.getInstance(byteArrayOutputStream.toByteArray());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}

        return image;
    }
  
    /*
    private Bitmap trocaCor(Bitmap bitmap, int corOrigem, int corDestino){
    	
		  int [] allpixels = new int [bitmap.getHeight() * bitmap.getWidth()];

		  bitmap.getPixels(allpixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

		  for(int i = 0; i < allpixels.length; i++) {
			  
		      if(allpixels[i] == corOrigem) {
		    	  
		         allpixels[i] = corDestino;
		      }
		  }

		  bitmap.setPixels(allpixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());	  

	return bitmap;
    }
    */
 
}