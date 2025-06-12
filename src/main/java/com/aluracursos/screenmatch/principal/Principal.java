package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=464cb2ce";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries= new ArrayList<>();              //para series web
    private  SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Principal(SerieRepository repository) {     // Constructor
       this. repositorio = repository;
    }


    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo  
                    5 - Top 5 Mejores Series
                    6-  Buscar Serie por categoria 
                    7-  Filtrar series por temporadas y evaluation   
                    8- Buscar episodios por titulo 
                    9- Top 5 episodios por Serie      
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;

                case 5:
                    buscarTop5Series();
                    break;

                case 6:
                    buscarSeriesPorCategoria();
                    break;

                case 7:
                filtrarSeriesPorTemporadasYEvaluacion();
                break;

                case 8:

                    buscarEpisodiosPorTitulo();
                    break;

                case 9:

                    buscarTop5Episodios();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarTop5Episodios() {
        buscarSeriesPorTitulo();

      if (serieBuscada.isPresent()){

          Serie serie = serieBuscada.get();
          List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
          topEpisodios.forEach(e -> System.out.printf("Serie: %s Temporada %s Episodio %s Evaluacion %s\n",
                  e.getSerie(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));


      }

    }

    private void buscarEpisodiosPorTitulo() {
        System.out.println("Escribe el nombre del episodio que deseas buscar");
        var nombreEpisodio= teclado.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s Temporada %s Episodio %s Evaluacion %s\n",
                e.getSerie(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }

    private void filtrarSeriesPorTemporadasYEvaluacion() {
        System.out.println("¿Filtrar series con cuantas temporadas? ");
        var  totalTemporadas = teclado.nextInt();
        teclado.nextLine();

        System.out.println("¿Evaluacion, apartir de cual valor? ");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();
        List<Serie> filtroSeries = repositorio.seriePorTemporadaYEvaluacion(totalTemporadas,evaluacion);
        System.out.println("*** Series filtradas ***");
        filtroSeries.forEach(s ->
        System.out.println(s.getTitulo() + " - evaluacion: " + s.getEvaluacion()));

    }

    private void buscarSeriesPorCategoria() {

        System.out.println("Escriba el genero/categoria de la serie que desea buscar");
        var genero = teclado.nextLine();
        var categotia = Categoria.fromEspañol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categotia);
        System.out.println("Las series de la categoria " + genero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarTop5Series() {

        List <Serie> top5Series = repositorio.findTop5ByOrderByEvaluacionDesc();
        top5Series.forEach(s -> System.out.println("Serie: " + s.getTitulo() + "Evaluacion: " + s.getEvaluacion()));
    }

    private void buscarSeriesPorTitulo() {

        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie=teclado.nextLine();

        serieBuscada = repositorio.findByTituloContainsIgnoreCase((nombreSerie)); //Envia nombre a SerieRepository

        if (serieBuscada.isPresent()){
            System.out.println("La serie buscada es: "+ serieBuscada.get());
        }else {
            System.out.println("Serie no encontrada");
        }

    }


    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");  // pide el nombre de la serie a buscar
        var nombreSerie = teclado.nextLine();    // Teclea el nombre de la serie
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY); // Envia URL a consumoApi y obtiene el Json
        System.out.println(json);                            // Imprime el Json
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);  // enviá json y la clase a convertir los datos a Convierte datos y obtiene resultado
        return datos;       //retorna resultados de ConvierteDatos
    }
    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();                                   // se remplaza datos serie por mostrarSeriesBuscadas
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios");
        Scanner scanner = new Scanner(System.in);
        var nombreSerie=teclado.nextLine();

        Optional<Serie> serie= series.stream()
                .filter(s ->s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
    if(serie.isPresent()){
        var serieEncontrada = serie.get();

        List<DatosTemporadas> temporadas = new ArrayList<>();   // Se crea un array list de tipo Datos temporada, que tiene el numero de la temporada y la lista de capitulos

        for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {   // genera ciclo for para recorrer cada temporada
            var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);// envia la url a obtenerDatos de ConsumoApi, y obtiene el jason
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class); //crea variable datosTemporada como tipo DatosTemporadas y envia el json y la clase(DatosTemporada) a ConvierteDatos() y obtiene
            // el resultado de la conversion del json a java
            temporadas.add(datosTemporada); // agrega los datos al array temporadas
        }
        temporadas.forEach(System.out::println); //imprime los datos en la consola de los episodios

        List<Episodio> episodios=temporadas.stream()
                .flatMap(d -> d.episodios().stream()
                        .map(e -> new Episodio(d.numero(),e)))
                .collect(Collectors.toList());

        serieEncontrada.setEpisodios(episodios);
        repositorio.save(serieEncontrada);

        //System.out.println("Episodios encontrados:");
        //episodios.forEach(System.out::println);



    }


        // se corto el codigo y se pego en el if de arriba
       // DatosSerie datosSerie = getDatosSerie();                // Se declara la variable datosSerie como tipo Datoserie y se le da el valor de getDatosSerie que tiene los datos convertidos a la clase DatosSerie

    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();  // Se crea la variable datos como tipo DatosSerie y se le da el valor de getDatosSerie, el cual retorna los datos convertidos de json a java
        Serie serie = new Serie(datos); // datos obtenidos se envian a Serie
        repositorio.save(serie); // guardamos la serie

       // datosSeries.add(datos);        // se agrega la informacion de los datos al array datosSeries
       System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {

        series = repositorio.findAll();   // se le borra List<Serie> por que se va ha crea una variable global arriba

        //List<Serie> series = repositorio.findAll();                // Se crea array series de tipo Serie el cual tiene la misma informacion de datosSeries, con toString propio
                                                                   // Ahora con repositorio.findAll(), traemos todas las series
        series.stream()                                     //  Toma la lista de series y comienza a procesarla nuevamente como un stream, ya que solo se puede usar el stream una vez
                .sorted(Comparator.comparing(Serie::getGenero)) // Ordena las series por el valor devuelto por el método getGenero().
                .forEach(System.out::println);                  //imprime */

     //------ Codigo anulado---------------------
      /*  series = datosSeries.stream()    // Crea una lista de objetos Serie a partir de una lista de datos crudos (datosSeries), y transforma con stream la lista en una secuencia de datos

                .map(d -> new Serie(d))            //  Usa .map() para transformar cada elemento 'd' de datosSeries en un nuevo objeto Serie.
                .collect(Collectors.toList());              // Recoge (collect) todos los objetos Serie generados en una nueva lista.
        series.stream()                                     //  Toma la lista de series y comienza a procesarla nuevamente como un stream, ya que solo se puede usar el stream una vez
                .sorted(Comparator.comparing(Serie::getGenero)) // Ordena las series por el valor devuelto por el método getGenero().
                .forEach(System.out::println);                  //imprime */

    }


}

