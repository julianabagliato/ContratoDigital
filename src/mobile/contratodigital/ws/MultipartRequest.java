package mobile.contratodigital.ws;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MultipartRequest extends Request<NetworkResponse> {
	
    private final String doisTracos = "--";
    private final String terminaLinha = "\r\n";
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private Response.Listener<NetworkResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private Map<String, String> mHeaders;
 
    public MultipartRequest(String url, Map<String, String> headers, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
    	
        super(Method.POST, url, errorListener);
        
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.mHeaders = headers;
    }
 
    public MultipartRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
    	
        super(method, url, errorListener);
        
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (mHeaders != null) ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
    	
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {        	
            Map<String, String> params = getParams();
            if (params != null && params.size() > 0) {
                textParse(dataOutputStream, params, getParamsEncoding());
            }
 
            Map<String, DataPart> data = getByteData();
            
            if (data != null && data.size() > 0) {
                dataParse(dataOutputStream, data);
            }
            
            dataOutputStream.writeBytes(doisTracos + boundary + doisTracos + terminaLinha);

            return byteArrayOutputStream.toByteArray();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    protected Map<String, DataPart> getByteData() throws AuthFailureError {
        return null;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
    	
        try {
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
        } 
        catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
   		mErrorListener.onErrorResponse(error);
    }
 
    private void textParse(DataOutputStream dataOutputStream, Map<String, String> params, String encoding) throws IOException {
    	
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                constroiTextPart(dataOutputStream, entry.getKey(), entry.getValue());
            }
        } 
        catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + encoding, uee);
        }
    }
 
    private void dataParse(DataOutputStream dataOutputStream, Map<String, DataPart> data) throws IOException {
    	
        for (Map.Entry<String, DataPart> entry : data.entrySet()) {
        	
            constroiDataPart(dataOutputStream, entry.getValue(), entry.getKey());
        }
    }
   
    private void constroiTextPart(DataOutputStream dataOutputStream, String chave, String valor) throws IOException {
    	
        dataOutputStream.writeBytes(doisTracos + boundary + terminaLinha);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + chave + "\"" + terminaLinha); 
        dataOutputStream.writeBytes(terminaLinha);
        dataOutputStream.writeBytes(valor + terminaLinha);
    }
 
    private void constroiDataPart(DataOutputStream dataOutputStream, DataPart dataPart, String inputName) throws IOException {
    	
        dataOutputStream.writeBytes(doisTracos + boundary + terminaLinha);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + dataPart.getFileName() + "\"" + terminaLinha);
        
        if (dataPart.getType() != null && !dataPart.getType().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + dataPart.getType() + terminaLinha);
        }
        dataOutputStream.writeBytes(terminaLinha);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataPart.getContent());
        int bytesAvailable = byteArrayInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = byteArrayInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = byteArrayInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = byteArrayInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(terminaLinha);
    }
 
    public class DataPart {
    	
        private String nomeImagem;
        private byte[] conteudo;
        private String type;

        public DataPart() {
        }

        public DataPart(String name, byte[] data) {
            nomeImagem = name;
            conteudo = data;
        }
        
        public DataPart(String name, byte[] data, String mimeType) {
            nomeImagem = name;
            conteudo = data;
            type = mimeType;
        }

        public String getFileName() {
            return nomeImagem;
        }
 
        public void setFileName(String fileName) {
            this.nomeImagem = fileName;
        }
        
        public byte[] getContent() {
            return conteudo;
        }

        public void setContent(byte[] content) {
            this.conteudo = content;
        }

        public String getType() {
            return type;
        }
  
        public void setType(String type) {
            this.type = type;
        }
    }
}
