# mathematical-expression-evaluator

This application provides the result of an arithmetic expression.
The allowed operands are unsigned integers.
The allowed operators are: ^, *, /, %, +, -.
The round brackets can be used to alter intrinsic priorities."

write the expression in the text field and press on "Valuta" to get the result

Evaluation algorithm
You use two stacks: the first is a stack of operands, the second is a stack of character operators. When an operand arrives, you push it to the top of the operand stack. When a operator, be it opc, we proceed as follows:
A) if opc is more priority than the operator emerging from the operator stack or that stack is empty, you pushes opc to the top of the operators stack.
B) If opc is no longer a priority over the top of the operators stack, you pick up the op operator at top of the stack operators, then pull two operands o2 (top) and o1 (top-1) from the stack operands (in case of exceptions, the expression is malformed). You run o1 op o2. The result is pushed to the top of the operands stack. You continue to perform step  B) if opc is still not more priority than the operator emerging the top of the operators stack. After this, or because opc is more priority than the operator at the top of the operators stack or because that stack is empty, applies case A.
When the expression ends, if the stack operators are not empty, you extract the operators present and apply to their two operands taken from the stack operands, such as explained in point B), each time pushing the result to the top of the operand stack. When the stack operators are empty, then the stack operands should contain only one element which is the result of the expression. Any other situation (stack operands empty or with more than one element) denotes a situation of malformed expression.

![image](https://user-images.githubusercontent.com/80411845/128207883-fa3fb033-0da7-4b95-9a90-da6bc09e1599.png)
