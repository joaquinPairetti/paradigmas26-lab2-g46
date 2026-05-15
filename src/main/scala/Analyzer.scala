// =====================================================================
// Ejercicios 3 y 5: Detección y conteo de entidades
// =====================================================================

/**
 * Responsable de detectar entidades nombradas en texto libre y
 * producir estadísticas sobre ellas.
 */
import scala.util.matching.Regex
object Analyzer {

  /**
   * Detecta las entidades del diccionario que aparecen en el texto dado.
   *
   * @param text       texto a analizar (ej: título o cuerpo de un post)
   * @param dictionary lista de entidades conocidas (cargadas desde los diccionarios)
   * @return lista de entidades cuyo texto aparece en el texto analizado
   *
   * TODO (Ejercicio 3): Implementar este método.
   *
   *   Para cada entidad en el diccionario, verificar si su texto aparece en el
   *   texto del post. Retornar únicamente las entidades que aparecen.
   *
   *   Ejemplo:
   *     text       = "Scala fue creado en EPFL por Martin Odersky"
   *     dictionary = List(
   *                    ProgrammingLanguage("Scala"),
   *                    University("EPFL"),
   *                    Person("Martin Odersky"),
   *                    Person("Ada Lovelace")   ← no aparece en el texto
   *                  )
   *     resultado  = List(
   *                    ProgrammingLanguage("Scala"),
   *                    University("EPFL"),
   *                    Person("Martin Odersky")
   *                  )
   */
  def detectEntities(text: String, dictionary: List[NamedEntity]): List[NamedEntity] = {
    dictionary.filter { entity =>
      //toma el texto literal de la entity y lo tranforma para que regex no se rompa
      //ej: c++ se tomaria mal sin esto pues + es un caracter especial de regex
      val escaped = Regex.quote(entity.text)
      //(?i)->ignore case, \\b es como marcar los limites de escaped, solo tiene en cuenta escaped
      //nada antes ni nada despues, ej escaped = java, text aparece javascript
      //sin \b se tomaria javascript erroneamente
      val pattern = ("(?i)\\b" + escaped + "\\b").r
      //metodo de regex para hallar la primera aparicion de lo que configuramos quie busque
      pattern.findFirstIn(text).nonEmpty
    }
  }

  /**
   * Cuenta cuántas entidades de cada tipo fueron detectadas.
   *
   * @param entities lista de entidades detectadas
   * @return mapa de entityType → cantidad de apariciones
   *
   * TODO (Ejercicio 5): Implementar este método.
   *
   *   Ejemplo:
   *     entities = List(
   *                  Person("Alan Turing"),
   *                  ProgrammingLanguage("Scala"),
   *                  Person("Ada Lovelace"),
   *                  University("MIT")
   *                )
   *     resultado = Map(
   *                   "Person"              -> 2,
   *                   "ProgrammingLanguage" -> 1,
   *                   "University"          -> 1
   *                 )
   */
  def countByType(entities: List[NamedEntity]): Map[String, Int] = {
    entities.groupBy(_.entityType).view.mapValues(_.length).toMap
  }

}

object TestEjercicio3 {
  def main(args: Array[String]): Unit = {
    val text = "I like Javascript"
    val dict = Dictionary.loadAll()
    val found = Analyzer.detectEntities(text, dict)
    found.foreach(e => println(e.describe))
  }
}
