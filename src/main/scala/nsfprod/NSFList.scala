package nsfprod

import scala.annotation.tailrec

import cats._

sealed trait NSFList[@specialized T] {

  def head: T

  def tail: NSFList[T]

  //TODO compile-time?
  def contraAs[V >: T]: NSFList[V] = {
    NSFList.Typeclass.foldRight[T, NSFList[V]](this, NSFNil[V])(NSFCons[V](_, _))
  }

  def reverse: NSFList[T] = {
    NSFList.Typeclass.foldLeft[T, NSFList[T]](this, NSFNil[T])((rst, e) => NSFCons[T](e, rst))
  }

  def ::(v: T): NSFList[T] = NSFCons(v, this)
  
  def :::(that: NSFList[T]): NSFList[T] = NSFList.Typeclass.foldRight(that, this)((v, lst) => v :: lst)
}

object NSFList {
  //TODO Traverse???
  implicit object Typeclass extends Foldable[NSFList] with Monad[NSFList] {

    // Applicative
    def pure[A](x: A): NSFList[A] = NSFCons(x, NSFNil[A])

    // FlatMap
    def flatMap[A, B](fa: NSFList[A])(f: A => NSFList[B]): NSFList[B] = {
      foldRight[A, NSFList[B]](fa, NSFNil[B])((a, lst) => f(a) ::: lst)
    }

    // Foldable
    @tailrec
    def foldLeft[A, B](fa: NSFList[A], b: B)(f: (B, A) => B): B = {
      fa match {
        case NSFCons(head, tail) => foldLeft(tail, f(b, head))(f)
        case NSFNil() => b
      }
    }
    
    def foldRight[A, B](fa: NSFList[A], b: B)(f: (A, B) => B): B = {
      foldLeft(fa.reverse, b)((b, a) => f(a, b))
    }
    
    def foldRight[A, B](fa: NSFList[A], b: Lazy[B])(f: (A, Lazy[B]) => B): Lazy[B] = {
      //TODO know about Lazy
      ???
    }
  }
}

case class NSFCons[T](head: T, tail: NSFList[T]) extends NSFList[T]

case class NSFNil[T]() extends NSFList[T] {
  def head = throw new Exception("head of empty list")
  def tail = throw new Exception("head of empty list")
}
