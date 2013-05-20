package com.example.mifarmacianatural;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class FarmaciaDbHelper extends SQLiteOpenHelper {
	
	private static int version = 1;
	private static String name = "FarmaciaDb" ;
	private static CursorFactory factory = null;
	 
	public FarmaciaDbHelper(Context context)
	   {
	      super(context, name, factory, version);
	   }

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(this.getClass().toString(), "Creando base de datos");
		 
		   db.execSQL( "CREATE TABLE PLANTA(" +
		          " _id INTEGER PRIMARY KEY," +
		          " pla_nombre TEXT NOT NULL, " +
		          " pla_caracteristicas TEXT, " +
		          " pla_propiedades TEXT)" );
		   
		   db.execSQL( "CREATE UNIQUE INDEX pla_nombre ON PLANTA(pla_nombre ASC)" );
		   
		   Log.i(this.getClass().toString(), "Tabla PLANTA creada");
		 
		   /*
		    * Insertamos datos iniciales
		    */
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(1,'Apio','Posee tallos estriados que forman una gruesa penca con hojas acuñadas. ','Ayuda al cuerpo a deshacerse de impurezas a través de su función diurética (por su contenido en un aceite volátil, el apiol).')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(2,'Boldo','El boldo es un árbol de mediano tamaño, perteneciente a la familia Monimiaceae. Puede superar los 15 metros de altura.','El fruto del boldo es comestible, y ha sido consumido crudo y cocido desde la época precolombina. La corteza, rica en taninos, se emplea también en la curtiembre.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(3,'Cebada','Es un cereal de los conocidos como cereal de invierno, se cosecha en primavera y generalmente su distribucion es similar a la del trigo.','Se utiliza para tratamiento de tos irritativa, digestiones pesadas, deficiencias en la secrecion de jugos digestivos, irritaciones digestivas.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(4,'Diente de Leon','Es una especie de planta con flor de la familia de las asteráceas. Considerada generalmente como una mala hierba y sus hojas se consumen en ensalada.','Puede actuar en el hígado, riñón y la vesícula biliar, y con su efecto diurético evita la aparición de piedras en el riñón.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(5,'Espinaca','Cultivada como verdura por sus hojas comestibles, grandes y de color verde muy oscuro.','Las espinacas son ricas en minerales como el calcio, hierro, magnesio, potasio, sodio, y presenta buenas cantidades de fósforo, zinc y yodo.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(6,'Eucalipto','Los eucaliptos son árboles perennes, de porte recto. Pueden llegar a medir más de 60 m de altura, si bien se habla de ejemplares ya desaparecidos que han alcanzado los 150 metros.','El aceite esencial de las hojas de eucalipto es usado como descongestionante y para combatir infecciones respiratorias.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(7,'Fresa','Presenta una roseta basal de donde surgen las hojas y los tallos florales, ambos de la misma longitud.','Su función más importante es de antioxidante para absorver los radicales libres que pueden causar enfermedades crónicas.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(8,'Hinojo',' Es una hierba perenne y sumamente aromática, cultivada para su empleo en gastronomía.','Se le puede dar un uso externo para aliviar las irritaciones de los ojos cansados y la conjuntivitis.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(9,'Linaza','De la semilla se extrae el aceite de linaza, el cual es rico en ácidos grasos de las series Omega.','El aceite de linaza o lino también es apreciado en las dietas vegetarianas y/o veganas.')");
		   db.execSQL("INSERT INTO PLANTA(_id, pla_nombre, pla_caracteristicas, pla_propiedades) " +
		   		"VALUES(10,'Romero','Es un arbusto leñoso de hojas perennes muy ramificado y ocasionalmente achaparrado y que puede llegar a medir 2 metros de altura.','La infusión de hojas de romero alivia la tos y es buena para el hígado y para atajar los espasmos intestinales. Debe tomarse antes o después de las comidas.')");
		 
		   Log.i(this.getClass().toString(), "Datos iniciales PLANTA insertados");
		 
		   Log.i(this.getClass().toString(), "Base de datos creada");   
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		/*if (newVersion == 2)
		   {
		      db.execSQL("UPDATE PLANTA SET pla_caracteristicas = 'Bueno para el Reumatismo y Tos'," +
		    		  	 "					pla_porpiedades = 'Reumatismo y Tos'" +
		                 " 					WHERE _id = 1");
		   }
		   */

	}

}
