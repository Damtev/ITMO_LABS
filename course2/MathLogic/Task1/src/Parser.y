{
module Parser where

import Grammar
import Lexer
}

%name parse
%tokentype { Token }
%error { parseError }
%monad     { Either String }{ >>= }{ return }

%token
	LEFTBR { LeftBr }
	RIGHTBR { RightBr }
	OR	{ OrT }
	AND	{ AndT }
	IMPL	{ ImplT }
	NOT	{ NotT }
	VART	{ VarT $$ }

%%

Expr	: Disj		{ $1 }
        | Disj IMPL Expr{ Binary Impl $1 $3}

Disj	: Conj		{ $1 }
     	| Disj OR Conj	{ Binary Or $1 $3 }

Conj	: Term		{ $1 }
     	| Conj AND Term	{ Binary And $1 $3 }

Term	: NOT Term	{ Not $2 }
     	| VART		{ Var $1 }
	| LEFTBR Expr RIGHTBR	{ $2 }

{
parseError = fail "Parse error"
}
