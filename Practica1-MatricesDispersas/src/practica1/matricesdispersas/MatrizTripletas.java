package practica1.matricesdispersas;

import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/** Representación de matrices como matrices dispersas utilizando tripletas.
 *
 * @author JoseMAGG, leomare12
 */
public class MatrizTripletas {
    private String nombre;
    protected Tripleta[] tripletas;

    /**
     * Construye una matriz en tripleta vacia, con un tamaño de arreglo del
     * unico caso y este es que todas las posiciones esten llenas con cero.
     *
     * @param f número de filas de la matriz
     * @param c número de columnas de la matriz
     * @param nombre identificador de la matriz
     */
    public MatrizTripletas(int f, int c, String nombre) { 
        this.tripletas = new Tripleta[1];
        Tripleta configuracion = new Tripleta(f, c, 0);
        tripletas[0] = configuracion;
        this.nombre = nombre;
    }
    
    /**
     * Construye una matriz en tripleta vacia, con un tamaño de arreglo del
     * unico caso y este es que todas las posiciones esten llenas con cero.
     *
     * @param f número de filas de la matriz
     * @param c número de columnas de la matriz
     */
    public MatrizTripletas(int f, int c){
        this.tripletas = new Tripleta[1];
        Tripleta configuracion = new Tripleta(f, c, 0);
        tripletas[0] = configuracion;
    }
     
    /** Calcula la matriz adjunta de la matriz que llama el método y se guarda
     *  en una nueva matriz con nombre (nombre de la matriz) + "Adj".
     * 
     * @return Matriz adjunta
     * @throws Exception 
     */
    public MatrizTripletas adjunta() throws Exception {
        MatrizTripletas adjunta = cofactores().traspuesta();
        if(nombre != null) adjunta.setNombre(nombre + " Adj");
        return adjunta;
    }
    
    /** Calcula la matriz de cofactores de la matriz que llama el método y se guarda
     *  en una nueva matriz con nombre (nombre de la matriz) + "Cof".
     * 
     * @return Matriz de cofactores
     * @throws Exception 
     */
    public MatrizTripletas cofactores() throws Exception{
        int filas = getConfiguracion().getF();
        int columnas = getConfiguracion().getC();
        MatrizTripletas cofactores = new MatrizTripletas(filas, columnas);
        if(nombre != null) cofactores.setNombre(nombre + " Cof");
        double celda;
        for(int i=1; i<=filas; i++){
            for(int j=1; j<=columnas; j++){
                celda = Math.pow(-1,(i+j)) * matrizSin(i, j).determinante();
                cofactores.setCelda(i, j, celda);
            }
        }
        return cofactores;
    }
    
    /** Calcula el determinante de la matriz que invoca el método siempre y cuando sea cuadrada.
     * 
     * @return deerminante de la matriz nxn.
     * @throws Exception 
     */
    public double determinante() throws Exception{
        Tripleta configuracion = getConfiguracion();
        int filas = configuracion.getF();
        if(filas != configuracion.getC()) throw new 
            Exception("La matriz debe ser cuadrada para calcular su determinante");
        if(filas == 1) return getValorEn(1, 1);
        if(filas == 2) return determinante2x2();
        if(filas == 3) return determinante3x3();
        double det = 0;
        for(int i = 1; i<= filas; i++){
            det = det + (getValorEn(1, i)* Math.pow(-1,(1+i)) * matrizSin(1, i).determinante());
        }
        return det;
    }
    
    /** Calcula el determinante de la matriz que llama al método, siempre y cuando
     * esta tenga tamaño 2x2, 2 filas y 2 columnas.
     * 
     * @return determinante de la matriz 2x2
     * @throws Exception 
     */
    private double determinante2x2() throws Exception{
        Tripleta configuracion = getConfiguracion();
        int filas = configuracion.getF();
        if(filas != 2 || configuracion.getC() != 2) throw new 
            Exception("La matriz debe ser de 2 filas y 2 columnas para calcular su determinante");
        double determinante = (getValorEn(1, 1) * getValorEn(2, 2)) - (getValorEn(1, 2) * getValorEn(2, 1));
        return determinante;
    }
    
