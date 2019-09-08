{
module Lexer where
}

%wrapper "basic"

$digit = 0-9
$alpha = [A-Z]

tokens :-

	$white+				;
	\(				{ \_ -> LeftBr }
	\)				{ \_ -> RightBr }
	\|				{ \_ -> OrT }
	&				{ \_ -> AndT }
	"->"				{ \_ -> ImplT }
	!				{ \_ -> NotT }
	$alpha [$alpha $digit '’']*		{ \s -> VarT s }

{

data Token = 
	LeftBr		|
	RightBr		|
	OrT		|
	AndT		|
	ImplT		|
	NotT		|
	VarT String	
	deriving (Show, Eq)

}
