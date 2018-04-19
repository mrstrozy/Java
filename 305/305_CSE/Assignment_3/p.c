#include <stdio.h>

int main(){
   int x, y;
   void p1(int y, void q(int)){
      void p2(int x){
         x = y+2;
         printf("%d\n", x);
         q(y);
      }
      if(x==y)
         q(y);
      else p1(y+1,p2);
   }
   void p2(int x){
      x = y+2;
      printf("%d\n", x);
   }
   x = 2; y = 2;
   p1(0,p2);
}
