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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.FILE:Preprocessor_DOM
/**
* Stack class to help Preprocessor::preprocessToObj()
* @ingroup Parser
*/
public class XomwPPDStack {
	public final    List_adp stack = List_adp_.New();
	public Xomw_prepro_piece top;
	private final    Xomw_prepro_flags flags = new Xomw_prepro_flags();
	protected Xomw_prepro_accum root_accum;
	protected Xomw_prepro_accum accum;

	public XomwPPDStack(Xomw_prepro_accum prototype) {
		root_accum = prototype.Make_new();
		accum = root_accum;
	}
	public void Clear() {
		stack.Clear();
		accum.Clear();
		top = null;
	}
	public int Count() {return stack.Len();}

	public Xomw_prepro_accum Get_accum() {return accum;}
	public Xomw_prepro_accum Get_root_accum() {return root_accum;}

	public XomwPPDPart Get_current_part() {
		if (top == null) {
			return null;
		}
		else {
			return top.Get_current_part();
		}
	}

	public void Push(Xomw_prepro_piece item) {
		stack.Add(item);
		this.top = (Xomw_prepro_piece)stack.Get_at(stack.Len() - 1);			
		accum = top.Get_accum();
	}

	public Xomw_prepro_piece Pop() {
		int len = stack.Count();
		if (len == 0) {
			throw Err_.new_wo_type("XomwPPDStack: no elements remaining");
		}

		Xomw_prepro_piece rv = (Xomw_prepro_piece)stack.Get_at(len - 1);
		stack.Del_at(len - 1);
		len--;

		if (len > 0) {
			this.top = (Xomw_prepro_piece)stack.Get_at(stack.Len() - 1);			
			this.accum = top.Get_accum();
		} else {
			this.top = null;
			this.accum = root_accum;
		}
		return rv;
	}

	public void Add_part(byte[] bry) {
		top.Add_part(bry);
		accum = top.Get_accum();
	}

	public Xomw_prepro_flags Get_flags() {
		if (stack.Count() == 0) {
			flags.findEquals = false;
			flags.findPipe = false;
			flags.inHeading = false;
			return flags;
		}
		else {
			top.Set_flags(flags);
			return flags;
		}
	}
}
