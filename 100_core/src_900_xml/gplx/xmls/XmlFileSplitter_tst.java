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
package gplx.xmls; import gplx.*;
import org.junit.*;
import gplx.ios.*; import gplx.texts.*;
public class XmlFileSplitter_tst {
	@Before public void setup() {
		splitter = new XmlFileSplitter();
		Io_mgr.I.InitEngine_mem();
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
		Io_mgr.I.DeleteDirDeep(tmpDir);
		splitter.Opts().StatusFmt_(null).PartDir_(tmpDir);
		splitter.Opts().Namer().Ctor_io(tmpDir, "", "fil_{0}.xml", "000");
		Io_mgr.I.SaveFilStr(xmlFil, txt);
		splitter.Split(xmlFil);
		Io_url[] tmpFilAry = Io_mgr.I.QueryDir_fils(tmpDir);
		Tfds.Eq(expd.length, tmpFilAry.length);
		for (int i = 0; i < tmpFilAry.length; i++) {
			Io_url tmpFil = tmpFilAry[i];
			Tfds.Eq(expd[i], Io_mgr.I.LoadFilStr(tmpFil));
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
