package mobile.contratodigital.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.WebView;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.model.ContratoUtil;
import sharedlib.contratodigital.model.Movimento;

public class TrabalhaComFotos {
	
	public void capturar(WebView webView, Movimento movimento, Dao dao, Context context, String srcContrato) {
				
		   File file = new File(srcContrato);
		    if(!file.exists()){
		    	file.getParentFile().mkdirs();  	
		    }

		Picture picture = webView.capturePicture();
						 Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
		picture.draw(new Canvas(bitmap));
		
		try {
		FileOutputStream fileOutputStream = new FileOutputStream(srcContrato);
			if (fileOutputStream != null) {				
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
				fileOutputStream.close();
			}
			new MeuAlerta("Consulta Gravada com sucesso!", null, context).meuAlertaOk();
		} 
		catch (Exception erro) {
			new MeuAlerta(""+erro, null, context).meuAlertaOk();
		}
	}


	public String devolveStringPicturePathBaseadoEmCaminhoDaFotoSelecionada(Context context, Uri caminhoDaFotoSelecionada) {
		String[] filePathColumn = { MediaColumns.DATA };
		Cursor cursor = context.getContentResolver().query(caminhoDaFotoSelecionada, filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}
	
	public BitmapDrawable devolveBitmapDrawableBaseadoEmCaminhoDaFotoSelecionada(Context context, Uri caminhoDaFotoSelecionada) {

		String[] filePathColumn = { MediaColumns.DATA };

		Cursor cursor = context.getContentResolver().query(caminhoDaFotoSelecionada, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

		String picturePath = cursor.getString(columnIndex);

		cursor.close();

		Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);

		return bitmapDrawable;
	}

	public boolean tiraScreenShot(Activity activity, Bitmap bitmap, String nomeEmpresa_cnpj) {

		boolean deuErro = false;

 		//----- Tira ScreenShot apenas da linearLayout_tela
		
		File imageFile = new File(criaStringDoCaminhoComDataAtual(nomeEmpresa_cnpj));

		if(!imageFile.exists()){
			imageFile.getParentFile().mkdirs();  	
    	}
		
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(imageFile);

			int quality = 100;

			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);

			fileOutputStream.flush();
			fileOutputStream.close();
		} 
		catch (Exception e) {

			deuErro = true;
			e.printStackTrace();

		}
		return deuErro;
	}

	private String criaStringDoCaminhoComDataAtual(String nomeEmpresa_cnpj) {

		DataPersonalizada dataPersonalizada = new DataPersonalizada();

		String dataAtual = dataPersonalizada.pegaDataAtual_DDMMYYYY_HHMMSS();
		
		String diretorio_comJPG = Environment.getExternalStorageDirectory() +"/ContratoDigital/"+nomeEmpresa_cnpj+"/"+dataAtual+".jpg";	
		
		return diretorio_comJPG;
	}

	public Uri criaEDevolveDiretorioOndeAFotoSeraSalva() {

		File newfile = new File(criaStringDoCaminhoComDataAtual(""));

		try {
			newfile.createNewFile();
		} catch (IOException iOException) {
			Log.e("tag", "" + iOException);
		}

		Uri outputFileUri = Uri.fromFile(newfile);

		return outputFileUri;
	}
	
	@SuppressLint("NewApi")
	public String devolveDiretorioDoArquivoSelecionado(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {
	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}

	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int column_index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(column_index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}

	private static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	private static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	private static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

}
