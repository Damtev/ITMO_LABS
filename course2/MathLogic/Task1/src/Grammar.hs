module Grammar where

import Data.List (intercalate)

data BinOp = Impl | Or | And

instance Show BinOp where
	show Impl = "->"
	show Or	  = "|"
	show And  = "&"

data Expr = Binary BinOp Expr Expr
	  | Not Expr
	  | Var String

instance Show Expr where
	show (Binary op a b) = "(" ++ intercalate "," [show op, show a, show b] ++ ")"
	show (Not expr)	     = "(!" ++ show expr ++ ")"
	show (Var v)	     = v