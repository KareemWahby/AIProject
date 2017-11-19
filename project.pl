:- include('grid.pl').
%get all pressed pades
pressedPads(X) :- 
		pPad(X),
		rock(A),
		at(X,Y,Z),
		at(A,Y,Z).
%get all unpressed pads
notPressedPads(Z):-
		pPad(Z),
		pressedPads(X),
		Z\=X.
%if all pads arer pressed portal is open
isPortalOpen(true):-
		forall(pPad(X),pressedPads(X)).	