package crystalball
/**
 * Created by pnakibarf on 7/19/15.
 */
class Problema(
                 val estadosNatureza: List[EstadoNatureza],
                 val acoes: List[Acao],
                 val matrizDeUtilidade: List[List[Float]],
                 val matrizDeCrebilidade: Option[List[List[Float]]]
                 ){

  def mudarMatrizDeUtilidade(y: Int, x: Int, n: Float): Problema = Mutator.changeMatrizUtilidade(this, y, x, n)

  def mudarMatrizCredibilidade(y: Int, x: Int, n: Float): Problema = Mutator.changeMatrizCredibilidade(this, y, x, n)

  def mudarValorEstado(pos: Int, descricao: String, prob: Float): Problema = {
    new Problema(
      estadosNatureza.updated(pos, new EstadoNatureza(descricao, prob)),
      acoes,
      matrizDeUtilidade,
      matrizDeCrebilidade
    )
  }

  def mudarValorAcao(pos: Int, descricao: String): Problema = {
    new Problema(
      estadosNatureza,
      acoes.updated(pos, new Acao(descricao)),
      matrizDeUtilidade,
      matrizDeCrebilidade
    )
  }

  override def toString():String = {
    def matrizToString(m: List[List[Any]]): String = {
      m.map(_.map(_.toString)).map(_.reduce((x,y)=>x+" "+y)).reduce((x,y)=>x+"\n"+y)
    }

    var s = "Estados:\n"

    s+="Descriçao - Probabilidade\n"
    estadosNatureza.foreach(x=>s+=x.descricao + " " + x.probabilidade+"\n")

    s+="Acoes:\n"
    acoes.foreach(x=>s+=x.descricao+"\n")

    s+="Matriz de utilidade:\n"
    s+= matrizToString(matrizDeUtilidade)

    if (matrizDeCrebilidade.isDefined) {
      s += "\nMatriz de Credbilidade:\n"
      s += matrizToString(matrizDeCrebilidade.get)
    }

    s
  }
}

class Acao(val descricao: String){
  override def toString(): String = descricao
}

case class EstadoNatureza(val descricao: String, val probabilidade: Float)
