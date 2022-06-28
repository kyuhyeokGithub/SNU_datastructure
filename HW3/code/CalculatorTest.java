import java.io.*;
import java.util.Stack;
import java.util.regex.Pattern;

public class CalculatorTest
{
	public static final String ERROR_MSG = "ERROR";
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println(ERROR_MSG);
			}
		}
	}

	// String input을 postfix 형태로 바꿔야 함 ( 띄어쓰기로 구분하기 )
	// -1--3 -> ~1-~3 으로 표현
	// !는 stack에 연산자로 쌓는순간 아래 숫자 pop해서 -1 곱하고 다시 push
	// Stack<Integer> stack = new Stack<>(); 이렇게 선언, import java.util.Stack; //import
	// 연산자 우선 순위 [+,-] < [*,/,%] < [!] < [^]
	// 괄호는 따로 처리
	// error : 1. 숫자사이 공백 2. 짝이 맞지 않는 괄호 3. 숫자&연산자&공백 외로 이루어진 문자열 4. 연산자별 에러케이스

	private static void command(String input) throws Exception
	{
		// 숫자사이 공백,탭 체크
		boolean space_check = Pattern.matches("^(?:.)*(?:[0-9])[\\s|\\t]+(?:[0-9])(?:.)*$", input);
		if (space_check) {
//			System.out.println("line42");
			throw new Exception("");
		}
		// 주어진 공백, 숫자, 특수문자로만 이루어졌는지 테스트
		boolean domain_check = Pattern.matches("^(?:[\\s|[0-9]|[+]|[*]|\\(|\\)|\\^|\\%|\\-|\\/])*$", input);
		if (!domain_check) {
//			System.out.println("line48");
			throw new Exception("");
		}

		// input의 모든 공백 제거
		input = input.replaceAll(" ","");
		input = input.replaceAll("\\t","");
		input = input.trim();

		int open = 0;
		int close = 0;

		// unary setting
		char[] input_A = input.toCharArray();
		int length = input_A.length;
		for (int i=0; i<length; i++){

			if (input_A[i]=='-'){
				if(i==0){
					input_A[i] = '~';
				}else if((!Character.isDigit(input_A[i-1]))&&(!(input_A[i-1]==')'))){
					input_A[i]='~';
				}
			} else if (input_A[i]=='('){
				open++;
			} else if (input_A[i]==')'){
				close++;
			}
		}

		// 짝이 맞지 않는 괄호 테스트
		if (open!=close){
//			System.out.println("line79");
			throw new Exception("");
		}

		// infix_input은 input의 unary '-'를 '~'로 바꿔준 문자열
		String infix_input = new String(input_A);

		// infix 식을 postfix 식으로
		String postfix_str = infixTopostfix(infix_input);



		// postfix 식을 가지고 stack 이용해서 계산하는 작업
		long result = Evaluate(postfix_str);

		System.out.println(postfix_str);
		System.out.println(result);
	}

	public static boolean isOperator(char target){
		if (target=='+'||target=='-'||target=='~'||target=='*'||target=='%'||target=='/'||target=='^'||target=='('||target==')') {
			return 1==1;
		} else {
			return 1 == 0;
		}
	}

	// operator
	// (:0, +-:1, */%:2, ~:3, ^:4
	// 아닌경우 에러 처리
	public static int Rank(char target) throws Exception {
		if (isOperator(target)){
			if (target=='+'||target=='-'){
				return 1;
			} else if (target=='*'||target=='/'||target=='%'){
				return 2;
			} else if (target=='~'){
				return 3;
			} else if (target=='^'){
				return 4;
			} else if (target=='('){
				// stack에서 어떤 연산이든 top이 (이면 여기에서 push 해야하므로 제일작은 0 할당
				return 0;
			}

		}else{
//			System.out.println("line126");
			throw new Exception("");

		}
		return -100;
	}

	// infix 형태인 input을 받아서
	// String 인 postfix 형태의 식으로 반환
	public static String infixTopostfix(String input) throws Exception{

		char[] input_A = input.toCharArray();
		int length = input_A.length;
		StringBuilder stringBuilder = new StringBuilder();
		int i = 0;
		Stack<Character> stack = new Stack<>();

		for(i=0; i<length; i++){
			if (Character.isDigit(input_A[i])){


				// 원소가 숫자라면 숫자가 끝날때까지 공백없이 계속 숫자 입력 후 숫자 아닌거 튀어나오면 공백 입력
				while(i<length&&Character.isDigit(input_A[i])){

					stringBuilder.append(input_A[i]);
					i++;
				}
				stringBuilder.append(" ");
				i--;

			} else if (isOperator(input_A[i])){
				// 원소가 operator 인 경우 처리


				if (stack.isEmpty()){
					stack.push(input_A[i]);
				} else {

					if (input_A[i]=='('){
						stack.push(input_A[i]);

					} else if (input_A[i]==')'){
						while(!(stack.peek()=='(')){
							stringBuilder.append(stack.pop()).append(' ');
						}
						char trash = stack.pop();

					} else if (input_A[i]=='~'){
						stack.push(input_A[i]);

					} else if (input_A[i]=='^') {
						// ^의 경우 else에 해당하는 연산자와 비슷하나 같은 연산자에 대해 뒤에 오는 녀석이 더 우선순위를 가지므로
						// while문에서 등호를 빼주어야한다
						while((!stack.isEmpty())&&(Rank(input_A[i])<Rank(stack.peek()))){
							stringBuilder.append(stack.pop()).append(' ');
						}
						stack.push(input_A[i]);
					} else {
						while((!stack.isEmpty())&&(Rank(input_A[i])<=Rank(stack.peek()))){
							stringBuilder.append(stack.pop()).append(' ');
						}
						stack.push(input_A[i]);
					}

				}
			// 숫자도, operator도 아니면 exception 처리
			} else {
//				System.out.println(input_A[i]);
//				System.out.println("193");
				throw new Exception("");
			}
		}



		// 입력한 input에 대해 String 과 stack 에 나누어 저장했음
		// stack에 쌓여있는 operator를 postfix 저장하는 String에 입력해줌
		while (!(stack.isEmpty())){
			stringBuilder.append(stack.pop()).append(' ');
		}

		// String으로 만들고 trim -> 양쪽 공백 없애주려고
		String str = stringBuilder.toString();
		str = str.trim();

		return str;

	}

	public static long Evaluate(String postfix_exp) throws Exception{
		Stack<Long> stack = new Stack<>();
		String[] exp_A = postfix_exp.split("\\s");
		long A;
		long B;
		int len = exp_A.length;
		for(int i=0; i<len; i++){
			if (Character.isDigit(exp_A[i].charAt(0))){
				stack.push(Long.parseLong(exp_A[i]));
			} else if (isOperator(exp_A[i].charAt(0))){
				if (exp_A[i].charAt(0)=='~'){
					A=stack.pop();
					A=(-1)*A;
					stack.push(A);
				} else {
					B=stack.pop();
					A=stack.pop();
					long n=0;
					switch(exp_A[i].charAt(0)){
						case '*' :
							n=A*B;
							break;
						case '+' :
							n=A+B;
							break;
						case '-' :
							n=A-B;
							break;
						case '/' :
							if (B==0){
//								System.out.println("244");
								throw new Exception("");
							}
							n=A/B;
							break;
						case '%' :
							if (B==0){
//								System.out.println("251");
								throw new Exception("");
							}
							n=A%B;
							break;
						case '^' :
							if (A==0 && B<0){
//								System.out.println("258");
								throw new Exception("");
							}
							n= (long) Math.pow((double)A,(double)B);
							break;
						default :
							n = -100;
							break;
					}
					stack.push(n);
				}

			}
		}
	// 연산이 완료되면 stack에 한개만 있어야 함
		long result;
		try {
			result = stack.pop();
			if (!stack.isEmpty()) {
//				System.out.println("277");
				throw new Exception("");
			}
		} catch (Exception e){
			throw e;
		}
		return result;

	}


}
