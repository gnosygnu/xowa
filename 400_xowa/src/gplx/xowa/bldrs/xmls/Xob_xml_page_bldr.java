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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.ios.*;
public class Xob_xml_page_bldr {
	public byte[] Xto_bry() {return bfr.XtoAryAndClear();}
	public Io_buffer_rdr XtoByteStreamRdr() {return XtoByteStreamRdr(Io_mgr.Len_kb);}
	public Io_buffer_rdr XtoByteStreamRdr(int bfr_len) {
		Io_url url = Io_url_.mem_fil_("mem/byteStreamRdr.txt");
		Io_mgr._.SaveFilBry(url, bfr.XtoAryAndClear());
		return Io_buffer_rdr.new_(gplx.ios.Io_stream_rdr_.file_(url), bfr_len);
	}
	public Bry_bfr Bfr() {return bfr;} Bry_bfr bfr = Bry_bfr.new_();
	public Xob_xml_page_bldr Upd(String find, String repl) {
		String all = bfr.XtoStrAndClear();
		all = String_.Replace(all, find, repl);
		bfr.Add_str(all);
		return this;
	}
	public Xob_xml_page_bldr Add_ary(Xodb_page... ary) {
		for (Xodb_page doc : ary)
			Add(doc);
		return this;
	}
	public Xob_xml_page_bldr Add(Xodb_page doc) {
		bfr.Add(Indent_2).Add(Xob_xml_parser_.Bry_page_bgn).Add_byte_nl();
		bfr.Add(Indent_4).Add(Xob_xml_parser_.Bry_title_bgn).Add(doc.Ttl_w_ns()).Add(Xob_xml_parser_.Bry_title_end).Add_byte_nl();
		bfr.Add(Indent_4).Add(Xob_xml_parser_.Bry_id_bgn).Add_int_variable(doc.Id()).Add(Xob_xml_parser_.Bry_id_end).Add_byte_nl();
		bfr.Add(Indent_4).Add(Xob_xml_parser_.Bry_redirect_bgn_frag).Add(Nde_inline).Add_byte_nl();
		bfr.Add(Indent_4).Add(Xob_xml_parser_.Bry_revision_bgn).Add_byte_nl();
		bfr.Add(Indent_6).Add(Xob_xml_parser_.Bry_id_bgn).Add_int_variable(Revision_id).Add(Xob_xml_parser_.Bry_id_end).Add_byte_nl();
		bfr.Add(Indent_6).Add(Xob_xml_parser_.Bry_timestamp_bgn).Add_dte(doc.Modified_on()).Add(Xob_xml_parser_.Bry_timestamp_end).Add_byte_nl();
		bfr.Add(Indent_6).Add(Xob_xml_parser_.Bry_contributor_bgn).Add_byte_nl();
		bfr.Add(Indent_8).Add(Xob_xml_parser_.Bry_username_bgn).Add(Contributor_username).Add(Xob_xml_parser_.Bry_username_end).Add_byte_nl();
		bfr.Add(Indent_8).Add(Xob_xml_parser_.Bry_id_bgn).Add_int_variable(Contributor_id).Add(Xob_xml_parser_.Bry_id_end).Add_byte_nl();
		bfr.Add(Indent_6).Add(Xob_xml_parser_.Bry_contributor_end).Add_byte_nl();
		bfr.Add(Indent_6).Add(Xob_xml_parser_.Bry_minor_bgn_frag).Add(Nde_inline).Add_byte_nl();
		bfr.Add(Indent_6).Add(Xob_xml_parser_.Bry_comment_bgn).Add(Revision_comment).Add(Xob_xml_parser_.Bry_comment_end).Add_byte_nl();
		bfr.Add(Indent_6).Add(Xob_xml_parser_.Bry_text_bgn).Add(doc.Text()).Add(Xob_xml_parser_.Bry_text_end).Add_byte_nl();
		bfr.Add(Indent_4).Add(Xob_xml_parser_.Bry_revision_end).Add_byte_nl();
		bfr.Add(Indent_2).Add(Xob_xml_parser_.Bry_page_end).Add_byte_nl();
		return this;
	}
	private static final byte[] Nde_inline = Bry_.new_ascii_(" />"), Indent_2 = Bry_.Repeat_space(2), Indent_4 = Bry_.Repeat_space(4), Indent_6 = Bry_.Repeat_space(6), Indent_8 = Bry_.Repeat_space(8);
	private static final int Revision_id = 1234, Contributor_id = 9876;
	private static final byte[] Contributor_username = Bry_.new_ascii_("contributor_username"), Revision_comment = Bry_.new_ascii_("revision_comment");
}
/*
  <page>
    <title>AccessibleComputing</title>
    <id>10</id>
    <redirect />
    <revision>
      <id>381202555</id>
      <timestamp>2010-08-26T22:38:36Z</timestamp>
      <contributor>
		<username>OlEnglish</username>
		<id>7181920</id>
      </contributor>
      <minor />
      <comment>[[Help:Reverting|Reverted]] edits by [[Special:Contributions/76.28.186.133|76.28.186.133]] ([[User talk:76.28.186.133|talk]]) to last version by Gurch</comment>
      <text xml:space="preserve">#REDIRECT [[Computer accessibility]] {{R from CamelCase}}</text>
    </revision>
  </page>
*/
