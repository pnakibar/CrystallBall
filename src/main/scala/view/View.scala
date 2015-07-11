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

  def mathMode(): Unit = {
    val menu =  "**********************************************************\n" +
                "ESCOLHA A SUA RECOMENDAÇÃO DO DIA! –\n" +
                "**********************************************************\n" +
                "1 – Laplace\n" +
                "2 – MaxMin\n" +
                "3 – Savage\n" +
                "4 - Hurwikz\n" +
                "5 – Valor médio esperado\n" +
                "6 – Perda da oportunidade esperada\n" +
                "7 – Determinar valor da Informação Perfeita\n" +
                "8 – Estimar de acordo com previsão de Consultoria\n" +
                "9 – Retornar ao menu principal"
                "**********************************************************"
    println(menu)
    println("Digite uma opção e aperte enter...")
    val opt = StdIn.readInt()
    control.doSomethingMath(opt)
  }

}
