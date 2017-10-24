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
package gplx.core.stores.xmls; import gplx.*; import gplx.core.*; import gplx.core.stores.*;
import org.junit.*;
public class XmlDataRdr_tst {
	@Test  public void Read() {
		DataRdr rdr = fx.rdr_("<title id=\"1\" name=\"first\" profiled=\"false\" />");
		Tfds.Eq(rdr.NameOfNode(), "title");
		Tfds.Eq(rdr.ReadStr("name"), "first");
		Tfds.Eq(rdr.ReadInt("id"), 1);
		Tfds.Eq(rdr.ReadBool("profiled"), false);
	}
	@Test  public void None() {
		DataRdr rdr = fx.rdr_
			(	"<root>"
			,		"<find/>"
			,	"</root>"
			);
		fx.tst_Subs_ByName(rdr, "no_nde", "no_atr");
	}
	@Test  public void One() {
		DataRdr rdr = fx.rdr_
			(	"<root>"
			,		"<find id=\"f0\" />"
			,	"</root>"
			);
		fx.tst_Subs_ByName(rdr, "find", "id", "f0");
	}
	@Test  public void One_IgnoreOthers() {
		DataRdr rdr = fx.rdr_
			(	"<root>"
			,		"<find id=\"f0\" />"
			,		"<skip id=\"s0\" />"
			,	"</root>"
			);
		fx.tst_Subs_ByName(rdr, "find", "id", "f0");
	}
	@Test  public void Many() {
		DataRdr rdr = fx.rdr_
			(	"<root>"
			,		"<find id=\"f0\" />"
			,		"<find id=\"f1\" />"
			,	"</root>"
			);
		fx.tst_Subs_ByName(rdr, "find", "id", "f0", "f1");
	}
	@Test  public void Nested() {
		DataRdr rdr = fx.rdr_
			(	"<root>"
			,		"<sub1>"
			,			"<find id=\"f0\" />"
			,			"<find id=\"f1\" />"
			,		"</sub1>"
			,	"</root>"
			);
		fx.tst_Subs_ByName(rdr, "sub1/find", "id", "f0", "f1");
	}
	@Test  public void Nested_IgnoreOthers() {
		DataRdr rdr = fx.rdr_
			(	"<root>"
			,		"<sub1>"
			,			"<find id=\"f0\" />"
			,			"<skip id=\"s0\" />"
			,		"</sub1>"
			,		"<sub1>"
			,			"<find id=\"f1\" />"	// NOTE: find across ndes
			,			"<skip id=\"s1\" />"
			,		"</sub1>"
			,	"</root>"
			);
		fx.tst_Subs_ByName(rdr, "sub1/find", "id", "f0", "f1");
	}
	XmlDataRdr_fxt fx = XmlDataRdr_fxt.new_();
}
class XmlDataRdr_fxt {
	public DataRdr rdr_(String... ary) {return XmlDataRdr_.text_(String_.Concat(ary));}
	public void tst_Subs_ByName(DataRdr rdr, String xpath, String key, String... expdAry) {
		DataRdr subRdr = rdr.Subs_byName(xpath);
		List_adp list = List_adp_.New();
		while (subRdr.MoveNextPeer())
			list.Add(subRdr.Read(key));

		String[] actlAry = list.To_str_ary();
		Tfds.Eq_ary(actlAry, expdAry);
	}
	public static XmlDataRdr_fxt new_() {return new XmlDataRdr_fxt();} XmlDataRdr_fxt() {}
}
