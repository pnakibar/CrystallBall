package control

import scala.collection.mutable.ListBuffer
import scala.io.StdIn

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
      case 6 => returnMathMenu()
      case 7 => sys.exit(0)
    }
    returnMainMenu()
  }
  def doSomethingMath(option: Int) ={
    option match {
      case 1 => println("O estado "+(mathematics.laplace(problema)+1)+" é o melhor utilizando LaPlace")
      case 2 => println("O estado "+(mathematics.maxMin(problema)+1) +" é o melhor utilizando Maxmin")
      case 3 => println("O estado "+(mathematics.savage(problema)+1) +" é o melhor utilizando Savage")
      case 4 => println("O estado "+{
              println("Digite o indice de otimismo");
              mathematics.hurwicz(problema, StdIn.readFloat())
            }) + "utilizando Hurwic"
      case 5 => println("Maximização do valor esperado: "+mathematics.maximizaçãoDoValorEsperado(problema).max)
      case 6 => println("A perda da oportunidade esperada é: "+mathematics.perdaDaOportunidadeEsperada(problema))
      case 7 => println("O valor da informação perfeita é: "+mathematics.valorDaInformaçãoPerfeita(problema))
      case 8 => println("O valor da consultoria deve ser de: "+mathematics.valorConsultoria(problema))
      case 9 => returnMainMenu()
    }
    returnMathMenu()
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
  def returnMathMenu() = view.view.mathMode()

}
