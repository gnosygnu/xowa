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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xobc_math_dir_list_tst {
	Xobc_math_dir_list_fxt fxt = new Xobc_math_dir_list_fxt();
	@Before public void init() {
		fxt.Reset().Root_dir_(Io_url_.mem_dir_("mem/xowa/file/math/")).Temp_dir_(Io_url_.mem_dir_("mem/xowa/temp/"));
	}
	@Test  public void Basic() {
		fxt.Create_fils("0/0/0", "000a.png", "000b.png");
		fxt.Create_fils("1/0/0", "100a.png", "100b.png");
		fxt.Create_fils("1/1/0", "110b.png", "110a.png");	// "unsorted"
		fxt.tst("0.txt", "000a", "000b");
		fxt.tst("1.txt", "100a", "100b", "110a", "110b");	// "sorted"; "combined"
	}
}
class Xobc_math_dir_list_fxt {
	public Xobc_math_dir_list_fxt Reset() {Io_mgr._.InitEngine_mem(); return this;}
	public Xobc_math_dir_list_fxt Root_dir_(Io_url v) {root_dir = v; return this;} Io_url root_dir;
	public Xobc_math_dir_list_fxt Temp_dir_(Io_url v) {temp_dir = v; return this;} Io_url temp_dir;
	public Xobc_math_dir_list_fxt Create_fils(String dir_rel_path, String... names) {
		Io_url dir = root_dir.GenSubDir(dir_rel_path);
		int names_len = names.length;
		for (int i = 0; i < names_len; i++) {
			Io_url fil = dir.GenSubFil(names[i]);
			Io_mgr._.SaveFilStr(fil, "");
		}
		return this;
	}
	public Xobc_math_dir_list_fxt tst(String fil_name, String... md5s) {
		Xobc_math_dir_manifest wkr = new Xobc_math_dir_manifest();
		wkr.Bld(root_dir, temp_dir);
		String fil_txt = Io_mgr._.LoadFilStr(temp_dir.GenSubFil(fil_name));
		String[] actl_names = String_.Split(fil_txt, '\n');
		Tfds.Eq_ary_str(md5s, actl_names);
		return this;
	}
}
class Xobc_math_dir_manifest {
	public void Bld(Io_url math_dir, Io_url gen_dir) {
		this.gen_dir = gen_dir;
		Bry_bfr dir_path = Bry_bfr.new_();
		Bld_dir(math_dir, dir_path, 0);
	}
	private void Bld_dir(Io_url dir, Bry_bfr dir_path, int depth) {
		Io_url[] subs = Io_mgr._.QueryDir_args(dir).DirInclude_().ExecAsUrlAry();
		Array_.Sort(subs); // NOTE: file system should have sorted these urls already, but sort again, just in case; sorting is "marginal" cost since it should be magnitudes faster than reading from the actual file system
		int subs_len = subs.length;
		for (int i = 0; i < subs_len; i++) {
			Io_url sub = subs[i];
			if (sub.Type_dir()) {
				byte[] name_bry = Bry_.new_ascii_(sub.NameOnly());
				if (name_bry.length != 1) throw Err_.new_fmt_("invalid math dir name; should be 1 char wide; {0}", dir.Raw());
				dir_path.Add_byte(name_bry[0]);
				Bld_dir(sub, dir_path, depth + 1);
				dir_path.Del_by_1();
			}
			else
				Write(sub);
		}
		if (depth == flush_depth) Flush(dir_path);
	}
	private void Write(Io_url fil) {
		byte[] md5 = Bry_.new_ascii_(fil.NameOnly());
		bfr.Add(md5).Add_byte_nl();
	}
	private void Flush(Bry_bfr dir_path) {
		Io_url fil = gen_dir.GenSubFil(dir_path.XtoStr() + ".txt");
		Io_mgr._.SaveFilBfr(fil, bfr);
	}
	Bry_bfr bfr = Bry_bfr.new_();
	Io_url gen_dir = null;
	int flush_depth = 1;
}