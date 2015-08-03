package crystalball

/**
 * Created by pnakibarf on 7/19/15.
 */
object Control {
  def mudarEstados(pos: Int, des: String, prob: Float) = problema = problema.mudarValorEstado(pos, des, prob)

  def mudarAcoes(pos: Int, des: String) = problema = problema.mudarValorAcao(pos, des)

  def mudarMatrizCredibilidade(y: Int, x: Int, n: Int): Unit = problema = problema.mudarMatrizCredibilidade(y,x,n)

  def mudarMatrizDeUtilidade(y: Int, x: Int, n: Int): Unit = problema = problema.mudarMatrizDeUtilidade(y,x,n)

  var problema = new Problema(List[EstadoNatureza](), List[Acao](), List[List[Float]](), None)


  def carregar(filepath: String): Problema = {
    problema = FileReader.read(filepath)
    problema
  }

  def salvar(filepath: String) = {
    Saver.save(problema, filepath)
  }


}
