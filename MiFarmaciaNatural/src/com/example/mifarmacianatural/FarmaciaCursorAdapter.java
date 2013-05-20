package com.example.mifarmacianatural;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class FarmaciaCursorAdapter extends CursorAdapter {
	
	private FarmaciaDbAdapter dbAdapter = null ;
	 
	   public FarmaciaCursorAdapter(Context context, Cursor c)
	   {
	      super(context, c);
	      dbAdapter = new FarmaciaDbAdapter(context);
	      dbAdapter.abrir();
	   }

	@Override
	public void bindView(View view, Context comtext, Cursor cursor) {
		
		TextView tv = (TextView) view ;
		tv.setText(cursor.getString(cursor.getColumnIndex(FarmaciaDbAdapter.C_COLUMNA_NOMBRE)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		final LayoutInflater inflater = LayoutInflater.from(context);
	    final View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
		
	    return view;
	}

}
