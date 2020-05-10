package dotty.samples._05new

object OptionalBraces

enum IndentWidth:

  case Run(ch: Char, n: Int)
  case Conc(l: IndentWidth, r: Run)

  def <= (that: IndentWidth): Boolean = this match
    case Run(ch1, n1) =>
      that match
        case Run(ch2, n2) => n1 <= n2 && (ch1 == ch2 || n1 == 0)
        case Conc(l, r)   => this <= l
    case Conc(l1, r1) =>
      that match
        case Conc(l2, r2) => l1 == l2 && r1 <= r2
        case _            => false

  def < (that: IndentWidth): Boolean =
    this <= that && !(that <= this)

  override def toString: String = this match
    case Run(ch, n) =>
      val kind = ch match
        case ' '  => "space"
        case '\t' => "tab"
        case _    => s"'$ch'-character"
      val suffix = if n == 1 then "" else "s"
      s"$n $kind$suffix"
    case Conc(l, r) =>
      s"$l, $r"

object IndentWidth:

  private inline val MaxCached = 40

  private val spaces = IArray.tabulate(MaxCached + 1)(new Run(' ', _))
  private val tabs = IArray.tabulate(MaxCached + 1)(new Run('\t', _))

  def Run(ch: Char, n: Int): Run =
    if n <= MaxCached && ch == ' ' then
      spaces(n)
    else if n <= MaxCached && ch == '\t' then
      tabs(n)
    else
      new Run(ch, n)
  end Run

  val Zero = Run(' ', 0)
end IndentWidth
