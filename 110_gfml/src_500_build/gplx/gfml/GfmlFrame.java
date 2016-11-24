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
package gplx.gfml; import gplx.*;
import gplx.core.lists.*; /*StackAdp*/
public interface GfmlFrame {
	GfmlLxr Lxr();							// each frame has only one lxr
	int FrameType();
	GfmlObjList WaitingTkns();
	void Build_end(GfmlBldr bldr, GfmlFrame ownerFrame);
	GfmlFrame MakeNew(GfmlLxr newLxr);
}
class GfmlFrameStack {
	public int Count() {return stack.Count();}
	public void Push(GfmlFrame frame) {stack.Push(frame);}
	public GfmlFrame Pop() {return (GfmlFrame)stack.Pop();}
	public GfmlFrame Peek() {return (GfmlFrame)stack.Peek();}
	public void Clear() {stack.Clear();}
	StackAdp stack = StackAdp_.new_();
        public static GfmlFrameStack new_() {return new GfmlFrameStack();} GfmlFrameStack() {}
}
