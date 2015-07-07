package sqlitedb;

import Reflection.Mapeadora;
import java.sql.*;
import java.util.ArrayList;
import model.Sueño;
import sqlitedb.DBSQLITE.SqliteHandler;

public class SqliteDB
{
        public static void main(String[] args)
        {
                Sueño sueño = new Sueño();
                //Mapeadora mapeadora = new Mapeadora(sueño.getClass());
                //System.out.println("" +  mapeadora.toString());
                ArrayList<Object> arr = SqliteHandler.query( new Sueño()  ,"SELECT * FROM sueños;");
                System.out.println("arr.size = " + arr.size());
                
                for(Object o : arr )
                {
                        System.out.println("" + o.toString());
                }
                /*
                
                System.out.println("ARRSIZE = "+ arr.size());
                for(Object o : arr)
                {
                        System.out.println("o = "  + o.toString());
                }*/
        }
}
