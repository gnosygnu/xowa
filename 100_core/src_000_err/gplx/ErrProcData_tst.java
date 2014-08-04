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
public class ErrProcData_tst {
	@Test  public void parse_() {
				tst_parse_("gplx._tst.Err__tst.RdrLoad(MethodData_tst.java:1)"
			, ErrProcData.new_()
			.SignatureRaw_("gplx._tst.Err__tst.RdrLoad")
			.SourceFileRaw_("MethodData_tst.java")
			.SourceLine_(1)
			.IdeAddress_("(MethodData_tst.java:1)")
			);
			}
	@Test  public void parse_ary_() {
		String stackTrace = "";
		try {ThrowException();} catch (Exception exc) {stackTrace = Err_.StackTrace_lang(exc);}
		ErrProcData[] ary = ErrProcData.parse_ary_(stackTrace);
		Tfds.Eq(2, Array_.Len(ary));
				Tfds.Eq("gplx.ErrProcData_tst.ThrowException", ary[0].SignatureRaw());
		Tfds.Eq("gplx.ErrProcData_tst.parse_ary_", ary[1].SignatureRaw());
			}
	Exception ThrowException() {
		throw new RuntimeException("msg");	
	}
	void tst_parse_(String raw, ErrProcData expd) {
		ErrProcData actl = ErrProcData.parse_(raw);
		Tfds.Eq(expd.SignatureRaw(), actl.SignatureRaw());
		Tfds.Eq(expd.SourceFileRaw(), actl.SourceFileRaw());
		Tfds.Eq(expd.SourceLine(), actl.SourceLine());
		Tfds.Eq(expd.IdeAddress(), actl.IdeAddress());
	}
}
