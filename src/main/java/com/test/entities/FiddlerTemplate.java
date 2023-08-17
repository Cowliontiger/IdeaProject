package com.test.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name = "testcase")
public class FiddlerTemplate {
	// # Result Protocol Host URL Body Caching Content-Type RequestMethod Head
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;


//	@Column(name = "testtime")
//       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
////       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "UTC")
////       @Type(type="datetime")
//	public String testTime;


	@Column(name = "caseid")
	public String caseId = "";

	@Column(name = "result")
	public String result = "";
	@Column(name = "protocol")
	public String protocol = "";
	@Column(name = "Host")
	public String Host = "";
	@Column(name = "url")
	public String url = "";
	@Column(name = "body")
	public String body = "";
	@Column(name = "caching")
	public String caching = "";
	@Column(name = "contentType")
	public String contentType = "";
	@Column(name = "requestMethod")
	public String requestMethod = "";
	@Column(name = "head")
	public String head = "";
	@Column(name = "fullUrl")
	public String fullUrl = "";
	@Column(name = "responseData")
	public String responseData = "";
	@Column(name = "finalTestResult")
	public String finalTestResult = "";

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getFinalTestResult() {
		return finalTestResult;
	}

	public void setFinalTestResult(String finalTestResult) {
		this.finalTestResult = finalTestResult;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return Host;
	}

	public void setHost(String host) {
		Host = host;
	}

	public String getCaching() {
		return caching;
	}

	public void setCaching(String caching) {
		this.caching = caching;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
//
//	public String getTestTime() {
//		return testTime;
//	}
//
//	public void setTestTime(String testTime) {
//		this.testTime = testTime;
//	}
}
