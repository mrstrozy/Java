//*********************************************************
//Assignment_2 for CSE305
//
//Due Date: Wednesday, February 25th at 11:59 PM
//
//File: TinyPL.java
//
//Creator: Matthew Strozyk
//
//*********************************************************


package Tiny;


public class TinyPL {
		
	public static void main(String args[]) {   
		   Lexer.lex();
		   new Program();  
	}
}

class Program {
	Decls d; Stmts s;//HashMap<Character,>
	public Program(){
		if(Lexer.nextToken == Token.KEY_BEGIN){ 
			Lexer.lex();
        } else { //Program did not start with "begin"
    	  System.err.println("Error: Program MUST start with \"begin\" ");
    	  System.exit(1);
		}
		while(Lexer.nextToken != Token.KEY_END){//Find when "end" is declared
			if(Lexer.nextToken == Token.KEY_INT || 
					Lexer.nextToken == Token.KEY_REAL ||
						Lexer.nextToken == Token.KEY_BOOL){ //Token is a Decls
				d = new Decls();
			} else if(Lexer.nextToken == Token.KEY_IF || Lexer.nextToken == Token.KEY_WHILE || Lexer.nextToken == Token.ID){ //Token is a while or if stmt
				s = new Stmts();
			}
		}
	}
}

class Decls {
	Idlist i,r,b;
	public Decls(){
		while(Lexer.nextToken == Token.KEY_INT || 
				Lexer.nextToken == Token.KEY_REAL ||
					Lexer.nextToken == Token.KEY_BOOL){
		   switch(Lexer.nextToken){
		   
		   case Token.KEY_INT:
	          Lexer.lex(); //Verify type, make new Idlist
	          i = new Idlist();
		      break;
		   case Token.KEY_REAL:
			  Lexer.lex(); //Verify type, make new Idlist
			  r = new Idlist();
			  break;
		   case Token.KEY_BOOL:
			  Lexer.lex(); //Verify type, make new Idlist
			  b = new Idlist();
			  break;
		   default:
			  System.err.println("Type unknown");
		      System.exit(1);
		      break;
		      
		   }
		   if(Lexer.nextToken == Token.SEMICOLON){
			   Lexer.lex(); //Pass over semicolon
		   } else {
			   System.err.println("Must end declarations in a ';'");
			   System.exit(1);
		   }
		}	
	}	 
}

class Idlist {
	Idlist i; char id;
	public Idlist(){
		if(Lexer.nextToken == Token.SEMICOLON || Lexer.nextToken == Token.COMMA){
			System.err.println("Empty ID Declaration");
			System.exit(1);
		}//Check for empty declaration
		
		if(Lexer.nextToken == Token.ID){
			id = Lexer.ident;
			Lexer.lex(); // Pass over ID
		} else {
			System.err.println("Type mismatch: " +Lexer.ident);
			System.exit(1);
		}
		if(Lexer.nextToken == Token.COMMA){//Check if more ID's are listed
			Lexer.lex();
			i = new Idlist();
		}
	}	 
}

class Stmts {
	Stmts st; Stmt s;
	public Stmts(){
			s = new Stmt();
			if(Lexer.nextToken == Token.KEY_IF || Lexer.nextToken == Token.KEY_WHILE || Lexer.nextToken == Token.ID){
				st = new Stmts();
			}
	}
	 
}

class Stmt {
	Assign a; Cond c; Loop l; Cmpd cm;
	public Stmt(){
		switch(Lexer.nextToken){
		case Token.ID:
			a = new Assign();
			break;
		case Token.KEY_IF:
			c = new Cond();
			break;
		case Token.KEY_WHILE:
			l = new Loop();
			break;
		case Token.LEFT_BRACE:			cm = new Cmpd();
			break;
		default:
			System.err.println("'" +Lexer.nextToken+"':Not recognized. Sorry. Exiting...");
			System.exit(1);
		}
	}
	 
} 

class Assign {
	Expr e; char id;
	public Assign(){
		id = Lexer.ident;
		Lexer.lex(); // Skip over ID
		if(Lexer.nextToken == Token.ASSIGN_OP){
			Lexer.lex();
			e = new Expr();
		} else {
			System.err.println("Not correct assignment syntax. Exiting...");
			System.exit(1);
		}
		//Lexer.lex();
		if(Lexer.nextToken != Token.SEMICOLON){
			System.err.println("Dangling assignment statement left. Exiting...");
			System.exit(1);
		}
		Lexer.lex();//Pass over Semicolon
	}
}

//Have already confirmed we are at an "if"
class Cond  {
	Expr e; Stmt s; Stmt s2;
	public Cond(){
		//System.out.println("***Cond Identified");
		Lexer.lex();//Pass over 'if'
		if(Lexer.nextToken == Token.LEFT_PAREN){
			//Lexer.lex();//Pass over Left Paren
			e = new Expr();
		} else {
			System.err.println("Need Left Paren after 'IF'");
			System.exit(1);
		}

		s = new Stmt();//Out of conditional, now for the statement
		
		if(Lexer.nextToken == Token.KEY_ELSE){
			Lexer.lex();
			s2 = new Stmt();
		}
		
	}
	 
}

