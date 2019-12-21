package migrate2dotty.ana1

import scala.util.chaining._
import util._

object Anagrams extends App {

  lineStart() pipe println

  // check List of possible anagrams against word
  // solution 1: just compares sorted strings

  def anagramsFor(word: String, wordsToCheck: List[String]): List[String] =
    wordsToCheck filter { isAnagram(_, word) }

  def isAnagram(toCheck: String, word: String): Boolean =
    if (toCheck.toLowerCase == word.toLowerCase)
      false
    else
      (lcSorted(toCheck), lcSorted(word)) match {
        case (str1, str2) if str1 == str2 => true
        case _                            => false
      }

  private def lcSorted(s: String) =
    s.toLowerCase.toSeq.sorted.unwrap

  // primitive tests

  anagramsFor("Rust", List("Rust"))
    .ensuring(_ == List()) pipe println
  anagramsFor("Rust", List("rust"))
    .ensuring(_ == List()) pipe println
  anagramsFor("Rust", List("rusty"))
    .ensuring(_ == List()) pipe println
  anagramsFor("Rust", List("yrust"))
    .ensuring(_ == List()) pipe println
  anagramsFor("Rust", List("urst", "srut", "surt", "tsru", "tsur", "rust", "trust", "trusty"))
    .ensuring(_ == List("urst", "srut", "surt", "tsru", "tsur")) pipe println

  lineEnd() pipe println
}
