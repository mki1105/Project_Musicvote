package DatabaseProject;
//테이블 "MUSIC"
//번호, 음악장르 
public class MusicVo2 {
	private long number; // "NUMBER" 
	private String musictype; // "MUSIC_TYPE"
 	private long vote; // "VOTE"
 	
 
	public MusicVo2(long number, String musictype, long vote) {
		super();
		this.number = number;
		this.musictype = musictype;
		this.vote = vote;
	}

	public MusicVo2() {
		super();
	
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getMusictype() {
		return musictype;
	}
	public void setMusictype(String musictype) {
		this.musictype = musictype;
	}
	public long getVote() {
		return vote;
	}
	public void setVote(long vote) {
		this.vote = vote;
	}

	@Override
	public String toString() {
		return number + "." + musictype + " ===> " + vote + " 표";
	}
}
