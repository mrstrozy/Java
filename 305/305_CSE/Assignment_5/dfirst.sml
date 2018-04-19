(*
//
// "dfirst.sml" written by Matthew Strozyk for CSE 305
//   in Spring 2015.
//*)


datatype 'a tree = leaf | node of 'a * 'a tree * 'a tree;

fun insert(i, leaf) = node(i,leaf,leaf)
  | insert(i, node(v,left,right)) =
                    if i = v then node(v,left,right)
                    else if i < v then node(v,insert(i, left),right)
                         else node(v,left,insert(i, right));




fun dfirst2(tr) =
 let fun df([], ans) = ans
        | df(leaf::t, ans) = df(t,ans)
        | df(node(v,t1,t2)::t, ans) = [v] @ df([t1],[]) @ df([t2],[])
  in df([tr],[])
 end;




fun testcase() =
         let val t1 = node(100,leaf,leaf);
             val t2 = insert(50, t1);
             val t3 = insert(150, t2);
             val t4 = insert(200, t3);
             val t5 = insert(125, t4);
             val t6 = insert(175, t5);
             val t7 = insert(250, t6);
             val t8 = insert(25, t7);
             val root = insert(75, t8)
  in root
 end;

fun test_dfirst2() = dfirst2(testcase());
~

