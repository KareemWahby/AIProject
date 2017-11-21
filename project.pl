:- include('grid.pl').

%get all pressed pades
pressedPads(pPad(X,Y)) :- 
		pPad(X,Y),
		rock(X,Y,_).
%get all unpressed pads
notPressedPads(pPad(X,Y)):-
		pPad(X,Y),
		pressedPads(pPad(A,B)),
		A\=X,B\=Y.
%if all pads are pressed portal is open
isPortalOpen(true):-
		forall(pPad(X), pressedPads(X)).
%check cell validity
validCell(X,Y):-
		\+rock(X,Y,_),
		\+block(X,Y),
		grid(A,B),
		(X<A),(X>0;X=0),(Y<B),(Y>0;Y=0).
%condition -> then_clause ; else_clause 

%ssa r2d2
r2d2(X, Y, result(A,S)):-
   		(A=north, Y1 is Y+1, r2d2(X,Y1,S),                         validCell(X,Y));
   		(A=south, Y1 is Y-1, r2d2(X,Y1,S),                         validCell(X,Y));
   		(A=east,  X1 is X+1, r2d2(X1,Y,S),                         validCell(X,Y));
   		(A=west,  X1 is X-1, r2d2(X1,Y,S),                         validCell(X,Y));
   		(A=north, Y1 is Y+1, r2d2(X,Y1,S), rock(X,Y,S), Y2 is Y-1, validCell(X,Y2));
   		(A=south, Y1 is Y-1, r2d2(X,Y1,S), rock(X,Y,S), Y2 is Y+1, validCell(X,Y2));
   		(A=east,  X1 is X+1, r2d2(X1,Y,S), rock(X,Y,S), X2 is X-1, validCell(X2,Y));
   		(A=west,  X1 is X-1, r2d2(X1,Y,S), rock(X,Y,S), X2 is X+1, validCell(X2,Y)).

%escaped(S):- r2d2(X,Y,S), teleportal(X,Y).