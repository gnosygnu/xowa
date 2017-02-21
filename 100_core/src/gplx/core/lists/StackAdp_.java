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
public class StackAdp_ {
	public static StackAdp new_() {return new StackAdp_base();}
}
class StackAdp_base implements StackAdp {
	public Object Peek() {return Peek_base();}
	public Object Pop() {return Pop_base();}
	public void Push(Object obj) {Push_base(obj);}
	public List_adp XtoList() {
		List_adp list = List_adp_.New();
		for (Object obj : stack)
			list.Add(obj);
		// NOTE: dotnet traverses last to first; java: first to last 
		return list;
	}
	final    java.util.Stack stack = new java.util.Stack();
	public StackAdp_base() {}
	public int Count() {return stack.size();}
	public void Clear() {stack.clear();}
	protected void Push_base(Object obj) {stack.push(obj);}
	protected Object Pop_base() {return stack.pop();}
	protected Object Peek_base() {return stack.peek();}
	public java.util.Iterator iterator() {return stack.iterator();}
}
