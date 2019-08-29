package com.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto implements Serializable {
	
	private int seq;
	private int origin_seq;
	private String title;
	private String content1;
	private String content2;
	private String content3;
	private String filename;
	private String author;
	private Date wdate;	
	
	//가변길이 매개변수 생성자(insert new 용도)
	public NoticeDto(String title, String author, String filename, String...content) {
		super();
		this.title = title;
		this.author = author;
		this.filename = filename;
		this.content1 = content[0];
		System.out.println("매개변수길이 : " + content.length);
		if(content.length == 3 ) {
			if( content[2].length() > 1 ) this.content3 = content[2];			
		}else if(content.length == 2){
			if( content[1].length() > 1 ) this.content2 = content[1];
		}				
	}	
	
	//가변길이 매개변수 생성자(get list 용도)
	public NoticeDto(int seq, String title, String author, String filename, Date wdate, String...content) {
		super();
		this.seq = seq;
		this.title = title;
		this.author = author;
		this.filename = filename;
		this.wdate = wdate;
		this.content1 = content[0];
		if(content.length == 3 ) {
			if( content[2].length() > 1 ) this.content3 = content[2];			
		}else if(content.length == 2){
			if( content[1].length() > 1 ) this.content2 = content[1];
		}			
	}	
}
