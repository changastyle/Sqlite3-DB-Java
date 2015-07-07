package model;
public class Sueño
{
        private String id;
        private String sueño;
        
        public Sueño()
        {
                id = "";
                sueño = "";
        }

        public String getId()
        {
                return id;
        }

        public void setId(String id)
        {
                this.id = id;
        }

        public String getSueño()
        {
                return sueño;
        }

        public void setSueño(String sueño)
        {
                this.sueño = sueño;
        }

        @Override
        public String toString()
        {
                return "Sue\u00f1o{" + "id=" + id + ", sue\u00f1o=" + sueño + '}';
        }
        
        
}
