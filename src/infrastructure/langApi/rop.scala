package globus.infrastructure.langApi

package object rop {

  /*
* Since Scala seems to lack(*) the equivalent of F# operator >> for composing functions or piping
* data into functions the operator ->> is introduced for this purpose. It enables the same "flow"
* in the code using this DSL as in the F# examples.
*
* (*) The closest Scala has to offer seems to be the for comprehension, but it isn't even coming
* close to the same readability and cleanness in the client code. (See the accompanying test code
* for examples.)
*/
  sealed case class ComposableFunction[A, B](f1: A => B) {
    def ->>[C](f2: B => C): A => C = (a: A) => f2(f1(a))
  }

  implicit def function2ComposableFunction[A, B](f: A => B): ComposableFunction[A, B] = ComposableFunction(f)

  sealed case class ComposableData[A](d: A) {
    def ->>[B](f: A => B): B = f(d)
  }

  implicit def data2ComposableData[A](d: A): ComposableData[A] = ComposableData(d)

  sealed case class ComposableStream[A](d: Stream[A]) {
    def ->>[B](f: Stream[A] => B): B = f(d)
  }
  implicit def data2ComposableStream[A](d: Stream[A]): ComposableStream[A] = ComposableStream(d)
  /**
    * The two-track type. Constructed as Success or Failure. Can be used
    * in Scala pattern matching and for comprehensions as well as with the
    * other functions in this package.
    */
  sealed trait R[+S, +F] {
    /**
      * Pipes this two-track value into a switch function
      * @param f switch function
      */
    def ->=[S1, F1](f: S => R[S1, F1]): R[S1, Any] = this ->> bind(f)
    def flatMap[S1, F1](f: S => R[S1, F1]): R[S1, Any] = ->=(f)
    def map[S1](f: S => S1): R[S1, Any] = flatMap(f ->> succeed)
  }
  case class Succ[S, F](data: S) extends R[S, F]
  case class Fail[S, F](msg: F) extends R[S, F]
  /**
    * Wraps the data in a Success object
    */
  def succeed[S, F](s: S): R[S, F] = Succ(s)
  /**
    * Wraps the data in a Failure object
    */
  def fail[S, F](f: F): R[S, F] = Fail(f)
  /**
    * Apply either a success function or failure function
    */
  def either[S, F, S1, F1](successFunc: S => R[S1, F], failureFunc: F => R[S1, F])(twoTrackInput: R[S, F]): R[S1, F] = {
    twoTrackInput match {
      case Succ(s) => s ->> successFunc
      case Fail(f) => f ->> failureFunc
    }
  }
  /**
    * Convert a switch function into a two-track function
    */
  def bind[S, F, S1, F1](f: S => R[S1, F]): R[S, F] => R[S1, F] = (input: R[S, F]) => input ->> either(f, fail)
  /**
    * Enables a switch to be composed with another switch into a combined switch.
    */
  sealed case class ComposableSwitchFunction[I, S, F](f1: I => R[S, F]) {
    /**
      * Compose two switches into another switch
      */
    def >=>[S1](f2: S => R[S1, F]): I => R[S1, F] = (input: I) => input ->> (f1 ->> bind(f2))
  }
  implicit def switchFuction2ComposableSwitchFunction[I, S, F](f: I => R[S, F]): ComposableSwitchFunction[I, S, F] = ComposableSwitchFunction(f)
  /**
    * Convert a one-track function into a switch
    */
  def switch[S1, S2](f: S1 => S2): S1 => R[S2, Nothing] = f ->> succeed
  /**
    * Convert a one-track function into a two-track function
    */
  def map[S1, S2](f: S1 => S2): R[S1, Any] => R[S2, Any] = (input: R[S1, Any]) => input map f
  /**
    * Convert a dead-end function into a one-track function
    */
  def tee[I](f: I => Unit): I => I = (input: I) => {
    f(input)
    input
  }
  /**
    * Convert a one-track function into a switch with exception handling
    */
  def tryCatch[S1, S2, F](f: S1 => S2, exnHandler: Throwable => F): S1 => R[S2, F] = (input: S1) => {
    try {
      input ->> f ->> succeed
    } catch {
      case t: Throwable => t ->> exnHandler ->> fail
    }
  }
  /**
    * Convert two one-track functions into a two-track function
    */
  //  def doubleMap[S1, F1, S2, F2](successFunc: S1 => S2, failureFunc: F1 => F2): R[S1, F1] => R[S2, F2] =
  //    (input: R[S1, F1]) => input ->> either(successFunc ->> succeed, failureFunc ->> fail)
  /**
    * Run two switches in parallel and combine the results
    */
  def plus[S, F, AS, AF](addSuccess: (S, S) => (AS), addFailure: (F, F) => AF)(switch1: S => R[S, F], switch2: S => R[S, F]):
  S => R[AS, AF] with Product with Serializable =
    (input: S) => (switch1(input), switch2(input)) match {
      case (Succ(s1), Succ(s2)) => Succ(addSuccess(s1, s2))
      case (Fail(f1), Succ(_)) => Fail(addFailure(f1, f1))
      case (Succ(_), Fail(f2)) => Fail(addFailure(f2, f2))
      case (Fail(f1), Fail(f2)) => Fail(addFailure(f1, f2))
    }
}
