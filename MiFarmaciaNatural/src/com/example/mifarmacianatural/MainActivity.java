package com.example.mifarmacianatural;

import com.example.mifarmacianatural.FarmaciaDbAdapter;
import com.example.mifarmacianatural.FarmaciaFormulario;
import com.example.mifarmacianatural.MainActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends ListActivity {

	public static final String C_MODO  = "modo" ;
	public static final int Cte_Ver = 551 ;
	public static final int Cte_Crear = 552 ;
	public static final int Cte_Edit = 553 ;
	public static final int Cte_Borrar = 554 ;
	
	private FarmaciaDbAdapter dbAdapter;
    private Cursor cursor;
    private FarmaciaCursorAdapter farmaciaAdapter ;
    private ListView lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_farmacia);
		
		lista = (ListView) findViewById(android.R.id.list);
		 
	      dbAdapter = new FarmaciaDbAdapter(this);
	      dbAdapter.abrir();
	 
	      consultar();
	      
	      registerForContextMenu(this.getListView());
		 	

	}
	
	private void consultar()
	   {
	      cursor = dbAdapter.getCursor();
	      startManagingCursor(cursor);
	      farmaciaAdapter = new FarmaciaCursorAdapter(this, cursor);
	      lista.setAdapter(farmaciaAdapter);
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void visualizar(long id)
	   {
	      
	      Intent i = new Intent(MainActivity.this, FarmaciaFormulario.class);
	      i.putExtra(C_MODO, Cte_Ver);
	      i.putExtra(FarmaciaDbAdapter.C_COLUMNA_ID, id);
	 
	      startActivityForResult(i, Cte_Ver);
	   }
	 
	   @Override
	   protected void onListItemClick(ListView l, View v, int position, long id)
	   {
	      super.onListItemClick(l, v, position, id);
	 
	      visualizar(id);
	   }
	   
	   @Override
	   public boolean onMenuItemSelected(int featureId, MenuItem item)
	   {
	      Intent i;
	       
	      switch (item.getItemId())
	      {
	         case R.id.menu_crear:
	            i = new Intent(MainActivity.this, FarmaciaFormulario.class);
	            i.putExtra(C_MODO, Cte_Crear);
	            startActivityForResult(i, Cte_Crear);
	            return true;
	      }
	      return super.onMenuItemSelected(featureId, item);
	   }
	   
	   @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data)
	   {
	      
	      switch(requestCode)
	      {
	         case Cte_Crear:
	        	 if (resultCode == RESULT_OK)
	               consultar();
	            
	         case Cte_Ver:
	        	 if (resultCode == RESULT_OK)
	        		 consultar();
	             
	         default:
	            super.onActivityResult(requestCode, resultCode, data);
	      }
	   }
	   
	   private void borrar(final long id)
		{
			/*
			 * Borrar registro
			 */
			AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
			
			dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
			dialogEliminar.setTitle(getResources().getString(R.string.farmacia_eliminar_titulo));
			dialogEliminar.setMessage(getResources().getString(R.string.farmacia_eliminar_mensaje));
			dialogEliminar.setCancelable(false);
			
			dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int boton) {
					dbAdapter.delete(id);
					Toast.makeText(MainActivity.this, R.string.farmacia_eliminar_confirmacion, Toast.LENGTH_SHORT).show();
					consultar();				
				}
			});
			
			dialogEliminar.setNegativeButton(android.R.string.no, null);
			
			dialogEliminar.show();
		}

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
			
			menu.setHeaderTitle(cursor.getString(cursor.getColumnIndex(FarmaciaDbAdapter.C_COLUMNA_NOMBRE)));
			menu.add(Menu.NONE, Cte_Ver, Menu.NONE, R.string.menu_visualizar);
			menu.add(Menu.NONE, Cte_Edit, Menu.NONE, R.string.menu_editar);
			menu.add(Menu.NONE, Cte_Borrar, Menu.NONE, R.string.menu_eliminar);
		}
		
		@Override
		public boolean onContextItemSelected(MenuItem item) {
			
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			Intent i;
			
			switch(item.getItemId())
			{
		    	case Cte_Borrar:
		    		borrar(info.id);
		    		return true;
		    	
		    	case Cte_Ver:
		    		visualizar(info.id);
					return true;
					
		    	case Cte_Edit:
		    		i = new Intent(MainActivity.this, FarmaciaFormulario.class);
		    		i.putExtra(C_MODO, Cte_Edit);
		    		i.putExtra(FarmaciaDbAdapter.C_COLUMNA_ID, info.id);
					
		    		startActivityForResult(i, Cte_Edit);
					return true;
		    }
		    return super.onContextItemSelected(item);
		}

}
