package Reflection;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mapeadora
{
        private Class clase;
        private Object intancia;
        
        public Mapeadora(Class clase)
        {
                try
                {
                        this.clase = clase;
                        this.intancia = clase.newInstance();
                        
                        //System.out.println("MAPEARE OBJ DEL TIPO :" + clase.getName());
                } catch (Exception ex)
                {
                       ex.printStackTrace();
                }
        }
        public ArrayList<Field> publicAttributes()
        {
                ArrayList<Field> fields = new ArrayList<Field>();
                
                for(int i = 0; i < clase.getFields().length ;i++)
                {
                        fields.add(clase.getFields()[i]);
                }
                return fields;
        }
        public ArrayList<String> privateAttributes()
        {
                ArrayList<String> arr  = new ArrayList<String>();
                
                try
                {
                        for (int i = 0 ; i < clase.getDeclaredMethods().length; i ++)
                        {
                                String name = clase.getDeclaredMethods()[i].getName() ;
                                if (name.startsWith("get"))
                                {
                                        String property = name.substring(3,4).toLowerCase() +  name.substring(4,name.length());
                                        //System.out.println("GETTER:" + property);
                                        arr.add(property);
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                } 
                return arr;
        }
        public ArrayList<Method> geters()
        {
                ArrayList<Method> methods = new ArrayList<Method>();
                
                for (int i = 0 ; i < clase.getDeclaredMethods().length; i ++)
                {
                        //System.out.println("" + i + " | " + clase.getDeclaredMethods()[i].getName());
                        String name = clase.getDeclaredMethods()[i].getName() ;
                         if (name.startsWith("get"))
                        {
                                String property = name.substring(3,4).toLowerCase() +  name.substring(4,name.length());
                                //System.out.println("GETTER:" + property);
                                methods.add(clase.getDeclaredMethods()[i]);
                        }
                }
                
                return methods;
        }
        public ArrayList<Method> seters()
        {
                ArrayList<Method> methods = new ArrayList<Method>();
                
                for (int i = 0 ; i < clase.getDeclaredMethods().length; i ++)
                {
                        //System.out.println("" + i + " | " + clase.getDeclaredMethods()[i].getName());
                        String name = clase.getDeclaredMethods()[i].getName() ;
                         if (name.startsWith("set"))
                        {
                                String property = name.substring(3,4).toLowerCase() +  name.substring(4,name.length());
                                //System.out.println("GETTER:" + property);
                                methods.add(clase.getDeclaredMethods()[i]);
                        }
                }
                
                return methods;
        }
         public void invoke(String methodName)
        {
                try
                {
                        Method method = clase.getMethod(methodName);
                        method.invoke(intancia);
                } 
                catch (Exception ex)
                {
                        ex.printStackTrace();
                } 
        }
        public void invoke(Method m,Object parametro)
        {
                try
                {
                        //Method method = clase.getMethod(methodName);
                        m.invoke(intancia, parametro);
                        
                } 
                catch (Exception ex)
                {
                        ex.printStackTrace();
                } 
        }
        public String toString()
        {
                String salida ="CLASE: " +  clase.getName();
                salida +="\nINSTANCIA: " + intancia.toString()  ;
            
                salida +="\nPUBLIC ATTIBUTES:"  ;
                for(Field f: this.publicAttributes())
                {
                        salida +="\n    " + f.getName();
                }
                
                salida +="\nPRIVATE ATTIBUTES:"  ;
                for(String s: this.privateAttributes())
                {
                        salida +="\n    " + s;
                }
                
                salida +="\nGETTERS:"  ;
                for(Method get: this.geters())
                {
                        salida +="\n    " + get.getName() ;
                }
                salida +="\nSETTERS:"  ;
                for(Method get: this.seters())
                {
                        salida +="\n    " + get.getName() ;
                }
                
                
                return salida;
        }

        public Class getClase()
        {
                return clase;
        }

        public void setClase(Class clase)
        {
                this.clase = clase;
        }

        public Object getIntancia()
        {
                return intancia;
        }

        public void setIntancia(Object intancia)
        {
                this.intancia = intancia;
        }
        
        
}
