package application;

import java.util.Date;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.doublyLinkedLists;
import eg.edu.alexu.csd.datastructure.queue.cs77_56_91.QueuesLinkedList;
import eg.edu.alexu.csd.datastructure.stack.cs77.Stacks;

public class Filter implements IFilter {
	
	public String typeOfSearch;
   
	
	public Filter(String typeOfSearch) {
	  this.typeOfSearch = typeOfSearch;	
	}
	
	public  doublyLinkedLists BinSearch(doublyLinkedLists mails,Object x) {
		Sort sort = new Sort();
		sort.setTypeOfSort(this.typeOfSearch);  
		sort.iterativeQsort(mails);
		int first = 0;
		int last = 0;
		if(this.typeOfSearch == "Date") {
		  first = firstoccDate(mails,(Date)x);
		  if(first == -1) {
			return null;
		  }
		  last = lastoccDate(mails,(Date)x);
		}
		else {
		  first = firstocc(mails,(String)x);
		  if(first == -1){
		    return null;
		  }
		  last = lastocc(mails,(String)x);
		}
		return (doublyLinkedLists) mails.sublist(first, last);
	}
	
	public int firstocc(doublyLinkedLists mails,String x)
	{
		Stacks stack = new Stacks();
		stack.push(0);
		stack.push(mails.size()-1);
		int result=-1;
		while(!stack.isEmpty())
		{
			int right = (int) stack.pop();
			int left = (int) stack.pop();
			int mid = left + ((right - left)/2);
			String comp = null;
			if(typeOfSearch.equals("Subject")) {
				comp = ((Mail)mails.get(mid)).getSubject();
			}
			else if (typeOfSearch.equals("Sender")) {
				comp = ((Mail)mails.get(mid)).getSender();
			}
			if (x.equals(comp))
			{
				result = mid;
				stack.push(0);
				stack.push(mid);
			}
			else if((x.compareTo(comp)) < 0)
			{
				stack.push(0);
				stack.push(mid);
			}
			else if((x.compareTo(comp)) > 0) 
			{
				stack.push(mid+1);
				stack.push(right);
			}
			if(right==left&&right==mid)
			{
				break;
			}
			
		}
		return result; 
	}
	public int lastocc(doublyLinkedLists mails,String x)
	{
		Stacks stack = new Stacks();
		stack.push(0);
		stack.push(mails.size()-1);
		int result=-1;
		while(!stack.isEmpty())
		{
			int right = (int) stack.pop();
			int left = (int) stack.pop();
			int mid = left + ((right - left)/2);
			String comp = null;
			if((left>mails.size()-1)||(right>mails.size()-1))
			{
				break;
			}
			if(typeOfSearch.equals("Subject")) {
				comp = ((Mail)mails.get(mid)).getSubject();
			}
			else if (typeOfSearch.equals("Sender")) {
				comp = ((Mail)mails.get(mid)).getSender();
			}
			if (x.equals(comp))
			{
				result = mid;
				stack.push(mid+1);
				stack.push(mails.size()-1);
			}
			else if((x.compareTo(comp)) < 0)
			{
				if(result!=-1)
				{
					break;
				}
				stack.push(0);
				stack.push(mid);
			}
			else
			{
				stack.push(mid+1);
				stack.push(right);
			}
			
			
		}
		return result; 
	}
	
	public int firstoccDate(doublyLinkedLists mails,Date x)
	{
		Stacks stack = new Stacks();
		stack.push(0);
		stack.push(mails.size()-1);
		int result=-1;
		while(!stack.isEmpty())
		{
			int right = (int) stack.pop();
			int left = (int) stack.pop();
			int mid = left + ((right - left)/2);
			Date comp = null;
			comp = ((Mail)mails.get(mid)).getDate();

			if (x.compareTo(comp) == 0)
			{
				result = mid;
				stack.push(0);
				stack.push(mid);
			}
			else if((x.compareTo(comp)) > 0)
			{
				stack.push(0);
				stack.push(mid);
			}
			else if((x.compareTo(comp)) < 0) 
			{
				stack.push(mid+1);
				stack.push(right);
			}
			if(right==left&&right==mid)
			{
				break;
			}
			
		}
		return result; 
	}
	public int lastoccDate(doublyLinkedLists mails,Date date)
	{
		Stacks stack = new Stacks();
		stack.push(0);
		stack.push(mails.size()-1);
		int result=-1;
		while(!stack.isEmpty())
		{
			int right = (int) stack.pop();
			int left = (int) stack.pop();
			int mid = left + ((right - left)/2);
			Date comp = null;
			if((left>mails.size()-1)||(right>mails.size()-1))
			{
				break;
			}
			comp = ((Mail)mails.get(mid)).getDate();
	
			if (date.compareTo(comp) == 0)
			{
				result = mid;
				stack.push(mid+1);
				stack.push(mails.size()-1);
			}
			else if((date.compareTo(comp)) > 0)
			{
				if(result!=-1)
				{
					break;
				}
				stack.push(0);
				stack.push(mid);
			}
			else
			{
				stack.push(mid+1);
				stack.push(right);
			}
			
			
		}
		return result; 
	}

}