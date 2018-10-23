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

  /**
    * exercise 1.5 create a function to sum all the leaves in a Tree[Int]
    */
  def sum(tree: Tree[Int]): Int = tree match {
    case Empty() => 0
    case Node(l, x, r) => x + sum(l) + sum(r)
  }

  /**
    * exercise 1.6 create a function that counts all the leaves in a tree
    */
  def leaves() = ???

}
