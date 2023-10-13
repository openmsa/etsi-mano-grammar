lexer grammar EtsiLexerV25;

COMMA: ',';
SLASH: '/';
OPEN_BRACKET: '(';
CLOSE_BRACKET: ')';
SEMICOLON: ';';
EQUAL: '=';
QUOTE: '\'';

EQ: 'eq';
NEQ: 'neq';

GT: 'gt';
LT: 'lt';
GTE: 'gte';
LTE: 'lte';

IN: 'in';
NIN: 'nin';
CONT: 'cont';
NCONT: 'ncont';
FILTER: 'filter';


QUOTED_STRING: QUOTE ~('\'' | '\\')+ QUOTE;
STRING: ~('('|')'|'.'|'='|','|'/'|'\'')+;
LITERAL: STRING | QUOTED_STRING;

