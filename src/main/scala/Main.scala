import view._

import scala.io.StdIn

/**
 * Created by pnakibarf on 7/10/15.
 */
object main {
  def main(args: Array[String]) {
    println("         _...._\n" +
            "       .`      `.\n" +
            "      / ***      \\         Crystal Ball\n" +
            "     : **         :         Sistema de\n" +
            "     :            :        Apoio a decisão\n" +
            "      \\          /       \n" +
            "       `-.,,,,.-'          \n" +
            "        _(    )_\n" +
            "       )        (\n" +
            "      (          )\n       `-......-`lc")

    StdIn.readLine("Pressione enter para continuar...")
    print("\033[2J")
    view.run()



  }

}
