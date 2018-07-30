package mobile.contratodigital.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
//import android.widget.Toast;
/**
 * Classe criada para solicitar e executar o download da nova aplicação
 *  @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class DownloadAplicativo {
	
	private ProgressDialog mProgressDialog;
	private Context context;
	private String nomeDoAPK;
	private String url;
	
	public DownloadAplicativo(Context _context, String _nomeDoAPK, String urlDoServidorComCaminhoDoRestService) {

		this.context = _context;
		this.nomeDoAPK = _nomeDoAPK;
		this.url = urlDoServidorComCaminhoDoRestService;
		
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("Baixando arquivo, aguarde...");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();

		startDownload();
	}

	private void startDownload() {
				
		new DownloadFileAsync().execute(url);
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;
			
			File myDirectory = null;

			try {

				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();

				myDirectory = new File(Environment.getExternalStorageDirectory(), nomeDoAPK ); // estava assim : "Caminhao"

				if (!myDirectory.exists()) {

					myDirectory.delete();

					myDirectory.mkdirs();
				}

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream("/sdcard/"+nomeDoAPK+"/"+nomeDoAPK+".apk");
			
				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					int bar = (int) ((total * (-1)) / lenghtOfFile) / 10000;
					publishProgress("" + bar);
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} 
			catch (Exception exception) {
				
				new MeuAlerta("exception: "+exception, null, context).meuAlertaOk();

				//Toast.makeText(context, "exception: "+exception, Toast.LENGTH_LONG).show();	
			}
			return myDirectory.getAbsolutePath();
		}

		// @Override
		@Override
		protected void onProgressUpdate(String... progress) {
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			
			try {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				       intent.setDataAndType(Uri.fromFile(new File(
				    		   				 Environment.getExternalStorageDirectory() +"/"+nomeDoAPK+"/"+nomeDoAPK+".apk")), "application/vnd.android.package-archive");
				       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				context.startActivity(intent);			
			} 
			catch (Exception exception) {
							 exception.printStackTrace();
								new MeuAlerta("exception: "+exception, null, context).meuAlertaOk();

			//	Toast.makeText(context, "exception"+exception, Toast.LENGTH_LONG).show();
				
				((Activity) context).finish();
			}
		}
	}
}