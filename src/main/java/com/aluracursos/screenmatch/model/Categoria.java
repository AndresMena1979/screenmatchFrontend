package com.aluracursos.screenmatch.model;       //  Define el paquete donde vive esta clase. Esto es parte de la organización del proyecto.

public enum Categoria {                          //  Declara un enum llamado Categoria. Los enum son tipos especiales que representan un conjunto limitado y constante de valores.

    ACCION("Action" , "Acción"),               //  Aquí defines los valores posibles del enum, y cada uno recibe un valor de texto asociado (la categoría que usaría una API externa como OMDb).
    ROMANCE("Romance" , "Romance"),             //  Por ejemplo: ACCION se mapea a "Action"  y COMEDIA se mapea a "Comedy"
    COMEDIA("Comedy" , "Comedia"),
    DRAMA("Drama" , "Drama"),
    CRIMEN("Crime" , "Crimen");

    private String categoriaOmdb;                 //  Campo privado que almacena el nombre de la categoría tal como viene de una fuente externa (por ejemplo, una API como OMDb).
    private String categoriaEspanol;

    Categoria(String categoriaOmdb, String categoriaEspanol){

        this.categoriaOmdb=categoriaOmdb;         //   Constructor del enum. Cada vez que declaras un valor como ACCION("Action"), se llama a este constructor para guardar "Action" en el campo categoriaOmdb.
        this.categoriaEspanol=categoriaEspanol;

    }
   // Constructor
    public static  Categoria fromString(String text) {                    // Método estático que permite convertir una cadena de texto (como "Action") en un valor del enum Categoria.
        for (Categoria categoria : Categoria.values()){                   //  Itera sobre todos los valores definidos en el enum (ACCION, ROMANCE, etc.).
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {         // Compara si el texto (sin distinguir mayúsculas/minúsculas) coincide con alguno de los valores categoriaOmdb. Si lo encuentra, devuelve el valor del enum correspondiente.
                return categoria;
            }
        }
throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);  // Si no encuentra una coincidencia, lanza una excepción, indicando que el texto no corresponde a ninguna categoría conocida.


        // La función fromString tiene como objetivo principal convertir un texto (String) en uno de los valores definidos en el enum Categoria.

     /* Categoria.values()?
        Es un método implícito que se genera automáticamente para todos los enum en Java.
        Devuelve un arreglo (Categoria[]) con todos los valores del enum, en el orden en que fueron declarados.
        Entonces, Categoria.values() devuelve algo como:
        [Categoria.ACCION, Categoria.ROMANCE, Categoria.COMEDIA, Categoria.DRAMA, Categoria.CRIMEN]
        ¿Y qué hace el for-each?
        for (Categoria categoria : Categoria.values()) {
        Código que se ejecuta por cada valor del enum
        Toma cada valor del enum uno por uno.
        Lo guarda temporalmente en la variable categoria.
        Ejecuta el bloque de código con ese valor. */


        }
//----------------------------------------------------------
// Copia de Categoria de arriba

    public static  Categoria fromEspañol(String text) {                    // Método estático que permite convertir una cadena de texto (como "Action") en un valor del enum Categoria.
        for (Categoria categoria : Categoria.values()) {                   //  Itera sobre todos los valores definidos en el enum (ACCION, ROMANCE, etc.).
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {         // Compara si el texto (sin distinguir mayúsculas/minúsculas) coincide con alguno de los valores categoriaOmdb. Si lo encuentra, devuelve el valor del enum correspondiente.
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);  // Si no encuentra una coincidencia, lanza una excepción, indicando que el texto no corresponde a ninguna categoría conocida.
    }

    }
//-----------------------------------------------------------



