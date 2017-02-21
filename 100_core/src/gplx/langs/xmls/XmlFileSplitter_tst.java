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
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import org.junit.*;
import gplx.core.ios.*; import gplx.core.texts.*;
public class XmlFileSplitter_tst {
	@Before public void setup() {
		splitter = new XmlFileSplitter();
		Io_mgr.Instance.InitEngine_mem();
	} XmlFileSplitter splitter;
	@Test  public void FindMatchPos() {
		tst_FindMatchPos("abcde", "a", 0);
		tst_FindMatchPos("abcde", "b", 1);
		tst_FindMatchPos("abcde", "cd", 2);
		tst_FindMatchPos("abcde", "f", -1);
		tst_FindMatchPos("abcde", "fg", -1);
	}	void tst_FindMatchPos(String src, String find, int expd) {Tfds.Eq(expd, splitter.FindMatchPos(byte_(src), byteAry_(find)));}
	@Test  public void FindMatchPosRev() {
		tst_FindMatchPosRev("abcde", "a", 0);
		tst_FindMatchPosRev("abcde", "b", 1);
		tst_FindMatchPosRev("abcde", "cd", 2);
		tst_FindMatchPosRev("abcde", "f", -1);
		tst_FindMatchPosRev("abcde", "ef", -1);
		tst_FindMatchPosRev("abcde", "za", -1);
		tst_FindMatchPosRev("dbcde", "d", 3);
	}	void tst_FindMatchPosRev(String src, String find, int expd) {Tfds.Eq(expd, splitter.FindMatchPosRev(byte_(src), byteAry_(find)));}
	@Test  public void ExtractHdr() {
		tst_ExtractHdr("<a><b>", "<b", "<a>", "<b>");
	}
	@Test  public void Split() {
		splitter.Opts().FileSizeMax_(30).XmlNames_("<a").XmlEnd_("</root>");
		tst_Split
			( "<root><a id='1'/><a id='2'/><a id='3'/><a id='4'/><a id='5'/></root>"
			, "<root><a id='1'/><a id='2'/></root>"
			, "<root><a id='3'/><a id='4'/></root>"
			, "<root><a id='5'/></root>"
			);
		tst_Split
			( "<root><a id='1' name='long_text_that_will_force_next_read'/><a id='2'/></root>"
			, "<root><a id='1' name='long_text_that_will_force_next_read'/></root>"
			, "<root><a id='2'/></root>"
			);
	}
	void tst_Split(String txt, String... expd) {
		Io_url xmlFil = Io_url_.mem_fil_("mem/800_misc/txt.xml");
		Io_url tmpDir = xmlFil.OwnerDir().GenSubDir("temp_xml");
		Io_mgr.Instance.DeleteDirDeep(tmpDir);
		splitter.Opts().StatusFmt_(null).PartDir_(tmpDir);
		splitter.Opts().Namer().Ctor_io(tmpDir, "", "fil_{0}.xml", "000");
		Io_mgr.Instance.SaveFilStr(xmlFil, txt);
		splitter.Split(xmlFil);
		Io_url[] tmpFilAry = Io_mgr.Instance.QueryDir_fils(tmpDir);
		Tfds.Eq(expd.length, tmpFilAry.length);
		for (int i = 0; i < tmpFilAry.length; i++) {
			Io_url tmpFil = tmpFilAry[i];
			Tfds.Eq(expd[i], Io_mgr.Instance.LoadFilStr(tmpFil));
		}
	}
	byte[] byte_(String s) {return Bry_.new_u8(s);}
	byte[][] byteAry_(String s) {
		byte[][] rv = new byte[1][];
		rv[0] = Bry_.new_u8(s);
		return rv;
	}
	void tst_ExtractHdr(String src, String find, String expdHdr, String expdSrc) {
		splitter.Clear();
		byte[] srcAry = byte_(src);
		int findPos = splitter.FindMatchPos(srcAry, byteAry_(find));
		srcAry = splitter.SplitHdr(srcAry, findPos);
            Tfds.Eq(String_.new_u8(splitter.Hdr()), expdHdr);
		Tfds.Eq(String_.new_u8(srcAry), expdSrc);
	}
}
