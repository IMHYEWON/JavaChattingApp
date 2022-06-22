package member;

public class IpMember {

	public String memId;
	public String memAddr;
	public int memPort;
	
	public IpMember(String memId, String memAddr, int memPort) {
		super();
		this.memId = memId;
		this.memAddr = memAddr;
		this.memPort = memPort;
	}
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemAddr() {
		return memAddr;
	}
	public void setMemAddr(String memAddr) {
		this.memAddr = memAddr;
	}
	public int getMemPort() {
		return memPort;
	}
	public void setMemPort(int memPort) {
		this.memPort = memPort;
	}
	
	
	
	
}
