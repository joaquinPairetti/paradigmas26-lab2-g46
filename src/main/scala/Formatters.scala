// =====================================================================
// Ejercicios 4 y 5: Formateo de resultados
// =====================================================================

/**
 * Responsable de convertir los resultados del análisis a texto para mostrar.
 */
object Formatters {

  /**
   * Formatea el análisis NER de un post individual.
   *
   * @param postTitle título del post analizado
   * @param entities  entidades detectadas en ese post
   * @return bloque de texto con el título y las entidades encontradas
   *
   * TODO (Ejercicio 4): Implementar este método.
   *
   *   Usar el método describe de cada entidad para generar la salida.
   *   No es necesario hacer match sobre el tipo concreto de cada entidad:
   *   describe ya funciona correctamente para cualquier subtipo (polimorfismo).
   *
   *   Ejemplo de salida esperada:
   *
   *     Post: "Scala 3 released at EPFL by Martin Odersky"
   *     Entidades detectadas:
   *       [ProgrammingLanguage] Scala
   *       [University] EPFL
   *       [Person] Martin Odersky
   *
   *   Si no se detectaron entidades, mostrar un mensaje indicándolo.
   */
  def formatNERResult(postTitle: String, entities: List[NamedEntity]): String = {
    val header = s"""Post: "$postTitle""""
    if (entities.isEmpty) {
      s"""$header
         |  (sin entidades detectadas)""".stripMargin
    } else {
      s"""$header
         |Entidades detectadas:
         |  ${entities.map(_.describe).mkString("\n  ")}""".stripMargin
    }
  }

  /**
   * Formatea un resumen de estadísticas de entidades por tipo.
   *
   * @param counts mapa de entityType → cantidad
   * @return texto con las estadísticas ordenadas por cantidad (de mayor a menor)
   *
   *   Ejemplo de salida esperada:
   *
   *     === Estadísticas de entidades ===
   *     Person: 5
   *     ProgrammingLanguage: 3
   *     Organization: 2
   *     University: 2
   */
  def formatEntityStats(counts: Map[String, Int]): String = {
    val header = "=== Estadísticas de entidades ==="
    val body = counts.toList
      .sortBy(-_._2)
      .map { case (entityType, count) => s"$entityType: $count" }
      .mkString("\n")
    s"$header\n$body"
  }
}

object TestEjercicio5 {
  def main(args: Array[String]): Unit = {
    val entities = List(
      new Person("Alan Turing"),
      new ProgrammingLanguage("Scala"),
      new Person("Ada Lovelace"),
      new University("MIT")
    )
    val counts = Analyzer.countByType(entities)
    val result = Formatters.formatEntityStats(counts)
    println(result)
  }
}

object TestEjercicio4 {
  def main(args: Array[String]): Unit = {
    val postTitle = "Scala 3 released at EPFL by Martin Odersky"
    val entities = List(
      new ProgrammingLanguage("Scala"),
      new University("EPFL"),
      new Person("Martin Odersky")
    )
    val result = Formatters.formatNERResult(postTitle, entities)
    println(result)
  }
}