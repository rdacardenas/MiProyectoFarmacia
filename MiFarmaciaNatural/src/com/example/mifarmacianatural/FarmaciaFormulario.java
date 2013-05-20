package com.example.mifarmacianatural;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class FarmaciaFormulario extends Activity {
 
   private FarmaciaDbAdapter dbAdapter;
   private Cursor cursor;
   private int modo ;
   private long id ;
   private EditText nombre;
   private EditText caracteristicas;
   private EditText propiedades;
   
   private Button boton_guardar;
   private Button boton_cancelar;
   
 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_farmacia_formulario);
 
      Intent intent = getIntent();
      Bundle extra = intent.getExtras();
 
      if (extra == null) return;
      
      nombre = (EditText) findViewById(R.id.nombre);
      caracteristicas = (EditText) findViewById(R.id.caracteristicas);
      propiedades = (EditText) findViewById(R.id.propiedades);
      
      boton_guardar = (Button) findViewById(R.id.boton_guardar);
      boton_cancelar = (Button) findViewById(R.id.boton_cancelar);
      
      dbAdapter = new FarmaciaDbAdapter(this);
      dbAdapter.abrir();
 
      if (extra.containsKey(FarmaciaDbAdapter.C_COLUMNA_ID))
      {
         id = extra.getLong(FarmaciaDbAdapter.C_COLUMNA_ID);
         consultar(id);
      }
 
      establecerModo(extra.getInt(MainActivity.C_MODO));
      
      boton_guardar.setOnClickListener(new View.OnClickListener() {
          
         @Override
         public void onClick(View v)
         {
            guardar();
         }
      });
       
      boton_cancelar.setOnClickListener(new View.OnClickListener() {
          
         @Override
         public void onClick(View v)
         {
            cancelar(); 
         }
      });
 
   }
   
private void establecerModo(int m)
   {
	   this.modo = m ;
	    
	   if (modo == MainActivity.Cte_Ver)
	   {
	      this.setTitle(nombre.getText().toString());
	      this.setEdicion(false);
	   }
	   else if (modo == MainActivity.Cte_Crear)
	   {
	      this.setTitle(R.string.farmacia_crear_titulo);
	      this.setEdicion(true);
	   }
	   else if (modo == MainActivity.Cte_Edit)
	   {
	      this.setTitle(R.string.farmacia_editar_titulo);
	      this.setEdicion(true);
	   }
   }
 
   private void consultar(long id)
   {
      cursor = dbAdapter.getRegistro(id);
 
      nombre.setText(cursor.getString(cursor.getColumnIndex(FarmaciaDbAdapter.C_COLUMNA_NOMBRE)));
      caracteristicas.setText(cursor.getString(cursor.getColumnIndex(FarmaciaDbAdapter.C_COLUMNA_CARACTERISTICAS)));
      propiedades.setText(cursor.getString(cursor.getColumnIndex(FarmaciaDbAdapter.C_COLUMNA_PROPIEDADES)));
    
   }
 
   private void setEdicion(boolean opcion)
   {
      nombre.setEnabled(opcion);
      caracteristicas.setEnabled(opcion);
      propiedades.setEnabled(opcion);
      
   }
   
   private void guardar()
   {
      ContentValues reg = new ContentValues();
      
      if (modo == MainActivity.Cte_Edit)
         reg.put(FarmaciaDbAdapter.C_COLUMNA_ID, id);
       
      reg.put(FarmaciaDbAdapter.C_COLUMNA_NOMBRE, nombre.getText().toString());
      reg.put(FarmaciaDbAdapter.C_COLUMNA_CARACTERISTICAS, caracteristicas.getText().toString());
      reg.put(FarmaciaDbAdapter.C_COLUMNA_PROPIEDADES, propiedades.getText().toString());
      
       
      if (modo == MainActivity.Cte_Crear)
      {
         dbAdapter.insert(reg);
         Toast.makeText(FarmaciaFormulario.this, R.string.farmacia_crear_confirmacion, Toast.LENGTH_SHORT).show();
      }
      else if (modo == MainActivity.Cte_Edit)
      {
         Toast.makeText(FarmaciaFormulario.this, R.string.farmacia_editar_confirmacion, Toast.LENGTH_SHORT).show();
         dbAdapter.update(reg);
      }
      
      setResult(RESULT_OK);
      finish();
   }
    
   private void cancelar()
   {
      setResult(RESULT_CANCELED, null);
      finish();
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
 
      menu.clear();
        
      if (modo == MainActivity.Cte_Ver)
         getMenuInflater().inflate(R.menu.farmacia_formulario_ver, menu);
       
      else
         getMenuInflater().inflate(R.menu.farmacia_formulario_editar, menu);
       
      return true;
   }
   
   @Override
   public boolean onMenuItemSelected(int featureId, MenuItem item) {
       
      switch (item.getItemId())
      {
         case R.id.menu_eliminar:
            borrar(id);
            return true;
             
         case R.id.menu_cancelar:
            cancelar();
            return true;
             
         case R.id.menu_guardar:
            guardar();
            return true;
            
         case R.id.menu_editar:
             establecerModo(MainActivity.Cte_Edit);
             return true;
      }
       
      return super.onMenuItemSelected(featureId, item);
   }
   
   private void borrar(final long id)
   {
      AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
       
      dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
      dialogEliminar.setTitle(getResources().getString(R.string.farmacia_eliminar_titulo));
      dialogEliminar.setMessage(getResources().getString(R.string.farmacia_eliminar_mensaje));
      dialogEliminar.setCancelable(false);
       
      dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
 
         public void onClick(DialogInterface dialog, int boton) {
            dbAdapter.delete(id);
            Toast.makeText(FarmaciaFormulario.this, R.string.farmacia_eliminar_confirmacion, Toast.LENGTH_SHORT).show();
            
            setResult(RESULT_OK);
            finish();
         }
      });
       
      dialogEliminar.setNegativeButton(android.R.string.no, null);
       
      dialogEliminar.show();
       
   }
   
   
   
   
}