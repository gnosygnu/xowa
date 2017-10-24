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
