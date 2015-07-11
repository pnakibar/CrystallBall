package control

import scala.io.{Source, StdIn}

/**
 * Created by pnakibarf on 7/10/15.
 */

case class Problema(acoes:Int, estados: Int, matrizDeUtilidade:List[List[Float]])

object controleDescreverProblema {
  def lerLinha(estados:Int): List[Float] = {
    val l = StdIn.readLine().split(" ").map(_.toFloat)
    l.dropRight(l.size - estados).toList
  }

  def lerMatriz(acoes: Int, estados: Int): List[List[Float]] = {
    def loop(acoes: Int, estados: Int, l: List[List[Float]]): List[List[Float]] = {
      if (acoes == 0)
        l.reverse
      else
        loop(acoes - 1, estados, lerLinha(estados) :: l)
    }
    loop(acoes, estados, List[List[Float]]())
  }

  def descrever(): Problema ={
    println("Você irá descrever um problema!")
    println("Digite o número de ações:")
    val numAcoes = StdIn.readInt()
    println("Digite o número de estados da natureza")
    val numEstados = StdIn.readInt()
    println("Digite a matriz de utilidade:")
    val matrizDeUtilidade = lerMatriz(numAcoes, numEstados)

    new Problema(numAcoes, numEstados, matrizDeUtilidade)
  }

  def matrixFromFile(filePath: String): List[List[Float]] = {
    def lineToListFloat(line: String): List[Float] = line.split(" ").map(_.toFloat).toList
    val file = Source.fromFile(filePath).getLines()

    def loop(file: Iterator[String], matrix:List[List[Float]]): List[List[Float]] ={
      if (! file.hasNext)
        matrix.reverse
      else{
        loop(file, lineToListFloat(file.next()) :: matrix)
      }

    }
    loop(file, List[List[Float]]())
  }

  def carregarProblema(filepath: String): Problema = {
    val matrix = matrixFromFile(filepath)
    val estados = matrix(0).length
    val acoes = matrix.length

    new Problema(acoes, estados, matrix)
  }


}
