package socktuator.dto;

import java.util.Map;

import org.springframework.util.MimeType;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Request {

	//TODO: Consider making Request generic with a type parameter that
	// reflects the type of Response expected from a request.

	String op;
	Map<String, Object> params;
	
	private String mimeType;
	
	public Request() {}
	public Request(String op, Map<String, Object>  params) {
		this.op = op;
		this.params = params;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	@Override
	public String toString() {
		try {
			return SharedObjectMapper.get().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Request withMimeType(MimeType mimeType) {
		setMimeType(mimeType.toString());
		return this;
	}
}