package mobile.contratodigital.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.TrabalhaComArquivos;
import mobile.contratodigital.util.TrabalhaComImagens;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.util.Generico;

public class ExportaArquivosWS {

	private ProgressDialog progressDialog;
	private Context context;
	private String URLescolhida;
	private static final String RESOURCE_REST_ARQUIVOS = "/Retorno/Arquivo/";
	
	public ExportaArquivosWS(Context _context, String URLescolhida) {
		this.context = _context;
		this.URLescolhida = URLescolhida;
	}

	public void exportar(final int nrVisita, final String diretorioDoCliente, final String pastaDoCliente, final Movimento movimento) {

		iniciaProgressDialog();
		
		String url = URLescolhida+RESOURCE_REST_ARQUIVOS;

		MultipartRequest multipartRequest = new MultipartRequest(

				Request.Method.POST, 
				url,
				new Response.Listener<NetworkResponse>() {
					@Override
					public void onResponse(NetworkResponse response) {

						encerraProgressDialog();

						String resultResponse = new String(response.data);

						if (resultResponse.equals("OK") || resultResponse.equals("")) {

							ExportarDadosWS exportarDados = new ExportarDadosWS(context, URLescolhida);
											exportarDados.exportar(nrVisita, diretorioDoCliente, movimento);	

						}else if(resultResponse.equals("ERRO")) {
							new MeuAlerta("Erro no envio dos arquivos", null, context).meuAlertaOk();
						}else {
							new MeuAlerta("Erro desconhecido com os arquivos", null, context).meuAlertaOk();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						encerraProgressDialog();
						
						if(volleyError.toString().contains("TimeoutError")) {
							
							new MeuAlerta("Favor tentar com outro link", null, context).meuAlertaOk();
						}else {							
							new MeuAlerta("Arquivos não enviados: " + volleyError, null, context).meuAlertaOk();
						}
					}
				}
		) {
			@Override
			protected Map<String, DataPart> getByteData() {

				int qtd = 1;

				Map<String, DataPart> params = new HashMap<String, DataPart>();

				//File file = new File(Environment.getExternalStorageDirectory() + "/ContratoDigital/");
				File file = new File(diretorioDoCliente);

				File[] listaComArquivos = file.listFiles();
						if (listaComArquivos.length == 0) {

							params.put("", new DataPart("", new byte[1]));
						} else {
							for (File arquivo : listaComArquivos) {

								String arq = arquivo.toString();

								if (arq.contains(".jpg")) {
									
									Bitmap bitmap = decodeSampledBitmapFromResource("" + arquivo, 800, 600);
									if (bitmap != null) {
										BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);

										params.put(""+qtd+pastaDoCliente, new DataPart(arquivo.getName(),
																		TrabalhaComImagens.pegaBytesDoDrawable(context, bitmapDrawable),
																		"file/jpeg"));
									}
								}

								if (arq.contains(".pdf")) {
									params.put(""+qtd+pastaDoCliente, new DataPart(arquivo.getName(), fileToByteArray(arquivo), "file/pdf"));
								}

								if (arq.contains(".doc")) {
									params.put(""+qtd+pastaDoCliente, new DataPart(arquivo.getName(), fileToByteArray(arquivo), "file/msword"));
								}
								qtd++;
							}
						}
				return params;
			}
		};

		RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

									  multipartRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());
					 requestQueue.add(multipartRequest);
	}

	private static byte[] fileToByteArray(File fileName) {
		try {
			FileInputStream in = new FileInputStream(fileName);
			byte[] bytes = new byte[(int) fileName.length()];
			int c = -1;
			int ix = 0;
			while ((c = in.read()) > -1) {
				// System.out.println(c);
				bytes[ix] = (byte) c;
				ix++;
			}
			in.close();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(pathName, options);

		// Calculate inSampleSize
		options.inSampleSize = calculaInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(pathName, options);
	}

	public static int calculaInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {

				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	private void iniciaProgressDialog() {

		progressDialog = new ProgressDialog(context);
		//progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Enviando arquivos...");
		progressDialog.show();
	}

	private void encerraProgressDialog() {

		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
}
