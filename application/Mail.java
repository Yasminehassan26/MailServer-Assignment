package application;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.*;
import eg.edu.alexu.csd.datastructure.queue.cs77_56_91.QueuesLinkedList;
import java.nio.file.Path;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.file.Files;

public class Mail implements IMail, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sender;
	private String subject;
	private QueuesLinkedList receiver;
	private String body;
	private SinglyLinkedLists attachment;
	private String path;
	private int priority;
	private String status;
	private Date date;
	private int serialNum;
	private Date deletionDate;

	public Mail(String sender, String subject, QueuesLinkedList receiver, String body) {
		this.sender = sender;
		this.subject = subject;
		this.receiver = receiver;
		this.body = body;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setReceiver(QueuesLinkedList receiver) {
		this.receiver = receiver;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setAttachments(SinglyLinkedLists attachment) {
		this.attachment = attachment;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getSender() {
		return this.sender;
	}

	public String getSubject() {
		return this.subject;
	}

	public QueuesLinkedList getReceiver() {
		return this.receiver;
	}

	public String getBody() {
		return this.body;
	}

	public SinglyLinkedLists getAttachments() {
		return this.attachment;
	}

	public String getPath() {
		return this.path;
	}

	public int getPriority() {
		return this.priority;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}

	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	
}