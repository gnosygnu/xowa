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
package gplx;
import org.junit.*;
public class Url_encoder_tst {
	@Before public void init() {fxt = new Url_encoder_fxt();} Url_encoder_fxt fxt;
	@Test  public void Id_nums() 			{fxt.Encoder_id().Test_encode_decode("0123456789", "0123456789");}
	@Test  public void Id_ltrs_lower() 		{fxt.Encoder_id().Test_encode_decode("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz");}
	@Test  public void Id_ltrs_upper() 		{fxt.Encoder_id().Test_encode_decode("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");}
	@Test  public void Id_syms() 			{fxt.Encoder_id().Test_encode("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~", ".21.22.23.24.25.26.27.28.29.2A.2B.2C-..2F:.3B.3C.3D.3E.3F.40.5B.5C.5D.5E_.60.7B.7C.7D.7E");}	// NOTE: not reversible since "." is encode_marker but not encoded
	@Test  public void Id_foreign() 		{fxt.Encoder_id().Test_encode_decode("aéb", "a.C3.A9b");}
	@Test  public void Id_space() 			{fxt.Encoder_id().Test_encode_decode("a b", "a_b");}
	@Test  public void Id_err() 			{
		byte[] raw = Bry_.new_ascii_("0%.jpg");
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		fxt.Encoder_id().Encoder().Decode(raw, 0, raw.length, tmp_bfr, false);
		Tfds.Eq("0%.jpg", tmp_bfr.XtoStrAndClear()); 
	}
	@Test  public void Id_nbsp() 			{fxt.Encoder_id().Test_encode("a&nbsp;b", "a.C2.A0b");}	// NOTE: not just .A0 (160) but utf8-encoded .C2.A0
	@Test  public void Url_syms() 			{fxt.Encoder_url().Test_encode_decode("!?^~", "%21%3F%5E%7E");}
	@Test  public void Url_foreign() 		{fxt.Encoder_url().Test_encode_decode("aéb", "a%C3%A9b");}
	@Test  public void Url_space() 			{fxt.Encoder_url().Test_encode_decode("a b", "a+b");}
	@Test  public void File_space() 		{
		fxt.Encoder_href().Test_encode("a b", "a_b");
//			fxt.Encoder_url().tst_decode("a_b", "a_b");
	}
	@Test  public void Href_special_and_anchor() { // PURPOSE: MediaWiki encodes with % for ttls, but . for anchors; REF:Title.php!(before-anchor)getLocalUrl;wfUrlencode (after-anchor)escapeFragmentForURL
		fxt.Encoder_href().Test_encode("^#^", "%5E#.5E");
		fxt.Encoder_href().Test_encode("A#", "A#");
		fxt.Encoder_href().tst_decode("%5E#.5E", "^#^");
	}
	@Test  public void Fsys_wnt() 		{
		fxt.Encoder_fsys_safe().Test_encode("Help:Options/HTML", "Help%3AOptions%2FHTML");
	}
	@Test  public void Invalid_url_decode() { // PURPOSE: check that invalid url decodings are rendered literally; DATE:2014-04-10
		fxt.Encoder_href().Test_encode("%GC", "%25GC");
	}
}
class Url_encoder_fxt {
	public Url_encoder Encoder() {return encoder;} Url_encoder encoder;
	public Url_encoder_fxt Encoder_id()			{encoder = Url_encoder.new_html_id_(); return this;}
	public Url_encoder_fxt Encoder_href()		{encoder = Url_encoder.new_html_href_mw_(); return this;}
	public Url_encoder_fxt Encoder_url()		{encoder = Url_encoder.new_http_url_(); return this;}
	public Url_encoder_fxt Encoder_fsys_safe()	{encoder = Url_encoder.new_fsys_wnt_(); return this;}
	public void Test_encode_decode(String raw, String encoded) {
		Test_encode(raw, encoded);
		tst_decode(encoded, raw);
	}	
	public void Test_encode(String raw, String expd) {
		byte[] bry = encoder.Encode(Bry_.new_utf8_(raw));
		Tfds.Eq(expd, String_.new_utf8_(bry));		
	}
	public void tst_decode(String raw, String expd) {
		byte[] bry = encoder.Decode(Bry_.new_utf8_(raw));
		Tfds.Eq(expd, String_.new_utf8_(bry));		
	}
}
