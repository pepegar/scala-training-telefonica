package exercise1

/**
  * exercise 1.3 create a binary tree structure
  */
sealed trait Tree[A]
case class Empty[A]() extends Tree[A]
case class Node[A](l: Tree[A], a: A, r: Tree[A]) extends Tree[A]
