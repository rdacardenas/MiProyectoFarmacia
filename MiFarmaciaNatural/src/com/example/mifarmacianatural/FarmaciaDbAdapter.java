package com.example.mifarmacianatural;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
//import android.widget.Toast;

public class FarmaciaDbAdapter {
	
	   public static final String C_TABLA = "PLANTA" ;
	 
	   public static final String C_COLUMNA_ID   = "_id";
	   public static final String C_COLUMNA_NOMBRE = "pla_nombre";
	   public static final String C_COLUMNA_CARACTERISTICAS = "pla_caracteristicas";
	   public static final String C_COLUMNA_PROPIEDADES = "pla_propiedades";
	   
	 
	   private Context contexto;
	   private FarmaciaDbHelper dbHelper;
	   private SQLiteDatabase db;
	 
	   
	   private String[] columnas = new String[]{ 
			   C_COLUMNA_ID, 
			   C_COLUMNA_NOMBRE, 
			   C_COLUMNA_CARACTERISTICAS, 
			   C_COLUMNA_PROPIEDADES};
	 
	   public FarmaciaDbAdapter(Context context) 
	   {
	      this.contexto = context;
	   }
	 
	   public FarmaciaDbAdapter abrir() throws SQLException
	   {
	      dbHelper = new FarmaciaDbHelper(contexto);
	      db = dbHelper.getWritableDatabase();
	      return this;
	   }
	 
	   public void cerrar()
	   {
	      dbHelper.close();
	   }
	   
	   public Cursor getCursor() throws SQLException
	   {
	      Cursor c = db.query( true, C_TABLA, columnas, null, null, null, null, null, null);
	      return c;
	   }
	   
	   public Cursor getRegistro(long id) throws SQLException
	   {
	      Cursor c = db.query( true, C_TABLA, columnas, C_COLUMNA_ID + "=" + id, null, null, null, null, null);
	 
	      if (c != null) {
	         c.moveToFirst();
	      }
	      return c;
	   }

	   public long insert(ContentValues reg)
	   {
	      if (db == null)
	         abrir();
	       
	      return db.insert(C_TABLA, null, reg);
	   }
	   
	   public long delete(long id)
	   {
	      if (db == null)
	         abrir();
	       
	      return db.delete(C_TABLA, "_id=" + id, null);
	   }

	   public long update(ContentValues reg)
	   {
	      long result = 0;
	       
	      if (db == null)
	         abrir();
	       
	      if (reg.containsKey(C_COLUMNA_ID))
	      {
	         long id = reg.getAsLong(C_COLUMNA_ID);
	         reg.remove(C_COLUMNA_ID);
	         result = db.update(C_TABLA, reg, "_id=" + id, null); 
	      }
	      return result;
	   }
	   
	   
}
