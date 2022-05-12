package DatabaseProject;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		MusicDao mdo = new MusicDao();
		
		int num = 0;
		long choice = 0;
		boolean check = true;

		while (check) {
			System.out.println("==좋아하는 음악장르 설문조사==");
			System.out.println("1. 초기화하기");
			System.out.println("2. 음악장르 등록하기");
			System.out.println("3. 설문참여하기");
			System.out.println("4. 항목 이름변경하기");
			System.out.println("5. 설문현황보기");
			System.out.println("6. 최종 투표 수 확인(종료)하기!");
			System.out.println("");
			System.out.println("==================");
			System.out.print("문항선택 => ");
			try {
				num = scan.nextInt();
			}catch(InputMismatchException e) {
				scan = new Scanner(System.in);
				System.out.println("숫자를 입력하세요!");
				continue;
			}	
			scan.nextLine();
			System.out.println("==================");

			switch (num) {
			case 1: // 초기화하기
				mdo.init();
				break;

			case 2: // 설문항목 등록하기
				System.out.print("항목(장르) 입력: ");
				String input = scan.nextLine();
				mdo.insertMusic(input);

				break;

			case 3: // 설문참여하기
				List<MusicVo> ret3 = mdo.selectAll();
				if (ret3 == null) {
					System.out.println("데이터가 없습니다.(음악장르를 등록하시오).");
					break;
				} else {
					for (MusicVo tmp : ret3)
						System.out.println(tmp);
				}
				System.out.printf("%d.기타(ex:트로트, EDM, 힙합):", mdo.countNum() + 1);
				System.out.println("\n");
				
				System.out.println("선택항목에 없다면 추가로 장르를 등록하시오.");
				System.out.print("선택(option) ==>> ");
				choice = scan.nextInt();
				scan.nextLine();
				
				if(choice == mdo.countNum() + 1) {
					System.out.print("장르를 입력하세요 : ");
					String addmusic = scan.nextLine(); //새로운 장르 입력
					mdo.newmusic(addmusic);
				
				}else {
					System.out.println(choice + "번 선택!");
					mdo.updatevote(choice); // "VOTE"누적.
				}
				break;

			case 4: // 설문항목 변경하기
				List<MusicVo> ret4 = mdo.selectAll();
				if (ret4 == null) {
					System.out.println("데이터가 없습니다(음악장르를 등록하시오).");
					break;
				} else {
					for (MusicVo tmp : ret4)
						System.out.println(tmp);
					
					System.out.println("변경하고자 하는 번호를 고르시오. ");
					choice = scan.nextInt();
					scan.nextLine();
					
					System.out.println("변경될 새로운 장르를 적으시오.");	
					String alter = scan.nextLine();		
				
					mdo.altermusic(alter, choice);
				}
				break;
				
			case 5: // 설문현황보기
				List<MusicVo> ret5 = mdo.selectAll();
				if (ret5 == null) {
					System.out.println("데이터가 없습니다.(음악장르를 등록하시오).");
					break;
				} else {
					for (MusicVo tmp : ret5)
						System.out.println(tmp.voteView());
				}				
				break;

			case 6: // 최종 투표 수 확인 후 종료하기
				List<MusicVo> ret6 = mdo.selectAll();
				if (ret6 == null) {
					System.out.println("데이터가 없습니다.(음악장르를 등록하시오).");
					break;
				} else {
					for (MusicVo tmp : ret6)
						System.out.println(tmp.voteView());
				}	
				System.out.println("==================");
				System.out.println("총 투표수 : " + mdo.totalvote() + " 표");
				System.out.println("==================");
				System.out.println("프로그램 종료!\n");
				check = false;
				break;
			}
			System.out.println();
		}
	}
}