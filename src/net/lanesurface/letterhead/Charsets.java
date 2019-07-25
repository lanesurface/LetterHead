package net.lanesurface.letterhead;

public class Charsets {
  private static final char[] ASCII_DETAIL =
    { '$'
    , '@'
    , 'B'
    , '%'
    , '8'
    , '&'
    , 'W'
    , 'M'
    , '#'
    , '*'
    , 'o'
    , 'a'
    , 'h'
    , 'k'
    , 'b'
    , 'd'
    , 'p'
    , 'q'
    , 'w'
    , 'm'
    , 'Z'
    , 'O'
    , '0'
    , 'Q'
    , 'L'
    , 'C'
    , 'J'
    , 'U'
    , 'Y'
    , 'X'
    , 'z'
    , 'c'
    , 'v'
    , 'u'
    , 'n'
    , 'x'
    , 'r'
    , 'j'
    , 'f'
    , 't'
    , '/'
    , '\\'
    , '|'
    , '('
    , ')'
    , '1'
    , '{'
    , '}'
    , '['
    , ']'
    , '?'
    , '-'
    , '_'
    , '+'
    , '~'
    , '<'
    , '>'
    , 'i'
    , '!'
    , 'l'
    , 'I'
    , ';'
    , ':'
    , ','
    , '\"'
    , '^'
    , '`'
    , '\''
    , '.'
    , ' ' };

  private static char[] ASCII_SIMPLE =
    { '@'
    , '%'
    , '#'
    , '*'
    , '+'
    , '='
    , '-'
    , ':'
    , '.'
    , ' ' };

  static char[] getChars(Charset ch) {
    switch (ch)
    {
    case SIMPLE:
      return ASCII_SIMPLE;
    case DETAIL:
    case BRAILLE:
    default:
      return ASCII_DETAIL;
    }
  }
}
