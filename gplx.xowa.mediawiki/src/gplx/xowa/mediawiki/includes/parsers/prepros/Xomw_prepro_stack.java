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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
class Xomw_prepro_stack {
	public List_adp stack = List_adp_.New();
	public Xomw_prepro_piece top;
	private Bry_bfr root_accum = Bry_bfr_.New(), accum;
	private final    Xomw_prepro_flags flags = new Xomw_prepro_flags();

	public Xomw_prepro_stack() {
		accum = root_accum;
	}
	public void Clear() {
		stack.Clear();
		accum.Clear();
		top = null;
	}
	public int Count() {return stack.Len();}
	public Bry_bfr Get_accum() {return accum;}
	public Bry_bfr Get_root_accum() {return root_accum;}

	public Xomw_prepro_part Get_current_part() {
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
			throw Err_.new_wo_type("Xomw_prepro_stack: no elements remaining");
		}

		Xomw_prepro_piece rv = (Xomw_prepro_piece)stack.Get_at(len - 1);
		stack.Del_at(len - 1);
		len--;

		if (len > 0) {
			this.top = (Xomw_prepro_piece)stack.Get_at(stack.Len() - 1);			
			accum = top.Get_accum();
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
			flags.Find_eq = false;
			flags.Find_pipe = false;
			flags.In_heading = false;
			return flags;
		}
		else {
			top.Set_flags(flags);
			return flags;
		}
	}
}
class Xomw_prepro_flags {
	public boolean Find_pipe;
	public boolean Find_eq;
	public boolean In_heading;
}
class Xomw_prepro_piece {
	public final    byte[] open;					// Opening character (\n for heading)
	public final    byte[] close;                   // Matching closing char;
	public int count;                               // Number of opening characters found (number of "=" for heading)
	public final    boolean line_start;                // True if the open char appeared at the start of the input line; Not set for headings.
	public final    int start_pos;
	public List_adp parts = List_adp_.New();
	public Xomw_prepro_piece(byte[] open, byte[] close, int count, int start_pos, boolean line_start) {
		this.open = open;
		this.close = close;
		this.count = count;
		this.start_pos = start_pos;
		this.line_start = line_start;
		parts.Add(new Xomw_prepro_part(Bry_.Empty));
	}
	public void Parts__renew() {
		parts.Clear();
		this.Add_part(Bry_.Empty);
	}
	public Xomw_prepro_part Get_current_part() {
		return (Xomw_prepro_part)parts.Get_at(parts.Len() - 1);
	}
	public Bry_bfr Get_accum() {
		return Get_current_part().bfr;
	}
	public void Add_part(byte[] bry) {
		parts.Add(new Xomw_prepro_part(bry));
	}
	public static final    byte[] Brack_bgn_bry = Bry_.new_a7("[");
	public void Set_flags(Xomw_prepro_flags flags) {
		int parts_len = parts.Len();
		boolean open_is_nl = Bry_.Eq(open, Byte_ascii.Nl_bry);
		boolean find_pipe = !open_is_nl && !Bry_.Eq(open, Brack_bgn_bry);
		flags.Find_pipe = find_pipe;
		flags.Find_eq = find_pipe && parts_len > 1 && ((Xomw_prepro_part)parts.Get_at(parts_len - 1)).Eqpos != -1;
		flags.In_heading = open_is_nl;
	}
	// Get the output String that would result if the close is not found.
	public byte[] Break_syntax(Bry_bfr tmp_bfr, int opening_count) {
		byte[] rv = Bry_.Empty;
		if (Bry_.Eq(open, Byte_ascii.Nl_bry)) {
			rv = ((Xomw_prepro_part)parts.Get_at(0)).bfr.To_bry();
		}
		else {
			if (opening_count == -1) {
				opening_count = count;
			}
			tmp_bfr.Add(Bry_.Repeat_bry(open, opening_count));

			// concat parts with "|"
			boolean first = true;
			int len = parts.Len();
			for (int i = 0; i < len; i++) {
				Xomw_prepro_part part = (Xomw_prepro_part)parts.Get_at(i);
				if (first) {
					first = false;
				}
				else {
					tmp_bfr.Add_byte_pipe();
				}
				tmp_bfr.Add(part.bfr.To_bry());
			}
			rv = tmp_bfr.To_bry_and_clear();
		}
		return rv;
	}
}
class Xomw_prepro_part {
	public Xomw_prepro_part(byte[] bry) {
		bfr.Add(bry);
	}
	public final    Bry_bfr bfr = Bry_bfr_.New();
	public int Eqpos = -1;
	public int comment_end = -1;
	public int visual_end = -1;
}
