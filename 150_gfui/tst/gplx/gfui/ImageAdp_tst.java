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
package gplx.gfui; import gplx.*;
import org.junit.*;
import gplx.ios.*;
import gplx.security.*;
public class ImageAdp_tst {
	@Before public void setup() {
		load = Tfds.RscDir.GenSubFil_nest("150_gfui", "imgs", "strawberry_java.bmp");	
	}	ImageAdp img; Io_url load;
	@Test  public void load_() {
		img = ImageAdp_.file_(load);
		Tfds.Eq(80, img.Width());
		Tfds.Eq(80, img.Height());
		Tfds.Eq("80,80", img.Size().toString());
		Tfds.Eq(img.Url(), load);
	}
	@Test  public void SaveAsBmp() {
		img = ImageAdp_.file_(load);
		Io_url save = load.GenNewNameOnly("strawberry_temp");
		DateAdp beforeModifiedTime = Io_mgr._.QueryFil(save).ModifiedTime();
		img.SaveAsBmp(save);
		DateAdp afterModifiedTime = Io_mgr._.QueryFil(save).ModifiedTime();
		Tfds.Eq_true(CompareAble_.Is_more(afterModifiedTime, beforeModifiedTime));

		String loadHash = HashAlgo_.Md5.CalcHash(ConsoleDlg_.Null, Io_mgr._.OpenStreamRead(load));
		String saveHash = HashAlgo_.Md5.CalcHash(ConsoleDlg_.Null, Io_mgr._.OpenStreamRead(save));
		Tfds.Eq(loadHash, saveHash);
	}
}