class Loop {
	Expr e; Stmt s;
	public Loop(){
		//System.out.println("***Loop Identified");
		Lexer.lex(); //Pass 'WHILE'
		if(Lexer.nextToken == Token.LEFT_PAREN){
			//Lexer.lex(); //Pass over Left Paren
			e = new Expr();
		} else {
			System.err.println("Found no Left Paren after Loop Stmt");
			System.exit(1);
		}

		s = new Stmt();//Out of condition, now parsing stmt

	}
}

class Cmpd  {
	Stmts s;
	public Cmpd(){
		//System.out.println("***Cmpd Identified");
		Lexer.lex(); //Pass over Left Brace
		s = new Stmts();
		if(Lexer.nextToken == Token.RIGHT_BRACE){
			Lexer.lex(); //Pass over Right Brace
		} else {
			System.err.println("Did not end Cmpd with '}'");
			System.exit(1);
		}
	}
	 
}


class Expr {  
	Term t; Expr e; String op = "";
	public Expr(){
		t = new Term();
		if(Lexer.nextToken == Token.ADD_OP ||
		   Lexer.nextToken == Token.SUB_OP ||
		   Lexer.nextToken == Token.OR_OP){
				op = Token.toString(Lexer.nextToken);
				Lexer.lex();//pass over op
				e = new Expr();
		}
	} 
}

class Term {  
	Factor f; Term t; String op = "";
	public Term(){
		f = new Factor();
		if(Lexer.nextToken == Token.MULT_OP ||
		   Lexer.nextToken == Token.DIV_OP ||
		   Lexer.nextToken == Token.AND_OP){
				op = Token.toString(Lexer.nextToken);
				Lexer.lex();//pass over op
				t = new Term();
		}
	}
}


class Factor {  
	Factor f; Expr e, e2; Id_Lit id;Int_Lit i; Bool_Lit b; Real_Lit r;
	String op = "";
	public Factor(){
		if(Lexer.nextToken == Token.INT_LIT ||
		   Lexer.nextToken == Token.REAL_LIT ||
		   Lexer.nextToken == Token.TRUE_LIT || 
		   Lexer.nextToken == Token.FALSE_LIT ||
		   Lexer.nextToken == Token.ID){
			switch(Lexer.nextToken){
			case 14:
				id = new Id_Lit();
				break;
			case 15:
				i = new Int_Lit();
				break;
			case 16:
				r = new Real_Lit();
				break;
			case 21:
				b = new Bool_Lit(true);
				break;
			case 22:
				b = new Bool_Lit(false);
				break;
			}
		} else if(Lexer.nextToken == Token.NEG_OP){
				op = Token.toString(Lexer.nextToken);
				Lexer.lex(); // Pass '!'
				f = new Factor();
		} else if(Lexer.nextToken == Token.LEFT_PAREN){
				Lexer.lex(); //Pass Left Paren
				e = new Expr();
				if(Lexer.nextToken == Token.RIGHT_PAREN){
					Lexer.lex();//Pass over ')'
				} else if(Lexer.nextToken == Token.LE_OP || //'<="
				          Lexer.nextToken == Token.GE_OP || // '>='
				          Lexer.nextToken == Token.EQ_OP || // '=='
				          Lexer.nextToken == Token.LT_OP || // '<'
				          Lexer.nextToken == Token.GT_OP){ // '>'
						op = Token.toString(Lexer.nextToken);//set op string
						Lexer.lex();//Pass over comparison Operator
						e2 = new Expr();
						if(Lexer.nextToken == Token.RIGHT_PAREN){
							Lexer.lex(); //Pass over Right Paren
						} else {
							System.err.println("Need Right Paren after Expression");
							System.exit(1);
						}
				} else {
					System.err.println("Expression not recognized. Exiting...");
					System.exit(1);
				}
		} else {
				System.err.println("Error, don't recognize assignment. Exiting...");
				System.exit(1);
		}
	}
	 
}


//PROBLEM: Subclass calls Literal on first call
class Literal {

	public Literal(){
		
	}
	 
}

class Int_Lit extends Literal {
	int i;
	public Int_Lit(){
		i = Lexer.intValue;
		Lexer.lex();//Skip Over Literal
		//Lexer.lex();//Next is a Semicolon, skip over it
	}
	 
	
}

class Real_Lit extends Literal {
	double d;
	public Real_Lit(){
		d = Lexer.realValue;
		Lexer.lex(); // Pass over Real Lit
		//Lexer.lex();//Next is a Semicolon, skip over it

	}
	 
	
}

class Bool_Lit extends Literal {
	 boolean b;
	 public Bool_Lit(boolean b2){
		 b = b2;
		 Lexer.lex(); //Pass over Bool Lit
		//Lexer.lex();//Next is a Semicolon, skip over it

	 }
	
}

class Id_Lit extends Literal {
	char literal;
	public Id_Lit(){
		literal = Lexer.ident;
		//System.out.println("ID is: " +literal);
		Lexer.lex();// Pass over ID Lit
		//Lexer.lex();//Next is a Semicolon, skip over it

	}	
}