    /** Calcula el determinante de la matriz que llama al método utilizando el método de
     * Sarrus, siempre y cuando la matriz tenga tamaño 3x3, 3 filas y 3 columnas.
     * 
     * @return determinante de la matriz 3x3
     * @throws Exception 
     */
    private double determinante3x3() throws Exception{
        Tripleta configuracion = getConfiguracion();
        int filas = configuracion.getF();
        if(filas != 3 || configuracion.getC() != 3) throw new 
            Exception("La matriz debe ser de 3 filas y 3 columnas para calcular su determinante");
        MatrizTripletas positivosDeterminante = new MatrizTripletas(filas, filas);
        int aux1, aux2;
        for(int i = 1; i <= filas; i++){
            aux1 = 1;
            for(int j = 1; j <= filas; j++){
                aux2 = aux1 - i;
                if(aux2 < 1) aux2 += filas;
                positivosDeterminante.setCelda(aux2, i, getValorEn(i, j));
                aux1++;
            }
        }
        MatrizTripletas negativosDeterminante = new MatrizTripletas(filas, filas);
        for(int i = 1; i <= filas; i++){
            aux1 = filas;
            for(int j = 1; j <= filas; j++){
                aux2 = aux1 - i;
                if(aux2 < 1) aux2 += filas;
                negativosDeterminante.setCelda(aux2, i, getValorEn(i, j));
                aux1--;
            }
        }
        
        //multiplicar las filas y sumarlas
        double positivos = 0, negativos = 0;
        for(int i = 1; i <= filas; i++){
            aux1 = 1;
            aux2 = 1;
            for(int j = 1; j <= filas; j++){
                aux1 *= positivosDeterminante.getValorEn(i, j);
                aux2 *= negativosDeterminante.getValorEn(i, j);
            }
            positivos += aux1;
            negativos += aux2;
        }
        return positivos - negativos;
    }
    
    /** Devuelve la tripleta de configuración que contiene el tamaño de la matriz
     * y el número de valores diferentes de 0.
     * 
     * @return configuracion
     */
    public Tripleta getConfiguracion(){return tripletas[0];}
    
    public String getNombre() {return nombre;}
    
    /** Devuelve el valor en la fila y columna dadas, si no hay dato en esa posición, devuelve 0.
     * 
     * @param fila
     * @param columna
     * @return valor en la posición dada por fila y columna
     */
    public double getValorEn(int fila, int columna){
        Tripleta recorrido;
        for( int i = 1; i<tripletas.length; i++){
            recorrido = tripletas[i];
            if(recorrido.getF() == fila && recorrido.getC() == columna) return recorrido.getV();
            if(recorrido.getF() > fila) break;
        }
        return  0;
    }
    
    /** Calcula la inversa de la matriz que llama el método y la retorna. 
     * 
     * @return matriz inversa
     * @throws Exception 
     */
    public MatrizTripletas inversa() throws Exception{
        double det = determinante();
        if(det == 0) throw new Exception("No se puede invertir la matriz porque es singular (su determinante es 0).");
        else{
            MatrizTripletas adj = adjunta();
            MatrizTripletas inversa = adj.multiplicarXConstante(1/det);
            if(nombre != null) inversa.setNombre(nombre + " Inversa");
            return inversa;
        }
    }
    
    /** Entrega la misma matriz que invoca el método sin la fila ni la columna ingresadas como parámetros.
     * Se le conoce como menor de la matriz.
     * La matriz queda reducida en 1 su número de fílas y de columnas.
     * 
     * @param fila
     * @param columna
     * @return
     * @throws Exception 
     */
    public MatrizTripletas matrizSin(int fila, int columna) throws Exception{
        MatrizTripletas matrizSin = new MatrizTripletas(getConfiguracion().getF() - 1, getConfiguracion().getC() - 1);
        for (int i=1; i<tripletas.length; i++){
            Tripleta recorrido = tripletas[i];
            if(recorrido.getF() != fila && recorrido.getC() != columna && recorrido.getV() != 0){
                int filaDestino = recorrido.getF(), columDestino = recorrido.getC();
                if(recorrido.getF() > fila) filaDestino--;
                if(recorrido.getC() > columna) columDestino--;
                matrizSin.setCelda(filaDestino, columDestino, recorrido.getV());
            }
        }
        return matrizSin;
    }
    
