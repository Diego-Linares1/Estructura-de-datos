
package registrobasico;

public class Nodo2 {
    // Atributos
    private Nodo2 enlace;
    private Persona2 informacion;
    
    public Nodo2(Persona2 informacion) {
        this.informacion = informacion;
        enlace = null;
    }
    
    public Nodo2(Persona2 informacion, Nodo2 enlace) {
        this.informacion = informacion;
        this.enlace = enlace;
    }

    public Nodo2 getEnlace() {
        return enlace;
    }

    public void setEnlace(Nodo2 enlace) {
        this.enlace = enlace;
    }

    public Persona2 getInformacion() {
        return informacion;
    }

    public void setInformacion(Persona2 informacion) {
        this.informacion = informacion;
    }
    
}
