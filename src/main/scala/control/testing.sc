import scala.collection.mutable

object bitches{
  val i = mutable.MutableList(mutable.MutableList(1,3,4),
                              mutable.MutableList(2,2,5),
                              mutable.MutableList(3,1,3))

  //i.map(x=>x(i(i.indexOf(1)).max))
  //i.map(x=>x(2))

  i.map(x=> x.map(y=> i.map(j=>j(x.indexOf(j))).max - y)).foreach(println)

  //i.map(x=> x.map( y=> x(i.indexOf(y).max)))
}