    /**Muestra la matriz representada en tripletas en un JTable que se pasa como
     * parámetro, escribiendo el tamaño de la matriz en el título de la primera
     * columna, el número de columna en el título de cada columna y el número de
     * fila en la primera columna de cada fila.
     * 
     * @param tabla 
     */
    public void mostrarEnTabla(JTable tabla){
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tabla.getColumnModel();
        int filas = getConfiguracion().getF();
        int columnas = getConfiguracion().getC() + 1;
        model.setRowCount(filas);
        model.setColumnCount(columnas);
        tabla.getColumn(columnModel.getColumn(0).getIdentifier()).setHeaderValue(filas + " x " + (columnas - 1));
        for(int i = 0; i<filas; i++){
            model.setValueAt(i + 1, i, 0);
            for(int j = 1; j<columnas; j++){                
                tabla.getColumn(columnModel.getColumn(j).getIdentifier()).setHeaderValue(j);
                model.setValueAt(null, i, j);
            }
        }
        for(int i = 1; i<tripletas.length; i++){
            model.setValueAt(tripletas[i].getV(), tripletas[i].getF() - 1, tripletas[i].getC());
        }
    }
    
    /** Multiplica la matriz que llama el método con la constante de tipo double 
     * que se pasa como parámetro.
     * 
     * @param constante
     * @return Matriz multiplicada por la constante.
     * @throws Exception 
     */
    private MatrizTripletas multiplicarXConstante(double constante) throws Exception{
        MatrizTripletas matriz = new MatrizTripletas(getConfiguracion().getF(), getConfiguracion().getC());
        if(constante == 0) return matriz;
        for (int i = 1; i< tripletas.length; i++){
            Tripleta recorrido = tripletas[i];
            matriz.setCelda(recorrido.getF(), recorrido.getC(), constante * recorrido.getV());
        }
        return matriz;
    }
    
    /**
     * Fijar un valor valido diferente de Cero en una celda
     *
     * @param filaDestino
     * @param columnaDestino
     * @param datoDestino
     * @throws java.lang.Exception
     */
    public void setCelda(int filaDestino, int columnaDestino, double datoDestino) throws Exception {
        if(datoDestino == 0) return;
        Tripleta configuracion = tripletas[0];

        /**
         * Valido limites
         */
        int filas = configuracion.getF();
        int columnas = configuracion.getC();

        if (filaDestino <= 0 || filas < filaDestino || columnaDestino <= 0 || columnas < columnaDestino) {
            throw new Exception("Esta fuera de los limites de la matriz");
        }

        /**
         * Fijamos el valor
         */
        int cantidadDatosActual = (int) configuracion.getV();
        Tripleta celdaDestino = null;
        int posicionInsertar = cantidadDatosActual + 1;
        for (int r = 1; cantidadDatosActual >= r; r++) {
            Tripleta tripletaRecorrido = tripletas[r];
            int filaRecorrido = tripletaRecorrido.getF();
            if (filaDestino > filaRecorrido) {
                // No hago nada
            } else if (filaDestino == filaRecorrido) {
                int columnaRecorrido = tripletaRecorrido.getC();
                if (columnaDestino > columnaRecorrido) {
                    // No hago nada  
                } else if (columnaDestino == columnaRecorrido) {
                    // La celda esta en el arreglo de tripletas
                    // Se debe realizar reemplazo del valor
                    celdaDestino = tripletaRecorrido;
                } else {
                    posicionInsertar = r;
                    break;
                }
            } else {
                posicionInsertar = r;
                break;
            }
        }

        /**
         * Valido si encontre la celda en el arreglo
         */
        if (celdaDestino != null) {
            celdaDestino.setV(datoDestino);
        } else {
            // Realiza crecimiento dinamico del arreglo de tripletas
            // Copiando en un nuevo arreglo todas las tripletas
            celdaDestino = new Tripleta(filaDestino, columnaDestino, datoDestino);
            Tripleta[] nuevasTripletas = new Tripleta[cantidadDatosActual + 1 + 1];
            configuracion.setV(cantidadDatosActual + 1);
            for (int i = 0; i < nuevasTripletas.length; i++) {
                if (i < posicionInsertar) {
                    nuevasTripletas[i] = tripletas[i];
                } else if (i == posicionInsertar) {
                    nuevasTripletas[i] = celdaDestino;
                } else {
                    nuevasTripletas[i] = tripletas[i - 1];
                }
            }
            tripletas = nuevasTripletas;
        }
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    /** Suma la matriz que llama al método con otra matriz que se pasa como
     * parámetro, siempre y cuando ambas matrices sean de igual tamaño.
     * 
     * @param matrizB
     * @return La suma de las dos matrices
     * @throws Exception 
     */
    public MatrizTripletas sumarMatriz(MatrizTripletas matrizB) throws Exception{
        Tripleta configuracion = getConfiguracion();
        Tripleta configuracionB = matrizB.getConfiguracion();
        
        if(configuracion.getF() == configuracionB.getF() && configuracion.getC() == configuracionB.getC()){
            MatrizTripletas matrizSuma = new MatrizTripletas(configuracion.getF(), configuracion.getC(),
                    (getNombre() + " + " + matrizB.getNombre()));
            for(int i = 1; i <= configuracion.getF(); i++){
                for(int j = 1; j <= configuracion.getC(); j++){
                    double suma = getValorEn(i, j) + matrizB.getValorEn(i, j);
                    matrizSuma.setCelda(i, j, suma);
                }
            }
            return matrizSuma;
        }
        throw new Exception("Las matrices deben ser de igual tamaño para poder realizar la suma.");
    }
    
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        // Obtengo la configuración de la matriz, fr y cr y la cantidadValores
        Tripleta configuracion = this.tripletas[0];
        int cantidadFilasMatriz = configuracion.getF();
        int cantidadColumnasMatriz = configuracion.getC();
        int valoresDiferentesCero = (int) configuracion.getV();

        int posicionArreglo = 1;

        cadena.append("\t");
        for (int columnasVirtuales = 1; columnasVirtuales <= cantidadColumnasMatriz; columnasVirtuales++) {
            cadena.append(columnasVirtuales + "\t");
        }
        cadena.append("\n");
        // Recorrido por una matriz virtual m x n
        for (int filasVirtuales = 1; filasVirtuales <= cantidadFilasMatriz; filasVirtuales++) {
            cadena.append(" " + filasVirtuales + " \t");
            for (int columnasVirtuales = 1; columnasVirtuales <= cantidadColumnasMatriz; columnasVirtuales++) {
                if (posicionArreglo <= valoresDiferentesCero) {
                    // Estoy en una posicion valida en el arreglo
                    Tripleta posibleTripletaMostrar = tripletas[posicionArreglo];
                    int filaCeldaMostrar = posibleTripletaMostrar.getF();
                    int columnaCeldaMostrar = posibleTripletaMostrar.getC();
                    if (filasVirtuales == filaCeldaMostrar) {
                        if (columnasVirtuales == columnaCeldaMostrar) {
                            Object valorCeldaMostrar = posibleTripletaMostrar.getV();
                            if (valorCeldaMostrar != null) {
                                cadena.append(valorCeldaMostrar + "\t");
                            } else {
                                cadena.append("0.0" + "\t");
                            }
                            posicionArreglo++;
                        } else {
                            cadena.append("0.0" + "\t");
                        }
                    } else {
                        cadena.append("0.0" + "\t");
                    }
                } else {
                    cadena.append("0.0" + "\t");
                }
            }
            cadena.append("\n");
        }
        return cadena.toString();
        }
    
