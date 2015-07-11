package view

import scala.io.StdIn
import control._

/**
 * Created by pnakibarf on 7/10/15.
 */
object view {
  def run(): Unit ={
    val menu =  "**********************************************************\n" +
                "CRYSTAL BALL - SISTEMA DE APOIO A DECISÃO –\n" +
                "O SEU ORÁCULO DE HOJE E SEMPRE\n" +
                "**********************************************************\n" +
                "1. Descrever um problema \n" +
                "2. Carregar um problema \n" +
                "3. Salvar um problema \n" +
                "4. Listar um problema \n" +
                "5. Alterar um problema ativo\n" +
                "6. Obter Recomendação\n" +
                "7. Sair\n" +
                "**********************************************************"

    println(menu)
    println("Digite uma opção e aperte enter...")
    val opt = StdIn.readInt()
    control.doSomething(opt)





  }

}
