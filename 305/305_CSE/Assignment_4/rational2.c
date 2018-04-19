//*********************************************************
//File : rational2.c programmed by Matthew Strozyk
//
//for CSE305 in the Spring 2015 Semeseter on
//
//Assignment 4.
//
//*********************************************************


//********************************************************
//
//a. 	normalize does not compute the normal form of the
//	rational number 77/88 because it's parameter is
//	passed by value instead of by reference. With this
//	only a copy of Rational r is being passed instead
//	of the actual Rational r, and thus nothing is 
//	changed for the declared r in main().
//
//********************************************************


//b.

int main(){

	Rational r;

	int normalize(Rational *r) {
		int gcd(int x, int y){
			while(x != y){
		   	if (x > y)
				x = x-y;
		   	else y = y-x;
			return x;
			}
		int g = gcd((*r).numr,(*r).denr);
		(*r).numr = (*r).numr/g;
		(*r).denr = (*r).denr/g;
		}
	}

	r.numr = 77;
	r.denr = 88;
	normalize(&r);

	printf("%d/%d\n", r.numr, r.denr);
}
	
