//package com.aluracursos.screenmatch;
//
//import com.aluracursos.screenmatch.principal.Principal;
//import com.aluracursos.screenmatch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationConsola implements CommandLineRunner {
//
//	@Autowired                             // Indica a Spring que haga una inyeccion de dependencias
//	private SerieRepository repository;   //dependencia que necesita que spring haga Inyeccion
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplicationConsola.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Principal principal = new Principal(repository); // lo enviamos en el constructor de la clase principal
//		principal.muestraElMenu();
//
//
//
//	}
//}





