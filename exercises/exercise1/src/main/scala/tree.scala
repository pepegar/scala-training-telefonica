package exercise1

/**
  * exercise 1.3 create a binary tree structure
  */
sealed trait Tree[A]
case class Empty[A]() extends Tree[A]
case class Node[A](l: Tree[A], a: A, r: Tree[A]) extends Tree[A]

object Tree {

  /**
    * exercise 1.4 create a function to calculate the height of a tree
    */
  def height[A](tree: Tree[A]): Int = tree match {
    case Empty() => 0
    case Node(l, _, r) => 1 + (height(l).max(height(r)))
  }

}
