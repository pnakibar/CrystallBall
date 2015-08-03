package crystalball
import java.io.{File, PrintWriter}

import scala.io.{Source, StdIn}

/**
 * Created by pnakibarf on 7/15/15.
 */


object Converter{
  def converterLinhaParaEstado(linha: String): EstadoNatureza ={
    //exemplo de entrada "nome descricao 0.4"
    val split = linha.split(" ").reverse
    new EstadoNatureza(split.tail.reverse.reduce((a,b)=>a+" "+b), split.head.toFloat)
  }

  def converterLinhaParaAcao(linha: String): Acao = {
    new Acao(linha)
  }

  def converterLinhaFloats(linha: String): List[Float] = linha.split(" ").map(_.toFloat).toList
}

object CommandLineReader{
  def read(): Problema = {
    println("Digite o numero de acoes:")
    val numAcoes = StdIn.readInt()
    println("Digite as acoes:")
    val acoes = readNAcoes(numAcoes)

    println("Digite o numero de estados:")
    val numEstados = StdIn.readInt()
    println("Digite os estados:")
    val estados = readNEstados(numEstados)

    println("Entre com a matriz de utilidade:")
    val matrizUtilidade = readMatriz(numAcoes)

    println("Entre com a matriz de credibilidade (se não tiver aperte enter):")
    try {
      val matrizDeCrebilidade = Option.apply(readMatriz(numAcoes))
      new Problema(estados, acoes, matrizUtilidade, matrizDeCrebilidade)
    } catch {
      case any => new Problema(estados, acoes, matrizUtilidade, None)
    }
  }

  def readNEstados(n: Int): List[EstadoNatureza] = {
    def loop(n: Int, l:List[EstadoNatureza]): List[EstadoNatureza] = {
      if (n == 0)
        l
      else
        loop(n-1, Converter.converterLinhaParaEstado(StdIn.readLine())::l)
    }

    loop(n, List[EstadoNatureza]())
  }

  def readNAcoes(n: Int): List[Acao] = {
    def loop(n: Int, l: List[Acao]): List[Acao] = {
      if (n == 0)
        l
      else
        loop(n - 1, Converter.converterLinhaParaAcao(StdIn.readLine()) :: l)
    }

    loop(n, List[Acao]())
  }

  def readMatriz(numLines: Int): List[List[Float]] = {
    def loop(numLines: Int, matriz: List[List[Float]]): List[List[Float]] = {
      if (numLines == 0)
        matriz
      else{
        val linha = Converter.converterLinhaFloats(StdIn.readLine())
        loop(numLines-1, linha :: matriz)
      }
    }
    loop(numLines, List[List[Float]]())
  }
}

object FileReader{
  def read(filepath: String): Problema ={
    val file = Source.fromFile(filepath).getLines()

    val numAcoes = file.next().toInt
    def readConvertToAcoes(numAcoes: Int, file:Iterator[String]): List[Acao] = {
      def loop(n: Int, file: Iterator[String], acoes: List[Acao]): List[Acao] = {
        if (n == 0)
          acoes.reverse
        else
          loop(n-1, file, Converter.converterLinhaParaAcao(file.next()) :: acoes)
      }

      loop(numAcoes, file, List[Acao]())
    }
    val acoes: List[Acao] = readConvertToAcoes(numAcoes, file)

    val numEstados = file.next().toInt
    def readConvertToEstados(numEstados: Int, file: Iterator[String]): List[EstadoNatureza] = {
      def loop(n: Int, file: Iterator[String], estados: List[EstadoNatureza]): List[EstadoNatureza] ={
        if (n == 0)
          estados.reverse
        else
          loop(n-1, file, Converter.converterLinhaParaEstado(file.next()) :: estados)
      }

      loop(numEstados, file, List[EstadoNatureza]())
    }
    val estados: List[EstadoNatureza] = readConvertToEstados(numEstados, file)

    def lerMatriz(numLinhas: Int, file: Iterator[String]): List[List[Float]] = {
      def loop(n: Int, file: Iterator[String], m: List[List[Float]]): List[List[Float]] = {
        if (n == 0)
          m.reverse
        else
          loop(n-1, file, Converter.converterLinhaFloats(file.next()) :: m)
      }

      loop(numLinhas, file, List[List[Float]]())
    }

    val matrizDeUtilidade = lerMatriz(numAcoes, file).reverse

    try{
      val matrizDeCredibilidade = Option.apply(lerMatriz(numAcoes, file).reverse)
      new Problema(estados, acoes, matrizDeUtilidade, matrizDeCredibilidade)
    } catch {
      case any => new Problema(estados, acoes, matrizDeUtilidade, None)
    }
  }
}

object Saver {
  def save (p: Problema, filepath: String) = {
    val writer = new PrintWriter(new File(filepath))

    writer.write(p.acoes.size.toString()+"\n")
    p.acoes.foreach(x=>writer.write(x.descricao + "\n"))

    writer.write(p.estadosNatureza.size.toString() + "\n")
    p.estadosNatureza.foreach(x=>writer.write(x.descricao + " " + x.probabilidade + "\n"))

    p.matrizDeUtilidade.foreach(x=>{
      x.foreach(y=>{
        writer.write(y.toString + " ")
      })
      writer.write("\n")
    })

    if (p.matrizDeCrebilidade.isDefined) {
      p.matrizDeCrebilidade.get.foreach(y=> {
        y.foreach(x => {
          writer.write(x.toString + " ")
        })
        writer.write("\n")
      })
    }

    writer.close()
  }
}


object Mutator {
  def changeMatriz(m: List[List[Float]], y: Int, x: Int, n: Float): List[List[Float]] = {
    val r = m.zipWithIndex.map(l=>{
      if (l._2 == y)
        l._1.zipWithIndex.map(e=>{
          if (e._2 == x)
            n
          else
            e._1
        })
      else
        l._1
    })

    r
  }

  def changeMatrizUtilidade(p: Problema, y: Int, x: Int, n: Float): Problema = {
    new Problema(p.estadosNatureza, p.acoes, changeMatriz(p.matrizDeUtilidade, y, x, n), p.matrizDeCrebilidade)
  }

  def changeMatrizCredibilidade(p: Problema, y:Int, x:Int, n: Float): Problema = {
    if (p.matrizDeCrebilidade.isDefined)
      new Problema(p.estadosNatureza, p.acoes, p.matrizDeUtilidade, Option.apply(changeMatriz(p.matrizDeCrebilidade.get, y, x, n)))
    else
      p
  }

}
