package crystalball;

/**
  * Created by pnakibarf on 7/19/15.
  */
object Operations {


  @throws[Exception]
  def valorDaConsultoria(p: Problema, prev: Int): List[Float] = {
    /*
     * prev == 0: baixa
     * prev == 1: media
     * prev == 2: alta
     */

    if (p.matrizDeCrebilidade.isEmpty) throw new Exception("Não tem matriz de credibilidade!")

    //calcula probabilidade de previsão:
    val probPrevisao = p.estadosNatureza.zipWithIndex.map(e=>e._1.probabilidade * p.matrizDeUtilidade(prev)(e._2))

    //Faz as contas de quanto seria o resultado para cada caso
    val somatorio = p.acoes.zipWithIndex.map(
      i=>{
        p.estadosNatureza.zipWithIndex.map(
          j=> (p.matrizDeCrebilidade.get(prev)(j._2)*j._1.probabilidade)/p.matrizDeUtilidade(i._2)(j._2)
        ).sum
      }
    )

    somatorio
  }

   def getMaxFromLine(m: List[List[Float]], i: Int): Float = {
    m.map(_.zipWithIndex.filter(_._2 == i).map(_._1)).map(_.head).max
  }
  def getMax(m: List[List[Float]]): List[Float] = {
    def loop(l: List[Float], i: Int): List[Float] = {
      if (i == m.size) l.reverse
      else loop(getMaxFromLine(m, i) :: l, i+1)
    }
    loop(List[Float](), 0)
  }

  def valorInformacaoPerfeita(p: Problema): Float = {
    val mve = valoresMedios(p)
    val mveip = (getMax(p.matrizDeUtilidade).zip(p.estadosNatureza.map(_.probabilidade))).map(x=>x._1 * x._2).sum


    mveip - mve.max
  }

  def perdaDaOportunidadeEsperada(p: Problema): Float = {
    val pesares = gerarMatrizPesares(p)
    val h = p.estadosNatureza.map(_.probabilidade)
    pesares.map(x=>{
      x.zipWithIndex.map(y=>{
        h(y._2) * y._1
      }).sum
    }).min
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
