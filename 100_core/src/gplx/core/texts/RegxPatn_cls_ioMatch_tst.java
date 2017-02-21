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
package gplx.core.texts; import gplx.*; import gplx.core.*;
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
