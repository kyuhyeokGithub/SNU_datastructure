import java.io.*;

public class Matching
{
	public static HashTable<String, String> Str_hashtable;

	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) throws IOException {
		String target = input.substring(2);
		if (input.charAt(0)=='<'){

			BufferedReader reader = new BufferedReader(
					new FileReader(target)
			);
			Str_hashtable = new HashTable<>();

			String line;
			int l = 0;
			while ((line = reader.readLine()) != null) {
				l++;
				int len = line.length();
				for (int j =0; j<=len-6; j++){
					String sub_str = line.substring(j,j+6);
					String item = "("+ Integer.toString(l) + ", "+ Integer.toString(j+1)+ ")";
					Str_hashtable.insert(sub_str, item);
				}
			}
			reader.close();


		} else if (input.charAt(0)=='@'){
			int slot_num = Integer.parseInt(target);
			AVLTree<String, String> target_tree = Str_hashtable.find_slot(slot_num);
			if (target_tree.root.msg == null){
				System.out.println("EMPTY");
			} else {
				StringBuilder strbuilder = new StringBuilder();
				StringBuilder strbuilder2 = target_tree.printall(target_tree.root, strbuilder);
				String Str = strbuilder2.toString();
				String Str2 = Str.replaceFirst(" ", "");
				System.out.println(Str2);
			}

		} else if (input.charAt(0)=='?'){
			/// 여기만 하면 됨 ///
			int len = target.length();
			String first6 = target.substring(0,6);
			AVLNode<String, String> target_AVLNode = Str_hashtable.search(first6);
			if (target_AVLNode==null){
				System.out.println("(0, 0)");
			} else {
				MyLinkedList<String> target_LinkedList = target_AVLNode.indexList;
				int iter_num = target_LinkedList.numItems;
				StringBuilder strbuilder = new StringBuilder();
				int cnt = 0;
				for (int j = 0; j < iter_num; j++) {
					int TF = 1; // pattern 판별에 사용될 예정
					String start = target_LinkedList.get(j);

					int start_line = get_line(start);
					int start_index = get_index(start);
					for (int k = len % 6; k <= len - 6; k += 6) {
						String sub6 = target.substring(k, k + 6);
						AVLNode<String, String> target_AVLNode2 = Str_hashtable.search(sub6);
						MyLinkedList<String> target_LinkedList2 = target_AVLNode2.indexList;
						String item = "(" + Integer.toString(start_line) + ", " + Integer.toString(start_index + k) + ")";
						int IS_HERE = target_LinkedList2.indexOf(item);
						if (IS_HERE == -1) {
							TF = 0;
							break;
						}

					}
					if (TF == 1) {
						cnt++;
						strbuilder.append(start).append(" ");
					}
				}
				if (cnt == 0) {
					System.out.println("(0, 0)");
				} else {
					String Str = strbuilder.toString().trim();
					System.out.println(Str);
				}
			}
		}
	}

	public static int get_line(String s){
		// String s 는 (1, 4) 이런 형태
		// 여기서 1을 리턴하고자 함
		s=s.substring(1, s.length()-1);
		s.replaceAll(" ","");
		String [] x = s.split(",");
		return Integer.parseInt(x[0].trim());
	}

	public static int get_index(String s){
		s=s.substring(1,s.length()-1);
		s.replaceAll(" ","");
		String [] x = s.split(",");
		return Integer.parseInt(x[1].trim());
	}


}

