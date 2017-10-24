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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.core.ios.*; import gplx.core.times.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_xml_parser_tst {
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		bldr = new Xob_bldr(app);
	}	private Xow_ns_mgr ns_mgr = Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.A7());
	@Test  public void Basic_docs_1() {
		Xowd_page_itm doc = doc_(1, "a", "a a", Date_1);
		fil = page_bldr.Add(doc).XtoByteStreamRdr();
		tst_parse(fil, doc, 0);
	}
	@Test  public void Basic_docs_2() {
		Xowd_page_itm doc1 = doc_(1, "a", "a a", Date_1);
		Xowd_page_itm doc2 = doc_(2, "b", "b b", Date_2);
		fil = page_bldr.Add_ary(doc1, doc2).XtoByteStreamRdr();
		int pos = tst_parse(fil, doc1, 0);
		tst_parse(fil, doc2, pos);
	}
	@Test  public void Basic_space() {
		Xowd_page_itm doc1 = doc_(1, "a_b", "abc", Date_1);
		fil = page_bldr.Add_ary(doc1).Upd("a_b", "a b").XtoByteStreamRdr();
		tst_parse(fil, doc1, 0);
	}
	@Test  public void Xml() {
		Xowd_page_itm doc = doc_(1, "a", "&quot;a &amp; b &lt;&gt; a | b&quot;", Date_1);
		fil = page_bldr.Add(doc).XtoByteStreamRdr();
		tst_parse(fil, doc.Text_(Bry_.new_a7("\"a & b <> a | b\"")), 0);
	}
	@Test  public void Tab() {
		Xowd_page_itm doc = doc_(1, "a", "a \t b", Date_1);
		fil = page_bldr.Add(doc).XtoByteStreamRdr();
		tst_parse(fil, doc.Text_(Bry_.new_a7("a &#09; b")), 0);
	}
	@Test  public void Tab_disable() {
		Xowd_page_itm doc = doc_(1, "a", "a \t b", Date_1);
		page_parser.Trie_tab_del_();
		fil = page_bldr.Add(doc).XtoByteStreamRdr();
		tst_parse(fil, doc.Text_(Bry_.new_a7("a \t b")), 0);
	}
	@Test  public void Cr_nl() {
		Xowd_page_itm doc = doc_(1, "a", "a \r\n b", Date_1);
		fil = page_bldr.Add(doc).XtoByteStreamRdr();
		tst_parse(fil, doc.Text_(Bry_.new_a7("a \n b")), 0);
	}
	@Test  public void Cr() {
		Xowd_page_itm doc = doc_(1, "a", "a \r b", Date_1);
		fil = page_bldr.Add(doc).XtoByteStreamRdr();
		tst_parse(fil, doc.Text_(Bry_.new_a7("a \n b")), 0);
	}
	@Test  public void Text_long() {
		String s = String_.Repeat("a", 1024);
		Xowd_page_itm doc = doc_(1, "a", s, Date_1);
		page_parser.Tag_len_max_(32);
		fil = page_bldr.Add(doc).XtoByteStreamRdr(512);
		tst_parse(fil, doc, 0);
	}
	@Test  public void Text_empty() {
		Xowd_page_itm doc = doc_(1, "a", "", Date_1);
		fil = page_bldr.Add(doc).Upd("<text></text>", "<text />").XtoByteStreamRdr();
		tst_parse(fil, doc, 0);
	}
	@Test  public void Text_frag() {
		Xowd_page_itm doc = doc_(1, "a", "a", Date_1);
		fil = page_bldr.Add(doc).Upd("<text>a</text>", "<text xml:space=\"preserve\">a</text>").XtoByteStreamRdr();
		tst_parse(fil, doc, 0);
	}
	@Test  public void Ns_file() {
		Xowd_page_itm doc = doc_(1, "File:a", "a", Date_1);
		Tfds.Eq(Xow_ns_.Tid__file, doc.Ns_id());
		Tfds.Eq("a", String_.new_u8(doc.Ttl_page_db()));
	}
	@Test  public void Ns_main() {
		Xowd_page_itm doc = doc_(1, "a", "a", Date_1);
		Tfds.Eq(Xow_ns_.Tid__main, doc.Ns_id());
		Tfds.Eq("a", String_.new_u8(doc.Ttl_page_db()));
	}
	@Test  public void Ns_main_book() {
		Xowd_page_itm doc = doc_(1, "Book", "a", Date_1);
		Tfds.Eq(Xow_ns_.Tid__main, doc.Ns_id());
		Tfds.Eq("Book", String_.new_u8(doc.Ttl_page_db()));
	}
	@Test  public void XmlEntities() {
		Xowd_page_itm orig = doc_(1, "A&amp;b", "a", Date_1);
		Xowd_page_itm actl = new Xowd_page_itm();
		fil = page_bldr.Add(orig).XtoByteStreamRdr();
		page_parser.Parse_page(actl, usr_dlg, fil, fil.Bfr(), 0, ns_mgr);
		Tfds.Eq("A&b", String_.new_u8(actl.Ttl_full_db()));
	}
	@Test  public void Root() {
		Xowd_page_itm doc = doc_(1, "a", "a", Date_1);
		page_bldr.Bfr().Add_str_a7("<root>\n");
		page_bldr.Add(doc);
		page_bldr.Bfr().Add_str_a7("</root>");
		fil = page_bldr.XtoByteStreamRdr();
		tst_parse(fil, doc, 0);
	}
	private static final    String Date_1 = "2012-01-01T01:01:01Z", Date_2 = "2012-02-02T02:02:02Z"; DateAdp_parser dateParser = DateAdp_parser.new_();
	Bry_bfr bfr = Bry_bfr_.New();
	Xob_xml_page_bldr page_bldr = new Xob_xml_page_bldr(); Io_buffer_rdr fil; Xob_xml_parser page_parser = new Xob_xml_parser(); Xob_bldr bldr;
	Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Test();
	int tst_parse(Io_buffer_rdr fil, Xowd_page_itm expd, int cur_pos) {
		Xowd_page_itm actl = new Xowd_page_itm();
		int rv = page_parser.Parse_page(actl, usr_dlg, fil, fil.Bfr(), cur_pos, ns_mgr);
		Tfds.Eq(expd.Id(), actl.Id(), "id");
		Tfds.Eq(String_.new_u8(expd.Ttl_full_db()), String_.new_u8(actl.Ttl_full_db()), "title");
		Tfds.Eq(String_.new_u8(expd.Text()), String_.new_u8(actl.Text()), "text");
		Tfds.Eq_date(expd.Modified_on(), actl.Modified_on(), "timestamp");
		return rv;
	}
	Xowd_page_itm doc_(int id, String title, String text, String date) {
		Xowd_page_itm rv = new Xowd_page_itm().Id_(id).Ttl_(Bry_.new_a7(title), ns_mgr).Text_(Bry_.new_a7(text));
		int[] modified_on = new int[7];
		dateParser.Parse_iso8651_like(modified_on, date);
		rv.Modified_on_(DateAdp_.seg_(modified_on));
		return rv;
	}
}
class Xob_xml_parser_fxt {
//		private final    Xob_xml_parser page_parser = new Xob_xml_parser();
//		public void Test__parse(Io_buffer_rdr fil, Xowd_page_itm expd, int cur_pos) {
//			Xowd_page_itm actl = new Xowd_page_itm();
//			int rv = page_parser.Parse_page(actl, usr_dlg, fil, fil.Bfr(), cur_pos, ns_mgr);
//			Tfds.Eq(expd.Id(), actl.Id(), "id");
//			Tfds.Eq(String_.new_u8(expd.Ttl_full_db()), String_.new_u8(actl.Ttl_full_db()), "title");
//			Tfds.Eq(String_.new_u8(expd.Text()), String_.new_u8(actl.Text()), "text");
//			Tfds.Eq_date(expd.Modified_on(), actl.Modified_on(), "timestamp");
//		}
}
