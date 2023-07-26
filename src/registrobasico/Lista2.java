
package registrobasico;

import javax.swing.table.DefaultTableModel;

public class Lista2 {
    // Atributos
    private Nodo2 inicio;
    private int tamanio;
    
    public Lista2() {
        this.inicio = null;
        this.tamanio = 0;
    }
    
    public void ingreso(Persona2 persona) {
        Nodo2 nuevoNodo = new Nodo2(persona);
        
        if (inicio == null) {
            inicio = nuevoNodo;
        } 
        else {
            Nodo2 aux = inicio;
            while (aux.getEnlace() != null) {
                aux = aux.getEnlace();
            }
            aux.setEnlace(nuevoNodo);
        }
        tamanio++;
    }
    
    // eliminar nodo
    public void eliminar(int codigo) {
        if (inicio == null) {
            // La lista está vacía, no hay nodos para eliminar
            return;
        }

        if (inicio.getInformacion().getCodigo() == codigo) {
            // El nodo a eliminar es el nodo de inicio
            inicio = inicio.getEnlace();
            tamanio--;
            return;
        }

        Nodo2 anterior = inicio;
        Nodo2 actual = inicio.getEnlace();

        while (actual != null) {
            if (actual.getInformacion().getCodigo() == codigo) {
                // Se encontró el nodo a eliminar
                anterior.setEnlace(actual.getEnlace());
                tamanio--;
                return;
            }

            anterior = actual;
            actual = actual.getEnlace();
        }
        // El nodo a eliminar no se encontró en la lista
    }

    
    public DefaultTableModel imprimirEnJTable() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");
        modelo.addColumn("Sexo");

        Nodo2 actual = inicio;
        while (actual != null) {
            Persona2 persona = actual.getInformacion();
            Object[] fila = {persona.getCodigo(), persona.getNombre(), persona.getApellido(), persona.getEdad(),persona.getSexo()};
            modelo.addRow(fila);
            actual = actual.getEnlace();
        }

        return modelo;
    }
    
    // Ordenamiento de fecha por quicksort
    public void ordenarQuickSort() {
        inicio = quickSort(inicio);
    }
    
    private Nodo2 quickSort(Nodo2 nodo) {
        if (nodo == null || nodo.getEnlace() == null) {
            return nodo;
        }
        
        Nodo2 pivote = nodo;
        Nodo2 menor = null;
        Nodo2 mayor = null;
        Nodo2 temp = nodo.getEnlace();
        
        while (temp != null) {
            Nodo2 siguiente = temp.getEnlace();
            temp.setEnlace(null);
            
            if (temp.getInformacion().getCodigo() < pivote.getInformacion().getCodigo()) {
                if (menor == null) {
                    menor = temp;
                } else {
                    Nodo2 tempMenor = menor;
                    while (tempMenor.getEnlace() != null) {
                        tempMenor = tempMenor.getEnlace();
                    }
                    tempMenor.setEnlace(temp);
                }
            } else {
                if (mayor == null) {
                    mayor = temp;
                } else {
                    Nodo2 tempMayor = mayor;
                    while (tempMayor.getEnlace() != null) {
                        tempMayor = tempMayor.getEnlace();
                    }
                    tempMayor.setEnlace(temp);
                }
            }
            
            temp = siguiente;
        }
        
        menor = quickSort(menor);
        mayor = quickSort(mayor);
        
        if (menor != null) {
            Nodo2 tempMenor = menor;
            while (tempMenor.getEnlace() != null) {
                tempMenor = tempMenor.getEnlace();
            }
            tempMenor.setEnlace(pivote);
            pivote.setEnlace(mayor);
            return menor;
        } else {
            pivote.setEnlace(mayor);
            return pivote;
        }
    }
    
    // Ordenamiento de nombre por MergeSort 
    public void listaMergeSort() {
        inicio = mergeSortNombre(inicio);
    }

    private Nodo2 mergeSortNombre(Nodo2 nodo) {
        if (nodo == null || nodo.getEnlace() == null) {
            return nodo;
        }

        Nodo2 mitad = dividir(nodo);
        Nodo2 mitadSiguiente = mitad.getEnlace();
        mitad.setEnlace(null);

        Nodo2 izquierda = mergeSortNombre(nodo);
        Nodo2 derecha = mergeSortNombre(mitadSiguiente);

        return combinarNombre(izquierda, derecha);
    }

    private Nodo2 dividir(Nodo2 nodo) {
        if (nodo == null || nodo.getEnlace() == null) {
            return nodo;
        }

        Nodo2 lento = nodo;
        Nodo2 rapido = nodo.getEnlace();

        while (rapido != null && rapido.getEnlace() != null) {
            lento = lento.getEnlace();
            rapido = rapido.getEnlace().getEnlace();
        }

        return lento;
    }

    private Nodo2 combinarNombre(Nodo2 izquierda, Nodo2 derecha) {
        if (izquierda == null) {
            return derecha;
        }

        if (derecha == null) {
            return izquierda;
        }

        Nodo2 resultado;

        if (izquierda.getInformacion().getNombre().compareToIgnoreCase(derecha.getInformacion().getNombre()) <= 0) {
            resultado = izquierda;
            resultado.setEnlace(combinarNombre(izquierda.getEnlace(), derecha));
        } else {
            resultado = derecha;
            resultado.setEnlace(combinarNombre(izquierda, derecha.getEnlace()));
        }

        return resultado;
    }
    
    // Ordenamiento de edad por HeapSort
    public void listaHeapSort() {
        // Convertir la lista enlazada a un arreglo
        Nodo2[] arreglo = convertirArreglo();

        int n = arreglo.length;

        // Construir el heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyEdad(arreglo, n, i);
        }

        // Extraer los elementos del heap uno por uno en orden descendente
        for (int i = n - 1; i > 0; i--) {
            intercambiarNodos(arreglo, 0, i);
            heapifyEdad(arreglo, i, 0);
        }

        // Reconstruir la lista enlazada a partir del arreglo ordenado
        reconstruirLista(arreglo);
    }

    private void heapifyEdad(Nodo2[] arreglo, int n, int indice) {
        int maximo = indice;
        int izquierda = 2 * indice + 1;
        int derecha = 2 * indice + 2;

        if (izquierda < n && arreglo[izquierda].getInformacion().getEdad() > arreglo[maximo].getInformacion().getEdad()) {
            maximo = izquierda;
        }

        if (derecha < n && arreglo[derecha].getInformacion().getEdad() > arreglo[maximo].getInformacion().getEdad()) {
            maximo = derecha;
        }

        if (maximo != indice) {
            intercambiarNodos(arreglo, indice, maximo);
            heapifyEdad(arreglo, n, maximo);
        }
    }

    private void intercambiarNodos(Nodo2[] arreglo, int indice1, int indice2) {
        Nodo2 temp = arreglo[indice1];
        arreglo[indice1] = arreglo[indice2];
        arreglo[indice2] = temp;
    }

    private void reconstruirLista(Nodo2[] arreglo) {
        inicio = arreglo[0];
        Nodo2 actual = inicio;

        for (int i = 1; i < arreglo.length; i++) {
            actual.setEnlace(arreglo[i]);
            actual = actual.getEnlace();
        }

        actual.setEnlace(null);
    }

    private Nodo2[] convertirArreglo() {
        Nodo2[] arreglo = new Nodo2[tamanio];
        Nodo2 actual = inicio;
        int indice = 0;

        while (actual != null) {
            arreglo[indice] = actual;
            actual = actual.getEnlace();
            indice++;
        }

        return arreglo;
    }

}
