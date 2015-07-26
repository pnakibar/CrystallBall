/**
  * Created by pnakibarf on 7/19/15.
  */
object Operations {
  def valorDaConsultoria(p: Problema): Float = {
    val probabilidades = p.estadosNatureza.map(_.probabilidade)
    val juntado = p.matrizDeUtilidade.map(_.zip(probabilidades))
    juntado.map(y=>y.map(x=> ((x._1 * x._2)/y.length))).map(_.sum).sum
  }

  def valorInformacaoPerfeita(p: Problema): Float = {
    val u = p.matrizDeUtilidade
    val mve = valoresMedios(p)

    mve.sum - mve.max
  }

  def perdaDaOportunidadeEsperada(p: Problema): Float = {
    val pesares = gerarMatrizPesares(p)
    val h = p.estadosNatureza.map(_.probabilidade)
    pesares.map(x=>{
      x.zipWithIndex.map(y=>{
        h(y._2) * y._1
      }).sum
    }).max
  }

  def valorMedioEsperado(p: Problema): Float = {
    val v = valoresMedios(p)
    (v.sum/v.length)
  }

  def valoresMedios(p: Problema): List[Float] = {
    val u = p.matrizDeUtilidade
    val h = p.estadosNatureza.map(_.probabilidade)
    u.map(x=>{
      x.zipWithIndex.map(y=>{
        h(y._2)*y._1
      }).sum
    })
  }

  def hurwikz(p: Problema, h: Float): Int = {
    val m = p.matrizDeUtilidade.map(x=>h*x.max + (1-h)*x.min)
    m.zipWithIndex.max._2 //._2 returns the index
  }

  def gerarMatrizPesares(p: Problema): List[List[Float]] = {
    val u = p.matrizDeUtilidade.map(_.zipWithIndex).zipWithIndex
    val pesares = u.map(x=>{
      x._1.map(y=>{
        u.map(_._1.filter(_._2 == y._2)).map(_.head._1).max - y._1
      })
    })
    pesares
  }

  def savage(p: Problema): Int = {
    gerarMatrizPesares(p).map(_.max).zipWithIndex.min._2
  }

  def maxmin(p: Problema): Int = {
    p.matrizDeUtilidade.map(_.min).zipWithIndex.max._2
  }

  def laplace(p: Problema): Int = {
    p.matrizDeUtilidade.map(x=>x.sum/x.length).zipWithIndex.max._2
  }

}
