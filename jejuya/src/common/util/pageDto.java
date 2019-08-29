package common.util;

public class pageDto {

	
	private int startnum;
	private int lastnum;
	private int pagignum;
	
	@Override
	public String toString() {
		return "pageDto [startnum=" + startnum + ", lastnum=" + lastnum + ", pagignum=" + pagignum + "]";
	}
	public int getStartnum() {
		return startnum;
	}
	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}
	public int getLastnum() {
		return lastnum;
	}
	public void setLastnum(int lastnum) {
		this.lastnum = lastnum;
	}
	public int getPagignum() {
		return pagignum;
	}
	public void setPagignum(int pagignum) {
		this.pagignum = pagignum;
	}
	public pageDto(int startnum, int lastnum, int pagignum) {
		super();
		this.startnum = startnum;
		this.lastnum = lastnum;
		this.pagignum = pagignum;
	}
}
