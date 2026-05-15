// =====================================================================
// Ejercicio 6: Integración del sistema completo
// =====================================================================
import Analyzer.detectEntities
import Dictionary.loadAll
import Formatters.formatNERResult
import java.text.Normalizer.Form

object Main {
  def main(args: Array[String]): Unit = {

    // ------------------------------------------------------------------
    // Paso 1: Cargar diccionarios
    // ------------------------------------------------------------------
    val dictionary: List[NamedEntity] = loadAll()
    
    println(s"\nDiccionario cargado: ${dictionary.size} entidades.\nDiccionario:")
    dictionary.foreach { entity =>
      println(entity.describe)
    }
    println("\n"++ "="*50)

    // ------------------------------------------------------------------
    // Paso 2: Descargar posts
    // ------------------------------------------------------------------
    val subscriptions = FileIO.readSubscriptions()

    val allPosts: List[(String, List[String])] = subscriptions.map { url =>
      println(s"Descargando posts de: $url")
      val json   = FileIO.downloadFeed(url)
      val titles = FileIO.extractPostTitles(json)
      (url, titles)
    }
    
    val procesados : List[(String, List[NamedEntity])] = 
      allPosts.flatMap { case (url, titles) =>
        
        titles.map { title =>
          val entities = 
            Analyzer.detectEntities(title, dictionary)
          (title, entities)
        }
      }

    procesados.foreach { case (title, entities) =>
      val formated = Formatters.formatNERResult(title, entities)
      println(formated)
    }

    val todasLasEntidades: List[NamedEntity] = 
      procesados.flatMap { case (_, entities) =>
        entities
      }
    
    val stats = Analyzer.countByType(todasLasEntidades)

    val formated = Formatters.formatEntityStats(stats)

    println(formated)
  }
}
