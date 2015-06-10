package project.ipromo.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by miguel on 17/05/2015.
 */
public class IpromoHelper extends SQLiteOpenHelper {

    private static int version = 1;
    private static String name = "ParseSqlite" ;
    private static SQLiteDatabase.CursorFactory factory = null;

    public IpromoHelper(Context context)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(this.getClass().toString(), "Creando base de datos");

        db.execSQL( "CREATE TABLE Promocion(" +
                " _Id INTEGER ," +
                " Title TEXT NOT NULL, " +
                " Subtitle TEXT, " +
                " Template_number INTEGER," +
                " Description TEXT," +
                " Image BLOB," +
                " Uuid INTEGER)" );



        Log.i(this.getClass().toString(), "Tabla HIPOTECA creada");

   /*
    * Insertamos datos iniciales
    */



        Log.i(this.getClass().toString(), "Datos iniciales PROMOCIONES insertados");

        Log.i(this.getClass().toString(), "Base de datos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
