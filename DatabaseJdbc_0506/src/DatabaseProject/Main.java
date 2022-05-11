package DatabaseProject;

import java.util.List;
import java.util.Scanner;
//1차 수정본
public class Main {
	// DB연동 설문조사 만들기(좋아하는 음악장르)
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		MusicDao mdo = new MusicDao();
		int choice = 0; // 선택지
		String input ="";
	
		while (true) {
			System.out.println("==좋아하는 음악장르 설문조사==");
			System.out.println("1. 설문항목 추가하기");
			System.out.println("2. 설문현황보기");
			System.out.println("3. 설문참여하기");
			System.out.println("4. 초기화하기");
			System.out.println("0. 종료하기");
			System.out.print("선택 -> ");
			choice = scan.nextInt(); scan.nextLine();

			switch (choice) {
			case 1: // 설문조사 항목추가하기
				System.out.print("항목(장르) 입력: ");
				input = scan.nextLine();
				mdo.insertMusic(input);
				
				break;
		
			case 2: // 설문표 현황
				List<MusicVo> ret2 = mdo.selectAll();
				if(ret2 == null){
					System.out.println("데이터가 없습니다.");
					break;
				}else {
					
					for (MusicVo tmp : ret2)
						System.out.println(tmp.voteView());
				}
				break;
					
			case 3: //설문참여하기
				List<MusicVo> ret = mdo.selectAll();
				for (MusicVo tmp : ret)
					System.out.println(tmp);
				
				// 현재 NUMBER에서 + 1로 증가해서 기타항목 추가하기
				System.out.printf("%d.기타(ex:트로트,EDM,힙합):", mdo.countNum() + 1);
				System.out.println("\n");
				
				System.out.print("선택(option) ==>> ");
				choice = scan.nextInt(); scan.nextLine();// 입력대기
				 
				if(choice == mdo.countNum() + 1) {
					System.out.print("장르를 입력하세요 : ");
					String addmusic = scan.nextLine(); //새로운 장르 입력
					mdo.newmusic(addmusic);
				}else {
					System.out.println(choice + "번 선택!");
					mdo.addvote(choice); // "VOTE"누적.
				}
				break;
					
			case 4 : //초기화
				mdo.init();
				break;

			case 0: // 프로그램 종료하고 다시 시작하기
				System.out.println("프로그램 종료!");
				System.out.println("==================");
				System.out.println("총 투표수 : " + mdo.totalvote() + " 표");
				System.out.println("==================");
				mdo.initvote();
				break;				
			}
			System.out.println(); 
		}
	}
}