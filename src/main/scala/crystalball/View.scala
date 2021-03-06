package crystalball
import scala.io.StdIn

/**
 * Created by pnakibarf on 7/19/15.
 */

object MainMenu {
    val mainMenu = "**********************************************************\nCRYSTAL BALL - SISTEMA DE APOIO A DECISÃO –\nO SEU ORÁCULO DE HOJE E SEMPRE\n**********************************************************\n1. Descrever um problema \n2. Carregar um problema \n3. Salvar um problema \n4. Listar um problema \n5. Alterar um problema ativo\n6. Obter Recomendação\n7. Sair\n**********************************************************"

  def run(): Unit ={
    println(mainMenu)
    println("Digite uma opção:")
    val option = StdIn.readInt()

    option match {
      case 1 => descrever()
      case 2 => carregar()
      case 3 => salvar()
      case 4 => listar()
      case 5 => alterar()
      case 6 => obterRecomendacao()
      case 7 => sair()
    }

    run()
  }



  def descrever(): Unit ={
    val prob = CommandLineReader.read()
    Control.problema = prob
  }

  def carregar(): Unit ={
    Control.carregar(StdIn.readLine("Qual o endereço do arquivo?"))
  }

  def salvar(): Unit ={
    val filepath = StdIn.readLine("Digite o endereço de onde você deseja salvar o arquivo:")
    try {
      Control.salvar(filepath)
      println("Arquivo salvo com sucesso!")
    } catch {
      case any => println("Não foi possível salvar o arquivo!")
    }
  }

  def listar(): Unit ={
    println(Control.problema.toString())
  }

  def alterar(): Unit ={
    println(
      "O quê você deseja alterar?\n" +
      "1 - Matriz de utilidade\n" +
      "2 - Matriz de credibilidade\n" +
      "3 - Acoes\n" +
      "4 - Estados\n" +
      "5 - Nada\n"
    )

    val option = StdIn.readInt()
    if (option < 3) {
      println("Digite o valor de Y:")
      val y = StdIn.readInt()
      println("Digite o valor de X:")
      val x = StdIn.readInt()
      println("Digite o novo valor:")
      val n = StdIn.readInt()

      if (option == 1)
        Control.mudarMatrizDeUtilidade(y,x,n)
      else
        Control.mudarMatrizCredibilidade(y,x,n)
    }
    else if (option < 5){
      println("Digite a posição do valor que você deseja alterar:")
      val pos = StdIn.readInt()

      println("Digite a nova descrição:")
      val des = StdIn.readLine()

      if (option == 3)
        Control.mudarAcoes(pos, des)
      else{
        println("Digite a probabilidade do estado:")
        val prob = StdIn.readFloat()
        Control.mudarEstados(pos, des, prob)
      }
    }
  }

  def obterRecomendacao(): Unit ={
    OperationsMenu.run()
  }

  def sair() = System.exit(0)
}

object OperationsMenu{
  val operationsMenu = "**********************************************************\nCRYSTAL BALL - SISTEMA DE APOIO A DECISÃO –\nDIGITE UMA OPÇÃO PARA APLICAR AO PROBLEMA\n**********************************************************\n1 – Laplace\n2 – MaxMin\n3 – Savage\n4 - Hurwikz\n5 – Valor médio esperado\n6 – Perda da oportunidade esperada\n7 – Determinar valor da Informação Perfeita\n8 – Estimar de acordo com previsão\n9 – Retornar ao menu principal\n**********************************************************"
  var p: Problema = Control.problema
  val previsoes = List("baixa", "media", "alta").zipWithIndex
  def getPrevisao(prev: String):Int = {
    previsoes.filter(_._1 == prev).head._2
  }

  def laplace(): Unit = {
    println("Melhor opção é "+p.acoes(Operations.laplace(p)))
  }

  def maxmin(): Unit = {
    println("Melhor opção é "+p.acoes(Operations.maxmin(p)))
  }

  def savage(): Unit = {
    println("Melhor opção é "+p.acoes(Operations.savage(p)))
  }

  def hurwikz(): Unit = {
    println("Digite o indicie de arrependimento:")
    val h = StdIn.readFloat()
    println("Melhor opção é "+p.acoes(Operations.hurwikz(p, h)))
  }

  def valorMedioEsperado(): Unit = {
    println("Valor médio esperado: "+Operations.valorMedioEsperado(p))
  }

  def perdaDaOportunidadeEsperada(): Unit = {
    println("Perda da oportunidade esperada: "+Operations.perdaDaOportunidadeEsperada(p))
  }

  def valorInformacaoPerfeita(): Unit = {
    println("Valor da informação perfeita: "+Operations.valorInformacaoPerfeita(p))
  }

  def valorDaConsultoria(): Unit = {
    val prev = getPrevisao(StdIn.readLine("Digite a previsao (baixa/media/alta"))
    println("De acordo com a estimativa de consultoria "+previsoes(prev)._1)
    Operations.valorDaConsultoria(p, prev).zipWithIndex.foreach(x=>{
      println("Para a ação "+ p.acoes(x._2).descricao + " temos o MVE = " + x._1)
    })

  }

  def run(): Unit ={
    p = Control.problema
    println(operationsMenu)
    println("Digite uma opção:")
    val option = StdIn.readInt()

    option match{
      case 1 => laplace()
      case 2 => maxmin()
      case 3 => savage()
      case 4 => hurwikz()
      case 5 => valorMedioEsperado()
      case 6 => perdaDaOportunidadeEsperada()
      case 7 => valorInformacaoPerfeita()
      case 8 => { try {valorDaConsultoria()} catch {case any=>println(any)}}
      case 9 => MainMenu.run()
    }

    run()
  }


}

