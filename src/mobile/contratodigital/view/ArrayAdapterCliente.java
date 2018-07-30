package mobile.contratodigital.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import sharedlib.contratodigital.model.Movimento;

/**
 * Classe do tipo ArrayAdapter  usada para tratar os dados do cliente
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class ArrayAdapterCliente extends ArrayAdapter<String> {

	private Context context;
	private List<Movimento> listaComMovimentos;
	private List<Movimento> listaComMovimentos_temporaria;
	private LayoutInflater layoutInflater;
	private Dao dao;
	private ImageView imageView_cliente;
	private TextView textView_conteudo;
	private Movimento movimento;

	public ArrayAdapterCliente(Context _context, int textViewResourceId, List _listaComMovimentos) {
		super(_context, textViewResourceId, _listaComMovimentos);

		this.context = _context;
		this.listaComMovimentos = _listaComMovimentos;
		this.listaComMovimentos_temporaria = _listaComMovimentos;
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dao = new Dao(context);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		ArrayList<Movimento> listaTodosMovimentos = null;
		movimento = null;
		movimento = listaComMovimentos.get(position);
		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
				Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
				Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());

		listaTodosMovimentos = dao.devolveListaComMovimentosPopulados(mov_informacoesCliente);

		int total2;
		total2 = 0;
		int lay;
		lay = 0;
		int tam2 = 0;

		while (tam2 < listaTodosMovimentos.size() && tam2 >= 0) {
			Movimento move = listaTodosMovimentos.get(tam2);
			if (move == null) {
				lay = 0;
			} else {

				lay = listaTodosMovimentos.get(tam2).getNr_layout();
			}
			if (lay == 21 || lay == 24 || lay == 25 || lay == 666 || lay == 667 || lay == 669 || lay == 668) {
				total2 = total2 + 1;

			}
			tam2++;

		}
		View view_Row = layoutInflater.inflate(R.layout.adapter_cliente, parent, false);

		imageView_cliente = (ImageView) view_Row.findViewById(R.id.imageView_cliente);
		imageView_cliente.setImageResource(R.drawable.cliente);

		textView_conteudo = (TextView) view_Row.findViewById(R.id.textView_conteudo);
		textView_conteudo.setTextSize(20);

	if (total2 <8){
		imageView_cliente.setBackgroundColor(R.color.Green);
	}
		if (!movimento.getNr_contrato().trim().equals("")) {
		
			imageView_cliente.setImageResource(R.drawable.ok);
		}
		if (movimento.getNr_contrato().trim().equals("")) {

		//if (movimento.getStatus() == Generico.NAO_REALIZADA.getValor()) {
			//textView_conteudo.setBackgroundColor(R.color.Black);
			imageView_cliente.setImageResource(R.drawable.cliente);
		}

		Movimento movimentoAtualizado = (Movimento) dao.devolveObjeto(Movimento.class,
				Movimento.COLUMN_INTEGER_NR_LAYOUT, movimento.getNr_layout(), Movimento.COLUMN_INTEGER_NR_VISITA,
				movimento.getNr_visita());
		if (movimentoAtualizado.getNr_contrato().trim().equals("")) {
			textView_conteudo.setText("" + movimento.getNr_visita() + " - " + movimentoAtualizado.getInformacao_1());

		} else {

			textView_conteudo.setText("" + movimento.getNr_visita() + " - " + movimentoAtualizado.getNr_contrato() + "-" + movimentoAtualizado.getInformacao_1());

		}
		view_Row.setId(movimento.getNr_visita());

		return view_Row;
	}

	/*
	 * private void percorreObjetoEfazAlgumaCoisa(Object objeto, int nrOrdem,
	 * TextView textView) {
	 * 
	 * try {
	 * 
	 * Class<?> classe = objeto.getClass();
	 * 
	 * for (Field atributo : classe.getDeclaredFields()) {
	 * 
	 * atributo.setAccessible(true);
	 * 
	 * if (atributo.getName().contains("informacao_")) {
	 * 
	 * int tamanhoTotal = atributo.getName().length();
	 * 
	 * String stringCapturada = atributo.getName().substring(11, tamanhoTotal);
	 * 
	 * int inteiroCapturado = Integer.parseInt(stringCapturada);
	 * 
	 * if (inteiroCapturado == nrOrdem) {
	 * 
	 * textView.setText(textView.getText() +""+
	 * String.valueOf(atributo.get(objeto)) + "\n"); } } } } catch (Exception e)
	 * { e.printStackTrace(); } }
	 */

	public void resetData() {
		listaComMovimentos = listaComMovimentos_temporaria;
	}

	@Override
	public int getCount() {
		return listaComMovimentos.size();
	}

	@Override
	public long getItemId(int position) {
		return listaComMovimentos.get(position).hashCode();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		super.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		super.unregisterDataSetObserver(observer);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

}