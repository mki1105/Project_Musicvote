package DatabaseProject;

// 기타 직접 입력하는 클래스 "NEWMUSICTYPE"
public class MusictypeVo {
	private long number; // "NUMBER"
	private String newmusic; // "NEW_MUSIC"
	
	public MusictypeVo() {
		super();
	
	}

	public MusictypeVo(long number, String newmusic) {
		super();
		this.number = number;
		this.newmusic = newmusic;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getNewmusic() {
		return newmusic;
	}

	public void setNewmusic(String newmusic) {
		this.newmusic = newmusic;
	}

	@Override
	public String toString() {
		return "MusictypeVo [number=" + number + ", newmusic=" + newmusic + "]";
	}


}

