package mobile.contratodigital.util;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class TrabalhaComImagens {    
    	   
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
   
}