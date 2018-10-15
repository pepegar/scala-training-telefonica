package exercise1

/**
  * Let's imagine a simple event sourced application.  We want to define
  * some events that we can handle:
  *
  * - An user logs in
  * - A customer adds an item to the basket
  * - An user starts payment process
  * - Payment goes through correctly
  * - Payment process fails with timeout
  * - Payment process fails because of Insufficent funds
  */
sealed trait Event
