package DatabaseProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusictypeDao {
		private JdbcTemplate jdbcTemplate;

		public MusictypeDao() {
			jdbcTemplate = JdbcTemplate.getInstance();
		}
		//등록, 카운트
		public List<MusictypeVo> addmusic() {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			List<MusictypeVo> ls = new ArrayList<>();

			String sql = "select \"NUMBER\", \"NEW_MUSIC\" from \"NEWMUSICTYPE\" order by \"NUMBER\" ASC";
			try {
				conn = jdbcTemplate.getConnection();
				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery(); // 쿼리 전송!
				while (rs.next()) {
					MusictypeVo tmp = new MusictypeVo(rs.getLong(1),
							rs.getString(2));
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
		
		// 기타 입력해서 행 추가하기
		public boolean newmusic(String newmusic) {
			boolean ret = false;
			Connection conn = null;
			PreparedStatement pstmt = null; // 쿼리 전송 결과 가져오기
			
			// NUMBER의 ? 이라는 값을 가진 NEW_MUSIC컬럼의 행에 1을 더해줌.
			String sql = "UPDATE \"NEWMUSICTYPE\" SET \"NEW_MUSIC\" = \"NEW_MUSIC\" + 1 WHERE \"NUMBER\" = ?";
			//String sql = "UPDATE \"NEWMUSICTYPE\" SET \"NUMBER\" = \"NUMBER\" + 1 WHERE \"NEW_MUSIC\" = ?";
			try {
				conn = jdbcTemplate.getConnection();
				pstmt = conn.prepareStatement(sql); // 쿼리 템플릿 준비
				pstmt.setObject(1, newmusic);
	
				int result = pstmt.executeUpdate(); // 쿼리 전송!
				System.out.println("등록되었습니다.");
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
		
		//삭제하기 - (select에서 사용하는 where조건 형태 활용) /where 필수
		public boolean deleteMusic(MusictypeVo mtv) {
			boolean ret = false;
			Connection conn = null;
			PreparedStatement pstmt = null; // 쿼리 전송 결과 가져오기
			
			String sql = "delete from \"NEWMUSICTYPE\" where \"NUMBER\" = ?";
			try {
				conn = jdbcTemplate.getConnection();

				pstmt = conn.prepareStatement(sql); // 쿼리 템플릿 준비
				pstmt.setLong(1, mtv.getNumber()); // 바인딩 파라미터에 값 설정
				
				int result = pstmt.executeUpdate(); // 쿼리 전송!
				System.out.println(result + "행이 삭제 되었습니다.");
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
}
