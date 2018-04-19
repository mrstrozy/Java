#include <stdio.h>

int main(){

   int fibo(){
      static int first;
      static int sec;
      int temp;

      if(first == 0){
         first = 1;
         return first;
      }

      temp = sec;
      sec = first;
      first = temp + first;
      return first;
   }
   printf("%d\n",fibo());   
   printf("%d\n",fibo());
   printf("%d\n",fibo());
   printf("%d\n",fibo());
   printf("%d\n",fibo());
   printf("%d\n",fibo());
   printf("%d\n",fibo());
   printf("%d\n",fibo());
}

