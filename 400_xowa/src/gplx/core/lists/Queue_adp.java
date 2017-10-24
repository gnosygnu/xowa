/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.lists; import gplx.*; import gplx.core.*;
public class Queue_adp {
	private int count;
	private Queue_itm head;
	private Queue_itm tail;
	public int Count() {return count;}
	public boolean Is_empty() {return count == 0;}
	public void Enqueue(Object data) {
		Queue_itm item = new Queue_itm(data);
		if (head == null) {
			head = item;
		}
		if (tail != null) {
			tail.next = item;
		}
		tail = item;
		count++;
	}
	public Object Dequeue() {
		Object rv = Peek();
		head = head.next;
		count--;
		return rv;
	}
	public Object Peek() {
		if (head == null) throw Err_.new_wo_type("queue is empty");
		Queue_itm rv = head;
		return rv.Data();
	}
}
class Queue_itm {
	private final    Object data;
	public Queue_itm next;
	public Queue_itm(Object data) {
		this.data = data;
	}
	public Object Data() {return data;}
}
