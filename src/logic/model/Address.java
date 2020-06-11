package logic.model;

public class Address {
	
	private Double latitude;
    private Double longitude;
    private String addr;
	
	public Address(Double latitude, Double longitude, String addr) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setAddress(addr);
    }
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
		
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public void setAddress(String addr) {
		this.addr = addr;
	}
	
	public String getAddress() {
		return addr;
	}

}
