package DatabaseProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicDao {
	private JdbcTemplate jdbcTemplate;

	public MusicDao() {
		jdbcTemplate = JdbcTemplate.getInstance();
	}
	
	// 1번 초기화
		public void init() {
			Connection conn = null;
			PreparedStatement pstmt = null;
			String[] init = new String[] { "TRUNCATE TABLE \"MUSIC_VOTE\"", "DROP SEQUENCE \"M_V\"",
					"CREATE SEQUENCE \"M_V\" NOCACHE" };

			try {
				conn = jdbcTemplate.getConnection();
				for (int i = 0; i < init.length; i++) {
					pstmt = conn.prepareStatement(init[i]); // 배열에 접근 쿼리 템플릿 준비
					pstmt.executeUpdate();
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

	// 2번 음악장르 등록하기
	public void insertMusic(String input) {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into \"MUSIC_VOTE\" values (\"M_V\".nextval, ?, 0)";
		try {
			conn = jdbcTemplate.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			pstmt.executeUpdate();
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

	// 목록보기
	public List<MusicVo> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<MusicVo> ls = new ArrayList<>();

		String sql = "select \"NUMBER\", \"MUSIC_TYPE\", \"VOTE\" from \"MUSIC_VOTE\" order by \"NUMBER\" ASC";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				MusicVo tmp = new MusicVo(rs.getLong(1), rs.getString(2), rs.getLong(3));
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
	
	// 현재 목록에서 추가로 등록
		public int countNum() {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int number = -1;

			// 테이블 "MUSIC_VOTE"의 현재 NUMBER 수 체크
			String sql = "SELECT COUNT(\"NUMBER\") FROM MUSIC_VOTE";
			try {
				conn = jdbcTemplate.getConnection();
				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery();
				while (rs.next()) {
					number = rs.getInt(1);
				}
				return number;
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

			return number;
		}
		// 3번 추가 음악장르 표 누적
		public void newmusic(String addmusic) {

			Connection conn = null;
			PreparedStatement pstmt = null;
			// SEQUENCE 활용, 추가하는 장르에 1표 자동누적
			String sql = "INSERT INTO MUSIC_VOTE VALUES(M_V.NEXTVAL, ? , 1)";
			try {
				conn = jdbcTemplate.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, addmusic);

				pstmt.executeUpdate();
				System.out.println("등록되었습니다.");

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
	// 표 누적
	public boolean updatevote(long number) {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE \"MUSIC_VOTE\" SET \"VOTE\" = \"VOTE\" + 1 WHERE \"NUMBER\" = ?";

		try {
			conn = jdbcTemplate.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, number); // VOTE 누적

			pstmt.executeUpdate();
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
	// 4번 설문항목 변경하기
	public void altermusic(String input, long number) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update \"MUSIC_VOTE\" set \"MUSIC_TYPE\" = ? where \"NUMBER\" = ?";
		try {
			conn = jdbcTemplate.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			pstmt.setLong(2, number);

			pstmt.executeUpdate();
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

	// 6번 총 투표수 집계
	public int totalvote() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalvt = 0; // 총 투표수

		// 테이블 "MUSIC_VOTE"의 VOTE 합계 수(집계)
		String sql = "SELECT SUM(\"VOTE\") FROM \"MUSIC_VOTE\"";

		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
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
