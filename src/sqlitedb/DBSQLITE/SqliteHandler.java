package sqlitedb.DBSQLITE;

import Reflection.Mapeadora;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteHandler
{
        private static Connection conexion = null;
        private static Statement statement = null;
        private static String rutaDB = "test.db";
                
        public static void conectar()
        {
                try
                {
                        Class.forName("org.sqlite.JDBC");
                        conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaDB);
                        conexion.setAutoCommit(false);
                        System.out.println("DB OPENED CORRECTLY");
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }
        public static ArrayList<Object> query(String sql)
        {
                ArrayList<Object> arrSalida = new ArrayList<Object>();
                
                if(conexion != null)
                {
                        try
                        {       
                                statement = conexion.createStatement();
                                ResultSet rs = statement.executeQuery(sql);
                                
                                int cantidadColumnas = rs.getMetaData().getColumnCount();
                                
                                while(rs.next())
                                {
                                        ArrayList<String> arrFila = new ArrayList<String>();
                                        for (int i = 1; i < cantidadColumnas; i++)
                                        {
                                                arrFila.add(rs.getString(i));
                                        }
                                        arrSalida.add(arrFila);
                                }
                                
                                statement.close();
                        } 
                        catch (Exception e)
                        {
                                e.printStackTrace();
                        }   
                }
                else
                {
                        conectar();
                        query(sql);
                }
                return arrSalida;
        }
        public static ArrayList<Object> query(Object obj, String sql)
        {
                ArrayList<Object> arrSalida = new ArrayList<Object>();
                Class clase = obj.getClass();
                Mapeadora mapeadora = new Mapeadora(clase);
                System.out.println("OBJ A MAPEAR = " + obj.getClass());
               
                if(conexion != null)
                {
                        System.out.println("CONEXION ABIERTA");
                        try
                        {       
                                statement = conexion.createStatement();
                                ResultSet rs = statement.executeQuery(sql);
                                
                                int cantidadColumnas = rs.getMetaData().getColumnCount();
                                System.out.println("cantidadColumnas: " + cantidadColumnas);
                                while(rs.next())
                                {
                                        mapeadora = new Mapeadora(clase);
                                        //ArrayList<String> arrFila = new ArrayList<String>();
                                        for (int i = 1; i <= cantidadColumnas; i++)
                                        {
                                                String nombreColumna = rs.getMetaData().getColumnLabel(i).toLowerCase();
                                                System.out.println("NombreColumna: "  + nombreColumna);
                                                for( java.lang.reflect.Method m : mapeadora.seters())
                                                {
                                                        System.out.println("NOMBRE CLASE = " + m.getName().toLowerCase().substring(3, m.getName().length()));
                                                        if(m.getName().toLowerCase().substring(3, m.getName().length()).equalsIgnoreCase(nombreColumna))
                                                        {
                                                                System.out.println("true");
                                                                mapeadora.invoke(m , rs.getString(i));
                                                        }
                                                }
                                                
                                                //arrFila.add(rs.getString(i));
                                        }
                                        //arrSalida.add(arrFila);
                                        arrSalida.add(mapeadora.getIntancia());
                                }
                                
                                statement.close();
                        } 
                        catch (Exception e)
                        {
                                e.printStackTrace();
                        }   
                }
                else
                {
                        System.out.println("CONEXION = NULL");
                        conectar();
                        arrSalida = query(obj,sql);
                }
                return arrSalida;
        }
        public static void execute(String sql)
        {
                 if(conexion != null)
                {
                        try
                        {       
                                statement = conexion.createStatement();
                                statement.executeUpdate(sql);
                                statement.close();
                                
                                conexion.commit();
                        } 
                        catch (Exception e)
                        {
                                e.printStackTrace();
                        }   
                }
                else
                {
                        conectar();
                        execute(sql);
                }
        }
        public  static void close()
        {
                try
                {
                        conexion.close();
                } 
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }
}
