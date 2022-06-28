import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";

    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    public char[] number;

    // if int i=-1234567, 0
    // then number = {'-','7','6','5','4','3','2','1'}, {'+'}
/*
    public BigInteger(int i)
    {
        if(i>=0){
            this.number[0]="+";
        } else{
            this.number[0]="-";
        }
        i = Math.abs(i);
        for ( int k=1; i==0; k++){
            this.number[k]=(char)(i%10+'0');
            i = i/10;
        }
    }
*/

//    public BigInteger(int[] num1) {}


    // if string s='-1234567', '0', '123'
    // then number = {'-','7','6','5','4','3','2','1'} , {'+','0'}, {'+','1','2','3'}
    public BigInteger(String s)
    {
        int s_len = s.length();
        String [] chs_r;
        String[] chs = s.split("");
        if (chs[0].equals("+")){
            chs_r = new String[s_len];
            chs_r[0]="+";
            for ( int k=1; k<=s_len-1; k++){
                chs_r[k]=chs[s_len-k];
            }
        } else if(chs[0].equals("-")){
            chs_r = new String[s_len];
            chs_r[0]="-";
            for ( int k=1; k<=s_len-1; k++){
                chs_r[k]=chs[s_len-k];
            }
        } else{
            chs_r = new String[s_len+1];
            chs_r[0]="+";
            for ( int k=0; k<=s_len-1; k++){
                chs_r[k+1]=chs[s_len-k-1];
            }
        }
        String chs_r_string = String.join("",chs_r);
        number = chs_r_string.toCharArray();
    }

    public char[] zeroarray(char[] c, int length)
    {
        char [] num = new char[length];
        int l = c.length;
        for (int i=0; i<l; i++){
            num[i]=c[i];
        }
        for (int j=l; j<length; j++){
            num[j]='0';
        }
        return num;
    }


    // char array와 int 주면 나머지는 '0'으로 채워주는 method 만들어야함
    public BigInteger add(BigInteger big)
    {
        int length = Math.max(this.number.length,big.number.length) +1;
        char[] num = new char[length];
        int[] calculate = new int[length];
        char[] num1 = zeroarray(this.number, length);
        char[] num2 = zeroarray(big.number, length);
        num[0] = this.number[0];
        calculate[0] = 0;
        for (int i=1; i<length; i++){
            calculate[i]=(num1[i]-'0')+(num2[i]-'0');
        }
        for (int i=1; i<length-1; i++){
            if(calculate[i]>=10){
                calculate[i] -= 10;
                calculate[i+1] += 1;
            }
        }
        for (int i=1; i<length; i++){
            num[i]=(char)(calculate[i]+'0');
        }
        String str = new String(num);
        BigInteger result = new BigInteger(str);
        return result;
    }
  
    public BigInteger subtract(BigInteger big)
    {
        int length = Math.max(this.number.length,big.number.length)+1;
        char[] num = new char[length];
        int[] calculate = new int[length];
        char[] num1 = zeroarray(this.number, length);
        char[] num2 = zeroarray(big.number, length);
        String p = "+";
        String m = "-";
        num[0] = p.charAt(0);
        calculate[0] = 0;

        for (int i=1; i<length; i++){
            calculate[i]=(num1[i]-'0')-(num2[i]-'0');
        }
        int targetj=-1;
        for(int j=length-1; j>=0; j--){
            if(calculate[j]!=0){
                targetj = j;
                break;
            }
        }
        if(targetj==-1){

        } else {
            if (calculate[targetj] < 0) {
                num[0] = m.charAt(0);
                for (int k = 1; k < length; k++) {
                    calculate[k] = calculate[k] * (-1);
                }
            }
            for (int r = 1; r < length - 1; r++) {
                if (calculate[r] < 0) {
                    calculate[r] += 10;
                    calculate[r + 1] -= 1;
                }
            }
        }
        for (int i=1; i<length; i++){
            num[i]=(char)(calculate[i]+'0');
        }
        String str = new String(num);
        BigInteger result = new BigInteger(str);
        return result;
    }
  
    public BigInteger multiply(BigInteger big)
    {
        int length = this.number.length+big.number.length-1;
        char[] num = new char[length];
        int[] calculate = new int[length];
        char[] num1 = this.number;
        char[] num2 = big.number;
        int length1 = this.number.length;
        int length2 = big.number.length;

        String p = "+";
        String m = "-";

        if(this.number[0]==big.number[0]){
            num[0]=p.charAt(0);
        } else {
            num[0]=m.charAt(0);
        }
        for (int i=1; i<length; i++){
            calculate[i]=0;
        }
        for (int i=1; i<length1; i++) {
            for (int j=1; j<length2; j++ ) {
                calculate[i+j-1] += (num1[i] - '0') * (num2[j] - '0');
            }
        }
        for (int i=1; i<length; i++){
            if(calculate[i]>=10){
                calculate[i+1] += calculate[i]/10;
                calculate[i] = calculate[i]%10;
            }
        }
        for (int i=1; i<length; i++){
            num[i]=(char)(calculate[i]+'0');
        }
        String str = new String(num);
        BigInteger result = new BigInteger(str);
        return result;
    }
  
    @Override
    public String toString()
    {
        char[] target = this.number;
        String output = Character.toString(target[0]);
        int l=target.length;
        int targeti=-1;
        String p = "+";
        for (int i=1; i<l; i++){
            if(target[i]!='0'){
                targeti=i;
                break;
            }
        }
        if (targeti==-1){
            return Integer.toString(0);
        } else {
            for (int j=targeti; j<l; j++) {
                output += Character.toString(target[j]);
            }
        }
        if (target[0]==p.charAt(0)){
            return output.substring(1);
        }else{
            return output;
        }
    }

    // 공백 신경쓰기
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // input의 모든 공백 제거
        input = input.replaceAll(" ","");

        int multiply_index = input.indexOf("*");
        String p = "+";
        String m = "-";
        // 곱하기 문자 발견시
        if (multiply_index>=0){

            String arg1 = input.substring(0,multiply_index);
            String arg2 = input.substring(multiply_index+1);
            BigInteger num1 = new BigInteger(arg1);
            BigInteger num2 = new BigInteger(arg2);
            BigInteger result = num1.multiply(num2);
            return result;

        } else {
            //곱하기 문자 발견 못한경우 -> 가운데 부호가 + 또는 -
            // 부호가 나오는 경우 : 가장 앞, 중간에 두개
            String target = input;
            target = target.replace("-","+");
            int plus_index = target.indexOf("+",1);
            int plusminus_index = plus_index;
            char plusminus = input.charAt(plusminus_index);
            String arg1 = input.substring(0,plusminus_index);
            String arg2 = input.substring(plusminus_index+1);
            BigInteger num1 = new BigInteger(arg1);
            BigInteger num2 = new BigInteger(arg2);

            if (plusminus==p.charAt(0)){
                if(num1.number[0]==num2.number[0]){
                    BigInteger result = num1.add(num2);
                    return result;
                } else {
                    if(num1.number[0]==p.charAt(0)) {
                        BigInteger result = num1.subtract(num2);
                        return result;
                    } else {
                        BigInteger result = num2.subtract(num1);
                        return result;
                    }
                }
            } else {
                if(num1.number[0]!=num2.number[0]){
                    BigInteger result = num1.add(num2);
                    return result;
                } else {
                    if(num1.number[0]==p.charAt(0)) {
                        BigInteger result = num1.subtract(num2);
                        return result;
                    } else {
                        BigInteger result = num2.subtract(num1);
                        return result;
                    }
                }
            }

        }


        // implement here
        // parse input
        // using regex is allowed
  
        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;
    }
  
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))

        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }

    // method 안에서 IllegalArgument Exception으로 언제 throw 할지 정해주어야함
    // input이 quit면 true, 아니면 계산 수행해서 프린트
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);

        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
            return false;
        }
    }
    // input 문자열과 QUIT_COMMAND(=quit)이 대소문자 구분없이 같으면 true, 아니면 false return
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
