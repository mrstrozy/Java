'''

File: qsort2.py by Matthew Strozyk for CSE305 in Spring 2015
      for Assignment 4.

'''

def qsort(a):
   if a ==  []: return []
   #left = [x for x in a[1:] if x < a[0]]
   #right = [x for x in a[1:] if x >= a[0]]
   left = []
   right = []

   for x in a[1:]:
      if x < a[0]:
         left.append(x)
      else:
         right.append(x)

   l = qsort(left)
   r = qsort(right)
   l.extend([a[0]])
   l.extend(r)
   return l

print(qsort([5,4,6,3,7,9,2,8,1,0])) 
