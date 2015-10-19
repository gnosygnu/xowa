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
package gplx.stores; import gplx.*;
import org.junit.*;
public class GfoNdeRdr_read_tst {
	@Test  public void ReadInt() {
		rdr = rdr_(IntClassXtn.Instance, "id", 1);
		Tfds.Eq(rdr.ReadInt("id"), 1);
	}
	@Test  public void ReadIntOr() {
		rdr = rdr_(IntClassXtn.Instance, "id", 1);
		Tfds.Eq(rdr.ReadIntOr("id", -1), 1);
	}
	@Test  public void ReadIntElse_minus1() {
		rdr = rdr_(IntClassXtn.Instance, "id", null);
		Tfds.Eq(rdr.ReadIntOr("id", -1), -1);
	}
	@Test  public void ReadInt_parse() {
		rdr = rdr_(StringClassXtn.Instance, "id", "1");
		Tfds.Eq(rdr.ReadInt("id"), 1);
	}
	@Test  public void ReadIntElse_parse() {
		rdr = rdr_(StringClassXtn.Instance, "id", "2");
		Tfds.Eq(rdr.ReadIntOr("id", -1), 2);
	}
	GfoNdeRdr rdr_(ClassXtn type, String key, Object val) {	// makes rdr with one row and one val
		GfoFldList flds = GfoFldList_.new_().Add(key, type);
		GfoNde row = GfoNde_.vals_(flds, new Object[] {val});
		boolean parse = type == StringClassXtn.Instance;	// assumes type is either StringClassXtn or IntClassXtn
		return GfoNdeRdr_.leaf_(row, parse);
	}
	GfoNdeRdr rdr;
}
