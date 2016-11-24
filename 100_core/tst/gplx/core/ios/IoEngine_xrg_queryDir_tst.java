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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import org.junit.*;
public class IoEngine_xrg_queryDir_tst {
	@Before public void setup() {
		engine = IoEngine_.Mem_init_();
	}	IoEngine engine; Io_url[] ary;
	@Test  public void Basic() {
		ary = save_text_(fil_("fil1.txt"));

		tst_ExecPathAry(finder_(), ary);
	}
	@Test  public void FilPath() {
		ary = save_text_(fil_("fil1.txt"), fil_("fil2.jpg"), fil_("fil3.txt"));
		
		tst_ExecPathAry(finder_(), ary);						// default: all files
		tst_ExecPathAry(finder_().FilPath_("*.txt")				// findPattern of *.txt
			, fil_("fil1.txt"), fil_("fil3.txt"));
	}
	@Test  public void Recur() {
		ary = save_text_(fil_("fil1.txt"), fil_("dirA", "fil1A.jpg"));

		tst_ExecPathAry(finder_(), fil_("fil1.txt"));			// default: no recursion
		tst_ExecPathAry(finder_().Recur_(), ary);				// recurse
	}
	@Test  public void DirPattern() {
		save_text_(fil_("fil1.txt"), fil_("dirA", "fil1A.jpg"));

		tst_ExecPathAry(finder_(), fil_("fil1.txt"));			// default: files only
		tst_ExecPathAry(finder_().DirInclude_()					// include dirs; NOTE: fil1A not returned b/c Recur_ is not true
			, dir_("dirA"), fil_("fil1.txt"));
	}
	@Test  public void Sort_by() {
		save_text_(fil_("fil2a.txt"), fil_("fil1.txt"));

		tst_ExecPathAry(finder_()								// default: sortByAscOrder
			, fil_("fil1.txt"), fil_("fil2a.txt"));
	}
	IoEngine_xrg_queryDir finder_() {return IoEngine_xrg_queryDir.new_(Io_url_.mem_dir_("mem/root"));}// NOTE: not in setup b/c finder must be newed several times inside test method
	Io_url fil_(String... ary) {return Io_url_.mem_dir_("mem/root").GenSubFil_nest(ary);}
	Io_url dir_(String... ary) {return Io_url_.mem_dir_("mem/root").GenSubDir_nest(ary);}
	
	Io_url[] save_text_(Io_url... ary) {
		for (Io_url url : ary)
			Io_mgr.Instance.SaveFilStr(url, url.Raw());
		return ary;
	}
	void tst_ExecPathAry(IoEngine_xrg_queryDir finder, Io_url... expd) {Tfds.Eq_ary(expd, finder.ExecAsUrlAry());}
}
