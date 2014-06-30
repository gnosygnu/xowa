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
package gplx.xowa; import gplx.*;
public class Xop_xnde_tag {
	public Xop_xnde_tag(int id, String name_str) {	// NOTE: should only be used by Xop_xnde_tag_
		this.id = id;
		this.name_bry = Bry_.new_ascii_(name_str);
		this.name_str = name_str;
		name_len = name_bry.length;
		xtn_end_tag = Bry_.Add(Xop_xnde_tag_.XtnEndTag_bgn, name_bry);	// always force endtag; needed for <noinclude>
		xtn_end_tag_tmp = new byte[xtn_end_tag.length]; Array_.Copy(xtn_end_tag, xtn_end_tag_tmp);
	}
	public int Id() {return id;} public Xop_xnde_tag Id_(int v) {id = v; return this;} private int id;
	public byte[] Name_bry() {return name_bry;} private byte[] name_bry;
	public String Name_str() {return name_str;} private String name_str;
	public int Name_len() {return name_len;} private int name_len;
	public boolean Xtn() {return xtn;} public Xop_xnde_tag Xtn_() {xtn = true; return this;} private boolean xtn;
	public byte[] XtnEndTag() {return xtn_end_tag;} private byte[] xtn_end_tag;
	public byte[] XtnEndTag_tmp() {return xtn_end_tag_tmp;} private byte[] xtn_end_tag_tmp;
	public int BgnNdeMode() {return bgnNdeMode;} private int bgnNdeMode = Xop_xnde_tag_.BgnNdeMode_normal;
	public Xop_xnde_tag BgnNdeMode_inline_() {bgnNdeMode = Xop_xnde_tag_.BgnNdeMode_inline; return this;}
	public int EndNdeMode() {return endNdeMode;} private int endNdeMode = Xop_xnde_tag_.EndNdeMode_normal;
	public Xop_xnde_tag EndNdeMode_inline_() {endNdeMode = Xop_xnde_tag_.EndNdeMode_inline; return this;}
	public Xop_xnde_tag EndNdeMode_escape_() {endNdeMode = Xop_xnde_tag_.EndNdeMode_escape; return this;}
	public boolean SingleOnly() {return singleOnly;} public Xop_xnde_tag SingleOnly_() {singleOnly = true; return this;} private boolean singleOnly;
	public boolean TblSub() {return tblSub;} public Xop_xnde_tag TblSub_() {tblSub = true; return this;} private boolean tblSub;
	public boolean Nest() {return nest;} public Xop_xnde_tag Nest_() {nest = true; return this;} private boolean nest;
	public boolean Restricted() {return restricted;} public Xop_xnde_tag Restricted_() {restricted = true; return this;} private boolean restricted;
	public boolean NoInline() {return noInline;} public Xop_xnde_tag NoInline_() {noInline = true; return this;} private boolean noInline;
	public boolean Inline_by_backslash() {return inline_by_backslash;} public Xop_xnde_tag Inline_by_backslash_() {inline_by_backslash = true; return this;} private boolean inline_by_backslash;
	public boolean Section() {return section;} public Xop_xnde_tag Section_() {section = true; return this;} private boolean section;
	public boolean Repeat_ends() {return repeat_ends;} public Xop_xnde_tag Repeat_ends_() {repeat_ends = true; return this;} private boolean repeat_ends;
	public boolean Repeat_mids() {return repeat_mids;} public Xop_xnde_tag Repeat_mids_() {repeat_mids = true; return this;} private boolean repeat_mids;
	public boolean Empty_ignored() {return empty_ignored;} public Xop_xnde_tag Empty_ignored_() {empty_ignored = true; return this;} private boolean empty_ignored;
	public boolean Raw() {return raw;} public Xop_xnde_tag Raw_() {raw = true; return this;} private boolean raw;
	public static final byte Block_noop = 0, Block_bgn = 1, Block_end = 2;
	public byte Block_open() {return block_open;} private byte block_open = Block_noop;
	public byte Block_close() {return block_close;} private byte block_close = Block_noop;
	public Xop_xnde_tag Block_open_bgn_() {block_open = Block_bgn; return this;} public Xop_xnde_tag Block_open_end_() {block_open = Block_end; return this;}
	public Xop_xnde_tag Block_close_bgn_() {block_close = Block_bgn; return this;} public Xop_xnde_tag Block_close_end_() {block_close = Block_end; return this;}
	public boolean Xtn_auto_close() {return xtn_auto_close;} public Xop_xnde_tag Xtn_auto_close_() {xtn_auto_close = true; return this;} private boolean xtn_auto_close;
	public boolean Ignore_empty() {return ignore_empty;} public Xop_xnde_tag Ignore_empty_() {ignore_empty = true; return this;} private boolean ignore_empty;
	public boolean Xtn_skips_template_args() {return xtn_skips_template_args;} public Xop_xnde_tag Xtn_skips_template_args_() {xtn_skips_template_args = true; return this;} private boolean xtn_skips_template_args;
}
