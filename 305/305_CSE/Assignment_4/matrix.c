//*************************************************************
//File: matrix.c by Matthew Strozyk for CSE305 in Spring 2015
//	on Assignment 4.
//*************************************************************


int main(){

   int a[2][2] = {{1,2},{3,4}};
   int b[2][2] = {{5,6},{7,8}};
   int c[2][2];

   int sigma(int *k, int low, int high, int expr()){
      int sum = 0;
      for(*k=low; *k<=high;(*k)++){
         //printf("sum = %d + %d = %d\n",sum,expr(),sum+expr());
         sum = sum + expr();
      }
      return sum;
   }

   void matmult(int a[2][2], int b[2][2], int c[2][2]){
      for(int i = 0; i < 2; i++){
         for(int j = 0; j < 2; j++){
            int start = 0;

            int matr(){
               return a[i][start] * b[start][j];
            }

            c[i][j] = sigma(&start, 0, 1, matr); 
         }
      }
   }
   matmult(a,b,c);
   printf("%d %d\n%d %d\n",c[0][0], c[0][1], c[1][0], c[1][1]);
}
