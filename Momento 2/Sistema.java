import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Sistema {

    public static void llenarManifiesto(Contenedor[] m) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el número de contenedores: ");
        int n = sc.nextInt();
        for (int i = 0; i < n && i < m.length; i++) {
            System.out.print("ID del contenedor " + (i + 1) + ": ");
            String id = sc.next();
            System.out.print("Peso: ");
            double peso = sc.nextDouble();
            System.out.print("Prioridad: ");
            int prioridad = sc.nextInt();
            m[i] = new Contenedor(id, peso, prioridad);
        }
        sc.close();
    }

    public static double pesoTotal(Contenedor[] m) {
        double total = 0;

        for (int i = 0; i < m.length; i++) {
            if (m[i] != null) {
                total += m[i].peso;
            }
        }

        return total;
    }

    public static void ubicarEnPatio(Contenedor[][] patio, Contenedor c) {

        for (int i = 0; i < patio.length; i++) {
            for (int j = 0; j < patio[i].length; j++) {

                if (patio[i][j] == null) {
                    patio[i][j] = c;
                    return;
                }
            }
        }

        System.out.println("Puerto Saturado");
    }

    public static void quitarFondo(Stack<Contenedor> pila) {

        Stack<Contenedor> aux = new Stack<>();

        while (pila.size() > 1) {
            aux.push(pila.pop());
        }

        System.out.println("Eliminado: " + pila.pop().id);

        while (!aux.isEmpty()) {
            pila.push(aux.pop());
        }
    }

    public static void main(String[] args) {

        Contenedor[] manifiesto = new Contenedor[10];
        Contenedor[][] patio = new Contenedor[5][5];
        Queue<Contenedor> inspeccion = new ArrayDeque<>();
        Stack<Contenedor> buque = new Stack<>();

        // 1. Llenar datos
        llenarManifiesto(manifiesto);

        // 2. Mostrar peso total
        System.out.println("Peso total: " + pesoTotal(manifiesto));

        // 3. Procesar contenedores
        for (int i = 0; i < manifiesto.length; i++) {

            Contenedor c = manifiesto[i];

            if (c != null) {

                // Ubicar en patio
                ubicarEnPatio(patio, c);

                // Cola (FIFO) - prioridad alta
                if (c.prioridad == 1) {
                    inspeccion.add(c);
                }

                // Pila (LIFO)
                buque.push(c);
            }
        }

        quitarFondo(buque);

        // 4. Inspección (cola)
        while (!inspeccion.isEmpty()) {
            Contenedor c = inspeccion.poll();
            System.out.println("Inspeccionando: " + c.id);
        }

        // 5. Buque (pila)
        while (!buque.isEmpty()) {
            Contenedor c = buque.pop();
            System.out.println("Descargando: " + c.id);
        }
    }
}