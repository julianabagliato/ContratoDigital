package mobile.contratodigital.view;

import java.io.ByteArrayOutputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import mobile.contratodigital.util.MeuAlerta;
//import android.widget.Toast;
/**
 * Classe para criação de view interativa de rubrica
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class ViewLousaRubrica extends View {
	
    private static final float STROKE_WIDTH = 5f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private Paint paint = new Paint();
    private Path path = new Path();
    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();
    private Context context;
    private Bitmap bitmap;   
    private LinearLayout linearLayout_telaHolder;
    //private Button button_salvar;
 
    private Paint paint2 = new Paint();
    
    public ViewLousaRubrica(Context _context, AttributeSet attrs, LinearLayout linearLayout__) {
        super(_context, attrs);
        this.context = _context;
        
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
        
        this.linearLayout_telaHolder = linearLayout__;
        //this.button_salvar = _button_salvar;
        
        paint2.setAntiAlias(true);
        paint2.setColor(Color.BLACK);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeWidth(STROKE_WIDTH);
    }

    public Bitmap retornaImagemDesenhada(View view) {
            
        if(bitmap == null) {
           bitmap =  Bitmap.createBitmap(linearLayout_telaHolder.getWidth(), linearLayout_telaHolder.getHeight(), Bitmap.Config.ARGB_8888); //RGB_565
        }
        
        Canvas canvas = new Canvas(bitmap);
        
        try {
            view.draw(canvas); 
            
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);             
        }
        catch(Exception erro) { 
        	new MeuAlerta(
        			"erro: "+erro, null, context).meuAlertaOk();
		
        //    Toast.makeText(context, "erro: "+erro, Toast.LENGTH_LONG).show(); 
        } 
        
        return bitmap;
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        
    	
       // canvas.drawLine(50, 450, 650, 450, paint2);
        
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
        float eventX = event.getX();
        float eventY = event.getY();
        //button_salvar.setEnabled(true);

        switch (event.getAction()) {
        
        case MotionEvent.ACTION_DOWN:
            path.moveTo(eventX, eventY);
            lastTouchX = eventX;
            lastTouchY = eventY;
            return true;

        case MotionEvent.ACTION_MOVE:

        case MotionEvent.ACTION_UP:

            resetDirtyRect(eventX, eventY);
            int historySize = event.getHistorySize();
            for (int i = 0; i < historySize; i++) 
            {
                float historicalX = event.getHistoricalX(i);
                float historicalY = event.getHistoricalY(i);
                expandDirtyRect(historicalX, historicalY);
                path.lineTo(historicalX, historicalY);
            }
            path.lineTo(eventX, eventY);
            break;

        default:
            debug("Ignored touch event: " + event.toString());
            return false;
        }

        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                   (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                   (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                   (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    private void debug(String string){
    }

    private void expandDirtyRect(float historicalX, float historicalY) {
    	
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } 
        else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }

        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } 
        else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    private void resetDirtyRect(float eventX, float eventY) {
    	
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }
}

