package main;

public class hilos {

   public static void main(String[] args) {

		Runnable tarea = () -> {
			String nombre = Thread.currentThread().getName();
			
			for (int i = 1; i <= 4; i++) {
				System.out.println(nombre + " -> paso " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		System.out.println("=== run(): No crea un hilo, ejecuta la tarea en el hilo principal ===");
		Thread a = new Thread(tarea, "Hilo-A");
		Thread b = new Thread(tarea, "Hilo-B");
		a.run();
		b.run();
		
		System.out.println("=== start(): Crea un hilo nuevo ===");
		Thread c = new Thread(tarea, "Hilo-C");
		Thread d = new Thread(tarea, "Hilo-D");
		c.start();
		d.start();
		
	}
} 

