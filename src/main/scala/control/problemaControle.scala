package control

import java.io.{File, PrintWriter}

import scala.io.{Source, StdIn}
import scala.collection.mutable.{ListBuffer}
/**
 * Created by pnakibarf on 7/10/15.
 */

case class Problema(acoes:Int, estados: Int, matrizDeUtilidade:ListBuffer[ListBuffer[Float]], matrizDeCrebilidade: Option[ListBuffer[ListBuffer[Float]]])



object controleDescreverProblema {

  def lerLinha(estados:Int): ListBuffer[Float] = {
    val l = StdIn.readLine().split(" ").map(_.toFloat)
    ListBuffer(l.dropRight(l.size - estados).toList: _*)
  }

  def lerMatriz(acoes: Int, estados: Int): ListBuffer[ListBuffer[Float]] = {
    def loop(acoes: Int, estados: Int, l: ListBuffer[ListBuffer[Float]]): ListBuffer[ListBuffer[Float]] = {
      if (acoes == 0)
        l
      else
        loop(acoes - 1, estados, ListBuffer(lerLinha(estados)) ++ l)
    }
    loop(acoes, estados, ListBuffer[ListBuffer[Float]]())
  }

  def descrever(): Problema ={
    println("Você irá descrever um problema!")

    println("Digite o número de ações:")
    val numAcoes = StdIn.readInt()

    println("Digite o número de estados da natureza")
    val numEstados = StdIn.readInt()

    println("Digite a matriz de utilidade:")
    val matrizDeUtilidade = lerMatriz(numAcoes, numEstados)

    println("Digite a matriz de credibilidade: (opcional, aperte enter para pular)")
    try{
      val matrizDeCredibilidade = lerMatriz(numAcoes, numEstados)
      new Problema(numAcoes, numEstados, matrizDeUtilidade, Option.apply(matrizDeCredibilidade))
    }
    catch{
      case any => new Problema(numAcoes, numEstados, matrizDeUtilidade, None)
    }

  }

  /*
  def matrixFromFile(filePath: String): ListBuffer[ListBuffer[Float]] = {
    val file = Source.fromFile(filePath).getLines()

    def loop(file: Iterator[String], matrix:ListBuffer[ListBuffer[Float]]): ListBuffer[ListBuffer[Float]] ={
      if (! file.hasNext)
        matrix.reverse
      else{
        loop(file, lineToListBufferFloat(file.next()) :: matrix)
      }

    }
    loop(file, ListBuffer[ListBuffer[Float]]())
  }
  */

  def lineToListBufferFloat(line: String): ListBuffer[Float] = ListBuffer(line.split(" ").map(_.toFloat).toList: _*)
  def carregarProblema(filepath: String): Problema = {
    val file = Source.fromFile(filepath).getLines()

    def loop(num: Int, file: Iterator[String], matrix:ListBuffer[ListBuffer[Float]]): ListBuffer[ListBuffer[Float]] = {
      if (num == 0)
        matrix
      else
        loop(num-1, file, ListBuffer(lineToListBufferFloat(file.next())) ++ matrix)
    }

    val numAcoes = file.next().toInt
    val numEstados = file.next().toInt
    val matrixDeDecisao = loop(numEstados, file, ListBuffer[ListBuffer[Float]]())

    try{
      val matrixDeCredibilidade = loop(numEstados, file, ListBuffer[ListBuffer[Float]]())
      new Problema(numAcoes, numEstados, matrixDeDecisao, Option.apply(matrixDeCredibilidade))
    }
    catch{
      case any => new Problema(numAcoes, numEstados, matrixDeDecisao, None)
    }
  }

  def salvarProblema(problema: Problema) = {
    println("Digite o local e o nome do arquivo aonde deseja salvar:")
    val filepath = StdIn.readLine()
    val writer = new PrintWriter(new File(filepath))

    problema.matrizDeUtilidade.foreach(l=>{
      l.foreach(u=>writer.write(u.toString + " "))
      writer.write("\n")
    })

    problema.matrizDeCrebilidade.foreach(l=>{
      l.foreach(u=>writer.write(u.toString + " "))
      writer.write("\n")
    })

    writer.close()
  }

  def printMatrix(matrix: ListBuffer[ListBuffer[Float]]) = {
    matrix.foreach( x => {
      x.foreach(y => print(y.toString+" "))
      println()
    })

  }
  def imprimirProblema(problema: Problema) = {
    println("Numero de ações: "+problema.acoes)
    println("Numero de estados: "+problema.estados)
    println("Matriz de utilidade:")
    printMatrix(problema.matrizDeUtilidade)

    if (!problema.matrizDeCrebilidade.isEmpty) {
      println("Matriz de credibilidade:")
      printMatrix(problema.matrizDeCrebilidade.get)
    }

  }

  def modificarProblema(problema: Problema) = {
    println("Deseja alterar a matriz de Utilidade ou Credidibilidade? (C/U)")
    val option = StdIn.readChar()
    println("Digite a linha:")
    val y = StdIn.readInt()
    println("Digite a coluna:")
    val x = StdIn.readInt()
    println("Qual o novo valor?")
    val n = StdIn.readFloat()

    if (option.toUpper == 'C') {
      if (!problema.matrizDeCrebilidade.isEmpty)
        problema.matrizDeCrebilidade.get(y-1)(x-1) = n
    }
    else
      problema.matrizDeUtilidade(y-1)(x-1) = n
  }


}
