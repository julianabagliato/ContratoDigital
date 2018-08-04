package mobile.contratodigital.view;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import mobile.contratodigital.R;
import mobile.contratodigital.model.ItemPeca;

public class ArrayAdapterItemPeca extends ArrayAdapter<String> {

	private Filter filter;
	private ViewItemLista viewItemLista;
	private List<ItemPeca> listaDeItemPecas;
	private List<ItemPeca> listaDeItemPecasTemporaria;

	public ArrayAdapterItemPeca(Context _context, int textViewResourceId, List _lista) {
		super(_context, textViewResourceId, _lista);

		listaDeItemPecas = _lista;
		listaDeItemPecasTemporaria = _lista;		
		viewItemLista = new ViewItemLista(_context);
	}

	public View getCustomView(int position, View view, ViewGroup parent) {
		
		ItemPeca itemPeca = listaDeItemPecas.get(position);
		
			   view = viewItemLista.criaTelaDoItem(itemPeca);
			
			   viewItemLista.imageView.setImageResource(R.drawable.signout);

			   view.setTag(itemPeca.getCodigo());
	
		return view;
	}
	
	@Override
	public int getCount() {
		return listaDeItemPecas.size();
	}	
	@Override
	public long getItemId(int position) {
		return listaDeItemPecas.get(position).hashCode();
	}
	public void resetData() {
		listaDeItemPecas = listaDeItemPecasTemporaria;
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	@Override
	public Filter getFilter() {
		if (filter == null)
			  filter = new PecasFilter();
		return filter;
	}

	private class PecasFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			
			FilterResults filterResults = new FilterResults();
			
			if (constraint == null || constraint.length() == 0) {
				
				filterResults.values = listaDeItemPecasTemporaria;
				filterResults.count = listaDeItemPecasTemporaria.size();
			} 
			else {
				
				List<ItemPeca> listaDeItemPecas_2 = new ArrayList<ItemPeca>();

				for (ItemPeca itemPeca : listaDeItemPecas) {
					
					if (containsIgnoreCase(removerAcentos(itemPeca.getNome()),constraint.toString())) {

						listaDeItemPecas_2.add(itemPeca);
					}
					else if (containsIgnoreCase(removerAcentos(""+itemPeca.getCodigo()),constraint.toString())) {

						listaDeItemPecas_2.add(itemPeca);
					}
				}
				filterResults.values = listaDeItemPecas_2;
				filterResults.count = listaDeItemPecas_2.size();
			}
			return filterResults;
		}
		
		public CharSequence removerAcentos(CharSequence str) {
		    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
				listaDeItemPecas = (List<ItemPeca>) results.values;
				notifyDataSetChanged();
		}
		public boolean containsIgnoreCase(CharSequence haystack, String needle) {
			if (needle.equals(""))
				return true;
			if (haystack == null || needle == null || haystack.equals(""))
				return false;

			Pattern p = Pattern.compile(needle, Pattern.CASE_INSENSITIVE + Pattern.LITERAL);
			Matcher m = p.matcher(haystack);
			return m.find();
		}
	}
}