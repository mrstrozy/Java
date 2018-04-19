//*********************************************
//File:	sigma.c programmed by Matthew Strozyk
//	for CSE305 in Spring 2015 on
//	Assignment 4.
//*********************************************


int main(){

   int sigma(int *k, int low, int high, int expr()) {
      int sum = 0;
      for (*k=low; *k<=high; (*k)++) {
         sum = sum + expr();
      }
      return sum;
   }
   
   int k = 0;
   int j = 0;
   int i = 0;
   int kay(){return k;}
   int one = sigma(&k, 0, 4, kay);
   int jay(){return j*one;}
   int two = sigma(&j, 0, 4, jay);
   int eye(){return i*two;}
   int result = sigma(&i, 0, 4, eye);
   printf("%d\n",result);

}
