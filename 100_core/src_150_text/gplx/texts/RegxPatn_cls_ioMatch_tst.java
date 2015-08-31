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
package gplx.texts; import gplx.*;
import org.junit.*;
public class RegxPatn_cls_ioMatch_tst {
	@Test  public void SimpleMatches() {
		tst_Matches("file.cs", "file.cs", true);							// basic
		tst_Matches("file.cs", "file.cs.exe", false);						// fail: must match name precisely
		tst_Matches("file.cs", "tst_file.cs", false);						// fail: must match name precisely
	}
	@Test  public void Wildcard() {
		tst_Matches("*.cs", "file.cs", true);								// pass: before
		tst_Matches("file*", "file_valid.cs", true);						// pass: after
		tst_Matches("*.exe", "file.cs", false);								// fail: before
		tst_Matches("file*", "invalid_file.cs", false);						// fail: after
	}
	@Test  public void DoubleWildcard() {
		tst_Matches("*cs*", "file.cs", true);								// pass: after
		tst_Matches("*cs*", "csFile.exe", true);							// pass: before
		tst_Matches("*cs*", "file.cs.exe", true);							// pass: middle
		tst_Matches("*cs*", "file.exe", false);								// fail
	}
	@Test  public void Compound() {
		tst_Matches("*.cs|*.exe", "file.cs", true);							// pass: match first
		tst_Matches("*.cs|*.exe", "file.exe", true);						// pass: match second
		tst_Matches("*.cs|*.exe", "file.dll", false);						// fail: match neither
		tst_Matches("*.cs|*.exe", "file.cs.exe.dll", false);				// fail: match neither (though both are embedded)
	}
	@Test  public void Backslash() {
		tst_Matches("*\\bin\\*", "C:\\project\\bin\\", true);				// pass: dir
		tst_Matches("*\\bin\\*", "C:\\project\\bin\\file.dll", true);		// pass: fil
		tst_Matches("*\\bin\\*", "C:\\project\\binFiles\\", false);			// fail
	}
	@Test  public void MixedCase() {
		tst_Matches("file.cs", "file.cs", true);							// pass: same case
		tst_Matches("file.cs", "File.cS", true);							// pass: diff case
	}
	void tst_Matches(String regx, String raw, boolean expd) {
		RegxPatn_cls_ioMatch pattern = RegxPatn_cls_ioMatch_.parse(regx, false);
		boolean actl = pattern.Matches(raw);
		Tfds.Eq(expd, actl);			
	}
}
