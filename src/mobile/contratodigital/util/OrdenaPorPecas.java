package mobile.contratodigital.util;

import java.util.Comparator;

import mobile.contratodigital.model.ItemPeca;

public class OrdenaPorPecas implements Comparator<ItemPeca> {
  

	@Override
	public int compare(ItemPeca um, ItemPeca dois) {
		// TODO Auto-generated method stub
        return um.getNome().compareTo(dois.getNome());

	}
}

