package control

import scala.collection.mutable.ListBuffer

/**
 * Created by pnakibarf on 7/11/15.
 */
object mathematics {
  //funções retornam a melhor coluna
  def laplace(problema: Problema): Int = {
    val sums = problema.matrizDeUtilidade.map(x=>x.sum/x.length)
    sums.indexOf(sums.max)
  }

  def maxMin(problema: Problema): Int = {
    val mins = problema.matrizDeUtilidade.map(x=>x.min)
    mins.indexOf(mins.max)
  }

  def hurwicz(problema: Problema, indicieOtimismo: Float): Int = {
    val m = problema.matrizDeUtilidade.map(x=> indicieOtimismo*x.max + (1-indicieOtimismo)*x.min)
    m.indexOf(m.max)

  }


  def geraMatrizDePesares(problema: Problema): ListBuffer[ListBuffer[Float]] = {
    val u = problema.matrizDeUtilidade
    val pesares = u.map(x =>{
      x.map(y=>{
        u.map(j=>j(x.indexOf(y))).max - y
      })
    })

    pesares
  }

  def savage(problema: Problema): Int = {
    val pesares = geraMatrizDePesares(problema)
    val max = pesares.map(x=>x.max)
    val min = max.min

    max.indexOf(min)
  }

  def maximizaçãoDoValorEsperado(problema: Problema): ListBuffer[Float]= {
    val u = problema.matrizDeUtilidade
    val h = u.map(x=>1/u.length)
    u.map(x=>{
      x.map(y=>{
        h(x.indexOf(y))*y
      }).sum
    })
  }


  def perdaDaOportunidadeEsperada(problema: Problema): Float = {
    val p = geraMatrizDePesares(problema)
    val h = p.map(x => 1 / p.length)
    p.map(x=>{
      x.map(y=>{
        h(x.indexOf(y))*y
      }).sum
    }).max
  }

  def valorDaInformaçãoPerfeita(problema: Problema): Float = {
    val u = problema.matrizDeUtilidade
    val s = u.map(x => 1 / u.length)
    val mve = maximizaçãoDoValorEsperado(problema)

    mve.sum - mve.max
  }

  def valorConsultoria(problema: Problema) = ???
}
