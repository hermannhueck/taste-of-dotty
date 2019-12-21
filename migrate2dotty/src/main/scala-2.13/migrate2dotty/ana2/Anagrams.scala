package migrate2dotty.ana2

import scala.util.chaining._
import util._

object Anagrams extends App {

  lineStart() pipe println

  // check List of possible anagrams against word
  // solution 2: deletes found character in word

  def anagramsFor(word: String, wordsToCheck: List[String]): List[String] =
    wordsToCheck filter { isAnagram(_, word) }

  def isAnagram(toCheck: String, word: String): Boolean =
    if (toCheck.toLowerCase == word.toLowerCase)
      false
    else
      isAnagram2Helper(toCheck.toLowerCase.toList, word.toLowerCase.toList)

  def isAnagram2Helper(toCheck: List[Char], word: List[Char]): Boolean =
    (toCheck, word) match {
      case (Nil, Nil) => true
      case (Nil, _)   => false
      case (ch :: tail, chars) if chars contains ch =>
        isAnagram2Helper(tail, deleteFrom(chars, ch))
      case _ => false
    }

  def deleteFrom(chars: List[Char], ch: Char): List[Char] =
    chars.takeWhile(_ != ch) ++ chars.dropWhile(_ != ch).tail

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
