package DatabaseProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// 2차 Dao 수정본
public class MusicDao2 {
		private JdbcTemplate jdbcTemplate;
		
		public MusicDao2() {
			jdbcTemplate = JdbcTemplate.getInstance();
		}
		// 등록하기
		public void insertMusic(String input) {
			boolean ret = false;
			Connection conn = null;
			PreparedStatement pstmt = null; // 쿼리 전송 결과 가져오기
			 // long number, String musictype, long vote
			String sql = "insert into \"MUSIC_VOTE\" values (\"M_V\".nextval, ?, 0)";
			try {
				conn = jdbcTemplate.getConnection();

				pstmt = conn.prepareStatement(sql); // 쿼리 템플릿 준비
				pstmt.setString(1, input);
				pstmt.executeUpdate(); // 쿼리 전송!
				System.out.println("등록되었습니다.");

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		//목록보기
		public List<MusicVo> selectAll() {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			List<MusicVo> ls = new ArrayList<>();
			
			String sql = "select \"NUMBER\", \"MUSIC_TYPE\", \"VOTE\" from \"MUSIC_VOTE\" order by \"NUMBER\" ASC";
			try {
				conn = jdbcTemplate.getConnection();
				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery(); // 쿼리 전송!
				while (rs.next()) {
					MusicVo tmp = new MusicVo(rs.getLong(1),
							rs.getString(2),
							rs.getLong(3));
					ls.add(tmp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return (ls.size() == 0) ? null : ls;
		}
		// 표 누적
		public boolean updatevote(int number) {
			boolean ret = false;
			Connection conn = null;
			PreparedStatement pstmt = null; // 쿼리 전송 결과 가져오기
			
			//NUMBER의 ? 이라는 값을 가진 VOTE열의 행에 1을 더해줌("VOTE" 누적)
			String sql = "UPDATE \"MUSIC_VOTE\" SET \"VOTE\" = \"VOTE\" + 1 WHERE \"NUMBER\" = ?";
			
			try {
				conn = jdbcTemplate.getConnection();

				pstmt = conn.prepareStatement(sql); // 쿼리 템플릿 준비
				pstmt.setObject(1, number); // VOTE 누적

				int result = pstmt.executeUpdate(); // 쿼리 전송!
				System.out.println("설문완료!!");
				ret = true;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return ret;
		}
		// 초기화
		public void init() {
			Connection conn = null;
			PreparedStatement pstmt = null;
			String[] init = new String[] {
					"TRUNCATE TABLE \"MUSIC_VOTE\"",
					"DROP SEQUENCE \"M_V\"",
					"CREATE SEQUENCE \"M_V\" NOCACHE"};
			
			try {
				conn = jdbcTemplate.getConnection();
					for(int i = 0; i < init.length; i++) {
					pstmt = conn.prepareStatement(init[i]); //배열에 접근 쿼리 템플릿 준비
					int result = pstmt.executeUpdate(); // 쿼리 전송!	
					}
				
				System.out.println("초기화 되었습니다.");
		
			} catch (SQLException e) {
				System.out.println("에러발생.");
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		// 변경하기
		public void altermusic(String input, int number) {
	
			Connection conn = null;
			PreparedStatement pstmt = null; // 쿼리 전송 결과 가져오기
			
			String sql = "update \"MUSIC_VOTE\" set \"MUSIC_TYPE\" = ? where \"NUMBER\" = ?";
			try {
				conn = jdbcTemplate.getConnection();

				pstmt = conn.prepareStatement(sql); // 쿼리 템플릿 준비
				pstmt.setString(1, input);
				pstmt.setLong(2, number);
				
				pstmt.executeUpdate(); // 쿼리 전송!
				System.out.println("항목이 변경 되었습니다.");
				
			} catch (SQLException e) {
				System.out.println("에러발생.");
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}

		// 변경하기
				public void altermusic2(MusicVo mvo) {
			
					Connection conn = null;
					PreparedStatement pstmt = null; // 쿼리 전송 결과 가져오기
					
					String sql = "update \"MUSIC_VOTE\" set \"MUSIC_TYPE\" = ? where \"NUMBER\" = ?";
					try {
						conn = jdbcTemplate.getConnection();

						pstmt = conn.prepareStatement(sql); // 쿼리 템플릿 준비
						pstmt.setString(1, mvo.getMusictype());
						pstmt.setLong(2, mvo.getNumber());
						
						pstmt.executeUpdate(); // 쿼리 전송!
						System.out.println("항목이 변경 되었습니다.");
						
					} catch (SQLException e) {
						System.out.println("에러발생.");
						e.printStackTrace();
					} finally {
						if (pstmt != null) {
							try {
								pstmt.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if (conn != null) {
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
			}
		// 총 투표수 누적
		public int totalvote()  {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int totalvt = 0; //총 투표수
			
			// 테이블 "MUSIC_VOTE"의 VOTE 합계 수(몇명이 투표했는지)
			String sql ="SELECT SUM(\"VOTE\") FROM \"MUSIC_VOTE\"";
			
			try {
				conn = jdbcTemplate.getConnection();
				pstmt = conn.prepareStatement(sql); // 쿼리 템플릿 준비
							
				rs = pstmt.executeQuery(); // 쿼리 전송!
				while (rs.next()) {
					totalvt = rs.getInt(1);	
				}
				return totalvt;
			} catch (SQLException e) {
				System.out.println("에러발생.");
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return totalvt;
		}
}
