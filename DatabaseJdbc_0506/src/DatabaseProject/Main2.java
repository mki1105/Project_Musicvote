package DatabaseProject;

import java.util.List;
import java.util.Scanner;

//2차 수정본
public class Main2 {
	// DB연동 설문조사 만들기(좋아하는 음악장르)
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		MusicDao2 mdo = new MusicDao2();
		int choice = 0; // 선택지
		
		while (true) {
			System.out.println("==좋아하는 음악장르 설문조사==");
			System.out.println("1. 초기화하기");
			System.out.println("2. 음악장르 등록하기");
			System.out.println("3. 설문참여하기");
			System.out.println("4. 설문현황보기");
			System.out.println("5. 항목 이름변경하기");
			System.out.println("6. 최종 투표 수 확인(종료)하기!");
			System.out.println("");
			System.out.println("==================");
			System.out.print("문항선택 => ");
			choice = scan.nextInt();
			scan.nextLine();
			System.out.println("==================");
					
			switch (choice) {
			case 1: // 초기화하기
				mdo.init();
				break;

			case 2: // 설문항목 등록하기
				System.out.print("항목(장르) 입력: ");
				String input = scan.nextLine();
				mdo.insertMusic(input);

				break;

			case 3: // 설문참여하기
				List<MusicVo> ret = mdo.selectAll();
				if (ret == null) {
					System.out.println("데이터가 없습니다.");
					break;
				} else {
					for (MusicVo tmp : ret)
					System.out.println(tmp);
					System.out.println("");
				}
				
				System.out.println("선택항목에 없다면 추가로 장르를 등록하시오.");
				System.out.print("선택(option) ==>> ");
				choice = scan.nextInt();
				scan.nextLine();

				// 항목에 없는 번호 선택했을 때 무효표 처리하기

				System.out.println(choice + "번 선택!");
				mdo.updatevote(choice); // "VOTE"누적
				break;

			case 4: // 설문현황보기
				List<MusicVo> ret2 = mdo.selectAll();
				if (ret2 == null) {
					System.out.println("데이터가 없습니다.");
					break;
				} else {
					for (MusicVo tmp : ret2)
						System.out.println(tmp.voteView());
				}
				break;

			case 5: // 오타로 입력했을 경우 변경하기
				System.out.println(" 몇번의 항목을 변경하시겠습니까?");
				choice = scan.nextInt();
				System.out.println(" 다시 입력하세요!");
				String alter = scan.nextLine();
				//mdo.altermusic();;
		
				break;
			case 6: // 최종 투표 수 확인 후 종료하기
				System.out.println("프로그램 종료!\n");
				System.out.println("==================");
				System.out.println("총 투표수 : " + mdo.totalvote() + " 표");
				System.out.println("==================");
				break;
			}
			System.out.println();
		}
	}
}