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
public class Xop_xnde_tag_regy {
	public ByteTrieMgr_slim XndeNames(int i) {
		if (nild) {Init(); nild = false;}
		switch (i) {
			case Xop_parser_.Parse_tid_tmpl:		return tagRegy_tmpl;
			case Xop_parser_.Parse_tid_page_tmpl:	return tagRegy_wiki_tmpl;
			case Xop_parser_.Parse_tid_page_wiki:	return tagRegy_wiki_main;
			default:								return tagRegy_wiki_tmpl; //throw Err_.unhandled(i);
		}
	}	boolean nild = true;
	public void Init() {
		Init_reg(tagRegy_tmpl		, FilterXtns(Xop_xnde_tag_.Ary, Xop_xnde_tag_.Tag_includeonly, Xop_xnde_tag_.Tag_noinclude, Xop_xnde_tag_.Tag_onlyinclude, Xop_xnde_tag_.Tag_nowiki));
		Init_reg(tagRegy_wiki_tmpl	, FilterXtns(Xop_xnde_tag_.Ary, Xop_xnde_tag_.Tag_includeonly, Xop_xnde_tag_.Tag_noinclude, Xop_xnde_tag_.Tag_onlyinclude, Xop_xnde_tag_.Tag_nowiki));
		Init_reg(tagRegy_wiki_main	, Xop_xnde_tag_.Ary);
	}
	Xop_xnde_tag[] FilterXtns(Xop_xnde_tag[] ary, Xop_xnde_tag... more) {
		ListAdp rv = ListAdp_.new_();
		for (Xop_xnde_tag itm : ary)
			if (itm.Xtn()) rv.Add(itm);
		for (Xop_xnde_tag itm : more)
			rv.Add(itm);
		return (Xop_xnde_tag[])rv.XtoAry(Xop_xnde_tag.class);
	}
	private void Init_reg(ByteTrieMgr_slim tagRegy, Xop_xnde_tag... ary) {
		for (Xop_xnde_tag tag : ary)
			tagRegy.Add(tag.Name_bry(), tag);
	}
	private ByteTrieMgr_slim
	  tagRegy_wiki_main = ByteTrieMgr_slim.ci_ascii_()	// NOTE:ci.ascii:MW_const.en; listed XML node names are en
	, tagRegy_wiki_tmpl = ByteTrieMgr_slim.ci_ascii_()
	, tagRegy_tmpl		= ByteTrieMgr_slim.ci_ascii_()
	;
}
