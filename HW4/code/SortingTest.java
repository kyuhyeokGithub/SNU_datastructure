import java.io.*;
import java.util.*;

import static java.lang.Math.abs;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					StringBuilder sb = new StringBuilder();
					String a = "";
					for (int i = 0; i < newvalue.length; i++)
					{
						sb.append(a);
						sb.append(Integer.toString(newvalue[i]));
						a="\n";
					}
					System.out.println(sb.toString());
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		int dummy = 0;
		boolean is_swap;
		int length = value.length;
		is_swap = false;
		for (int i=length-1; i>=1; i--){

			for( int j=0; j<i; j++){
				if (value[j]>value[j+1]){
					dummy = value[j];
					value[j]=value[j+1];
					value[j+1]=dummy;
					is_swap=true;
				}
			}
			if (is_swap == false){
				break;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		// TODO : Insertion Sort 를 구현하라.
		int length = value.length;
		for (int i=1; i<length; i++){
			int index = i-1;
			int target = value[i];
			while ( index>=0 && target<value[index]){
				value[index+1]=value[index];
				index--;
			}
			value[index+1]=target;
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		int length = value.length;
		int dummy;
		DobuildHeap(value, 0, length-1 );
		for(int i=length-1; i>0; i--){
			dummy=value[0];
			value[0]=value[i];
			value[i]=dummy;
			DopercolateDown(value, 0, i-1);
		}
		return (value);
	}
	private static void DobuildHeap(int[] value, int a, int b){
		if (b-a>=1){
			for (int i = (b-1)/2; i>=a; i--){
				DopercolateDown(value,i,b);
			}
		}
	}

	private static void DopercolateDown(int[] value, int i, int b){
		int child = 2*i+1;
		int rightchild= 2*i+2;
		int dummy;
		if (child<=b){
			if((rightchild<=b)&&(value[child]<value[rightchild])){
				child=rightchild;
			}
			if (value[i]<value[child]){
				dummy=value[i];
				value[i]=value[child];
				value[child]=dummy;
				DopercolateDown(value, child, b);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		int[] dummy = new int[value.length];
		for (int i=0; i<value.length; i++){
			dummy[i]=value[i];
		}
		msort(0,value.length-1, value, dummy);
		return (value);
	}

	private static void msort(int p, int r, int A[], int B[]){
		if (p<r){
			int q = (p+r)/2;
			msort( p, q, B, A);
			msort(q+1, r, B, A);
			merge(p,q,r,B,A); // 정렬된 B를 A에 합치는 작업을 할것임, 즉 A에 sorting된 결과가 나옴
		}
	}

	private static void merge(int p, int q, int r, int C[], int D[]){
		int x=p; // 앞 msort 결과물의 시작부분
		int y=q+1; // 뒤 msort 결과물의 시작부분
		int i=p; // D의 index 위치
		while( x<=q && y<=r ){
			if(C[x]<=C[y]){
				D[i]=C[x];
				i++;
				x++;
			} else {
				D[i]=C[y];
				i++;
				y++;
			}
		}
		while ( x<=q ){
			D[i]=C[x];
			i++;
			x++;
		}
		while ( y<=r ){
			D[i]=C[y];
			i++;
			y++;
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		int length = value.length;
		qSort(value, 0, length-1);

		return (value);
	}

	private static void qSort(int[] value, int p, int r){
		if (p<r){
			int q = partition(value, p, r);
			qSort(value, p, q-1);
			qSort(value, q+1, r);
		}
	}

	private static int partition(int[] value, int p, int r){
		int pivot = value[r];
		int i = p-1; // pivot 이하의 element 처리를 위함
		int j = p;
		int dummy;
		for(j=p; j<r; j++){
			if (value[j]<=pivot){
				i++;
				dummy = value[j];
				value[j]=value[i];
				value[i]=dummy;
			}
		}
		i++;
		dummy = pivot;
		value[r]=value[i];
		value[i]=dummy;
		return i;
	}



	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		int length = value.length;
		int q = plus_minus(value, 0, length-1); // value[q]부터 0이상의 값들만 몰려있음, 해당 작업은 theta(n) 작업
		int max = -1;
		int min = 1;

		if (q==0){
			int[] plus = new int[length];

			for (int i=0; i<length; i++){
				if (value[i]>max) max=value[i];
				plus[i]=value[i];
			}
			int numDigits = (int)Math.log10(max) + 1;
			rSort(plus, numDigits);
			return plus;

		} else if (q==length){
			int[] minus = new int[length];

			for (int i=0; i<length; i++){
				if (value[i]<min) min=value[i];
				minus[i]=(-1)*value[i];
			}
			int numDigits = (int)Math.log10(abs(min)) + 1;
			rSort(minus, numDigits);
			for (int i=0; i<length; i++){
				value[i]=(-1)*minus[length-1-i];
			}
			return value;

		} else {
			int[] result_A = new int[length];
			int[] plus = new int[length-q];
			int[] minus = new int[q];
			int p=0;
			int m=0;
			for (int i=0; i<length; i++){
				if (value[i]>max){
					max=value[i];
				} else if (value[i]<min){
					min = value[i];
				}
				if (i>=q){
					plus[p]=value[i];
					p++;
				} else {
					minus[m]=(-1)*value[i];
					m++;
				}
			}
			int PnumDigits = (int)Math.log10(max) + 1;
			int MnumDigits = (int)Math.log10(abs(min)) + 1;

			rSort(plus, PnumDigits);
			rSort(minus, MnumDigits);

			for (int i=0; i<length; i++){
				if(i<q){
					result_A[i]=(-1)*minus[q-1-i];
				} else {
					result_A[i]=plus[i-q];
				}
			}
			return result_A;
		}
	}

	private static int plus_minus(int[] value, int p, int r){
		int pivot = 0;
		int i = p-1; // pivot 이하의 element 처리를 위함
		int j = p;
		int dummy;
		for(j=p; j<=r; j++){
			if (value[j]<pivot){
				i++;
				dummy = value[j];
				value[j]=value[i];
				value[i]=dummy;
			}
		}
		i++;
		return i;
	}

	private static void rSort(int[] A, int numDigits){
		int cnt[] = new int[10];
		int start[] = new int[10];
		int B[] = new int[A.length];
		for (int digit=1; digit <= numDigits; digit++){
			for(int d =0; d<=9; d++){
				cnt[d]=0;
			}
			for(int i =0; i<A.length; i++){
				cnt[(int)(A[i]/Math.pow(10, digit-1))%10]++;
			}
			start[0]=0;
			for (int j=1; j<=9; j++){
				start[j]=start[j-1]+cnt[j-1];
			}
			for(int i =0; i<A.length; i++){
				B[start[(int)(A[i]/Math.pow(10, digit-1))%10]++] = A[i];
			}
			for (int i=0; i<A.length; i++){
				A[i]=B[i];
			}


		}


	}


}

