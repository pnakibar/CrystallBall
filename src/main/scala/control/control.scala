package control

import scala.collection.mutable.ListBuffer

/**
 * Created by pnakibarf on 7/11/15.
 */
object control {
  var problema: Problema = new Problema(0,0, ListBuffer[ListBuffer[Float]](), None)

  def doSomething(option: Int) ={
    option match {
      case 1 => descreverProblema()
      case 2 => carregarArquivo()
      case 3 => salvarArquivo()
      case 4 => listarProblema()
      case 5 => alterarProblema()
      case 6 => "undefined"
      case 7 => sys.exit(0)
    }
    returnMainMenu()
  }

  def descreverProblema() = {
    problema = controleDescreverProblema.descrever()
  }

  def carregarArquivo() = {
    problema = controleDescreverProblema.carregarProblema("/home/pnakibarf/git/crystalball/test")
  }

  def salvarArquivo() = {
    try {
      controleDescreverProblema.salvarProblema(problema)
      println("Salvo com sucesso!")
    }
    catch{
      case any => println("Não foi possível salvar!")
    }
    finally{
      returnMainMenu()
    }
  }

  def listarProblema(): Unit ={
    println("Problema na memória:")
    controleDescreverProblema.imprimirProblema(problema)
    println()
  }

  def alterarProblema() = {
    controleDescreverProblema.modificarProblema(problema)
  }

  def returnMainMenu() = view.view.run()

}
