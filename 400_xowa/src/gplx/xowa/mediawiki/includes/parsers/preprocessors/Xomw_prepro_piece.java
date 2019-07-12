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
public class Xomw_prepro_piece {
	public final    byte[] open;					// Opening character (\n for heading)
	public final    byte[] close;                   // Matching closing char;
	public int count;                               // Number of opening characters found (number of "=" for heading)
	public final    boolean line_start;                // True if the open char appeared at the start of the input line; Not set for headings.
	public final    int start_pos;
	public List_adp parts = List_adp_.New();
	private final    XomwPPDPart part_factory;
	public Xomw_prepro_piece(XomwPPDPart part_factory, byte[] open, byte[] close, int count, int start_pos, boolean line_start) {
		this.part_factory = part_factory;
		this.open = open;
		this.close = close;
		this.count = count;
		this.start_pos = start_pos;
		this.line_start = line_start;
		parts.Add(part_factory.Make_new(""));
	}
	public void Parts__renew() {
		parts.Clear();
		this.Add_part(Bry_.Empty);
	}
	public XomwPPDPart Get_current_part() {
		return (XomwPPDPart)parts.Get_at(parts.Len() - 1);
	}
	public Xomw_prepro_accum Get_accum() {
		return Get_current_part().Accum();
	}
	public void Add_part(byte[] bry) {
		parts.Add(part_factory.Make_new(String_.new_u8(bry)));
	}
	public static final    byte[] Brack_bgn_bry = Bry_.new_a7("[");
	public void Set_flags(Xomw_prepro_flags flags) {
		int parts_len = parts.Len();
		boolean open_is_nl = Bry_.Eq(open, Byte_ascii.Nl_bry);
		boolean findPipe = !open_is_nl && !Bry_.Eq(open, Brack_bgn_bry);
		flags.findPipe = findPipe;
		flags.findEquals = findPipe && parts_len > 1 && ((XomwPPDPart)parts.Get_at(parts_len - 1)).eqpos != -1;
		flags.inHeading = open_is_nl;
	}
	// Get the output String that would result if the close is not found.
	public byte[] Break_syntax(Bry_bfr tmp_bfr, int opening_count) {
		byte[] rv = Bry_.Empty;
		if (Bry_.Eq(open, Byte_ascii.Nl_bry)) {
			rv = ((XomwPPDPart_DOM)parts.Get_at(0)).To_bry();
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
				XomwPPDPart part = (XomwPPDPart)parts.Get_at(i);
				if (first) {
					first = false;
				}
				else {
					tmp_bfr.Add_byte_pipe();
				}					
				tmp_bfr.Add(((XomwPPDPart_DOM)part).To_bry());
			}
			rv = tmp_bfr.To_bry_and_clear();
		}
		return rv;
	}
}
