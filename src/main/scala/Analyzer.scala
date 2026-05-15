// =====================================================================
// Ejercicios 3 y 5: Detección y conteo de entidades
// =====================================================================

/**
 * Responsable de detectar entidades nombradas en texto libre y
 * producir estadísticas sobre ellas.
 */
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
    ???
  }

  /**
   * Cuenta cuántas entidades de cada tipo fueron detectadas.
   *
   * @param entities lista de entidades detectadas
   * @return mapa de entityType → cantidad de apariciones
   */
  def countByType(entities: List[NamedEntity]): Map[String, Int] = {
    entities.groupBy(_.entityType).view.mapValues(_.length).toMap
  }
}
