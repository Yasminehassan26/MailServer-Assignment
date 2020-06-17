package application;

import java.util.Date;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.doublyLinkedLists;
import eg.edu.alexu.csd.datastructure.stack.cs77.Stacks;

public class Sort implements ISort{

	private  String typeOfSort;

	public void iterativeQsort(doublyLinkedLists arrs) {
    	Stacks stack = new Stacks();
        stack.push(0);
        stack.push(arrs.size());

        while (!stack.isEmpty()) {
            int end = (int) stack.pop();
            int start = (int) stack.pop();
            if (end - start < 2) {
                continue;
            }
            int p = start + ((end - start) / 2);
            if(typeOfSort.equals("Subject")) {
               p = partitionSub(arrs, p, start, end);
            }
            else if (typeOfSort.equals("Sender")) {
               p = partitionSender(arrs, p, start, end);
            }
            else if (typeOfSort.equals("Date")) {
               p = partitionDate(arrs, p, start, end);
            }

            stack.push((p + 1));
            stack.push(end);

            stack.push(start);
            stack.push(p);

        }
    }
    
	 private  int partitionSender(doublyLinkedLists input, int position, int start, int end) {
    	 int l = start;
         int h = end - 2;
         Mail mail = (Mail)input.get(position);
         String piv = mail.getSender();
         swap(input, position, end - 1);
         Mail mail1;
     	 Mail mail2;

         while (l < h) {
         	 mail1 = (Mail)input.get(l);
         	 mail2 = (Mail)input.get(h);
             if (mail1.getSender().compareTo(piv) < 0) 
             {
                 l++;
             } else if (mail2.getSender().compareTo(piv) >= 0) {
                 h--;
             } else {
                 swap(input, l, h);
             }
         }
         int idx = h;
         mail2 = (Mail)input.get(h);
         if (mail2.getSender().compareTo(piv) < 0) {
             idx++;
         }
         swap(input, end - 1, idx);
         return idx;
    }
	 private int partitionSub(doublyLinkedLists input, int position, int start, int end) {
	        int l = start;
	        int h = end - 2;
	        Mail mail = (Mail)input.get(position);
	        String piv = mail.getSubject();
	        swap(input, position, end - 1);
	        Mail mail1;
	    	Mail mail2;

	        while (l < h) {
	        	 mail1 = (Mail)input.get(l);
	        	 mail2 = (Mail)input.get(h);
	            if (mail1.getSubject().compareTo(piv) < 0) 
	            {
	                l++;
	            } else if (mail2.getSubject().compareTo(piv) >= 0) {
	                h--;
	            } else {
	                swap(input, l, h);
	            }
	        }
	        int idx = h;
	        mail2 = (Mail)input.get(h);
	        if (mail2.getSubject().compareTo(piv) < 0) {
	            idx++;
	        }
	        swap(input, end - 1, idx);
	        return idx;
	    }

	    private int partitionDate(doublyLinkedLists input, int position, int start, int end) {
	        int l = start;
	        int h = end - 2;
	        Mail mail = (Mail)input.get(position);
	        Date piv = mail.getDate();
	        swap(input, position, end - 1);
	        Mail mail1;
	    	Mail mail2;

	        while (l < h) {
	        	 mail1 = (Mail)input.get(l);
	        	 mail2 = (Mail)input.get(h);
	            if (mail1.getDate().compareTo(piv) > 0) {
	                l++;
	            } 
	            else if (mail2.getDate().compareTo(piv) <= 0) {
	                h--;
	            }
	            else {
	                swap(input, l, h);
	            }
	        }
	        int idx = h;
	        mail2 = (Mail)input.get(h);
	        if (mail2.getDate().compareTo(piv) > 0) {
	            idx++;
	        }
	        swap(input, end - 1, idx);
	        return idx;
	    }

	     /*
	     * @param arr - array on which swap will happen
	     * @param i
	     * @param j
	     */
	    private void swap(doublyLinkedLists arr, int i, int j) {
	        Mail temp = (Mail) arr.get(i);
	        arr.set(i,arr.get(j)) ;
	        arr.set(j, temp);
	    }

		public String getTypeOfSort() {
			return typeOfSort;
		}

		public  void setTypeOfSort(String typeOfSort) {
			this.typeOfSort = typeOfSort;
		}
	
		
		public doublyLinkedLists sortPriority(doublyLinkedLists mails) {
			PriorityQueue q = new PriorityQueue();
			for(int i = 0; i<mails.size();i++) {
				Mail m = (Mail) mails.get(i);
			  if(m.getPriority() != 0) {
				q.insert(m,m.getPriority());
			  }
			}
			doublyLinkedLists sorted = new doublyLinkedLists();
			for(int i = 0; i < q.size();i++) {
				sorted.add(q.removeMin());
			}
			return sorted;
		}
	
	
}