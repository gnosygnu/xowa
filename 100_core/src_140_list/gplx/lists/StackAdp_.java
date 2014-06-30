/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.lists; import gplx.*;
public class StackAdp_ {
	public static StackAdp new_() {return new StackAdp_base();}
}
class StackAdp_base implements StackAdp {
	public Object Peek() {return Peek_base();}
	public Object Pop() {return Pop_base();}
	public void Push(Object obj) {Push_base(obj);}
	public ListAdp XtoList() {
		ListAdp list = ListAdp_.new_();
		for (Object obj : stack)
			list.Add(obj);
		// NOTE: dotnet traverses last to first; java: first to last 
		return list;
	}
	final java.util.Stack stack = new java.util.Stack();
	public StackAdp_base() {}
	public int Count() {return stack.size();}
	public void Clear() {stack.clear();}
	protected void Push_base(Object obj) {stack.push(obj);}
	protected Object Pop_base() {return stack.pop();}
	protected Object Peek_base() {return stack.peek();}
	public java.util.Iterator iterator() {return stack.iterator();}
}