    /** Calcula la matriz traspuesta de la matriz que llama el método y se guarda
     *  en una nueva matriz con nombre (nombre de la matriz) + "Tras".
     * 
     * @return Matriz traspuesta
     * @throws Exception 
     */
    public MatrizTripletas traspuesta() throws Exception{        
        int filas = getConfiguracion().getF();
        int columnas = getConfiguracion().getC();
        MatrizTripletas traspuesta = new MatrizTripletas(columnas, filas);
        if(nombre != null) traspuesta.setNombre(nombre + " Tras");
        for (int i = 1; i< tripletas.length; i++){
            Tripleta recorrido = tripletas[i];
            traspuesta.setCelda(recorrido.getC(), recorrido.getF(), recorrido.getV());
        }
        return traspuesta;
    }
    
    /*
    public MatrizTripletas matrizSin(int fila, int columna) throws Exception{
        int c = 1, d = 1;
        Tripleta configuracion = getConfiguracion();
        int filas = configuracion.getF();
        int columnas = configuracion.getC();
        MatrizTripletas matrizSin = new MatrizTripletas(filas - 1, columnas - 1);
        for(int i = 1; i<=filas; i++){
            for(int j = 1; j<=columnas; j++){
                while( i != fila && j != columna && getValorEn(i, j) != 0){
                    if(d == filas){
                        c++;
                        d = 1;
                    }
                    System.out.printf("c: %d, d: %d \n", c,d);
                    System.out.println(matrizSin);
                    matrizSin.setCelda(c, d, getValorEn(i, j));
                    d++;
                }
            }
        }
    }*/
}
