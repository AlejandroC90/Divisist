package com.example.divisist.divisist.divisiondesistemas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CardInflater implements IAdapterViewInflater<CardItemData>
{
	@Override
	public View inflate(final BaseInflaterAdapter<CardItemData> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.card_materia, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemData item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private TextView nombre;
		private TextView PP;
		private TextView SP;
        private TextView TP;
        private TextView EX;
        private TextView HA;
        private TextView Def;

		public ViewHolder(View rootView)
		{
			m_rootView = rootView;
			nombre = (TextView) m_rootView.findViewById(R.id.nombreM);
			PP = (TextView) m_rootView.findViewById(R.id.notaPP);
			SP = (TextView) m_rootView.findViewById(R.id.notaSP);
            TP = (TextView) m_rootView.findViewById(R.id.notaTP);
            EX = (TextView) m_rootView.findViewById(R.id.notaEX);
            HA = (TextView) m_rootView.findViewById(R.id.notaHA);
            Def = (TextView) m_rootView.findViewById(R.id.notaDef);
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemData item)
		{
			nombre.setText(item.getText1());
			PP.setText(item.getText2());
			SP.setText(item.getText3());
            TP.setText(item.getText4());
            EX.setText(item.getText5());
            HA.setText(item.getText6());
            Def.setText(item.getText7());

		}
	}
}
