package control

import java.io.{File, PrintWriter}

import scala.io.StdIn

/**
 * Created by pnakibarf on 7/11/15.
 */
object control {
  var problema: Problema = new Problema(0,0, List[List[Float]]())

  def doSomething(option: Int) ={
    option match {
      case 1 => descreverProblema()
      case 2 => carregarArquivo()
      case 3 => salvarArquivo()
      case 4 => "undefined"
      case 5 => "undefined"
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
      //println("Digite o local e o nome do arquivo aonde deseja salvar:")
      val filepath = StdIn.readLine()
      val writer = new PrintWriter(new File(filepath))
      problema.matrizDeUtilidade.foreach(l=>{
        l.foreach(u=>writer.write(u.toString + " "))
        writer.write("\n")
      })

      writer.close()
      println("Salvo com sucesso!")

    }
    catch{
      case any => println("Não foi possível salvar!")
    }
    finally{
      returnMainMenu()
    }
  }

  def returnMainMenu() = view.view.run()

}
