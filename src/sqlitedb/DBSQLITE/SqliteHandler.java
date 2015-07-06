package sqlitedb.DBSQLITE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SqliteHandler
{
        private Connection conexion = null;
        private Statement statement = null;
        private String rutaDB = "test.db";
                
        public void conectar()
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
        public ArrayList<Object> query(String sql)
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
        public void execute(String sql)
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
        public void close()
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
