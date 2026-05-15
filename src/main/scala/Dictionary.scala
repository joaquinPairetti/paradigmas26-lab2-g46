// =====================================================================
// Ejercicio 2: Cargar diccionarios de entidades
// =====================================================================

/**
 * Responsable de cargar colecciones de entidades nombradas desde archivos.
 *
 * Un diccionario es un archivo de texto plano donde cada línea contiene
 * el nombre de una entidad conocida del mismo tipo.
 *
 * Ejemplo — data/people.txt:
 *   Martin Odersky
 *   Alan Turing
 *   Ada Lovelace
 *
 * Ejemplo — data/languages.txt:
 *   Scala
 *   Python
 *   Haskell
 */
object Dictionary {

  /**
   * Lee un archivo de diccionario y crea una lista de entidades del tipo indicado.
   *
   * @param filePath   ruta al archivo de diccionario (ej: "data/people.txt")
   * @param entityType tipo de entidad: "Person", "University", "ProgrammingLanguage", etc.
   * @return lista de NamedEntity del tipo correspondiente
   */
  def loadFromFile(filePath: String, entityType: String): List[NamedEntity] = {
    val lines = FileIO.readLines(filePath)
    lines.map { line => 
      entityType match {
        case "Person"              => new Person(line)
        case "Organization"        => new Organization(line)
        case "University"          => new University(line)
        case "Place"               => new Place(line)
        case "Technology"          => new Technology(line)
        case "ProgrammingLanguage" => new ProgrammingLanguage(line)
      }
    }
  }

  /**
   * Carga todos los diccionarios disponibles y combina sus entidades.
   *
   * @return lista con todas las entidades de todos los diccionarios
   */
  def loadAll(): List[NamedEntity] = {
    val people = loadFromFile("data/people.txt", "Person")
    val universities = loadFromFile("data/universities.txt", "University")
    val languages = loadFromFile("data/languages.txt", "ProgrammingLanguage")
    val organizations = loadFromFile("data/organizations.txt", "Organization")
    val places = loadFromFile("data/places.txt", "Place")

    people ++ universities ++ languages ++ organizations ++ places
  }
}

object TestEjercicio2 {
  def main(args: Array[String]): Unit = {
    val dict = Dictionary.loadAll()
    println(s"Total de entidades: ${dict.size}")
    dict.filter(_.entityType == "Person").foreach(p =>
    println(p.describe))
  }
}
